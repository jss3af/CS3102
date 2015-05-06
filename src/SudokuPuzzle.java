import javax.swing.JFileChooser;
import javax.swing.JFrame;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class SudokuPuzzle {

	private JFrame frame;
	JFileChooser fc;
	JButton file;

	public SudokuPuzzle() {
		initialize();
	}

	public void initialize() {
		frame = new JFrame();
		frame.setTitle("CS3102 Solver");
		frame.setSize(700, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		fc = new JFileChooser();
		int[] dimensions = { 4, 9, 16, 25, 36 };

		// Create the combo box, select item at index 4.
		// Indices start at 0, so 4 specifies the pig.
		final JComboBox<Integer> dimList = new JComboBox<Integer>();
		for (int i = 0; i < 5; i++) {
			dimList.addItem(dimensions[i]);
		}
		dimList.setSelectedIndex(1);
		dimList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox) e.getSource();
				int dim = (Integer) cb.getSelectedItem();
			}
		});
		dimList.setBounds(90, 10, 45, 20);
		JLabel label = new JLabel("Dimension: ");
		label.setBounds(10, 10, 80, 20);
		frame.getContentPane().add(dimList);
		frame.getContentPane().add(label);
		final JButton btnAdd = new JButton("Choose File");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnVal = fc.showOpenDialog(btnAdd);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					try {
						SudokuSolver solver = new SudokuSolver(file,
								(Integer) (dimList.getSelectedItem()));
						showPuzzle(solver.returnSolutions());
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}

				}
			}
		});
		final JButton generate = new JButton("Generate a Sudoku");
		generate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				SudokuGenerator gen = new SudokuGenerator((Integer) (dimList
						.getSelectedItem()));
				showPuzzle(gen.returnPuzzle());

			}
		});
		generate.setBounds(275, 325, 150, 20);
		btnAdd.setBounds(300, 300, 100, 20);
		frame.getContentPane().add(btnAdd);
		frame.getContentPane().add(generate);
		frame.setVisible(true);
	}

	public void showPuzzle(ArrayList<int[][]> solutions) {
		frame.getContentPane().removeAll();
		int puzzle[][] = solutions.get(0);
		int dimension = puzzle[0].length;
		frame.setSize(700, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(dimension, dimension));
		for (int x = 0; x < dimension; x++) {
			for (int y = 0; y < dimension; y++) {
				JTextField a = new JTextField("" + puzzle[x][y]);
				a.setEditable(false);
				frame.getContentPane().add(a);
			}
		}
		frame.setVisible(true);

	}

	public void showPuzzle(int puzzle[][]) {
		frame.getContentPane().removeAll();
		int dimension = puzzle[0].length;
		frame.setSize(700, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(dimension, dimension));
		for (int x = 0; x < dimension; x++) {
			for (int y = 0; y < dimension; y++) {
				JTextField a;
				if(puzzle[x][y]!=0){
				a = new JTextField("" + puzzle[x][y]);
				}
				else{
					a = new JTextField(" ");
				}
				a.setEditable(false);
				frame.getContentPane().add(a);
			}
		}
		frame.setVisible(true);

	}

	public static void main(String[] args) {
		SudokuPuzzle app = new SudokuPuzzle();

	}

}
