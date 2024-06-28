package com.example.seven;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MyListener implements ActionListener {
    SimpleCaculator calc;


    MyListener(SimpleCaculator o) {
        calc = o;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String t1, t2;
        try {
            t1 = calc.x1.getText();// 获取文本框两个数
            t2 = calc.x2.getText();
            if (e.getSource() == calc.button[0]) {
                calc.y1.setText("+");
                double sum = Integer.parseInt(t1) + Integer.parseInt(t2);
                calc.x3.setText(String.valueOf(sum));
            }
            if (e.getSource() == calc.button[1]) {
                calc.y1.setText("-");
                double sum = Integer.parseInt(t1) - Integer.parseInt(t2);
                calc.x3.setText(String.valueOf(sum));
            }
            if (e.getSource() == calc.button[2]) {
                calc.y1.setText("*");
                double sum = Integer.parseInt(t1) * Integer.parseInt(t2);
                calc.x3.setText(String.valueOf(sum));
            }

            if (e.getSource() == calc.button[3]) {
                calc.y1.setText("/");
                if (Integer.parseInt(t2) == 0){
                    throw new DivideZreoException();
                }
                double sum=Integer.parseInt(t1)*1.0/Integer.parseInt(t2);
                calc.x3.setText(""+sum );
            }
        }catch (DivideZreoException divideZreoException) {
            JOptionPane.showMessageDialog(null, "除数不能为0");
        } catch(Exception e2){
            JOptionPane.showMessageDialog(null, "请输入两个整数");
        }
    }
}