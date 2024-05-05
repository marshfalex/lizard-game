package api;

import hw3.Lizard;

/**
 * Represents a single cell of the grid.
 */
public class Cell {
	private int col;
	private int row;
	private Lizard lizard;
	private Exit exit;
	private Wall wall;

	/**
	 * Creates a new cell at the given column and row.
	 * 
	 * @param col the column where the cell is located
	 * @param row the row where the cell is located
	 */
	public Cell(int col, int row) {
		this.col = col;
		this.row = row;
	}

	/**
	 * @return the column where the cell is located
	 */
	public int getCol() {
		return col;
	}

	/**
	 * @return the row where the cell is located
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Adds the given lizard on this cell.
	 * 
	 * @param lizard to place on cell
	 */
	public void placeLizard(Lizard lizard) {
		this.lizard = lizard;
	}

	/**
	 * Adds the given exit to this cell.
	 * 
	 * @param exit to place on cell
	 */
	public void placeExit(Exit exit) {
		this.exit = exit;
	}

	/**
	 * Adds the given wall to this cell.
	 * 
	 * @param wall to place on cell
	 */
	public void placeWall(Wall wall) {
		this.wall = wall;
	}

	/**
	 * Removes the lizard from this cell.
	 */
	public void removeLizard() {
		placeLizard(null);
	}

	/**
	 * Removes the exit from this cell.
	 */
	public void removeExit() {
		placeExit(null);
	}

	/**
	 * Removes the wall from this cell.
	 */
	public void removeWall() {
		placeWall(null);
	}

	/**
	 * @return true if there is nothing on the cell, false otherwise
	 */
	public boolean isEmpty() {
		return wall == null && exit == null && lizard == null;
	}

	/**
	 * @return the lizard on the cell or null if there is none
	 */
	public Lizard getLizard() {
		return lizard;
	}

	/**
	 * @return the exit on the cell or null if there is none
	 */
	public Exit getExit() {
		return exit;
	}

	/**
	 * @return the wall on the cell or null if there is none
	 */
	public Wall getWall() {
		return wall;
	}

	@Override
	public String toString() {
		String type = "Ground";
		if (exit != null) {
			type = "Exit";
		} else if (wall != null) {
			type = "Wall";
		}
		String occupied = "Empty";
		if (lizard != null) {
			occupied = "Lizard";
		}
		return String.format("%20s", "(" + col + "," + row + "," + type + "," + occupied + ")");
	}
}
