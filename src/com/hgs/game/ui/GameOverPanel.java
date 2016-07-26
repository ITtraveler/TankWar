package com.hgs.game.ui;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import com.hgs.game.util.GameData;

public class GameOverPanel extends JPanel{
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setFont(new Font("华文隶书", Font.BOLD, 50));
		g2d.drawString("Game Over", 100, 100);
		g2d.setFont(new Font("华文隶书", Font.BOLD, 25));
		g2d.drawString("杀敌数："+GameData.totalEnemyTankNum, 400, 260);
		g2d.drawString("总得分："+GameData.score, 400, 320);
		g2d.drawString("通关数："+GameData.customsPass, 400, 380);
	}
}
