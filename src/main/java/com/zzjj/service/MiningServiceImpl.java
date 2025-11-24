package com.zzjj.service;

import com.zzjj.domain.Node;

public class MiningServiceImpl implements MiningService {
    @Override
    public void startMining(Node node, int difficulty) {
        Thread t = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
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
