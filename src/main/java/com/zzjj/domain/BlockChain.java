package com.zzjj.domain;

import java.util.ArrayList;
import java.util.List;

public class BlockChain {
    private final List<Block> chain = new ArrayList<>();
    private final int difficulty;

    public BlockChain(int difficulty) {
        this.difficulty = difficulty;
        chain.add(createGenesis());
    }

    private Block createGenesis() {
        List<String> data = List.of("Genesis");
        String merkle = Block.calculateMerkleRoot(data);
        long nonce = 0;
        return new Block(0, System.currentTimeMillis(), "0", merkle, nonce, data);
    }

    public synchronized Block getLastBlock() {
        return chain.getLast();
    }

    public synchronized boolean addBlock(Block newBlock) {
        Block lastBlock = getLastBlock();
        if (!newBlock.isValid(lastBlock, difficulty)) {
            System.out.println("[BlockChain] 블록 거부: index=" + newBlock.getIndex());
            return false;
        }

        chain.add(newBlock);
        System.out.println("[BlockChain] 블록 추가됨: index=" + newBlock.getIndex()
                + " | 길이 = " + chain.size()
                + "   " + visualizeChainLength()
        );
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

    private String visualizeChainLength() {
        int len = chain.size();
        return "|".concat("-".repeat(Math.max(0, len * 3 - 3)));
    }
}
