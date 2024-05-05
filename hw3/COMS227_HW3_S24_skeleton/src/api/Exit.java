package api;

/**
 * Represents an exit.
 */
public class Exit {
	private Cell cell;
	
	public Exit(Cell cell) {
		this.cell = cell;
	}

	public Cell getCell() {
		return cell; 
	}
	
	public void setCell(Cell cell) {
		this.cell = cell;
	}
}
