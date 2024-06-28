package com.example.eight;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ClockThread extends Thread {
    private final Label label;
    private final String placeName;
    private final int timeInterval; // 时间间隔

    public ClockThread(Label label,String placeName, int timeInterval) {
        this.label = label;
        this.placeName = placeName;
        this.timeInterval = timeInterval;
    }

    public void run() {
        while (true) {
            Calendar now = Calendar.getInstance();
            now.add(Calendar.HOUR, timeInterval);
            String sdf = new SimpleDateFormat("HH:mm:ss").format(now.getTime());
            label.setText(placeName + ":" + sdf);
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


/*
    当一个线程的start()方法被调用时，该线程就进入了就绪状态，等待被系统调度执行。
    一旦线程获得了执行的机会，就会自动调用它的run()方法。
    所以必须要通过start()方法启动线程，而不能直接调用run()方法启动线程，
    否则该线程会被当作普通的对象方法执行，而不会被作为一个独立的线程执行。
*/
