
public class Coordinates {
//public class Coordinates implements Comparable<Coordinates> {
	int x, y;

	public Coordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}
	  
	@Override
	public String toString() {
		return "(X: " + x + ", Y: " + y + ")";
	}

//	@Override
//	public int compareTo(Coordinates c) {
//		return this.possibleValues.size() - c.possibleValues.size();
//	}
}