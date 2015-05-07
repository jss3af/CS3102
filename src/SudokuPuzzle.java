import javax.swing.JFileChooser;
import javax.swing.JFrame;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
		frame = new JFrame();
		frame.setTitle("CS3102 Solver");
		frame.setSize(2000,2000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		initialize();
	}

	public void initialize() {
		frame.setTitle("CS3102 Solver");
		frame.getContentPane().removeAll();
		frame.getContentPane().setLayout(null);
		fc = new JFileChooser();
		int[] dimensions = { 4, 9, 16, 25, 36 };
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
		final JCheckBox samurai = new JCheckBox("Samurai");
		samurai.setSelected(false);
		final JButton btnAdd = new JButton("Choose File");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnVal = fc.showOpenDialog(btnAdd);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					try {
						if(!samurai.isSelected()){
						SudokuSolver solver = new SudokuSolver(file,
								(Integer) (dimList.getSelectedItem()));
						frame.getContentPane().removeAll();
						showPuzzle(solver.returnSolutions(),0);
						}
						else{
							SamuraiSudoku solver = new SamuraiSudoku(file, (Integer) (dimList.getSelectedItem()));
							frame.getContentPane().removeAll();
							showPuzzle(solver.returnSolutions(),0);
						}
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
				frame.getContentPane().removeAll();
				showPuzzle(gen.returnPuzzle());

			}
		});
		generate.setBounds(150, 10, 150, 20);
		btnAdd.setBounds(315, 10, 100, 20);
		samurai.setBounds(430,10,100,20);
		frame.getContentPane().add(samurai);
		frame.getContentPane().add(btnAdd);
		frame.getContentPane().add(generate);
		frame.setVisible(true);
	}

	public void showPuzzle(final ArrayList<int[][]> solutions, final int index) {
		//frame.getContentPane().removeAll();
//		frame = new JFrame();
//		frame.setTitle("CS3102 Solver");
//		frame.setSize(2000,2000);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		if(solutions.isEmpty()){
			frame.getContentPane().removeAll();
			initialize();
		}
		int puzzle[][] = solutions.get(index);
		int dimension = puzzle[0].length;
		JPanel p = new JPanel(new GridLayout(dimension, dimension));
		//frame.getContentPane().add(p);
		frame.getContentPane().setLayout(new GridLayout(1, 2));
		 //frame.getContentPane().setLayout(new GridLayout(dimension,
//		 dimension));
		for (int x = 0; x < dimension; x++) {
			for (int y = 0; y < dimension; y++) {
				JTextField a = new JTextField("" + puzzle[x][y]);
				a.setEditable(false);
				p.add(a);
			}
		}
		frame.getContentPane().add(p);
		final JButton reset = new JButton("Reset");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				//frame.revalidate();
				frame.repaint(100);
				initialize();
			}
		});
		if ((solutions.size()-1) - index > 0) {
			final JButton next = new JButton("Next");
			next.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frame.getContentPane().removeAll();
					//frame.revalidate();
					frame.repaint(100);
					showPuzzle(solutions, (index + 1));
				}
			});
			JPanel inside = new JPanel(new GridLayout(2,1));
			inside.add(reset);
			inside.add(next);
			frame.getContentPane().add(inside);
		} else {
			frame.getContentPane().add(reset);
		}
		
		//frame.getContentPane().add(reset);
		
		frame.setVisible(true);

	}

	public void showPuzzle(final int puzzle[][]) {
		//frame.getContentPane().removeAll();
		final int dimension = puzzle[0].length;
		JPanel p = new JPanel(new GridLayout(dimension, dimension));
		frame.getContentPane().setLayout(new GridLayout(1, 2));
		frame.getContentPane().add(p);
		for (int x = 0; x < dimension; x++) {
			for (int y = 0; y < dimension; y++) {
				JTextField a;
				if (puzzle[x][y] != 0) {
					a = new JTextField("" + puzzle[x][y]);
				} else {
					a = new JTextField(" ");
				}
				a.setEditable(false);
				p.add(a);
			}
		}
		final JButton reset = new JButton("Reset");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
//				frame.revalidate();
				frame.repaint(100);
				initialize();
			}
		});
		final JButton solve = new JButton("Solve");
		solve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
//				frame.revalidate();
				frame.repaint(100);
				SudokuSolver solver = new SudokuSolver(puzzle, dimension);
				showPuzzle(solver.returnSolutions(), 0);
			}
		});
		JPanel inside = new JPanel(new GridLayout(2, 1));
		inside.add(reset);
		inside.add(solve);
		frame.getContentPane().add(inside);
		frame.setVisible(true);

	}

	public static void main(String[] args) {
		SudokuPuzzle app = new SudokuPuzzle();

	}

}
