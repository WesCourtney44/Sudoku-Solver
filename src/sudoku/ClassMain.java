package sudoku;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class ClassMain {
	public static void main(String[] args) {
		long startTime = System.nanoTime();
		SudokuPuzzle sp = new SudokuPuzzle("E,1,E,E,E,3,E,E,E,E,E,E,E,4,E,9,E,8,E,7,2,E,E,E,5,E,E,5,E,E,E,9,E,E,E,E,E,9,E,E,E,E,E,2,E,E,E,E,E,3,E,E,E,4,E,E,1,E,E,E,2,7,E,3,E,9,E,8,E,E,E,E,E,E,E,6,E,E,E,3,E");
		sp.solvePuzzle();
		long timeElapsed = (System.nanoTime()-startTime)/1000000;
		System.out.println("Completed in " + timeElapsed + " milliseconds.");
		NumberFormat formatter = new DecimalFormat("#0.0"); 
		System.out.println("That is " + formatter.format((double) timeElapsed/1000*100) + "% of one single second.");
	}
}
