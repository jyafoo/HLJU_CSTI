package com.example.night;


import javax.swing.*;

public class NotepadFrame extends JFrame {

    public boolean keyispressed=false;
    JTextArea jTextArea;//文本区域
    JMenuItem jMenuItem1, jMenuItem2, jMenuItem3,jMenuItem4;//菜单项
    JMenuBar jMenuBar;//菜单条
    JMenu jMenu;//菜单
    JScrollPane jScrollPane;//滚动面板
    MyListener myListener = new MyListener(this);
    public NotepadFrame(String title){
        super(title);
        jTextArea = new JTextArea(300,100);
        jMenuBar = new JMenuBar();
        jMenu = new JMenu("文件");
        jMenuItem1 = new JMenuItem("打开");
        jMenuItem2 = new JMenuItem("保存");
        jMenuItem3 = new JMenuItem("另存为");
        jMenuItem4 = new JMenuItem("退出");
        JSeparator jSeparator = new JSeparator(); //分割线

        //添加滚动条
        jScrollPane = new JScrollPane();
        jTextArea = new JTextArea();
        jScrollPane.setViewportView(jTextArea);
        this.add(jScrollPane);

        jMenu.add(jMenuItem1);
        jMenu.add(jMenuItem2);
        jMenu.add(jMenuItem3);
        jMenu.add(jSeparator);
        jMenu.add(jMenuItem4);
        jMenuBar.add(jMenu);

        this.setJMenuBar(jMenuBar);//设置为窗体的菜单栏
        this.jTextArea.setEditable(true);       //编辑区可编辑
        setResizable(false);
        this.setSize(400,200);
        this.setVisible(true);
        this.setDefaultCloseOperation(0);
    }

    public void go(){
        jTextArea.addKeyListener(myListener);
        jMenuItem1.addActionListener(myListener);     //打开
        jMenuItem2.addActionListener(myListener);     //保存
        jMenuItem3.addActionListener(myListener);     //另存为
        jMenuItem4.addActionListener(myListener);     //退出
        addWindowListener(myListener);
         //this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
       // int n = JOptionPane.showConfirmDialog(null, "是否保存文件?", "标题",JOptionPane.YES_NO_OPTION);
    }
}
