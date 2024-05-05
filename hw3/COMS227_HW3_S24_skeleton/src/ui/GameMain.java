package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;

import hw3.LizardGame;

public class GameMain extends JFrame {
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameMain frame = new GameMain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GameMain() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 400);
		
		LizardGame game = new LizardGame(10, 10);
		
		GridViz playGrid = new GridViz(game);
		GamePanel gamePanel = new GamePanel(game);
		setContentPane(gamePanel);
	
		gamePanel.setPlayGrid(playGrid);
		game.setListeners(gamePanel, gamePanel);
	}
}
