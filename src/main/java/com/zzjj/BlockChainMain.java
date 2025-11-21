package com.zzjj;

import com.zzjj.domain.BlockChain;
import com.zzjj.domain.NetWork;
import com.zzjj.domain.Node;
import com.zzjj.domain.Transaction;

public class BlockChainMain {
    public static void main(String[] args) {
        BlockChain chain = new BlockChain(5); // 난이도 2 정도
        NetWork netWork = new NetWork();

        Node alice = new Node("Alice", "Alice", chain, netWork);
        Node bob   = new Node("Bob", "Bob", chain, netWork);
        Node carol = new Node("Carol", "Carol", chain, netWork);

        netWork.register(alice);
        netWork.register(bob);
        netWork.register(carol);

        //트랜잭션 하나 전파
        Transaction tx = new Transaction("Alice", "Bob", 1.0, "sig");
        netWork.broadCastTransasction(tx, alice);

        //노드별 채굴 스레드 시작
        startMiner(alice, 5);
        startMiner(bob, 5);
        startMiner(carol, 5);

        // 메인 스레드가 바로 종료되지 않게 잠깐 대기
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void startMiner(Node node, int difficulty) {
        Thread t = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1_000);
                    node.mineBlock(10, difficulty);
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }
        }, node.getNodeId() + "-Miner");
        t.start();
    }
}