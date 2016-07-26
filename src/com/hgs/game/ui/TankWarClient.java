package com.hgs.game.ui;

import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.hgs.game.resource.R;
import com.hgs.game.tank.MyTank;
import com.hgs.game.tank.MyTankFactory;
import com.hgs.game.util.GameData;
import com.hgs.game.util.Images;

public class TankWarClient extends JFrame implements ActionListener {

	private static final int width = 895;
	private static final int height = 660;
	private static final long serialVersionUID = 1L;
	private MyTank mTank;// 初始化一个自己的主坦克
	private JMenuBar jMenuBar;
	private JMenu jMenu;
	private JMenuItem startGame, exitGame, help, copyright, pause;
	private JMenuItem[] jItems = { startGame = new JMenuItem("开始游戏"),
			pause = new JMenuItem("暂停"), help = new JMenuItem("帮助"),
			copyright = new JMenuItem("版权说明"), exitGame = new JMenuItem("退出游戏") };
	private JButton bn_StartGame, bn_help, bn_back;
	private Box baseBox;
	private boolean isPause = true;
	private GamePanel3 gPanel;
	private MenuPanel menuPanel;
	private GameOverPanel gOverPanel;
	private boolean running = false;// 是否为游戏界面运行中

	public TankWarClient() {
		setBounds(150, 100, width, height);// 22x15個40x40的格子
		setTitle("坦克大战");
		setVisible(true);
		// setResizable(false);
		setIconImage(Images.getImage(R.Tank.MyTank.IMG_MYTANK_U));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		mTank = new MyTankFactory().createTank();
		addKeyListener(new MyKeyAdapter());// 添加键盘事件监听
		initMenu();
		initMenuPanel();
		new Thread(new MyPaintRunnable()).start();
	}

	/**
	 * 初始化菜单栏
	 */
	private void initMenu() {
		jMenuBar = new JMenuBar();
		jMenu = new JMenu("游戏菜单");
		for (JMenuItem jItem : jItems) {
			jItem.addActionListener(this);
			jMenu.add(jItem);
		}
		jMenuBar.add(jMenu);
		setJMenuBar(jMenuBar);
	
	}

	/**
	 * 初始化游戏面板
	 */
	private void initGamePanel() {
		gPanel = new GamePanel3(mTank);
		gPanel.setLayout(new FlowLayout());
		add(gPanel);
		isPause = false;
		gPanel.updateUI();// 调用此方法的原因是防止启动游戏不显示游戏界面，起到头回刷新的作用让游戏显示出来

	}

	/**
	 * 初始化菜单面板
	 */
	private void initMenuPanel() {
		menuPanel = new MenuPanel();
		menuPanel.setLayout(new FlowLayout());
		bn_StartGame = new JButton("开始游戏");
		bn_help = new JButton("帮         助");
		bn_StartGame.addActionListener(this);
		bn_help.addActionListener(this);
		baseBox = Box.createVerticalBox();
		baseBox.add(Box.createVerticalStrut(300));
		baseBox.add(bn_StartGame);
		baseBox.add(Box.createVerticalStrut(20));
		baseBox.add(bn_help);
		menuPanel.add(baseBox);
		add(menuPanel);
		menuPanel.updateUI();
	}

	private void initGOverPanel() {
		gOverPanel = new GameOverPanel();
		gOverPanel.setLayout(new FlowLayout());
		bn_back = new JButton("返回主菜单");
		bn_back.addActionListener(this);
		Box box = Box.createVerticalBox();
		box.add(Box.createVerticalStrut(480));
		box.add(bn_back);
		gOverPanel.add(box);
		add(gOverPanel);
		gOverPanel.updateUI();
	}

	/**
	 * 各种按钮的事件处理
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == bn_StartGame || source == startGame) {
			setFocusable(true);
			isPause = false;
			menuPanel.setVisible(false);
			if (!running) {
				initGamePanel();
				System.out.println("run");
				running = true;
			}
			gPanel.setVisible(true);
			gPanel.updateUI();
			
		} else if (source == bn_help || source == help) {
			MyDialog.show_Message(this, "help", GameData.GAME_HELP);
		} else if (source == copyright) {
			MyDialog.show_Message(this, "copyright", GameData.COPYRIGHT);
		} else if (source == pause) {
			isPause = true;
		} else if (source == exitGame) {
			System.exit(0);
		} else if (source == bn_back) {
			initMenuPanel();
			menuPanel.setVisible(true);
			gOverPanel.setVisible(false);
			gPanel.setVisible(false);
			running = false;
			// gPanel.initEnemyTankNum();
			GameData.clear();
		}
	}

	/**
	 * 键盘的事件
	 * 
	 * @author Administrator
	 *
	 */
	class MyKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent arg0) {
			super.keyPressed(arg0);
			mTank.keyPressed(arg0);
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			super.keyReleased(arg0);
			mTank.keyReleased(arg0);
		}
	}

	class MyPaintRunnable implements Runnable {

		@Override
		public void run() {
			while (true) {
				if (!isPause) {
					repaint();
				}
				if (!mTank.isLive()) {
					initGOverPanel();
					gOverPanel.setVisible(true);
					gPanel.setVisible(false);
					System.out.println("fff");
					mTank.setLive(true);
				}
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		TankWarClient tank = new TankWarClient();
	}
}
