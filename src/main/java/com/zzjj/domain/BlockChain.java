package com.zzjj.domain;

import java.util.ArrayList;
import java.util.List;

public class BlockChain {
    private final List<Block> chain = new ArrayList<>();
    private final int difficulty;

    public BlockChain(int difficulty) {
        this.difficulty = difficulty;
    }

    private Block createGenesis() {
        List<String> data = List.of("Genesis");
        String merkle = Block.calculateMerkleRoot(data);
        long nonce = 0;
        String hash = Block.calculateHash(0, System.currentTimeMillis(), "0", merkle, nonce);
        return new Block(0, System.currentTimeMillis(), "0", merkle, hash, nonce, data);
    }

    public synchronized Block getLastBlock() {
        return chain.getLast();
    }

    public synchronized boolean addBlock(Block newBlock) {
        Block lastBlock = getLastBlock();
        if (!newBlock.isValid(lastBlock, difficulty)) {
            return false;
        }
        chain.add(newBlock);
        return true;
    }

    public synchronized boolean isChainValid() {
        for (int i = 1; i < chain.size(); i++) {
            Block prev = chain.get(i - 1);
            Block cur = chain.get(i);
            if (!cur.isValid(prev, difficulty)) {
                return false;
            }
        }
        return true;
    }

    public synchronized List<Block> getChainCopy() {
        return new ArrayList<>(chain);
    }
}
