package com.example.night;



import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.SQLOutput;

public class MyListener extends KeyAdapter implements ActionListener,WindowListener,KeyListener {
    NotepadFrame notepadFrame;
    String fileName;
    boolean edited = false;      //true情况下需要提示保存
    JFileChooser jFileChooser;
    BufferedReader bufferedReader;

    public MyListener() {
        fileName = new String("哟，新来的呀.txt");
        edited = true;
    }
   public MyListener(NotepadFrame n) {
        this.notepadFrame = n;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    /**
     * 当按键时，说明内容发生变化，即此时正在操作文本
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
        edited = true;
        System.out.println("内容被修改");
    }


    /**
     * 保存当前文本内容时
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // 当打开新文件时，判断当前文件是否需要保存
        if (e.getSource() == notepadFrame.jMenuItem1) {
            if (edited) {
                int choose = JOptionPane.showConfirmDialog(null, "是否保存当前内容？", "简易文本编辑器",
                        JOptionPane.YES_NO_CANCEL_OPTION);
                if (choose == JOptionPane.CANCEL_OPTION) {
                    return;
                } else {
                    JFileChooser chooser = new JFileChooser();
                    if (choose == JOptionPane.YES_OPTION) {
                        if (fileName == null) {
                            chooser.showSaveDialog(notepadFrame.jMenuItem3);
                            fileName = chooser.getSelectedFile().getAbsolutePath();
                        }
                        try {
                            FileWriter fileWriter = new FileWriter(fileName);
                            String s = notepadFrame.jTextArea.getText();
                            fileWriter.write(s);
                            fileWriter.flush();
                            fileWriter.close();
                        } catch (IOException ioE) {
                            ioE.printStackTrace();
                        }
                    }
                }
            }

            //打开功能
            JFileChooser chooser = new JFileChooser();
            int choose = chooser.showOpenDialog(notepadFrame.jMenuItem1);
            if (chooser.getSelectedFile() == null || choose == JFileChooser.CANCEL_OPTION) {
                return;
            }
            fileName = chooser.getSelectedFile().getAbsolutePath();
            try {
                bufferedReader = new BufferedReader(new FileReader(fileName));
                String s;
                notepadFrame.jTextArea.setText("");
                while ((s = bufferedReader.readLine()) != null) {
                    notepadFrame.jTextArea.append(s);
                    notepadFrame.jTextArea.append("\n");
                }
                bufferedReader.close();
                System.out.println("读档成功");
            } catch (IOException ioException) {
                ioException.getMessage();
            }
        } else if (e.getSource() == notepadFrame.jMenuItem2) {
            JFileChooser chooser = new JFileChooser();
            if (fileName == null) {
                chooser.showSaveDialog(null);
                fileName = chooser.getSelectedFile().getAbsolutePath();
            }
            try {
                FileWriter fileWriter = new FileWriter(fileName);
                String s = notepadFrame.jTextArea.getText();
                fileWriter.write(s);
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } else if (e.getSource() == notepadFrame.jMenuItem3) {
            JFileChooser chooser = new JFileChooser();
            chooser.showSaveDialog(null);
            fileName = chooser.getSelectedFile().getAbsolutePath();
            fileName = jFileChooser.getSelectedFile().getAbsolutePath();
            try {
                FileWriter fileWriter = new FileWriter(fileName);
                String s = notepadFrame.jTextArea.getText();
                fileWriter.write(s);
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } else {
            if (edited) {
                int choose = JOptionPane.showConfirmDialog(null, "是否保存当前内容?", "简易文本编辑器"
                        , JOptionPane.YES_NO_CANCEL_OPTION);
                if (choose == JOptionPane.YES_OPTION) {
                    JFileChooser chooser = new JFileChooser();
                    if (fileName == null) {
                        chooser.showSaveDialog(null);
                        fileName = chooser.getSelectedFile().getAbsolutePath();
                    }
                    try {
                        FileWriter fileWriter = new FileWriter(fileName);
                        String s = notepadFrame.jTextArea.getText();
                        fileWriter.write(s);
                        fileWriter.flush();
                        fileWriter.close();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    System.exit(0);
                } else if (choose == JOptionPane.NO_OPTION) {
                    System.exit(0);
                }
            } else {
                System.exit(0);
            }
        }
        edited = false;//重置
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    /**
     * 窗体被关闭时，判断文本是否有修改痕迹，即edit是否为true
     * @param e the event to be processed
     */
    @Override
    public void windowClosing(WindowEvent e) {
        if (edited) {
            int choose = JOptionPane.showConfirmDialog(null, "是否保存当前内容?", "简易文本编辑器"
                    , JOptionPane.YES_NO_CANCEL_OPTION);
            if (choose == JOptionPane.YES_OPTION) {
                // 文件选择器
                JFileChooser chooser = new JFileChooser();
                if (fileName == null) {
                    chooser.showSaveDialog(null);
                    fileName = chooser.getSelectedFile().getAbsolutePath();
                }
                try {
                    FileWriter fileWriter = new FileWriter(fileName);
                    String s = notepadFrame.jTextArea.getText();
                    fileWriter.write(s);
                    fileWriter.flush();
                    fileWriter.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                System.exit(0);
            } else if (choose == JOptionPane.NO_OPTION) {
                System.exit(0);
            }
        } else {
            System.exit(0);
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
