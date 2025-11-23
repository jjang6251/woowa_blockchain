package com.zzjj.controller;

import com.zzjj.domain.BlockChain;
import com.zzjj.domain.NetWork;
import com.zzjj.domain.Node;
import com.zzjj.domain.Transaction;
import com.zzjj.factory.NodeFactory;
import com.zzjj.service.MiningService;

public class BlockChainController {
    private final MiningService miningService;

    public BlockChainController(MiningService miningService) {
        this.miningService = miningService;
    }

    public void start() {
        BlockChain chain = new BlockChain(5); // 난이도 2 정도
        NetWork netWork = new NetWork();
        NodeFactory nodeFactory = new NodeFactory(chain, netWork);

        Node alice = nodeFactory.createNode("Alice");
        Node bob = nodeFactory.createNode("Bob");
        Node carol = nodeFactory.createNode("Carol");

        netWork.register(alice);
        netWork.register(bob);
        netWork.register(carol);

        //트랜잭션 하나 전파
        Transaction tx = new Transaction("Alice", "Bob", 1.0, "sig");
        netWork.broadCastTransasction(tx, alice);

        //노드별 채굴 스레드 시작
        miningService.startMining(alice, 5);
        miningService.startMining(bob, 5);
        miningService.startMining(carol, 5);

        // 메인 스레드가 바로 종료되지 않게 잠깐 대기
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
