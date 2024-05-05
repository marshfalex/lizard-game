package api;

import hw3.Lizard;

/**
 * Represents one segment of the lizard's body.
 */
public class BodySegment {
	private Lizard lizard;
	private Cell cell;

	/**
	 * Creates a new BodySegment of the given lizard located at the given cell.
	 * 
	 * @param lizard associated with the segment
	 * @param cell the location of the lizard
	 */
	public BodySegment(Lizard lizard, Cell cell) {
		this.lizard = lizard;
		this.cell = cell;
		cell.placeLizard(lizard);
	}

	/**
	 * Gets the lizard associated with this segment.
	 * 
	 * @return a lizard
	 */
	public Lizard getLizard() {
		return lizard;
	}

	/**
	 * Sets the lizard associate with this segment.
	 * 
	 * @param lizard for the segment
	 */
	public void setLizard(Lizard lizard) {
		this.lizard = lizard;
	}

	/**
	 * Gets the cell associated with this body segment.
	 * 
	 * @return a cell
	 */
	public Cell getCell() {
		return cell;
	}

	/**
	 * Sets the cell associated with this body segment.
	 * <p>
	 * This method called placeLizard() on the cell so that the cell knows about the
	 * segment.
	 * 
	 * @param cell where the segment is located
	 */
	public void setCell(Cell cell) {
		this.cell = cell;
		cell.placeLizard(lizard);
	}

	@Override
	public String toString() {
		return cell.toString();
	}
}
