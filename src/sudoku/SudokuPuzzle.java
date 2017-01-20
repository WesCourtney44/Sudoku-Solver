package sudoku;

import java.util.ArrayList;

public class SudokuPuzzle {

	private int currentRow, currentColumn;
	private Cell[][] cells;

	public SudokuPuzzle(String puzzle) {
		String[] splitPuzzle = puzzle.split(",");
		// Build grid from string
		int rows = 9, columns = 9;
		cells = new Cell[columns][rows];
		int i = 0;
		// iterate down through rows
		for (int r = 0; r < rows; r++) {
			// iterate over through the columns
			for (int c = 0; c < columns; c++) {
				if (splitPuzzle[i].equals("E")) {
					cells[c][r] = new Cell(this);
				} else {
					cells[c][r] = new Cell(this, Integer.parseInt(splitPuzzle[i]));
				}
				i++;
			}
		}
	}

	public void solvePuzzle() {
		currentRow = 0;
		currentColumn = 0;
		int iterationCount = 0;
		boolean keepGoing = true;
		while (keepGoing) {
			keepGoing = false;
			// Iterate through rows
			rowLoop:
			while (currentRow < 9) {
				// Iterate through columns
				while (currentColumn < 9) {
					Cell cell = cells[currentColumn][currentRow];
					if (cell.isSolved() == false) {
						System.out.println("unsolved cell: Column: " + currentColumn + " Row: " + currentRow );
						cell.update();
						if (cell.isSolved()) {
							keepGoing = true;
							System.out.println("Cell[" + currentColumn + "][" + currentRow + "] Solved. Value: " + cell.getMyValue() + "WOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO HOOOOOOOOOOOOOOOOOOOOO");
							currentRow = 0;
							currentColumn = 0;
							break rowLoop;
						}
					}
					currentColumn++;
				}
				currentColumn = 0;
				currentRow++;
			}
			iterationCount++;
		}
		System.out.println("Iterations: " + iterationCount);
		printPuzzle();
	}

	private void printPuzzle() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				Cell cell = cells[j][i];
				System.out.print(cell.getMyValue() + " | ");
			}
			System.out.println("");
		}
	}

	private void addValuesIfUnique(ArrayList<Integer> values, ArrayList<Integer> destination) {
		// iterate through values
		for (int i = 0; i < values.size(); i++) {
			Integer cValue = values.get(i);
			// iterate through destination
			boolean unique = true;
			for (int j = 0; j < destination.size(); j++) {
				Integer cDestinationValue = destination.get(j);
				if (cValue == cDestinationValue) {
					unique = false;
				}
			}
			if (unique == true) {
				destination.add(cValue);
			}
		}
	}

	private ArrayList<Integer> getHorImpos(int row) {
		ArrayList<Integer> imposibilities = new ArrayList<Integer>();
		for (int i = 0; i < 9; i++) {
			Cell cell = cells[i][row];
			if (cell.isSolved()) {
				imposibilities.add(cell.getMyValue());
			}
		}
		System.out.println("Horizontal Impos: " + imposibilities.toString());
		return imposibilities;
	}

	private ArrayList<Integer> getVerImpos(int column) {
		ArrayList<Integer> imposibilities = new ArrayList<Integer>();
		for (int i = 0; i < 9; i++) {
			Cell cell = cells[column][i];
			if (cell.isSolved()) {
				imposibilities.add(cell.getMyValue());
			}
		}
		System.out.println("Vertical Impos: " + imposibilities.toString());
		return imposibilities;
	}

	private ArrayList<Integer> getBlockImpos(int column, int row) {
		int blockColumn = (int) (Math.floor(column / 3) * 3);
		int blockRow = (int) (Math.floor(row / 3) * 3);
		ArrayList<Integer> imposibilities = new ArrayList<Integer>();
		// iterate through block's rows
		for (int i = blockRow; i < blockRow + 3; i++) {
			// iterate through block's columns
			for (int j = blockColumn; j < blockColumn + 3; j++) {
				Cell cell = cells[j][i];
				if (cell.isSolved()) {
					imposibilities.add(cell.getMyValue());
				}
			}
		}
		System.out.println("Block Impos: " + imposibilities.toString());
		return imposibilities;
	}

	public ArrayList<Integer> getImposibilities() {
		int row = currentRow;
		int column = currentColumn;
		ArrayList<Integer> imposibilities = new ArrayList<Integer>();
		addValuesIfUnique(getHorImpos(row), imposibilities);
		addValuesIfUnique(getVerImpos(column), imposibilities);
		addValuesIfUnique(getBlockImpos(column, row), imposibilities);
		return imposibilities;
	}

	
//////////////////////////////////////////////////////////////////
//                                                              //
//                     CLASS: Cell                              //
//                                                              //
//////////////////////////////////////////////////////////////////

	private class Cell {
		private SudokuPuzzle sp;
		private boolean solved;
		private int myValue;
		private ArrayList<Integer> possibilities;

		public Cell(SudokuPuzzle sp) {
			this.sp = sp;
			solved = false;
			myValue = 0;
			possibilities = new ArrayList<Integer>();
			for (int i = 1; i < 10; i++) {
				possibilities.add(i);
			}
		}

		public Cell(SudokuPuzzle sp, int value) {
			this.sp = sp;
			solved = true;
			myValue = value;
		}

		public void update() {
			if (solved == false) {
				removeImpos(sp.getImposibilities());
				if (possibilities.size() == 1) {
					myValue = possibilities.get(0);
					solved = true;
				}
				System.out.println("Remaining Possibilities: " + possibilities.toString() + "\n");
			}
		}

		private void removeImpos(ArrayList<Integer> imposibilities) {
			// iterate through impossibilities
			for (int i = 0; i < imposibilities.size(); i++) {
				Integer impossible = imposibilities.get(i);
				// iterate through possibilities
				for (int j = 0; j < possibilities.size(); j++) {
					Integer possible = possibilities.get(j);
					if (impossible == possible) {
						possibilities.remove(j);
					}
				}
			}
		}

		public boolean isSolved() {
			return solved;
		}

		public int getMyValue() {
			return myValue;
		}

	}
}