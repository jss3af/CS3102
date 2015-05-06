import javax.swing.JFileChooser;
import javax.swing.JFrame;

import java.awt.EventQueue;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

public class SudokuPuzzle{
	
	private JFrame frame;
	JFileChooser fc;
	JButton file;
	public SudokuPuzzle() {
		initialize();
	}
	
	public void initialize() {
		frame = new JFrame();
		frame.setSize(700,700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		fc = new JFileChooser();
		final JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnVal = fc.showOpenDialog(btnAdd);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
	                File file = fc.getSelectedFile();
	                
				}
			}
		});
		btnAdd.setBounds(300, 50, 100, 20);
		frame.getContentPane().add(btnAdd);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SudokuPuzzle app = new SudokuPuzzle();

	}
	
	

}