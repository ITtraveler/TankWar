package com.hgs.game.ui;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class MenuPanel extends JPanel{
	public MenuPanel(){
		
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		drawInit(g2d);
	}
	private void drawInit(Graphics2D g2d) {
		g2d.setFont(new Font("华文彩云", Font.BOLD | Font.ITALIC, 60));
		g2d.drawString("坦 克 大 战", 260, 100);

	}
}
