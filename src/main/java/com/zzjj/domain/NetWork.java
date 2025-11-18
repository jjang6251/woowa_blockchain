package com.zzjj.domain;

import java.util.ArrayList;
import java.util.List;

public class NetWork {
    private final List<Node> nodes = new ArrayList<>();

    public void register(Node node) {
        nodes.add(node);
    }

    public void broadCastTransasction(Transaction tx, Node from) {
        for (Node n : nodes) {
            n.receiveTransaction(tx);
        }
    }

    public void broadCastBlock(Block block, Node from) {
        for (Node node : nodes) {
            node.receiveBlock(block);
        }
    }
}
