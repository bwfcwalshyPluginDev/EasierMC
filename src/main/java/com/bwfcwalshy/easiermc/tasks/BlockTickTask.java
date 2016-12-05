package com.bwfcwalshy.easiermc.tasks;

import com.bwfcwalshy.easiermc.EasierMC;
import com.bwfcwalshy.easiermc.Handler;

public class BlockTickTask implements Runnable {

    private Handler handler;
    public BlockTickTask(EasierMC plugin){
        this.handler = plugin.getHandler();
    }

    @Override
    public void run() {
        handler.getBlocks().keySet().forEach(location -> handler.getBlocks().get(location).tick(location));
    }
}
