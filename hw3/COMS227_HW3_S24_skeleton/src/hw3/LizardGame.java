package hw3;
import java.util.ArrayList;

import api.Cell;
import api.Direction;
import api.Exit;
import api.ScoreUpdateListener;
import api.ShowDialogListener;
import api.Wall;
import api.BodySegment;

/**
 * Class that models a game.
 * @author Marshall Alexander
 */
public class LizardGame {
	private ShowDialogListener dialogListener;
	private ScoreUpdateListener scoreListener;
	private int width; // number of columns in the grid
	private int height; // number of rows in the grid@
    private Cell[][] grid; // 2D array (the grid of cells)
    private ArrayList<Lizard> lizards; // list of lizards on the grid

	/**
	 * Constructs a new LizardGame object with given grid dimensions.
	 * 
	 * @param width  number of columns
	 * @param height number of rows
	 */
	public LizardGame(int width, int height) {
		this.width = width;
		this.height = height;
		grid = new Cell[width][height];
		lizards = new ArrayList<>();
		
		// initializes cells in the grid
        for (int column = 0; column < width; column++) {
            for (int row = 0; row < height; row++) {
                grid[column][row] = new Cell(column, row); // assumes cell constructor exists
            }
        }
	}

	/**
	 * Get the grid's width.
	 * 
	 * @return width of the grid
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Get the grid's height.
	 * 
	 * @return height of the grid
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Adds a wall to the grid.
	 * <p>
	 * Specifically, this method calls placeWall on the Cell object associated with
	 * the wall (see the Wall class for how to get the cell associated with the
	 * wall). This class assumes a cell has already been set on the wall before
	 * being called.
	 * 
	 * @param wall to add
	 */
	public void addWall(Wall wall) {
		Cell wallCell = wall.getCell();
		if (wallCell != null) {
	        wallCell.removeWall(); // removes any existing wall on the cell
	        wallCell.placeWall(wall);
	    }
	}

	/**
	 * Adds an exit to the grid.
	 * <p>
	 * Specifically, this method calls placeExit on the Cell object associated with
	 * the exit (see the Exit class for how to get the cell associated with the
	 * exit). This class assumes a cell has already been set on the exit before
	 * being called.
	 * 
	 * @param exit to add
	 */
	public void addExit(Exit exit) {
		Cell exitCell = exit.getCell();
	    if (exitCell != null) {
	        exitCell.removeExit(); // removes any existing exit on the cell
	        exitCell.placeExit(exit);
	    }
	}

	/**
	 * Gets a list of all lizards on the grid. Does not include lizards that have
	 * exited.
	 * 
	 * @return lizards list of lizards
	 */
	public ArrayList<Lizard> getLizards() {
		return lizards;
	}

	/**
	 * Adds the given lizard to the grid.
	 * <p>
	 * The scoreListener to should be updated with the number of lizards.
	 * 
	 * @param lizard to add
	 */
	public void addLizard(Lizard lizard) {
		// adds lizard to list of lizards
	    lizards.add(lizard);
	    
	    // updates score by pinging score listener
	    if (scoreListener != null) {
	        scoreListener.updateScore(lizards.size());
	    }
	}

	/**
	 * Removes the given lizard from the grid. Be aware that each cell object knows
	 * about a lizard that is placed on top of it. It is expected that this method
	 * updates all cells that the lizard used to be on, so that they now have no
	 * lizard placed on them.
	 * <p>
	 * The scoreListener to should be updated with the number of lizards using
	 * updateScore().
	 * 
	 * @param lizard to remove
	 */
	public void removeLizard(Lizard lizard) {
		// removes lizard from the list of lizards
	    lizards.remove(lizard);
	    
	    // update score by pinging the score listener
	    if (scoreListener != null) {
	        scoreListener.updateScore(lizards.size());
	    }
	    
	    // removes all segments of lizard from the grid
	    for (BodySegment segment : lizard.getSegments()) {
	        Cell cell = segment.getCell();
	        if (cell != null) {
	            cell.removeLizard();
	        }
	    }
	}

	/**
	 * Gets the cell for the given column and row.
	 * <p>
	 * If the column or row are outside of the boundaries of the grid the method
	 * returns null.
	 * 
	 * @param column columnumn of the cell
	 * @param row of the cell
	 * @return the cell or null
	 */
	public Cell getCell(int column, int row) {
		// check if the column and row are within the boundaries of grid
	    if (column >= 0 && column < width && row >= 0 && row < height) {
	        return grid[column][row];
	    }
	    // return null if the coordinates are outside the grid
	    return null;
	}

	/**
	 * Gets the cell that is adjacent to (one over from) the given column and row,
	 * when moving in the given direction. For example (1, 4, UP) returns the cell
	 * at (1, 3).
	 * <p>
	 * If the adjacent cell is outside of the boundaries of the grid, the method
	 * returns null.
	 * 
	 * @param column the given column
	 * @param row the given row
	 * @param dir the direction from the given column and row to the adjacent cell
	 * @return the adjacent cell or null
	 */
	public Cell getAdjacentCell(int column, int row, Direction dir) {
		// calculates the column and row of the adjacent cell based on direction
	    int adjcolumn = column;
	    int adjRow = row;
	    switch (dir) {
	        case UP:
	            adjRow--;
	            break;
	        case DOWN:
	            adjRow++;
	            break;
	        case LEFT:
	            adjcolumn--;
	            break;
	        case RIGHT:
	            adjcolumn++;
	            break;
	    }

	    // will check if the adjacent cell is within boundaries of the grid
	    if (adjcolumn >= 0 && adjcolumn < width && adjRow >= 0 && adjRow < height) {
	        return grid[adjcolumn][adjRow]; // returns the adjacent cell
	    } else {
	        return null;
	    }
	}

	/**
	 * Resets the grid. After calling this method the game should have a grid of
	 * size width x height containing all empty cells. Empty means cells with no
	 * walls, exits, etc.
	 * <p>
	 * All lizards should also be removed from the grid.
	 * 
	 * @param width  number of column of the resized grid
	 * @param height number of rows of the resized grid
	 */
	public void resetGrid(int width, int height) {
		this.width = width;
	    this.height = height;
	    this.grid = new Cell[width][height]; // creates a new grid with specified dimensions
	    lizards.clear();
	    
	    // initializes cells in the grid
        for (int column = 0; column < width; column++) {
            for (int row = 0; row < height; row++) {
                grid[column][row] = new Cell(column, row);
            }
        }
	}

	/**
	 * Returns true if a given cell location (column, row) is available for a lizard to
	 * move into. Specifically the cell cannot contain a wall or a lizard. Any other
	 * type of cell, including an exit is available.
	 * 
	 * @param row of the cell being tested
	 * @param column of the cell being tested
	 * @return true if the cell is available, false otherwise
	 */
	public boolean isAvailable(int column, int row) {
		Cell cell = getCell(column, row);
	    	// checks if the given position is within the grid boundaries
		    if (column < 0 || column > width || row < 0 || row > height) {
		        return false;
		    }
	    	
	    	if (cell.getExit() != null) {
	            return true;
	        }
	        // for no exit cells check if empty
	        if (cell.getWall() != null || cell.getLizard() != null) {
	            return false;
	        }
		    return true;

	    	
	}

	/**
	 * Move the lizard specified by its body segment at the given position (column,
	 * row) one cell in the given direction. The entire body of the lizard must move
	 * in a snake like fashion, in other words, each body segment pushes and pulls
	 * the segments it is connected to forward or backward in the path of the
	 * lizard's body. The given direction may result in the lizard moving its body
	 * either forward or backward by one cell.
	 * <p>
	 * The segments of a lizard's body are linked together and movement must always
	 * be "in-line" with the body. It is allowed to implement movement by either
	 * shifting every body segment one cell over or by creating a new head or tail
	 * segment and removing an existing head or tail segment to achieve the same
	 * effect of movement in the forward or backward direction.
	 * <p>
	 * If any segment of the lizard moves over an exit cell, the lizard should be
	 * removed from the grid.
	 * <p>
	 * If there are no lizards left on the grid the player has won the puzzle the
	 * the dialog listener should be used to display (see showDialog) the message
	 * "You win!".
	 * <p>
	 * It is possible that the given direction is not in-line with the body of the
	 * lizard (as described above), in that case this method should do nothing.
	 * <p>
	 * It is possible that the given columnumn and row are outside the bounds of the
	 * grid, in that case this method should do nothing.
	 * <p>
	 * It is possible that there is no lizard at the given columnumn and row, in that
	 * case this method should do nothing.
	 * <p>
	 * It is possible that the lizard is blocked and cannot move in the requested
	 * direction, in that case this method should do nothing.
	 * <p>
	 * <b>Developer's note: You may have noticed that there are a lot of details
	 * that need to be considered when implement this method method. It is highly
	 * recommend to explore how you can use the public API methods of this class,
	 * Grid and Lizard (hint: there are many helpful methods in those classes that
	 * will simplify your logic here) and also create your own private helper
	 * methods. Break the problem into smaller parts are work on each part
	 * individually.</b>
	 * 
	 * @param column the given column of a selected segment
	 * @param row the given row of a selected segment
	 * @param dir the given direction to move the selected segment
	 */
	public void move(int column, int row, Direction dir) {

		if (column < 0 || column >= width || row < 0 || row >= height) {
	        return ;
	    }
	    // gets current cell based on given position
	    Cell currentCell = getCell(column, row);
	    if (currentCell == null) {
	        return;
	    }

	    // gets lizard on current cell
	    Lizard lizard = currentCell.getLizard();
	    if (lizard == null) {
	        return; // does nothing if no lizard on cell
	    }
	    
	    // gets list of segments
	    ArrayList<BodySegment> segments = lizard.getSegments();

	    // gets direction lizard is facing
	    Direction lizardDirection = lizard.getHeadDirection();
	    if (lizardDirection == null) {
	        return;
	    }

	    // gets cell lizard is moving to
	    Cell nextCell = getAdjacentCell(column, row, dir);
	    if (nextCell == null) {
	        return;
	    }
	      
	    // checks if cell is available
	    if (isAvailable(nextCell.getCol(), nextCell.getRow())) {	           
	        
	    	// determines if lizard is moving forward or backward
	        // forward from head
	        if (lizard.getSegmentAt(currentCell) == lizard.getHeadSegment()) {
	        	headMovement(lizard, nextCell, segments);	           
	        }
	        else if (lizard.getSegmentAt(currentCell) == lizard.getTailSegment()) {
	        	tailMovement(lizard, nextCell, segments);
	        }
	    } // checks if lizard is moving to a segment in segments list
	    else if (segments.contains(lizard.getSegmentAt(nextCell)) ){
	    	// if lizard is moving forward
	    	if (lizard.getDirectionToSegmentAhead(lizard.getSegmentAt(currentCell)) == dir) {
		    	// sets cell head will move to if push towards head
		        Cell headAutoAdjacent = getAdjacentCell(lizard.getHeadSegment().getCell().getCol(), lizard.getHeadSegment().getCell().getRow(), lizard.getHeadDirection());
		        if (headAutoAdjacent == null) {
		        	return;
		        } // checks availability of where head is moving to
		        if (isAvailable(headAutoAdjacent.getCol(), headAutoAdjacent.getRow())) {
		        	headMovement(lizard, headAutoAdjacent, segments);
		        	
		        	
			        }
	    	} // if lizard is moving backwards
	    	else if (lizard.getDirectionToSegmentBehind(lizard.getSegmentAt(currentCell)) == dir) {
	    		// sets cell tail will move to if pull towards tail
		        Cell tailAutoAdjacent = getAdjacentCell(lizard.getTailSegment().getCell().getCol(), lizard.getTailSegment().getCell().getRow(), lizard.getTailDirection());   
		    	if (tailAutoAdjacent == null) {
		    		return;
		    	} // checks availability of where tail is moving to
		        if (isAvailable(tailAutoAdjacent.getCol(), tailAutoAdjacent.getRow())) {
		        	tailMovement(lizard, tailAutoAdjacent, segments);
		        	
		        }
	    	}
	    } 

	    // checks if lizard is on an exit cell and remove it from grid and list
	    if (lizard.getHeadSegment().getCell().getExit() != null || lizard.getTailSegment().getCell().getExit() != null) {
	        removeLizard(lizard);	        
	        if (lizards.size() == 0) {
	        	dialogListener.showDialog("\"You Win!\"");	
	        	
	        }	
	    }
	}
	
	
	/**
	 * Shifts the lizards tail to the adjacent cell while adjusting the positions of the remaining segments of the lizards body accordingly.
	 * 
	 * @param lizard   the lizard object
	 * @param nextCell the next cell where the tail will be moved to
	 * @param segments the list of body segments of the lizard
	 */
	private void tailMovement(Lizard lizard, Cell nextCell, ArrayList<BodySegment> segments) {
	    // sets previous cell
	    Cell previousCell = lizard.getTailSegment().getCell();	            
	    // moves tail to next cell
	    lizard.getTailSegment().setCell(nextCell);
	    // moves rest of the lizard
	    for (int i = 1; i <= segments.size() - 1; i++) {
	        Cell temporaryCell = segments.get(i).getCell();
	        segments.get(i).setCell(previousCell);
	        previousCell = temporaryCell;
	    }
	    // clears cell previously occupied by the tail
	    previousCell.removeLizard();
	}

	
	/**
	 * Transfers the lizards head to the designated adjacent cell and adjusts the positions of the remaining segments of the lizards body accordingly.
	 * The previous cell owned by the head is cleared.
	 *
	 * @param lizard   the lizard object whose head is being moved
	 * @param nextCell the cell to which the head is being moved
	 * @param segments the list of body segments of the lizard
	 */
	private void headMovement(Lizard lizard, Cell nextCell, ArrayList<BodySegment> segments) {
	    // set previous cell
	    Cell previousCell = lizard.getHeadSegment().getCell();
	    // moves head to next cell
	    lizard.getHeadSegment().setCell(nextCell);
	    // moves rest of the lizard 
	    for (int i = segments.size() - 2; i >= 0; i--) {
	        Cell temporaryCell = segments.get(i).getCell();
	        segments.get(i).setCell(previousCell);
	        previousCell = temporaryCell;
	    }
	    // clears cell previously occupied by the tail
	    previousCell.removeLizard();
	}
	
	

	


	/**
	 * Sets callback listeners for game events.
	 * 
	 * @param dialogListener listener for creating a user dialog
	 * @param scoreListener  listener for updating the player's score
	 */
	public void setListeners(ShowDialogListener dialogListener, ScoreUpdateListener scoreListener) {
		this.dialogListener = dialogListener;
		this.scoreListener = scoreListener;
	}

	/**
	 * Load the game from the given file path
	 * 
	 * @param filePath location of file to load
	 */
	public void load(String filePath) {
		GameFileUtil.load(filePath, this);
	}

	@Override
	public String toString() {
		String str = "---------- GRID ----------\n";
		str += "Dimensions:\n";
		str += getWidth() + " " + getHeight() + "\n";
		str += "Layout:\n";
		for (int y = 0; y < getHeight(); y++) {
			if (y > 0) {
				str += "\n";
			}
			for (int x = 0; x < getWidth(); x++) {
				str += getCell(x, y);
			}
		}
		str += "\nLizards:\n";
		for (Lizard l : getLizards()) {
			str += l;
		}
		str += "\n--------------------------\n";
		return str;
	}
}




