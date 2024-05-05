package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import api.ScoreUpdateListener;
import api.ShowDialogListener;
import hw3.LizardGame;

public class GamePanel extends JPanel implements ShowDialogListener, ScoreUpdateListener {
	private static final long serialVersionUID = 1L;
	private GridViz playGrid;
	private Box box;
	private LizardGame game;
	private JLabel scoreLabel;

	public GamePanel(LizardGame game) {
		this.game = game;
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout(0, 0));
		JPanel southPanel = new JPanel();
		southPanel.setOpaque(false);
		JButton loadButton = new JButton("Load");
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				load();
			}
		});
		scoreLabel = new JLabel();
		scoreLabel.setForeground(Color.WHITE);
		southPanel.add(loadButton);
		southPanel.add(scoreLabel);
		add(southPanel, BorderLayout.SOUTH);
		setBackground(new Color(0x444444));
		box = new Box(BoxLayout.Y_AXIS);
		add(box, BorderLayout.CENTER);
	}
	
	public void setPlayGrid(GridViz playGrid) {
		this.playGrid = playGrid;
		box.removeAll();
		box.add(Box.createVerticalGlue());
		box.add(playGrid);
		box.add(Box.createVerticalGlue());
		repaint();
	}
	
	public String fileChooser(boolean save) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		int result;
		if (save) {
			result = fileChooser.showSaveDialog(this);
		} else {
			result = fileChooser.showOpenDialog(this);
		}
		if (result == JFileChooser.APPROVE_OPTION) {
		    File selectedFile = fileChooser.getSelectedFile();
		    return selectedFile.getAbsolutePath();
		}	
		return "";
	}
	
	public void load() {
		String filePath = fileChooser(false);
		game.load(filePath);
		setPlayGrid(playGrid);
	}

	@Override
	public void showDialog(String dialog) {
		JOptionPane.showMessageDialog(this, dialog);
	}

	@Override
	public void updateScore(long score) {
		scoreLabel.setText("Lizards: " + score);
	}
}
