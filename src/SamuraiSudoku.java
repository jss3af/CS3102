import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class SamuraiSudoku {
	ArrayList<int[][]> samuraiSolutions;
	public int dimension;
	int p1[][];
	int p2[][];
	int p3[][];
	int p4[][];
	int p5[][];
	int big;
	public SamuraiSudoku(File file, int dimension) throws FileNotFoundException{
		this.dimension=dimension;
		readInAndAssignValues(file);
		solveSamurai();
	}
	
	public void readInAndAssignValues(File file) throws FileNotFoundException{
		Scanner keyboard = new Scanner(file);
		int[] numArray = new int[(dimension*dimension*5)];
		int counter = 0;
		while (keyboard.hasNext()) {
			String next = keyboard.next();
			int i = Integer.parseInt(next);
			numArray[counter] = i;
			counter++;
		}

		counter = 0;
		p1 = new int[dimension][dimension];
		p2 = new int[dimension][dimension];
		p3 = new int[dimension][dimension];
		p4 = new int[dimension][dimension];
		p5 = new int[dimension][dimension];
		
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
	}
	int[][] copyPuzzle(int a[][]) {
		int copy[][] = new int[dimension][dimension];
		for (int x = 0; x < dimension; x++) {
			for (int y = 0; y < dimension; y++) {
				copy[x][y] = a[x][y];
			}
		}
		return copy;
	}
	
	boolean isUpdateValid(Coordinates coord, int value, int[][] puzzle) {
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

	public int[][] updateSamurai(int[][] p, int[][] s, int dimension, int region) {
		int d = (int) Math.sqrt(dimension);
		int box = dimension - d; // int value of first index (either row or col) of last box
		int original[][] = copyPuzzle(p);
		if (region == 1) {
			// Update p1
			for(int row = box; row < dimension; row++) {
				for(int col = box; col < dimension; col++) {
					// iterate through p5
					for(int x = 0; x < d; x++) { 
						for(int y = 0; y < d; y++) {
							Coordinates c = new Coordinates(row, col);
							if ( isUpdateValid(c, s[x][y], p) ) {
								p[row][col] = s[x][y];
							}
							else{
								return original;
							}
						}
					}
				}
			}
		}
		if (region == 2) {
			// Update p2
			for(int row = box; row < dimension; row++) {
				for(int col = 0; col < d; col++) {
					for(int x = 0; x < d; x++) {
						for(int y = box; y < dimension; y++) {
							Coordinates c = new Coordinates(row, col);
							if ( isUpdateValid(c, s[x][y], p) ) {
								p[row][col] = s[x][y];
							}
							else{
								return original;
							}
						}
					}
				}
			}
		}
		if (region == 3) {
			// Update p3
			for(int row = 0; row < d; row++) {
				for(int col = box; col < dimension; col++) {
					for(int x = box; x < dimension; x++) {
						for(int y = 0; y < d; y++) {
							Coordinates c = new Coordinates(row, col);
							if ( isUpdateValid(c, s[x][y], p) ) {
								p[row][col] = s[x][y];
							}
							else{
								return original;
							}
						}
					}
				}
			}
		}
		if (region == 4) {
			// Update p4
			for(int row = 0; row < d; row++) {
				for(int col = 0; col < d; col++) {
					for(int x = box; x < dimension; x++) {
						for(int y = box; y < dimension; y++) {
							Coordinates c = new Coordinates(row, col);
							if ( isUpdateValid(c, s[x][y], p) ) {
								p[row][col] = s[x][y];
							}
							else{
								return original;
							}
						}
					}
				}
			}
		}
		return p;
	}

	void solveSamurai() {
		
		samuraiSolutions = new ArrayList<int[][]>();
		
		SudokuSolver solver5 = new SudokuSolver(p5, dimension);
		ArrayList<Coordinates> emptyCells5 = solver5.getEmptyCells();
		solver5.solve(emptyCells5, 0);

		// Update p1-p4 with solutions from solver5.solutions.get(i)
	
		for(int num5[][]: solver5.returnSolutions()) {
			ArrayList<int[][]> possibleUpdates1 = new ArrayList<int[][]>();
			ArrayList<int[][]> possibleUpdates2 = new ArrayList<int[][]>();
			ArrayList<int[][]> possibleUpdates3 = new ArrayList<int[][]>();
			ArrayList<int[][]> possibleUpdates4 = new ArrayList<int[][]>();
			
			
			if(Arrays.equals(updateSamurai(p1, num5, dimension, 1),p1)){
				continue;
			}
			if(Arrays.equals(updateSamurai(p2, num5, dimension, 1),p2)){
				continue;
			}
			if(Arrays.equals(updateSamurai(p3, num5, dimension, 1),p3)){
				continue;
			}
			if(Arrays.equals(updateSamurai(p4, num5, dimension, 1),p4)){
				continue;
			}
			possibleUpdates1.add(updateSamurai(p1, num5, dimension, 1));
			possibleUpdates2.add(updateSamurai(p2, num5, dimension, 2));
			possibleUpdates3.add(updateSamurai(p3, num5, dimension, 3));
			possibleUpdates4.add(updateSamurai(p4, num5, dimension, 4));
			
			SudokuSolver solver1 = new SudokuSolver(possibleUpdates1.get(0), dimension);
			SudokuSolver solver2 = new SudokuSolver(possibleUpdates2.get(0), dimension);
			SudokuSolver solver3 = new SudokuSolver(possibleUpdates3.get(0), dimension);
			SudokuSolver solver4 = new SudokuSolver(possibleUpdates4.get(0), dimension);
			
			if (solver1.returnSolutions().size() > 0 && solver2.returnSolutions().size() > 0 
					&& solver3.returnSolutions().size() > 0 && solver4.returnSolutions().size() > 0) {
				int offset = (int)(Math.sqrt(dimension)-2)*(int)(Math.sqrt(dimension));
				big = 2*dimension+offset;
				int offset2 = dimension -  (int)(Math.sqrt(dimension));
				int[][] result = new int[big][big];
				for(int num1[][]: solver1.solutions){
					for(int num2[][]: solver2.solutions){
						for(int num3[][]: solver3.solutions){
							for(int num4[][]: solver4.solutions){
								for(int x = 0; x < dimension; x++)
									for(int y = 0; y < dimension; y++){
										result[x][y] = num1[x][y];
									}
								for(int x = 0; x < dimension; x++)
									for(int y = 0; y < dimension; y++){
										result[x][y+offset+dimension] = num2[x][y];
									}
								for(int x = 0; x < dimension; x++)
									for(int y = 0; y < dimension; y++){
										result[x+offset+dimension][y] = num3[x][y];
									}
								for(int x = 0; x < dimension; x++)
									for(int y = 0; y < dimension; y++){
										result[x+offset+dimension][y+offset+dimension] = num4[x][y];
									}
								for(int x = 0; x < dimension; x++)
									for(int y = 0; y < dimension; y++){
										result[x+offset2][y+offset2] = num5[x][y];
									}
							}
							if (samuraiSolutions.size()>0){
								if(Arrays.equals(samuraiSolutions.get(samuraiSolutions.size()-1),result)){
									samuraiSolutions.remove(samuraiSolutions.size()-1);
								}
							}
							samuraiSolutions.add(result);
						}
					}
				}
				
//
//				result.add(solver1.solutions.get(i));
//				result.add(solver2.solutions.get(i));
//				result.add(solver3.solutions.get(i));
//				result.add(solver4.solutions.get(i));
//				result.add(solver5.solutions.get(i));
				
			}
		}

	}
	
	public ArrayList<int [][]> returnSolutions(){
		return this.samuraiSolutions;
	}
	
}
