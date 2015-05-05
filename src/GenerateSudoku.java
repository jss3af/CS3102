import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class GenerateSudoku {
	
	public int dimension;
	public int puzzle[][];
	
	public GenerateSudoku(){
		ArrayList<Integer> ar = new ArrayList<Integer>();
		Scanner keyboard = new Scanner(System.in);
		System.out.print("Enter the number of rows and columns: ");
		dimension = Integer.parseInt(keyboard.next());
		int[][] bs = new int[dimension][dimension];
		puzzle = new int[dimension][dimension];
		for (int i = 0; i < dimension; i++) {
			ar.add(i + 1);
		}
		Collections.shuffle(ar);

		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < dimension; j++) {

				bs[i][j] = ar.get(j);
				puzzle[i][j] = ar.get(j);
			}

		}
		boolean check = true;

		for (int i = 0; i < dimension; i++) {
			if (i == ((int)Math.sqrt(dimension))-1) {
				check = checkPath(bs, i, dimension);
				if (check == false)
					i = i - (((int)Math.sqrt(dimension))-1) ;
			}
			if (i == (2*((int)Math.sqrt(dimension)))) {
				check = checkPath(bs, i, dimension);
				if (check == false) {
					i = i - (((int)Math.sqrt(dimension))-1) ;
				}
			}

			for (int j = 0; j < dimension; j++) {
				if (i > 0) {
					check = checker(bs, i, ar, dimension);
					if (check == false) {
						i--;
						break;
					} else {
						bs[i][j] = ar.get(j);
						puzzle[i][j] = ar.get(j);
					}
				}

			}

			Collections.shuffle(ar);
		}
		for (int k = 0; k < (dimension-1)*(dimension-1); k++) {
			int x = (int) (Math.random() * dimension);
			int y = (int) (Math.random() * dimension);
			puzzle[x][y] = 0;
		}
		keyboard.close();
	}
	
	public static boolean checker(int[][] bs, int i, ArrayList<Integer> ar, int dimension) {
		ArrayList<Integer> temp_ar = new ArrayList<Integer>();
		boolean check1 = true; // For returning true for good Sudoku
		for (int e = 0; e < dimension; e++) {
			bs[i][e] = ar.get(e);
		}

		for (int t = 0; t < dimension; t++) {
			for (int e = 0; e <= i; e++) {

				temp_ar.add(e, bs[e][t]);

			}
			Set<Integer> temp_set = new HashSet<Integer>(temp_ar);
			if (temp_set.size() < temp_ar.size()) {
				check1 = false;
				break;
			} else {
				temp_ar.clear();
				temp_set.clear();
			}
		}
		return check1;
	}

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

	public static void main(String[] args) {
		GenerateSudoku gen = new GenerateSudoku();
		printPuzzle(gen.puzzle, gen.dimension);
	}

	public static boolean checkPath(int[][] bs, int i, int dimension) {
		ArrayList<Integer> temp_cP = new ArrayList<Integer>();
		Set<Integer> temp_setcP = new HashSet<Integer>();
		boolean denoter = true;
		while (i == ((int)Math.sqrt(dimension))-1) {
			for (int k = 0; k < i; k++) {
				for (int e = 0; e < ((int)Math.sqrt(dimension)) ; e++) {
					temp_cP.add(e, bs[k][e]);
				}
			}
			temp_setcP = new HashSet<Integer>(temp_cP);
			if (temp_cP.size() > temp_setcP.size()) {
				denoter = false;
				break;

			} else {
				temp_cP.clear();
				temp_setcP.clear();

				for (int k = 0; k < i; k++) {
					for (int e = ((int)Math.sqrt(dimension)); e < (2*((int)Math.sqrt(dimension))); e++) {
						temp_cP.add(bs[k][e]);
					}
				}
				temp_setcP = new HashSet<Integer>(temp_cP);
				if (temp_cP.size() > temp_setcP.size()) {
					denoter = false;
					break;

				} else {
					break;
				}
			}
		}
		while (i == (2*((int)Math.sqrt(dimension))) ) {

			for (int k = ((int)Math.sqrt(dimension)); k < i; k++) {
				for (int e = 0; e < ((int)Math.sqrt(dimension)); e++) {
					temp_cP.add(e, bs[k][e]);
				}
			}
			temp_setcP = new HashSet<Integer>(temp_cP);
			if (temp_cP.size() > temp_setcP.size()) {
				denoter = false;
				break;

			} else {
				temp_cP.clear();
				temp_setcP.clear();

				for (int k = ((int)Math.sqrt(dimension)); k < i; k++) {
					for (int e = ((int)Math.sqrt(dimension)); e < (2*((int)Math.sqrt(dimension))); e++) {
						temp_cP.add(bs[k][e]);
					}
				}
				temp_setcP = new HashSet<Integer>(temp_cP);
				if (temp_cP.size() > temp_setcP.size()) {
					denoter = false;
					break;

				} else {
					break;
				}
			}

		}
		return denoter;
	}
}
