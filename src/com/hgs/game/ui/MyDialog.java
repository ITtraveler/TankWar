package com.hgs.game.ui;

import java.awt.Component;

import javax.swing.JOptionPane;

public class MyDialog {
    /**
     * 
     * @param context ���
     * @param s Ҫ��ʾ����Ϣ
     */
    public static void show_Message(Component context,String title,String content){
            JOptionPane.showMessageDialog(context,content ,title ,JOptionPane.INFORMATION_MESSAGE);//���洰
    }
}
