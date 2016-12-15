package com.bwfcwalshy.easiermc.tasks;

import com.bwfcwalshy.easiermc.EasierMC;
import com.bwfcwalshy.easiermc.Handler;

import java.util.ArrayList;
import java.util.List;

public class BlockTickTask implements Runnable {

    private List<Long> latestReadings = new ArrayList<>();

    private Handler handler;
    public BlockTickTask(EasierMC plugin){
        this.handler = plugin.getHandler();
    }

    private int tick = 1;

    @Override
    public void run() {
        long start = System.nanoTime();
        tick++;
        handler.getBlocks().keySet().forEach(location -> handler.getBlocks().get(location).tick(location, tick));
        if(tick == 20) tick = 1;
        long end = System.nanoTime();
        long time = end - start;
//        System.out.println("Tick completed in " + ((end - start)/1000000) + " ms (" + (end - start) + ")");
//        System.out.println("Total machines ticking: " + handler.getBlocks().keySet().size());
        if(latestReadings.size() < 100) latestReadings.add(time);
        else{
            latestReadings.add(time);
            latestReadings.remove(100);
        }
    }

    public double getAverageTime(){
        return latestReadings.stream().mapToLong(Long::longValue).average().getAsDouble();
    }
}
