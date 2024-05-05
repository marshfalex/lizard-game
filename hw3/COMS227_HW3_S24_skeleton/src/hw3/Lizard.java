package hw3;
import java.util.ArrayList;
import api.BodySegment;
import api.Cell;
import api.Direction;

/**
 * Represents a Lizard as a collection of body segments.
 * @author Marshall Alexander
 */
public class Lizard {
	private ArrayList<BodySegment> segments;
	/**
	 * Constructs a Lizard object.
	 */
	public Lizard() {
		segments = new ArrayList<>();
	}

	/**
	 * Sets the segments of the lizard. Segments should be ordered from tail to
	 * head.
	 * 
	 * @param segments list of segments ordered from tail to head
	 */
	public void setSegments(ArrayList<BodySegment> segments) {
		this.segments = segments;
	}

	/**
	 * Gets the segments of the lizard. Segments are ordered from tail to head.
	 * 
	 * @return a list of segments ordered from tail to head
	 */
	public ArrayList<BodySegment> getSegments() {
		return segments; 
	}

	/**
	 * Gets the head segment of the lizard. Returns null if the segments have not
	 * been initialized or there are no segments.
	 * 
	 * @return the head segment
	 */
	public BodySegment getHeadSegment() {
		if (segments.isEmpty()) {
			return null;
		}			
		return segments.get(segments.size() - 1);
	}

	/**
	 * Gets the tail segment of the lizard. Returns null if the segments have not
	 * been initialized or there are no segments.
	 * 
	 * @return the tail segment
	 */
	public BodySegment getTailSegment() {
		if (segments.isEmpty()) {
			return null; 
		}  
		return segments.get(0);
	}

	/**
	 * Gets the segment that is located at a given cell or null if there is no
	 * segment at that cell.
	 * 
	 * @param cell to look for lizard
	 * @return the segment that is on the cell or null if there is none
	 */
	public BodySegment getSegmentAt(Cell cell) {
		for (BodySegment segment : segments) { // iterates through BodySegment
			if (segment.getCell().equals(cell)) {
	            return segment; 
			}
		}
		return null; // segment not found
	}

	/**
	 * Get the segment that is in front of (closer to the head segment than) the
	 * given segment. Returns null if there is no segment ahead.
	 * 
	 * @param segment the starting segment
	 * @return the segment in front of the given segment or null
	 */
	public BodySegment getSegmentAhead(BodySegment segment) {
		int i = segments.indexOf(segment);
	    if (i == -1 || i == segments.size() - 1) {
	        return null;
	    }
	    return segments.get(i + 1);
	}

	/**
	 * Get the segment that is behind (closer to the tail segment than) the given
	 * segment. Returns null if there is not segment behind.
	 * 
	 * @param segment the starting segment
	 * @return the segment behind of the given segment or null
	 */
	public BodySegment getSegmentBehind(BodySegment segment) {
		int i = segments.indexOf(segment); // index of segment
        if (i <= 0) { 
            return null;
        }
        return segments.get(i - 1);
	}

	/**
	 * Gets the direction from the perspective of the given segment point to the
	 * segment ahead (in front of) of it. Returns null if there is no segment ahead
	 * of the given segment.
	 * 
	 * @param segment the starting segment
	 * @return the direction to the segment ahead of the given segment or null
	 */
	public Direction getDirectionToSegmentAhead(BodySegment segment) {
		BodySegment aheadSegment = getSegmentAhead(segment);
        if (aheadSegment == null) {
            return null;
        }
        return directionFromTo(segment.getCell(), aheadSegment.getCell());
	}

	/**
	 * Gets the direction from the perspective of the given segment point to the
	 * segment behind it. Returns null if there is no segment behind of the given
	 * segment.
	 * 
	 * @param segment the starting segment
	 * @return the direction to the segment behind of the given segment or null
	 */
	public Direction getDirectionToSegmentBehind(BodySegment segment) {
		BodySegment behindSegment = getSegmentBehind(segment);
        if (behindSegment == null) {
            return null;
        }
        return directionFromTo(segment.getCell(), behindSegment.getCell()); 
	}

	/**
	 * Gets the direction in which the head segment is pointing. This is the
	 * direction formed by going from the segment behind the head segment to the
	 * head segment. A lizard that does not have more than one segment has no
	 * defined head direction and returns null.
	 * 
	 * @return the direction in which the head segment is pointing or null
	 */
	public Direction getHeadDirection() {
		BodySegment head = getHeadSegment();
        if (head == null || segments.size() <= 1) {
            return null;
        }
        BodySegment segmentBehindHead = getSegmentBehind(head);
        if (segmentBehindHead == null) {
            return null;
        }
        return directionFromTo(segmentBehindHead.getCell(), head.getCell()); 
	}

	/**
	 * Gets the direction in which the tail segment is pointing. This is the
	 * direction formed by going from the segment ahead of the tail segment to the
	 * tail segment. A lizard that does not have more than one segment has no
	 * defined tail direction and returns null.
	 * 
	 * @return the direction in which the tail segment is pointing or null
	 */
	public Direction getTailDirection() {

		BodySegment tail = getTailSegment();
	    if (tail == null || segments.size() <= 1) {
	        return null;
	    }
	    BodySegment segmentAheadTail = getSegmentAhead(tail);
        if (segmentAheadTail == null) {
            return null;
        }
        return directionFromTo(segmentAheadTail.getCell(), tail.getCell()); 
	}
	
	/**
     * Determines the direction from one cell to another based on their coordinates.
     *
     * @param from the starting cell
     * @param to   the destination cell
     * @return the direction from the starting cell to the destination cell
     *         (UP, DOWN, LEFT, RIGHT), or null if the cells are not adjacent
     */
    private Direction directionFromTo(Cell from, Cell to) {
        int dx = to.getCol() - from.getCol();
        int dy = to.getRow() - from.getRow();

        if (dx == 0 && dy == -1) {
            return Direction.UP;
        }
		else if (dx == 0 && dy == 1) {
            return Direction.DOWN;
        }
		else if (dx == -1 && dy == 0) {
            return Direction.LEFT;
        }
		else if (dx == 1 && dy == 0) {
            return Direction.RIGHT;
        }
		else {
            return null;
        }
    }

	@Override
	public String toString() {
		String result = "";
		for (BodySegment seg : getSegments()) {
			result += seg + " ";
		}
		return result;
	}
}
