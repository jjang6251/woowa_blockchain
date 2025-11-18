package com.zzjj.domain;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class Block {
    //헤더
    private final int index;
    private final long timestamp;
    private final String previousHash;
    private final String merkleRoot;
    private final long nonce;
    private final String hash;
    //바디
    private final List<String> dataList;

    public Block(int index, long timestamp, String previousHash, String merkleRoot, long nonce,
                 List<String> dataList) {
        this.index = index;
        this.timestamp = timestamp;
        this.previousHash = previousHash;
        this.merkleRoot = merkleRoot;
        this.nonce = nonce;
        this.hash = calculateHash(index, timestamp, previousHash, merkleRoot, nonce);
        this.dataList = dataList;
    }

    public int getIndex() {
        return index;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public String getMerkleRoot() {
        return merkleRoot;
    }

    public long getNonce() {
        return nonce;
    }

    public String getHash() {
        return hash;
    }

    public List<String> getDataList() {
        return dataList;
    }

    public static String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String calculateMerkleRoot(List<String> leaves) {
        if (leaves == null || leaves.isEmpty()) {
            return sha256("");
        }
        List<String> layer = new ArrayList<>(leaves);
        for (int i = 0; i < layer.size(); i++) {
            layer.set(i, sha256(layer.get(i)));
        }
        while (layer.size() > 1) {
            List<String> next = new ArrayList<>();
            for (int i = 0; i < layer.size(); i += 2) {
                String left = layer.get(i);
                String right = (i + 1 < layer.size()) ? layer.get(i + 1) : left;
                next.add(sha256(left + right));
            }
            layer = next;
        }
        return layer.get(0);
    }

    public static String calculateHash(int index, long timestamp, String previousHash,
                                       String merkleRoot, long nonce) {
        String input = index + "|" + timestamp + "|" + previousHash + "|" + merkleRoot + "|" + nonce;
        return sha256(input);
    }

    public boolean isValid(Block prev, int difficulty) {
        if (prev != null) {
            if (this.index != prev.index + 1) {
                return false;
            }
            if (!this.previousHash.equals(prev.hash)) {
                return false;
            }
        }
        String targetPrefix = "0".repeat(Math.max(0, difficulty));
        String checkHash = calculateHash(this.index, this.timestamp, this.previousHash, this.merkleRoot, this.nonce);
        if (!checkHash.equals(this.hash)) {
            return false;
        }
        return this.hash.startsWith(targetPrefix);
    }

    @Override
    public String toString() {
        return "Block{" + index + ", prev=" + previousHash.substring(0, Math.min(8, previousHash.length()))
                + ", merkle=" + merkleRoot.substring(0, Math.min(8, merkleRoot.length()))
                + ", nonce=" + nonce + ", hash=" + hash.substring(0, 8)
                + ", txs=" + dataList.size() + "}";
    }
}
