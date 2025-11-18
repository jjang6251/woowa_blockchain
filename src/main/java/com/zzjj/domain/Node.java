package com.zzjj.domain;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private final String nodeId;
    private final String privateKey;
    private final BlockChain blockChain;
    private final List<Transaction> mempool = new ArrayList<>();
    private final NetWork netWork;

    public Node(String nodeId, String privateKey, BlockChain blockChain, NetWork netWork) {
        this.nodeId = nodeId;
        this.privateKey = privateKey;
        this.blockChain = blockChain;
        this.netWork = netWork;
    }

    public String getNodeId() {
        return nodeId;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public BlockChain getBlockchain() {
        return blockChain;
    }

    public String sign(String payload) {
        return Block.sha256(payload + "|" + privateKey);
    }

    public void createAndBroadCastTx(String to, double amount) {
        String payload = nodeId + "->" + to + ":" + amount;
        String signature = sign(payload);
        Transaction tx = new Transaction(nodeId, to, amount, signature);
        netWork.broadCastTransasction(tx, this);
    }

    public synchronized void receiveTransaction(Transaction tx) {
        if (validateTransaction(tx)) {
            mempool.add(tx);
        } else {
            System.out.println(nodeId + " REJECTED TX: " + tx);
        }
    }

    public boolean validateTransaction(Transaction tx) {
        String payload = tx.getFrom() + "->" + tx.getTo() + ":" + tx.getAmount();
        String expectedSig = Block.sha256(payload + "|" + tx.getFrom());
        return tx.getSignature() != null && !tx.getSignature().isEmpty();
    }

    public synchronized void receiveBlock(Block block) {
        boolean accepted = blockChain.addBlock(block);
        if (accepted) {
            List<String> txIds = block.getDataList();
            mempool.removeIf(tx -> txIds.contains(tx.compactRepresentation()));
            System.out.println(nodeId + " ACCEPTED BLOCK: " + block);
        } else {
            System.out.println(nodeId + " REJECTED BLOCK: " + block);
        }
    }

    public Block mineBlock(int maxTx, int difficulty) {
        List<Transaction> toInclude;
        synchronized (this) {
            toInclude = new ArrayList<>(mempool.subList(0, Math.min(maxTx, mempool.size())));
        }
        List<String> compact = toInclude.stream().map(Transaction::compactRepresentation).toList();
        String merkle = Block.calculateMerkleRoot(compact);
        Block last = blockChain.getLastBlock();
        int newIndex = last.getIndex() + 1;
        long nonce = 0;
        String hash;
        String targetPrefix = "0".repeat(Math.max(0, difficulty));
        long start = System.currentTimeMillis();

        while (true) {
            hash = Block.calculateHash(newIndex, System.currentTimeMillis(), last.getHash(), merkle, nonce);
            if (hash.startsWith(targetPrefix)) break;
            nonce++;
        }
        Block newBlock = new Block(newIndex, System.currentTimeMillis(), last.getHash(), merkle, nonce, compact);
        netWork.broadCastBlock(newBlock, this);
        return newBlock;
    }

    public void printMempool() {
        System.out.println("Mempool of " + nodeId + ":");
        mempool.forEach(tx -> System.out.println(" " + tx.compactRepresentation()));
    }
}

