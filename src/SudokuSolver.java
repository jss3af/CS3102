import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SudokuSolver {
	static int dimension= 9;
	static int puzzle[][]; 
	
	static boolean isValid(Coordinates coord, int value) {
		if (puzzle[coord.x][coord.y] != 0) {
			throw new RuntimeException(
					"Cannot call for coord which already has a value");
		}
		for (int column = 0; column < dimension; column++) {
			if (puzzle[coord.x][column] == value)
				return false;
		}
		for (int row = 0; row < dimension; row++) {
			if (puzzle[row][coord.y] == value)
				return false;
		}
		int x1 = 3 * (coord.x / 3);
		int y1 = 3 * (coord.y / 3);
		int x2 = x1 + 2;
		int y2 = y1 + 2;
		for (int x = x1; x <= x2; x++)
			for (int y = y1; y <= y2; y++)
				if (puzzle[x][y] == value)
					return false;
		return true;
	}
	
	static Coordinates getNextCell(Coordinates cur) {
		int x = cur.x;
		int y = cur.y;
		y++;
		if (y > (dimension-1)) {
			y = 0;
			x++;
		}
		if (x > (dimension-1))
			return null; 

		Coordinates next = new Coordinates(x, y);
		return next;
	}
	
	static boolean solve(Coordinates cur) {
		if (cur == null)
			return true;
		if (puzzle[cur.x][cur.y] != 0) {
			return solve(getNextCell(cur));
		}
		for (int i = 1; i <= dimension; i++) {
			boolean valid = isValid(cur, i);
			if (!valid) 
				continue;
			puzzle[cur.x][cur.y] = i;
			boolean solved = solve(getNextCell(cur));
			if (solved)
				return true;
			else
				puzzle[cur.x][cur.y] = 0; 
		}
		return false;
	}

	public static void main(String[] args) throws FileNotFoundException {
		Scanner keyboard = new Scanner(System.in);
		System.out.print("Enter a file name: ");
		String filename = keyboard.nextLine();
		puzzle = new int[dimension][dimension];
		File file = new File(filename);
		Scanner sc = new Scanner(file);
		int xVal = 0, yVal = 0;
		while (sc.hasNext()) {
			String next = sc.next();
			int i = Integer.parseInt(next);
			puzzle[xVal][yVal] = i;
			if (xVal < (dimension-1)) {
				if (yVal < (dimension-1)) {
					yVal++;
				} else {
					yVal = 0;
					xVal++;
				}
			}
		}
		System.out.println();
		sc.close();
		boolean solved = solve(new Coordinates(0, 0));
		if (!solved) {
			System.out.println("SUDOKU cannot be solved.");
			return;
		}
		printPuzzle(puzzle);
	}

	static void printPuzzle(int puzzle[][]) {
		boolean once = true;
		for (int x = 0; x < dimension; x++) {
			for (int y = 0; y < dimension; y++) {
				if ((x) % 3 == 0 && once && x != 0) {
					System.out.println("                    ");
					once = false;
				}
				System.out.print(puzzle[x][y] + " ");
				if ((y + 1) % 3 == 0 && y != (dimension-1)) {
					System.out.print("  ");
				}
			}
			System.out.println();
			once = true;
		}
	}
}
