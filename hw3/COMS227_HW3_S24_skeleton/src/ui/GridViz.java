package ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import api.BodySegment;
import api.Cell;
import api.Direction;
import hw3.Lizard;
import hw3.LizardGame;

public class GridViz extends JPanel {
	private static final String HEAD_FILENAME = "resources/head.png";
	private static final String SKIN_FILENAME = "resources/skin.png";
	private static final String WALL_FILENAME = "resources/wall.png";
	private static final String EXIT_FILENAME = "resources/exit.png";
	private static final int DEFAULT_SCALE = 30;
	private static final long serialVersionUID = 1L;
	private LizardGame game;
	private int scale;
	private int row;
	private int col;
	private BufferedImage headImage;
	private BufferedImage lizardImage;
	private BufferedImage wallImage;
	private BufferedImage exitImage;
	
	public GridViz(LizardGame game) {
		this.game = game;
		scale = DEFAULT_SCALE;
		
		Dimension dim = new Dimension(game.getWidth() * scale, game.getHeight() * scale);
		setBackground(new Color(0x000000));
		setPreferredSize(dim);
		setMaximumSize(dim);
		setMinimumSize(dim);
		
		try {
			headImage = ImageIO.read(new File(HEAD_FILENAME));
			lizardImage = ImageIO.read(new File(SKIN_FILENAME));
			wallImage = ImageIO.read(new File(WALL_FILENAME));
			exitImage = ImageIO.read(new File(EXIT_FILENAME));
		} catch (IOException e) {
			System.err.println("ERROR: cannot find file(s) "
					+ HEAD_FILENAME + ", "
					+ SKIN_FILENAME + ", "
					+ WALL_FILENAME + ", or "
					+ EXIT_FILENAME
					+ " make sure these files are present in your project.");
			System.exit(1);
		}

		MouseAdapter mouseEventListener = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				row = e.getY() / scale;
				col = e.getX() / scale;
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				int newrow = e.getY() / scale;
				int newcol = e.getX() / scale;
				if (newrow > row) {
					game.move(col, row, Direction.DOWN);
				} else if (newrow < row) {
					game.move(col, row, Direction.UP);
				} else if (newcol > col) {
					game.move(col, row, Direction.RIGHT);
				} else if (newcol < col) {
					game.move(col, row, Direction.LEFT);
				}
				row = newrow;
				col = newcol;
				update();
			}
		};
		addMouseMotionListener(mouseEventListener);
		addMouseListener(mouseEventListener);
	}
	
	private void paintCell(Graphics2D g2, Cell cell) {
		if (cell.getWall() != null) {
			g2.drawImage(wallImage, cell.getCol() * scale, cell.getRow() * scale, null);
		} else if (cell.getExit() != null) {
			g2.drawImage(exitImage, cell.getCol() * scale, cell.getRow() * scale, null);
		} else {
			g2.setColor(Color.GRAY);
			g2.fillRect((int)((cell.getCol() + 0.5) * scale), (int)((cell.getRow() + 0.5) * scale), 4, 4);
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		for (int i=0; i<game.getWidth(); i++) {
			for (int j=0; j<game.getHeight(); j++) {
				paintCell(g2, game.getCell(i, j));
			}
		}
		
		ArrayList<Lizard> lizards = game.getLizards();
		for (Lizard l: lizards) {
			ArrayList<BodySegment> segments = l.getSegments();
			for (BodySegment s: segments) {
				Cell cell = s.getCell();
				BodySegment nextSegment = l.getSegmentAhead(s);
				if (nextSegment != null) {
				Cell nextCell = l.getSegmentAhead(s).getCell();
				Rectangle2D anchorRect = new Rectangle(1, 1, 11, 11);
				TexturePaint paint = new TexturePaint(lizardImage, anchorRect);
				g2.setPaint(paint);
				g2.setStroke(new BasicStroke(12.0F));
				g2.draw(new Line2D.Float(
						(int)((cell.getCol()+0.5)*scale),
						(int)((cell.getRow()+0.5)*scale),
						(int)((nextCell.getCol()+0.5)*scale),
						(int)((nextCell.getRow()+0.5)*scale)));
				}
			}
			BodySegment head = l.getHeadSegment();
			Cell headCell = head.getCell();
			g2.drawImage(headImage, headCell.getCol() * scale, headCell.getRow() * scale, null);
		}
	}

	public void update() {
		repaint();
	}
}
