package com.zzjj;

import com.zzjj.controller.BlockChainController;
import com.zzjj.service.MiningService;
import com.zzjj.service.MiningServiceImpl;
import com.zzjj.view.View;

public class BlockChainMain {
    public static void main(String[] args) {
        MiningService miningService = new MiningServiceImpl();
        View view = new View();
        BlockChainController blockChainController = new BlockChainController(miningService, view);
        blockChainController.start();
    }
}