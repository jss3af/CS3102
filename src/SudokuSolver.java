//import java.io.File;
//import java.io.FileNotFoundException;
//import java.util.ArrayList;
//import java.util.Scanner;
//
//public class SudokuSolver {
//	private int dimension;
//	public ArrayList<int[][]> solutions;
//	public int puzzle[][];
//
//	public SudokuSolver(File file, int given) throws FileNotFoundException {
//		dimension = given;
//		this.puzzle = readInAndMakePuzzle(file);
//		solutions = new ArrayList<int[][]>();
//	}
//	
//	public SudokuSolver(int puzzle[][], int given) {
//		dimension = given;
//		this.puzzle = puzzle;
//		solutions = new ArrayList<int[][]>();
//	}
//
//	boolean isValid(Coordinates coord, int value) {
//		if (this.puzzle[coord.x][coord.y] != 0) {
//			throw new RuntimeException(
//					"Cannot call for coord which already has a value");
//		}
//		for (int column = 0; column < dimension; column++) {
//			if (puzzle[coord.x][column] == value)
//				return false;
//		}
//		for (int row = 0; row < dimension; row++) {
//			if (puzzle[row][coord.y] == value)
//				return false;
//		}
//		int x1 = ((int) (Math.sqrt(dimension)) * (coord.x / ((int) Math
//				.sqrt(dimension))));
//		int y1 = ((int) (Math.sqrt(dimension)) * (coord.y / ((int) Math
//				.sqrt(dimension))));
//		int x2 = x1 + ((int) Math.sqrt(dimension)) - 1;
//		int y2 = y1 + ((int) Math.sqrt(dimension)) - 1;
//		for (int x = x1; x <= x2; x++)
//			for (int y = y1; y <= y2; y++)
//				if (puzzle[x][y] == value)
//					return false;
//		return true;
//	}
//
//	int[][] copyPuzzle(int a[][]) {
//		int copy[][] = new int[dimension][dimension];
//		for (int x = 0; x < dimension; x++) {
//			for (int y = 0; y < dimension; y++) {
//				copy[x][y] = this.puzzle[x][y];
//			}
//		}
//		return copy;
//	}
//
//	Coordinates getNextCell(Coordinates cur) {
//		int x = cur.x;
//		int y = cur.y;
//		y++;
//		if (y > (dimension-1)) {
//			y = 0;
//			x++;
//		}
//		if (x > (dimension-1))
//			return null; 
//
//		Coordinates next = new Coordinates(x, y);
//		return next;
//	}
//
//	boolean solve(Coordinates cur) {
//		if (cur == null)
//			return true;
//
//		if (this.puzzle[cur.x][cur.y] != 0) {
//			if (cur.x == (this.dimension - 1) && cur.y == (this.dimension - 1)) {
//				int[][] copy = copyPuzzle(this.puzzle);
//				this.solutions.add(copy);
//			}
//			return solve(getNextCell(cur));
//		}
//		for (int i = 1; i <= this.dimension; i++) {
//			boolean valid = isValid(cur, i);
//			if (!valid)
//				continue;
//			this.puzzle[cur.x][cur.y] = i;
//			boolean solved;
//			solved = solve(getNextCell(cur));
//			if (solved) {
//
//				if (cur.x == (this.dimension - 1)
//						&& cur.y == (this.dimension - 1)) {
//					int[][] copy = copyPuzzle(this.puzzle);
//					this.solutions.add(copy);
//					
//				}
//				this.puzzle[cur.x][cur.y] = 0;
//				return true;
//			} else {
//				this.puzzle[cur.x][cur.y] = 0;
//			}
//		}
//		return false;
//	}
//	
//	public int[][] readInAndMakePuzzle(File file) throws FileNotFoundException{
//		Scanner sc = new Scanner(file);
//		int[] numArray = new int[(dimension * dimension)];
//		int puzzle[][] = new int[dimension][dimension];
//		int counter = 0;
//		while (sc.hasNext()) {
//			String next = sc.next();
//			int i = Integer.parseInt(next);
//			numArray[counter] = i;
//			counter++;
//		}
//		sc.close();
//		counter = 0;
//		for (int x = 0; x < dimension; x++) {
//			for (int y = 0; y < dimension; y++) {
//				puzzle[x][y] = numArray[counter];
//				counter++;
//			}
//		}
//		return puzzle;
//	}
//	
//	public void solve(){
//		
//		boolean solved = solve((new Coordinates(0,0)));
//		if (!solved) {
//			System.out.println("SUDOKU cannot be solved.");
//			System.exit(0);
//		}
//	}
//
//	public void printSolutions(){
//		solve();
//		for (int[][] thePuzzle : this.solutions) {
//			System.out.println();
//			printPuzzle(thePuzzle);
//		}
//	}
//	
//	public static void main(String[] args) throws FileNotFoundException {
//		Scanner keyboard = new Scanner(System.in);
//		System.out.print("Enter a file name: ");
//		String filename = keyboard.nextLine();
//		System.out.print("Enter the number of rows and columns: ");
//		int dimension = Integer.parseInt(keyboard.next());
//		File file = new File(filename);
//		SudokuSolver solver = new SudokuSolver(file, dimension);
//		solver.printSolutions();
//		keyboard.close();
//	}
//
//	public ArrayList<int[][]> returnSolutions() {
//		solve();
//		return this.solutions;
//	}
//
//	public void printPuzzle(int puzzle[][]) {
//		boolean once = true;
//		for (int x = 0; x < dimension; x++) {
//			for (int y = 0; y < dimension; y++) {
//				if ((x) % ((int) Math.sqrt(dimension)) == 0 && once && x != 0) {
//					System.out.println("                    ");
//					once = false;
//				}
//				int num = puzzle[x][y];
//				if (num < 10)
//					System.out.print(num + "  ");
//				else
//					System.out.print(num + " ");
//				if ((y + 1) % ((int) Math.sqrt(dimension)) == 0
//						&& y != (dimension - 1)) {
//					System.out.print("   ");
//				}
//			}
//			System.out.println();
//			once = true;
//		}
//	}
//}
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class SudokuSolver {
	public int dimension;
	public ArrayList<int[][]> solutions;
	public ArrayList<Coordinates> emptyCells;
	public int puzzle[][];

	public SudokuSolver(File file, int given) throws FileNotFoundException {
		dimension = given;
		this.puzzle = readInAndMakePuzzle(file);
		solutions = new ArrayList<int[][]>();
	}
	
	public SudokuSolver(int puzzle[][], int given) {
		dimension = given;
		this.puzzle = puzzle;
		solutions = new ArrayList<int[][]>();
	}
	
	boolean isValid(Coordinates coord, int value) {
		if (this.puzzle[coord.x][coord.y] != 0) {
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
		int x1 = ((int) (Math.sqrt(dimension)) * (coord.x / ((int) Math
				.sqrt(dimension))));
		int y1 = ((int) (Math.sqrt(dimension)) * (coord.y / ((int) Math
				.sqrt(dimension))));
		int x2 = x1 + ((int) Math.sqrt(dimension)) - 1;
		int y2 = y1 + ((int) Math.sqrt(dimension)) - 1;
		for (int x = x1; x <= x2; x++)
			for (int y = y1; y <= y2; y++)
				if (puzzle[x][y] == value)
					return false;
		return true;
	}

	Coordinates getNextCell(Coordinates cur) {
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
	
	
	boolean solve(Coordinates cur) {
		if (cur == null)
			return true;

		if (this.puzzle[cur.x][cur.y] != 0) {
			if (cur.x == (this.dimension - 1) && cur.y == (this.dimension - 1)) {
				int[][] copy = copyPuzzle(this.puzzle);
				this.solutions.add(copy);
			}
			return solve(getNextCell(cur));
		}
		for (int i = 1; i <= this.dimension; i++) {
			boolean valid = isValid(cur, i);
			if (!valid)
				continue;
			this.puzzle[cur.x][cur.y] = i;
			boolean solved;
			solved = solve(getNextCell(cur));
			if (solved) {

				if (cur.x == (this.dimension - 1)
						&& cur.y == (this.dimension - 1)) {
					int[][] copy = copyPuzzle(this.puzzle);
					this.solutions.add(copy);
					
				}
				this.puzzle[cur.x][cur.y] = 0;
				return true;
			} else {
				this.puzzle[cur.x][cur.y] = 0;
			}
		}
		return false;
	}
	
	boolean isValid(Coordinates coord, int value, int[][] puzzle) {
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

		int x1 = ((int) (Math.sqrt(dimension)) * (coord.x / ((int) Math.sqrt(dimension))));
		int y1 = ((int) (Math.sqrt(dimension)) * (coord.y / ((int) Math.sqrt(dimension))));
		int x2 = x1 + ((int) Math.sqrt(dimension)) - 1;
		int y2 = y1 + ((int) Math.sqrt(dimension)) - 1;

		for (int x = x1; x <= x2; x++)
			for (int y = y1; y <= y2; y++)
				if (puzzle[x][y] == value)
					return false;
		return true;
	}

	int[][] copyPuzzle(int a[][]) {
		int copy[][] = new int[dimension][dimension];
		for (int x = 0; x < dimension; x++) {
			for (int y = 0; y < dimension; y++) {
				copy[x][y] = this.puzzle[x][y];
			}
		}
		return copy;
	}

    public ArrayList<Coordinates> getEmptyCells() {
    	emptyCells = new ArrayList<Coordinates>();
        for (int x = 0; x < dimension; x++) {
            for (int y = 0; y < dimension; y++) {
                if (puzzle[x][y] == 0) {
                	Coordinates coord = new Coordinates(x, y);
                    emptyCells.add(coord);
                }
            }
        }
        return emptyCells;
    }
	
    // Recursive method to return all possible solutions to puzzle
	public void solve(ArrayList<Coordinates> emptyCells, int emptyIndex) {
		
		// Base Case - solution has been reached
        if (emptyIndex >= emptyCells.size()) {
        	solutions.add(copyPuzzle(puzzle));
        	//printPuzzle(puzzle);
            return;
        }
        
        Coordinates cur = new Coordinates(emptyCells.get(emptyIndex).x, emptyCells.get(emptyIndex).y);
        
        // Try all possible values for current empty cell
        for (int val = 1; val <= dimension; val++) {
			if (isValid(cur, val, puzzle)) {
				puzzle[cur.x][cur.y] = val;
				int nextEmptyIndex = emptyIndex + 1;
	            solve(emptyCells, nextEmptyIndex);
	            // Backtrack by replacing the current cell value with 0
	            puzzle[cur.x][cur.y] = 0;
			}
        }
	}
	public void solve(){
		emptyCells = getEmptyCells();
		solve(emptyCells, 0);
		if (solutions.size() == 0) {
			System.out.println("SUDOKU cannot be solved.");
		}
		
	}
	public int[][] readInAndMakePuzzle(File file) throws FileNotFoundException{
		Scanner sc = new Scanner(file);
		int[] numArray = new int[(dimension * dimension)];
		int puzzle[][] = new int[dimension][dimension];
		int counter = 0;
		while (sc.hasNext()) {
			String next = sc.next();
			int i = Integer.parseInt(next);
			numArray[counter] = i;
			counter++;
		}
		sc.close();
		counter = 0;
		for (int x = 0; x < dimension; x++) {
			for (int y = 0; y < dimension; y++) {
				puzzle[x][y] = numArray[counter];
				counter++;
			}
		}
		return puzzle;
	}

	public static void main(String[] args) throws FileNotFoundException {
		Scanner keyboard = new Scanner(System.in);
		System.out.print("Enter a file name: ");
		String filename = keyboard.nextLine();
		System.out.print("Enter '0' for traditional Sudoku or '1' for Samurai Sudoku:");
		int type = Integer.parseInt(keyboard.next());
		System.out.print("Enter the number of rows and columns: ");
		int dimension = Integer.parseInt(keyboard.next());
		keyboard.close();
		
		File file = new File(filename);
		
		// Traditional Sudoku
		if(type == 0) {
			SudokuSolver solver = new SudokuSolver(file, dimension);
			for(int num[][] : solver.returnSolutions()){
				System.out.println();
				solver.printPuzzle(num);
			}
		}
		
		// Samurai Sudoku
//		if(type == 1) {
//			int[] numArray = new int[(dimension*dimension*5)];
//			int counter = 0;
//			while (sc.hasNext()) {
//				String next = sc.next();
//				int i = Integer.parseInt(next);
//				numArray[counter] = i;
//				counter++;
//			}
//
//			//sc.close();
//			counter = 0;
//			int p1[][] = new int[dimension][dimension];
//			int p2[][] = new int[dimension][dimension];
//			int p3[][] = new int[dimension][dimension];
//			int p4[][] = new int[dimension][dimension];
//			int p5[][] = new int[dimension][dimension];
//			
//			for(int x = 0; x < dimension; x++){
//				for(int y = 0; y < dimension; y++){
//					p1[x][y] = numArray[counter];
//					counter++;
//				}
//			}
//			for(int x = 0; x < dimension; x++){
//				for(int y = 0; y < dimension; y++){
//					p2[x][y] = numArray[counter];
//					counter++;
//				}
//			}
//			for(int x = 0; x < dimension; x++){
//				for(int y = 0; y < dimension; y++){
//					p3[x][y] = numArray[counter];
//					counter++;
//				}
//			}
//			for(int x = 0; x < dimension; x++){
//				for(int y = 0; y < dimension; y++){
//					p4[x][y] = numArray[counter];
//					counter++;
//				}
//			}
//			for(int x = 0; x < dimension; x++){
//				for(int y = 0; y < dimension; y++){
//					p5[x][y] = numArray[counter];
//					counter++;
//				}
//			}
//			
//			// Solve p5
//			SudokuSolver solver5 = new SudokuSolver(p5, dimension);
//			ArrayList<Coordinates> emptyCells5 = solver5.getEmptyCells(p5);
//			solver5.solve(p5, emptyCells5, 0);
//			
//			// Update p1-p4 with solutions from p5
//			int d = (int) Math.sqrt(dimension);
//			int box = dimension - d; // int value of first index (either row or col) of last box
//			
//			// Update p1
//			for(int row = box; row < dimension; row++) {
//				for(int col = box; col < dimension; col++) {
//					// iterate through p5
//					for(int x = 0; x < d; x++) { 
//						for(int y = 0; y < d; y++) {
//							p1[row][col] = p5[x][y];
//						}
//					}
//				}
//			}
//			// Update p2
//			for(int row = box; row < dimension; row++) {
//				for(int col = 0; col < d; col++) {
//					for(int x = 0; x < d; x++) {
//						for(int y = box; y < dimension; y++) {
//							p2[row][col] = p5[x][y];
//						}
//					}
//				}
//			}
//			// Update p3
//			for(int row = 0; row < d; row++) {
//				for(int col = box; col < dimension; col++) {
//					for(int x = box; x < dimension; x++) {
//						for(int y = 0; y < d; y++) {
//							p3[row][col] = p5[x][y];
//						}
//					}
//				}
//			}
//			// Update p4
//			for(int row = 0; row < d; row++) {
//				for(int col = 0; col < d; col++) {
//					for(int x = box; x < dimension; x++) {
//						for(int y = box; y < dimension; y++) {
//							p4[row][col] = p5[x][y];
//						}
//					}
//				}
//			}
//			
//			// Solve for p1-p4
//			
//			SudokuSolver solver1 = new SudokuSolver(p1, dimension);
//			SudokuSolver solver2 = new SudokuSolver(p2, dimension);
//			SudokuSolver solver3 = new SudokuSolver(p3, dimension);
//			SudokuSolver solver4 = new SudokuSolver(p4, dimension);
//			
//			ArrayList<Coordinates> emptyCells1 = solver1.getEmptyCells(p1);
//			ArrayList<Coordinates> emptyCells2 = solver2.getEmptyCells(p2);
//			ArrayList<Coordinates> emptyCells3 = solver3.getEmptyCells(p3);
//			ArrayList<Coordinates> emptyCells4 = solver4.getEmptyCells(p4);
//
//			solver1.solve(p1, emptyCells1, 0);
//			solver2.solve(p2, emptyCells2, 0);
//			solver3.solve(p3, emptyCells3, 0);
//			solver4.solve(p4, emptyCells4, 0);
//
//			solver1.printPuzzle(p1);
//			System.out.println();
//			solver2.printPuzzle(p2);
//			System.out.println();
//			solver3.printPuzzle(p3);
//			System.out.println();
//			solver4.printPuzzle(p4);
//			System.out.println();
//			solver5.printPuzzle(p5);
//			
//			if (solver1.solutions.size() == 0 || solver2.solutions.size() == 0 
//					|| solver3.solutions.size() == 0 || solver4.solutions.size() == 0 
//					|| solver5.solutions.size() == 0) {
//				System.out.println("SUDOKU cannot be solved.");
//				return;
//			}
			// Print Samurai method call
			
		//}
	}
	
	public void printSolutions(){
		solve();
		for (int[][] thePuzzle : this.solutions) {
			System.out.println();
			printPuzzle(thePuzzle);
		}
	}

	public ArrayList<int[][]> returnSolutions() {
		solve();
		return this.solutions;
	}

	public void printPuzzle(int puzzle[][]) {
		boolean once = true;
		for (int x = 0; x < dimension; x++) {
			for (int y = 0; y < dimension; y++) {
				if ((x) % ((int) Math.sqrt(dimension)) == 0 && once && x != 0) {	
					System.out.println("                    ");
					once = false;
				}
				int num = puzzle[x][y];
				if (num < 10)
					System.out.print(num + "  ");
				else
					System.out.print(num + " ");
				if ((y + 1) % ((int) Math.sqrt(dimension)) == 0 && y != (dimension - 1)) {
					System.out.print("   ");
				}
			}
			System.out.println();
			once = true;
		}
		System.out.println();
	}
}