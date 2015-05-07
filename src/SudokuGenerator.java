import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class SudokuGenerator {
	int puzzle[][];
	int with0 [][];
	int dimension;
	public SudokuGenerator(int [][] puzzle, int dimension){
		this.dimension = dimension;
		this.puzzle=puzzle;
		this.with0=generate();
	}
	public SudokuGenerator(int dimension){
		this.dimension = dimension;
		this.puzzle=new int[dimension][dimension];
		this.with0=generate();
	}
	public void printPuzzle() {
		boolean once = true;
		for (int x = 0; x < dimension; x++) {
			for (int y = 0; y < dimension; y++) {
				if ((x) % ((int) Math.sqrt(dimension)) == 0 && once && x != 0) {
					System.out.println("                    ");
					once = false;
				}
				int num = with0[x][y];
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
	public int[][] returnPuzzle(){
		return this.with0;
	}
	public static void main(String[] args) throws FileNotFoundException {
		Scanner keyboard = new Scanner(System.in);
		System.out.print("Enter the number of rows and columns: ");
		int dimension = Integer.parseInt(keyboard.next());
		int puzzle[][] = new int[dimension][dimension];
		SudokuGenerator gen = new SudokuGenerator(puzzle, dimension);
		gen.printPuzzle();
		keyboard.close();
		SudokuSolver solver = new SudokuSolver(puzzle, dimension);
		solver.printSolutions();
		
	}
	public int[][] generate() {
		ArrayList<int [][]> solutions;
		for (int out = 0; out < (dimension); out = out
				+ (int) (Math.sqrt(dimension))) {
			ArrayList<Integer> option = new ArrayList<Integer>();
			for (int i = 1; i <= dimension; i++) {
				option.add(i);
			}
			Collections.shuffle(option);
			int counter = 0;
			for (int x = out; x < out + (int) (Math.sqrt(dimension)); x++) {
				for (int y = out; y < out + (int) (Math.sqrt(dimension)); y++) {
					puzzle[x][y] = option.get(counter);
					counter++;
				}
			}
		}
		if (dimension == 4) {
			for (int x = 2; x < 4; x++) {
				for (int y = 2; y < 4; y++) {
					puzzle[x][y] = 0;
				}
			}
		}
		SudokuSolver solver = new SudokuSolver(puzzle, dimension);
		boolean solved = solver.solve(new Coordinates(0, 0));
		if (!solved) {
			System.out.println("SUDOKU cannot be generated.");
			System.exit(0);
		}
		solutions = solver.solutions; 
		int a[][]=solutions.get(0);
		for(int i=0; i< (int)(.6*dimension*dimension);i++){
			int x = (int)(Math.random()*dimension);
			int y = (int)(Math.random()*dimension);
			if(a[x][y]==0){
				i--;
			}
			else{
				a[x][y]=0;
			}
		}
		return a;
	}

}
