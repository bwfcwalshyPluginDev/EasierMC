package com.bwfcwalshy.easiermcnewinv.tasks;

import com.bwfcwalshy.easiermcnewinv.EasierMC;
import com.bwfcwalshy.easiermcnewinv.Handler;

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
