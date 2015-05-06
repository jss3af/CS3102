
public class Puzzle {
	int[][] p;
	int d; // dimension

	public Puzzle(int[][] p, int d) {
		this.p = p;
		this.d = d;
	}

	@Override
	public String toString() {
		return "(P: " + p + ")";
	}
}
