import static api.Direction.*;

import java.util.ArrayList;

import api.BodySegment;
import api.Cell;
import api.Direction;
import hw3.GameFileUtil;
import hw3.Lizard;
import hw3.LizardGame;
import ui.GameConsole;

/**
 * Examples of using the LizardGame, GameFileUtil, and Lizard classes. The
 * main() method in this class only displays to the console. For the full game
 * GUI, run the ui.GameMain class.
 */
public class SimpleTests {
	public static void main(String args[]) {
		// Example tests for Lizard class
		Lizard liz = new Lizard();
		Cell cell0 = new Cell(1, 1);
		Cell cell1 = new Cell(2, 1);
		Cell cell2 = new Cell(2, 2);
		ArrayList<BodySegment> segments = new ArrayList<BodySegment>();
		segments.add(new BodySegment(liz, cell0));
		segments.add(new BodySegment(liz, cell1));
		segments.add(new BodySegment(liz, cell2));

		liz.setSegments(segments);
		System.out.println("The lizard has " + liz.getSegments().size() + " segments, expected 3.");

		BodySegment headSegment = liz.getHeadSegment();
		Cell headCell = headSegment.getCell();
		System.out.println("The head segment is at " + headCell + ", expected (2,2,Ground,Lizard).");

		BodySegment tailSegment = liz.getTailSegment();
		Cell tailCell = tailSegment.getCell();
		System.out.println("The tail segment is at " + tailCell + ", expected (1,1,Ground,Lizard).");

		BodySegment middleSegment = liz.getSegmentAt(cell1);
		Cell middleCell = middleSegment.getCell();
		System.out.println("The middle segment is at " + middleCell + ", expected (2,1,Ground,Lizard).");

		BodySegment aheadSegment = liz.getSegmentAhead(middleSegment);
		Cell aheadCell = aheadSegment.getCell();
		System.out.println("The segment ahead of the middel is at " + aheadCell + ", expected (2,2,Ground,Lizard).");

		BodySegment behindSegment = liz.getSegmentBehind(middleSegment);
		Cell behindCell = behindSegment.getCell();
		System.out.println("The segment behind the middel is at " + behindCell + ", expected (1,1,Ground,Lizard).");

		Direction aheadDir = liz.getDirectionToSegmentAhead(middleSegment);
		System.out.println("From the middle segment, ahead is " + aheadDir + ", expected DOWN.");

		Direction behindDir = liz.getDirectionToSegmentBehind(middleSegment);
		System.out.println("From the middle segment, behind is " + behindDir + ", expected LEFT.");

		Direction headDir = liz.getHeadDirection();
		System.out.println("The head is pointing " + headDir + ", expected DOWN.");

		Direction tailDir = liz.getTailDirection();
		System.out.println("The tail is pointing " + tailDir + ", expected LEFT.");

		// Example tests for GameFileUtil class
		// (requires some implementation of LizardGame)
		LizardGame game = new LizardGame(0, 0);
		GameConsole gc = new GameConsole();
		game.setListeners(gc, gc);

		System.out.println();
		GameFileUtil.load("examples/game1.txt", game);
		System.out.println("Expected a message saying the number of lizards is now 1.");
		System.out.println(
				"DO NOT print this message in GameFileUtil, the ScoreListener needs to be called in LizardGame.");

		System.out.println();
		System.out.println("The grid with is " + game.getWidth() + ", expected 8.");
		System.out.println("The grid height is " + game.getHeight() + ", expected 4.");
		System.out.println("The cell at (0,0) is empty (" + game.getCell(0, 0).isEmpty() + "), expected true.");
		System.out.println(
				"The cell at (1,1) has a wall (" + (game.getCell(1, 1).getWall() != null) + "), expected true.");
		System.out.println(
				"The cell at (7,2) has an exit (" + (game.getCell(7, 2).getExit() != null) + "), expected true.");
		System.out.println(
				"The cell at (2,2) has a lizard (" + (game.getCell(2, 2).getLizard() != null) + "), expected true.");

		// Example tests for LizardGame
		// (assuming previous tests worked and the game is loaded)
		segments = new ArrayList<BodySegment>();
		segments.add(new BodySegment(liz, game.getCell(1, 0)));
		segments.add(new BodySegment(liz, game.getCell(2, 0)));
		segments.add(new BodySegment(liz, game.getCell(3, 0)));
		liz = new Lizard();
		liz.setSegments(segments);
		System.out.println();
		game.addLizard(liz);
		System.out.println("Expected a message saying the number of lizards is now 2.");

		System.out.println();
		game.removeLizard(liz);
		System.out.println("Expected a message saying the number of lizards is now 1.");

		System.out.println();
		liz = game.getLizards().get(0);
		Cell adjCell = game.getAdjacentCell(1, 1, RIGHT);
		System.out.println("Right of cell (1,1) is " + adjCell + ", expected cell (2,1,Ground,Empty)");
		System.out.println("Cell (5,2) is available (" + game.getCell(5, 2) + "), expected true.");
		System.out.println("Moving head of lizard one RIGHT.");
		game.move(4, 2, RIGHT);
		System.out.println("Cell (5,2) is available (" + game.isAvailable(5, 2) + "), expected false.");
		System.out.println("The head of the lizard is in cell (5,2) ("
				+ (liz.getHeadSegment().getCell() == game.getCell(5, 2)) + "), expected true.");
		
		System.out.println();
		System.out.println(game);
	}
}
