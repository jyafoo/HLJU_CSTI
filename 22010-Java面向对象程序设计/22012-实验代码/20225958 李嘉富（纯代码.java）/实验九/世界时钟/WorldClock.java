package com.example.eight;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


public class WorldClock extends JFrame {
    private Label labelBeiJing, labelNewYork, labelLondon, labelTokyo;
    private Button pauseBeiJing, pauseNewYork, pauseLondon, pauseTokyo;
    private ClockThread threadBeiJing, threadNewYork, threadLondon, threadTokyo;


    public WorldClock() {
        super("世界时钟");
        setSize(600, 300);
        setFont(new Font("宋体", Font.BOLD, 16));
        setLayout(new GridLayout(4, 2, 50, 50));
        addComponents();
        addThread();
        addListeners();
        setVisible(true);
    }

    private void addComponents() {
        Font font = new Font("宋体", Font.BOLD, 30);

        labelBeiJing = new Label("Beijing Time");
        labelBeiJing.setFont(font);
        add(labelBeiJing);

        pauseBeiJing = new Button("pause");
        add(pauseBeiJing);

        labelNewYork = new Label("New York Time");
        labelNewYork.setFont(font);
        add(labelNewYork);

        pauseNewYork = new Button("pause");
        add(pauseNewYork);

        labelLondon = new Label("London Time");
        labelLondon.setFont(font);
        add(labelLondon);

        pauseLondon = new Button("pause");
        add(pauseLondon);

        labelTokyo = new Label("Tkoyo Time");
        labelTokyo.setFont(font);
        add(labelTokyo);

        pauseTokyo = new Button("pause");
        add(pauseTokyo);


    }

    private void addThread(){
        //创建时钟线程并启动
        threadBeiJing = new ClockThread(labelBeiJing, "Beijing",0);
        threadNewYork = new ClockThread(labelNewYork,"NewYork", -12);
        threadLondon = new ClockThread(labelLondon, "London",-7);
        threadTokyo = new ClockThread(labelTokyo, "Tkoyo",2);

        threadBeiJing.start();
        threadNewYork.start();
        threadLondon.start();
        threadTokyo.start();
    }


    private void addListeners() {
        MyListener listener = new MyListener();
        pauseBeiJing.addActionListener(this::pauseBeijing);
        pauseNewYork.addActionListener(listener);
        pauseLondon.addActionListener(listener);
        pauseTokyo.addActionListener(listener);
    }
    boolean paused = false;


    /*
    引用成员方法：
        其他类：其它类对象：：方法名
        本类：this：；方法名
        父类：super：：方法名
     */
    public void pauseBeijing(ActionEvent e){
        if (e.getSource() == pauseBeiJing) {
            if (!paused) {
                threadBeiJing.suspend();
                pauseBeiJing.setLabel("continue");
                paused = true;
            } else {
                threadBeiJing.resume();
                pauseBeiJing.setLabel("pause");
                paused = false;
            }
        }
    }


    class MyListener implements ActionListener, WindowListener{
        // boolean paused = false;

        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == pauseNewYork) {
                if (!paused) {
                    threadNewYork.suspend();
                    pauseNewYork.setLabel("continue");
                    paused = true;
                } else {
                    threadNewYork.resume();
                    pauseNewYork.setLabel("pause");
                    paused = false;
                }
            } else if (e.getSource() == pauseLondon) {
                if (!paused) {
                    threadLondon.suspend();
                    pauseLondon.setLabel("continue");
                    paused = true;
                } else {
                    threadLondon.resume();
                    pauseLondon.setLabel("pause");
                    paused = false;
                }
            } else if (e.getSource() == pauseTokyo) {
                if (!paused) {
                    threadTokyo.suspend();
                    pauseTokyo.setLabel("continue");
                    paused = true;
                } else {
                    threadTokyo.resume();
                    pauseTokyo.setLabel("pause");
                    paused = false;
                }
            }
        }

        /**
         * Invoked the first time a window is made visible.
         *
         * @param e the event to be processed
         */
        @Override
        public void windowOpened(WindowEvent e) {

        }

        /**
         * Invoked when the user attempts to close the window
         * from the window's system menu.
         *
         * @param e the event to be processed
         */
        @Override
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }

        /**
         * Invoked when a window has been closed as the result
         * of calling dispose on the window.
         *
         * @param e the event to be processed
         */
        @Override
        public void windowClosed(WindowEvent e) {

        }

        /**
         * Invoked when a window is changed from a normal to a
         * minimized state. For many platforms, a minimized window
         * is displayed as the icon specified in the window's
         * iconImage property.
         *
         * @param e the event to be processed
         * @see Frame#setIconImage
         */
        @Override
        public void windowIconified(WindowEvent e) {

        }

        /**
         * Invoked when a window is changed from a minimized
         * to a normal state.
         *
         * @param e the event to be processed
         */
        @Override
        public void windowDeiconified(WindowEvent e) {

        }

        /**
         * Invoked when the Window is set to be the active Window. Only a Frame or
         * a Dialog can be the active Window. The native windowing system may
         * denote the active Window or its children with special decorations, such
         * as a highlighted title bar. The active Window is always either the
         * focused Window, or the first Frame or Dialog that is an owner of the
         * focused Window.
         *
         * @param e the event to be processed
         */
        @Override
        public void windowActivated(WindowEvent e) {

        }

        /**
         * Invoked when a Window is no longer the active Window. Only a Frame or a
         * Dialog can be the active Window. The native windowing system may denote
         * the active Window or its children with special decorations, such as a
         * highlighted title bar. The active Window is always either the focused
         * Window, or the first Frame or Dialog that is an owner of the focused
         * Window.
         *
         * @param e the event to be processed
         */
        @Override
        public void windowDeactivated(WindowEvent e) {

        }
    }


}