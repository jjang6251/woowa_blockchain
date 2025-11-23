package com.zzjj.service;

import com.zzjj.domain.Node;

public interface MiningService {
    void startMining(Node node, int difficulty);
}
