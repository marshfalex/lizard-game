package api;

/**
 * Represents a wall.
 */
public class Wall {
	private Cell cell;
	
	public Wall(Cell cell) {
		this.cell = cell;
	}

	public Cell getCell() {
		return cell; 
	}
	
	public void setCell(Cell cell) {
		this.cell = cell;
	}
}
