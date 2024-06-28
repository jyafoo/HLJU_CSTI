package com.example.seven;

import javax.swing.*;
import java.awt.*;

public class SimpleCaculator extends JFrame{

    public SimpleCaculator() {
        super();
    }
    String[] z = {"Plus", "Minus", "Times", "Divide"};
    JTextField x1, x2, x3; // 三个文本框
    JLabel y1, y2;
    JPanel p1, p2;
    JButton[] button = new JButton[4];

    public void go(){
        String[] z ={"Plus", "Minus", "Times", "Divide"};
        x1=new JTextField(10);
        x2=new JTextField(10);
        x3=new JTextField(10);
        y1=new JLabel("");
        y2=new JLabel("=" );
        p1=new JPanel();
        p2=new JPanel();
        p1.add(x1);
        p1.add(y1);
        p1.add(x2);
        p1.add(y2);
        p1.add(x3);
        add(p1, BorderLayout.NORTH); // 规定位置
        MyListener mylistener=new MyListener(this);
        for(int j=0;j<4;j++){
            button[j]=new JButton(z[j]);
            add(button[j]);
            p2.add(button[j]);
            button[j].addActionListener(mylistener);
        }
        add(p2,BorderLayout.SOUTH);
        this.setSize(400,100);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

}

