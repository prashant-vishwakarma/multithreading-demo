package com.mediaocean;

import java.time.LocalTime;

public class DemoThread extends Thread {
    @Override
    public void run() {
        super.run();
        System.out.println("Thread Executed at Time: " + LocalTime.now());
    }
}
