import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class SudokuGenerator {
	
	static void printPuzzle(int puzzle[][], int dimension) {
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
				if ((y + 1) % ((int) Math.sqrt(dimension)) == 0
						&& y != (dimension - 1)) {
					System.out.print("   ");
				}
			}
			System.out.println();
			once = true;
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		ArrayList<int [] []>solutions;
		Scanner keyboard = new Scanner(System.in);
		System.out.print("Enter the number of rows and columns: ");
		int dimension = Integer.parseInt(keyboard.next());
		int puzzle[][] = new int[dimension][dimension];
		int[] numArray = new int[(dimension*dimension)];
		int counter = 0;
		for( int num: numArray){
			num = 0;
		}
		for(int i =0; i < (((dimension)*(dimension)))-(((dimension-1)*(dimension-1))); i++){
			//numArray[((int)(Math.random()*(dimension*dimension)))] = ((int)(Math.random()*dimension))+1;
			System.out.println(((int)(Math.random()*dimension))+1);
		}
		for(int x = 0; x < dimension; x++){
			for(int y = 0; y < dimension; y++){
				puzzle[x][y] = numArray[counter];
				counter++;
			}
		}
		SudokuSolver solver = new SudokuSolver(puzzle, dimension);
		boolean solved = solver.solve(new Coordinates(0, 0));
		if (!solved) {
			System.out.println("SUDOKU cannot be generated.");
			return;
		}
		solutions = solver.solutions;
		for(int [][]thePuzzle : solutions){
			System.out.println();
			solver.printPuzzle(thePuzzle);
		}
		keyboard.close();

	}

}
