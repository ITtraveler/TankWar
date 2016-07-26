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
	private MyTank mTank;// ��ʼ��һ���Լ�����̹��
	private JMenuBar jMenuBar;
	private JMenu jMenu;
	private JMenuItem startGame, exitGame, help, copyright, pause;
	private JMenuItem[] jItems = { startGame = new JMenuItem("��ʼ��Ϸ"),
			pause = new JMenuItem("��ͣ"), help = new JMenuItem("����"),
			copyright = new JMenuItem("��Ȩ˵��"), exitGame = new JMenuItem("�˳���Ϸ") };
	private JButton bn_StartGame, bn_help, bn_back;
	private Box baseBox;
	private boolean isPause = true;
	private GamePanel3 gPanel;
	private MenuPanel menuPanel;
	private GameOverPanel gOverPanel;
	private boolean running = false;// �Ƿ�Ϊ��Ϸ����������

	public TankWarClient() {
		setBounds(150, 100, width, height);// 22x15��40x40�ĸ���
		setTitle("̹�˴�ս");
		setVisible(true);
		// setResizable(false);
		setIconImage(Images.getImage(R.Tank.MyTank.IMG_MYTANK_U));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		mTank = new MyTankFactory().createTank();
		addKeyListener(new MyKeyAdapter());// ��Ӽ����¼�����
		initMenu();
		initMenuPanel();
		new Thread(new MyPaintRunnable()).start();
	}

	/**
	 * ��ʼ���˵���
	 */
	private void initMenu() {
		jMenuBar = new JMenuBar();
		jMenu = new JMenu("��Ϸ�˵�");
		for (JMenuItem jItem : jItems) {
			jItem.addActionListener(this);
			jMenu.add(jItem);
		}
		jMenuBar.add(jMenu);
		setJMenuBar(jMenuBar);
	
	}

	/**
	 * ��ʼ����Ϸ���
	 */
	private void initGamePanel() {
		gPanel = new GamePanel3(mTank);
		gPanel.setLayout(new FlowLayout());
		add(gPanel);
		isPause = false;
		gPanel.updateUI();// ���ô˷�����ԭ���Ƿ�ֹ������Ϸ����ʾ��Ϸ���棬��ͷ��ˢ�µ���������Ϸ��ʾ����

	}

	/**
	 * ��ʼ���˵����
	 */
	private void initMenuPanel() {
		menuPanel = new MenuPanel();
		menuPanel.setLayout(new FlowLayout());
		bn_StartGame = new JButton("��ʼ��Ϸ");
		bn_help = new JButton("��         ��");
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
		bn_back = new JButton("�������˵�");
		bn_back.addActionListener(this);
		Box box = Box.createVerticalBox();
		box.add(Box.createVerticalStrut(480));
		box.add(bn_back);
		gOverPanel.add(box);
		add(gOverPanel);
		gOverPanel.updateUI();
	}

	/**
	 * ���ְ�ť���¼�����
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
	 * ���̵��¼�
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
