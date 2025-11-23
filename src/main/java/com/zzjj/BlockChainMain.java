package com.zzjj;

import com.zzjj.controller.BlockChainController;
import com.zzjj.service.MiningService;
import com.zzjj.service.MiningServiceImpl;

public class BlockChainMain {
    public static void main(String[] args) {
        MiningService miningService = new MiningServiceImpl();
        BlockChainController blockChainController = new BlockChainController(miningService);
        blockChainController.start();
    }
}