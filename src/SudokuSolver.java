import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
//import java.util.Collections;
import java.util.Scanner;
//import java.lang.*;

public class SudokuSolver {
	private static int dimension; 
	private ArrayList<int[][]> solutions; 
	private ArrayList<Coordinates> emptyCells;
	private int puzzle[][];
	private int p1[][];
	private int p2[][];
	private int p3[][];
	private int p4[][];
	private int p5[][];
	
	public SudokuSolver(int [][] puzzle, int given, ArrayList<int[][]> solutions){
		this.puzzle = puzzle;
		dimension = given;
		this.solutions = new ArrayList<int [][]>();
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
		int x1 = ((int) (Math.sqrt(dimension)) * (coord.x / ((int)Math.sqrt(dimension))));
		int y1 = ((int) (Math.sqrt(dimension)) * (coord.y / ((int)Math.sqrt(dimension))));
		int x2 = x1 +((int)Math.sqrt(dimension))-1;
		int y2 = y1 +((int)Math.sqrt(dimension))-1;
		for (int x = x1; x <= x2; x++)
			for (int y = y1; y <= y2; y++)
				if (puzzle[x][y] == value)
					return false;
		return true;
	}
	
	int[][] copyPuzzle(int a[][]){
		int copy[][] = new int[dimension][dimension];
		for (int x = 0; x < dimension; x++) {
			for (int y = 0; y < dimension; y++) {
				copy [x][y] = this.puzzle[x][y];
			}
		}
		return copy;
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
	
	// Method to return ArrayList of empty cells
    ArrayList<Coordinates> getEmptyCells(int[][] p) {
    	emptyCells = new ArrayList<Coordinates>();
        for (int x = 0; x < dimension; x++) {
            for (int y = 0; y < dimension; y++) {
                if (p[x][y] == 0) {
                	Coordinates coord = new Coordinates(x, y);
                    emptyCells.add(coord);
                }
            }
        }
        return emptyCells;
    }
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Solve method - i holds the current index of the emptyCells ArrayList
	private void solve(int[][] puzzle, ArrayList<Coordinates> emptyCells, int currentEmptyCell) {
		
		// Base Case: When the current chain of values has arrived at a solution
        if (currentEmptyCell >= emptyCells.size()) {
        	solutions.add(copyPuzzle(puzzle));
        	//printPuzzle(puzzle);
            return;
        }

        // Current emptySpot that is being considered to be filled
        Coordinates cur = new Coordinates(emptyCells.get(currentEmptyCell).x, emptyCells.get(currentEmptyCell).y);
        
        // Try all the possible values for current empty cell
        for (int val = 1; val <= dimension; val++) {
			if (isValid(cur, val, puzzle)) {
				puzzle[cur.x][cur.y] = val;
				int nextEmptyCell = currentEmptyCell + 1;
	            solve(puzzle, emptyCells, nextEmptyCell);
	            // backtrack
	            puzzle[cur.x][cur.y] = 0;
			}
        }
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
		Scanner sc = new Scanner(file);
		
		ArrayList<int[][]> solutions = new ArrayList<int[][]>();
		//ArrayList<Coordinates> emptyCells = new ArrayList<Coordinates>();
		
		// Traditional Sudoku
		if(type == 0) {
			int[] numArray = new int[(dimension*dimension)];
			int counter = 0;
			while (sc.hasNext()) {
				String next = sc.next();
				int i = Integer.parseInt(next);
				numArray[counter] = i;
				counter++;
			}
			sc.close();
			counter = 0;
			int puzzle[][] = new int[dimension][dimension];
			
			for(int x = 0; x < dimension; x++){
				for(int y = 0; y < dimension; y++){
					puzzle[x][y] = numArray[counter];
					counter++;
				}
			}
			
			SudokuSolver solver = new SudokuSolver(puzzle, dimension, solutions);
			solver.emptyCells = solver.getEmptyCells(puzzle);
			solver.solve(puzzle, solver.emptyCells, 0);
			if (solver.solutions.size() == 0) {
				System.out.println("SUDOKU cannot be solved.");
			}
			for(int i = 0; i < solver.solutions.size(); i++){
				System.out.println();
				solver.printPuzzle(solver.solutions.get(i));
			}
		}
		
		// Samurai Sudoku
		if(type == 1) {
			int[] numArray = new int[(dimension*dimension*5)];
			int counter = 0;
			while (sc.hasNext()) {
				String next = sc.next();
				int i = Integer.parseInt(next);
				numArray[counter] = i;
				counter++;
			}
//			for(int t=0; t<numArray.length; t++) {
//				System.out.println(numArray[t]);
//			}
			
			sc.close();
			counter = 0;
			int p1[][] = new int[dimension][dimension];
			int p2[][] = new int[dimension][dimension];
			int p3[][] = new int[dimension][dimension];
			int p4[][] = new int[dimension][dimension];
			int p5[][] = new int[dimension][dimension];
			
			for(int x = 0; x < dimension; x++){
				for(int y = 0; y < dimension; y++){
					p1[x][y] = numArray[counter];
					counter++;
				}
			}
			for(int x = 0; x < dimension; x++){
				for(int y = 0; y < dimension; y++){
					p2[x][y] = numArray[counter];
					counter++;
				}
			}
			for(int x = 0; x < dimension; x++){
				for(int y = 0; y < dimension; y++){
					p3[x][y] = numArray[counter];
					counter++;
				}
			}
			for(int x = 0; x < dimension; x++){
				for(int y = 0; y < dimension; y++){
					p4[x][y] = numArray[counter];
					counter++;
				}
			}
			for(int x = 0; x < dimension; x++){
				for(int y = 0; y < dimension; y++){
					p5[x][y] = numArray[counter];
					counter++;
				}
			}
			
			// Solve p5
			SudokuSolver solver5 = new SudokuSolver(p5, dimension, solutions);
			ArrayList<Coordinates> emptyCells5 = solver5.getEmptyCells(p5);
			solver5.solve(p5, emptyCells5, 0);
			
//			for(int i = 0; i < solver5.solutions.size(); i++){
//				System.out.println();
//				solver5.printPuzzle(solver5.solutions.get(i));
//			}
			
			// Update p1-p4 with solutions from p5
			int d = (int) Math.sqrt(dimension);
			int box = dimension - d; // int value of first index (either row or col) of last box
			
			// Update p1
			for(int row = box; row < dimension; row++) {
				for(int col = box; col < dimension; col++) {
					// iterate through p5
					for(int x = 0; x < d; x++) { 
						for(int y = 0; y < d; y++) {
							p1[row][col] = p5[x][y];
						}
					}
				}
			}
			// Update p2
			for(int row = box; row < dimension; row++) {
				for(int col = 0; col < d; col++) {
					for(int x = 0; x < d; x++) {
						for(int y = box; y < dimension; y++) {
							p2[row][col] = p5[x][y];
						}
					}
				}
			}
			// Update p3
			for(int row = 0; row < d; row++) {
				for(int col = box; col < dimension; col++) {
					for(int x = box; x < dimension; x++) {
						for(int y = 0; y < d; y++) {
							p3[row][col] = p5[x][y];
						}
					}
				}
			}
			// Update p4
			for(int row = 0; row < d; row++) {
				for(int col = 0; col < d; col++) {
					for(int x = box; x < dimension; x++) {
						for(int y = box; y < dimension; y++) {
							p4[row][col] = p5[x][y];
						}
					}
				}
			}
			
			// Solve for p1-p4
			
			SudokuSolver solver1 = new SudokuSolver(p1, dimension, solutions);
			SudokuSolver solver2 = new SudokuSolver(p2, dimension, solutions);
			SudokuSolver solver3 = new SudokuSolver(p3, dimension, solutions);
			SudokuSolver solver4 = new SudokuSolver(p4, dimension, solutions);
			
			ArrayList<Coordinates> emptyCells1 = solver1.getEmptyCells(p1);
			ArrayList<Coordinates> emptyCells2 = solver2.getEmptyCells(p2);
			ArrayList<Coordinates> emptyCells3 = solver3.getEmptyCells(p3);
			ArrayList<Coordinates> emptyCells4 = solver4.getEmptyCells(p4);

			solver1.solve(p1, emptyCells1, 0);
			solver2.solve(p2, emptyCells2, 0);
			solver3.solve(p3, emptyCells3, 0);
			solver4.solve(p4, emptyCells4, 0);

			solver1.printPuzzle(p1);
			System.out.println();
			solver2.printPuzzle(p2);
			System.out.println();
			solver3.printPuzzle(p3);
			System.out.println();
			solver4.printPuzzle(p4);
			System.out.println();
			solver5.printPuzzle(p5);
			
			if (solver1.solutions.size() == 0 || solver2.solutions.size() == 0 
					|| solver3.solutions.size() == 0 || solver4.solutions.size() == 0 
					|| solver5.solutions.size() == 0) {
				System.out.println("SUDOKU cannot be solved.");
				return;
			}
			// Print Samurai method call
			
		}
	}

	void printPuzzle(int puzzle[][]) {
		boolean once = true;
		for (int x = 0; x < dimension; x++) {
			for (int y = 0; y < dimension; y++) {
				if ((x) % ((int)Math.sqrt(dimension)) == 0 && once && x != 0) {
					System.out.println("                    ");
					once = false;
				}
				int num = puzzle[x][y];
				if(num<10)
					System.out.print(num + "  ");
				else
					System.out.print(num + " ");
				if ((y + 1) % ((int)Math.sqrt(dimension)) == 0 && y != (dimension-1)) {
					System.out.print("   ");
				}
			}
			System.out.println();
			once = true;
		}
		System.out.println();
	}
}