import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SudokuSolver {
	static int dimension = 9;

	static int puzzle[][]; /*= { { 3, 0, 6, 5, 0, 8, 4, 0, 0 },
			{ 5, 2, 0, 0, 0, 0, 0, 0, 0 }, { 0, 8, 7, 0, 0, 0, 0, 3, 1 }, //
			{ 0, 0, 3, 0, 1, 0, 0, 8, 0 }, //
			{ 9, 0, 0, 8, 6, 3, 0, 0, 5 }, //
			{ 0, 5, 0, 0, 9, 0, 6, 0, 0 }, //
			{ 1, 3, 0, 0, 0, 0, 2, 5, 0 }, //
			{ 0, 0, 0, 0, 0, 0, 0, 7, 4 }, //
			{ 0, 0, 5, 2, 0, 6, 3, 0, 0 } };
			*/

	/**
	 * Utility function to check whether @param value is valid for @param coord
	 */

	static boolean isValid(Coordinates coord, int value) {

		if (puzzle[coord.x][coord.y] != 0) {
			throw new RuntimeException(
					"Cannot call for coord which already has a value");
		}

		// if v present row, return false
		for (int c = 0; c < 9; c++) {
			if (puzzle[coord.x][c] == value)
				return false;
		}

		// if v present in col, return false
		for (int r = 0; r < 9; r++) {
			if (puzzle[r][coord.y] == value)
				return false;
		}

		// if v present in puzzle, return false

		// to get the puzzle we should calculate (x1,y1) (x2,y2)
		int x1 = 3 * (coord.x / 3);
		int y1 = 3 * (coord.y / 3);
		int x2 = x1 + 2;
		int y2 = y1 + 2;

		for (int x = x1; x <= x2; x++)
			for (int y = y1; y <= y2; y++)
				if (puzzle[x][y] == value)
					return false;

		// if value not present in row, col and bounding box, return true
		return true;
	}

	// simple function to get the next coord
	// read for yourself, very simple and straight forward
	static Coordinates getNextCell(Coordinates cur) {

		int x = cur.x;
		int y = cur.y;

		// next coord => col++
		y++;

		// if col > 8, then col = 0, row++
		// reached end of row, got to next row
		if (y > 8) {
			// goto next line
			y = 0;
			x++;
		}

		// reached end of matrix, return null
		if (x > 8)
			return null; // reached end

		Coordinates next = new Coordinates(x, y);
		return next;
	}

	// everything is put together here
	// very simple solution
	// must return true, if the soduku is solved, return false otherwise
	static boolean solve(Coordinates cur) {

		// if the coord is null, we have reached the end
		if (cur == null)
			return true;

		// if puzzle[cur] already has a value, there is nothing to solve here,
		// continue on to next coord
		if (puzzle[cur.x][cur.y] != 0) {
			// return whatever is being returned by solve(next)
			// i.e the state of soduku's solution is not being determined by
			// this coord, but by other cells
			return solve(getNextCell(cur));
		}

		// this is where each possible value is being assigned to the coord, and
		// checked if a solutions could be arrived at.

		// if puzzle[cur] doesn't have a value
		// try each possible value
		for (int i = 1; i <= 9; i++) {
			// check if valid, if valid, then update
			boolean valid = isValid(cur, i);

			if (!valid) // i not valid for this coord, try other values
				continue;

			// assign here
			puzzle[cur.x][cur.y] = i;

			// continue with next coord
			boolean solved = solve(getNextCell(cur));
			// if solved, return, else try other values
			if (solved)
				return true;
			else
				puzzle[cur.x][cur.y] = 0; // reset
			// continue with other possible values
		}

		// if you reach here, then no value from 1 - 9 for this coord can solve
		// return false
		return false;
	}

	public static void main(String[] args) throws FileNotFoundException {
		Scanner keyboard = new Scanner(System.in);
		System.out.print("Enter a file name: ");
		String filename = keyboard.nextLine();
		File file = new File(filename);
		Scanner sc = new Scanner(file);
		int xVal=0, yVal=0;
		while (sc.hasNext()) {
			String next = sc.next();
				
				int i = Integer.parseInt(next);
				puzzle[xVal][yVal]=i;
				if(yVal<9){
				if(xVal<9){
					xVal++;
				}
				else{
					xVal=0;
					yVal++;
				}
				}
				System.out.print(i);
			
			
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

	// utility to print the puzzle
	static void printPuzzle(int puzzle[][]) {
		boolean once = true;
		for (int x = 0; x < dimension; x++) {
			for (int y = 0; y < dimension; y++) {
				if ((x) % 3 == 0 && once && x != 0) {
					System.out.println("                    ");
					once = false;
				}
				System.out.print(puzzle[x][y] + " ");
				if ((y + 1) % 3 == 0 && y != 8) {
					System.out.print("  ");
				}

			}
			System.out.println();
			once = true;
		}
	}
}
