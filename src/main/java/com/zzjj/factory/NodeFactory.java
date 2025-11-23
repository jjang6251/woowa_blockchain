package com.zzjj.factory;

import com.zzjj.domain.BlockChain;
import com.zzjj.domain.NetWork;
import com.zzjj.domain.Node;
import java.util.UUID;

public class NodeFactory {
    private final BlockChain blockChain;
    private final NetWork netWork;

    public NodeFactory(BlockChain blockChain, NetWork netWork) {
        this.blockChain = blockChain;
        this.netWork = netWork;
    }

    public Node createNode(String nodeId) {
        String privateKey = UUID.randomUUID().toString();
        Node node = new Node(nodeId, privateKey, blockChain, netWork);
        netWork.register(node);
        return node;
    }
}
