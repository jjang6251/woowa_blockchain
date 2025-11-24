package com.zzjj.controller;

import com.zzjj.domain.BlockChain;
import com.zzjj.domain.NetWork;
import com.zzjj.domain.Node;
import com.zzjj.domain.Transaction;
import com.zzjj.factory.NodeFactory;
import com.zzjj.service.MiningService;
import com.zzjj.view.View;
import java.util.ArrayList;
import java.util.List;

public class BlockChainController {
    private final MiningService miningService;
    private final View view;

    public BlockChainController(MiningService miningService, View view) {
        this.miningService = miningService;
        this.view = view;
    }

    public void start() {
        int blockChainDifficulty = view.blockChainDifficulty();
        int nodeCount = view.nodeNum();
        BlockChain chain = new BlockChain(blockChainDifficulty); // 난이도 2 정도
        NetWork netWork = new NetWork();
        NodeFactory nodeFactory = new NodeFactory(chain, netWork);
        List<Node> nodes = new ArrayList<>();

        for (int i = 0; i < nodeCount; i++) {
            String nodeId = view.nodeName(i);
            Node node = nodeFactory.createNode(nodeId);
            nodes.add(node);
            netWork.register(node);
        }

        if (nodes.size() >= 2) {
            Node from = nodes.get(0);
            Node to = nodes.get(1);
            Transaction tx = new Transaction(from.getNodeId(), to.getNodeId(), 1.0, "sig");
            netWork.broadCastTransasction(tx, from);
        }

        for (Node node : nodes) {
            miningService.startMining(node, blockChainDifficulty);
        }

        // 메인 스레드가 바로 종료되지 않게 잠깐 대기
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
