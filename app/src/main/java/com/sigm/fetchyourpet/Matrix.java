package com.sigm.fetchyourpet;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class models a mathematical matrix
 *
 * @author Garrett Neilson
 */
public class Matrix {

    private double[][] matrix;
    private int rowNum;
    private int colNum;

    /**
     * Constructor accepting a double [][]
     *
     * @param data: a double [][]
     */
    public Matrix(double[][] data) {
        this.rowNum = data.length;
        this.colNum = data[0].length;

        this.matrix = new double[rowNum][colNum];

        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < colNum; j++) {
                matrix[i][j] = data[i][j];
            }
        }
    }

    /**
     * Constructor accepting dimensions and an identity boolean trigger
     *
     * @param dimensions: a double [][]
     */
    public Matrix(int dimensions) {
        this.rowNum = dimensions;
        this.colNum = dimensions;

        this.matrix = new double[rowNum][colNum];

        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < colNum; j++) {
                if (i == j) {
                    matrix[i][j] = 1;
                } else {
                    matrix[i][j] = 0;
                }
            }
        }
    }

    /**
     * Constructor accepting a double[], creates column vector
     *
     * @param data: a double[]
     */
    public Matrix(double[] data) {
        this.rowNum = data.length;
        this.colNum = 1;

        this.matrix = new double[rowNum][colNum];

        for (int i = 0; i < rowNum; i++) {
            matrix[i][0] = data[i];
        }
    }


    /**
     * Constructor accepting a double[], creates row vector
     *
     * @param data        double []
     * @param isRowVector boolean for triggering creation of row vector
     */
    public Matrix(double[] data, boolean isRowVector) {
        this.rowNum = 1;
        this.colNum = data.length;

        this.matrix = new double[rowNum][colNum];

        for (int i = 0; i < colNum; i++) {
            matrix[0][i] = data[i];
        }
    }

    /**
     * Constructor accepting an int[]
     *
     * @param data: an int[]
     */
    public Matrix(int[] data) {
        this.rowNum = data.length;
        this.colNum = 1;

        this.matrix = new double[rowNum][colNum];

        for (int i = 0; i < rowNum; i++) {
            matrix[i][1] = data[i];
        }
    }

    /**
     * Constructor accepting an int [][]
     *
     * @param data: an int [][]
     */
    public Matrix(int[][] data) {
        this.rowNum = data.length;
        this.colNum = data[0].length;

        this.matrix = new double[rowNum][colNum];

        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < colNum; j++) {
                matrix[i][j] = data[i][j];
            }
        }
    }

    /**
     * This is the function that reads in the matrix data
     * @param filename name of file
     * @param rowNums number of rows of the matrix in the file
     * @param colNums number of columns of the matrix in the file
     * @return a double[][] containing the data
     */
//	public Matrix(String filename, int rowNums, int colNums){
//		BufferedReader bufRead;
//		String myLine;
//		this.rowNum = rowNums;
//		this.colNum = colNums;
//		this.matrix = new double[rowNums][colNums];
//		try {
//			bufRead = Files.newBufferedReader(Paths.get(filename));
//			int rowNum = 0;
//			while ((myLine = bufRead.readLine()) != null) {
//				String[] data = myLine.split("	");
//				for(int i = 0; i < data.length; i++) {
//					this.matrix[rowNum][i] = Double.parseDouble(data[i].trim());
//				}
//				rowNum++;
//			}
//			bufRead.close();
//		}catch(IOException e) {}
//	}

    /**
     * get the dimensions of the matrix
     *
     * @return the dimensions
     */
    public String getDimensions() {
        return this.rowNum + "x" + this.colNum;
    }


    /**
     * Gets the value at the given index
     *
     * @param i: ith row
     * @param j: jth column
     * @return the value at the index
     */
    public double getValueAt(int i, int j) {
        return this.matrix[i][j];
    }

    /**
     * Sets the value at a given index
     *
     * @param i:    ith row
     * @param j:    jth column
     * @param value the value that you want to insert
     */
    public void setValueAt(int i, int j, double value) {
        this.matrix[i][j] = value;
    }

    /**
     * gets the number of rows in the matrix
     *
     * @return the number of rows
     */
    public int getRowNum() {
        return this.rowNum;
    }

    /**
     * gets the number of columns in the matrix
     *
     * @return the column number of the matrix
     */
    public int getColNum() {
        return this.colNum;
    }

    /**
     * gets a row of the matrix as a double[]
     *
     * @param i the row number
     * @return the row
     */
    public double[] getRowAsArray(int i) {
        return this.matrix[i];
    }

    /**
     * gets a row of the matrix as a matrix
     *
     * @param i the row number
     * @return the row
     */
    public Matrix getRowAsVector(int i) {
        return new Matrix(this.matrix[i], true);
    }

    public double cos_sim(Matrix other_matrix) {
        double product = (this.transpose()).matrixMult(other_matrix).getValueAt(0, 0);
//		System.out.println("The dot product of the two vectors is: " + product);
        double norm_a = this.getMagnitude();
//		System.out.println("The magnitude of the first vector is: " + norm_a);
        double norm_b = other_matrix.getMagnitude();
//		System.out.println("The magnitude of the second vector is: " + norm_b); 
//		System.out.println("The cosine similarity of the two vectors is: " + (product/ (norm_a * norm_b)));
        return product / (norm_a * norm_b);

    }

    /**
     * transposes a given matrix
     *
     * @return the transposed matrix
     */
    public Matrix transpose() {
        double[][] data = new double[colNum][rowNum];

        for (int i = 0; i < this.rowNum; i++) {
            for (int j = 0; j < this.colNum; j++) {
                data[j][i] = this.matrix[i][j];
            }
        }
        Matrix newMatrix = new Matrix(data);
        return newMatrix;
    }

    /**
     * adds two matrices together
     *
     * @param otherMatrix another given matrix
     * @return the sum of the matrices
     */
    public Matrix add(Matrix otherMatrix) {
        if (rowNum != otherMatrix.getRowNum()
                || colNum != otherMatrix.getColNum()) {
            System.out.println("Can't add two matricies of differing dimensions");
        }

        Matrix sumMatrix = new Matrix(new double[rowNum][colNum]);

        for (int i = 0; i < this.rowNum; i++) {
            for (int j = 0; j < this.colNum; j++) {
                double value = otherMatrix.getValueAt(i, j) + this.matrix[i][j];
                sumMatrix.setValueAt(i, j, value);
            }
        }
        return sumMatrix;
    }

    /**
     * subs two matrices together
     *
     * @param otherMatrix another given matrix
     * @return the difference of the matrices
     */
    public Matrix sub(Matrix otherMatrix) {
        if (rowNum != otherMatrix.getRowNum()
                || colNum != otherMatrix.getColNum()) {
            System.out.println("Can't add two matricies of differing dimensions");
        }

        Matrix diffMatrix = new Matrix(new double[rowNum][colNum]);

        for (int i = 0; i < this.rowNum; i++) {
            for (int j = 0; j < this.colNum; j++) {
                double value = this.matrix[i][j] - otherMatrix.getValueAt(i, j);
                diffMatrix.setValueAt(i, j, value);
            }
        }
        return diffMatrix;
    }

    /**
     * Scalar multiplication of a matrix
     *
     * @param scalar the scalar multiplier
     * @return the final product of scalar multiplication
     */
    public Matrix scalarMult(double scalar) {
        Matrix scalarMult = new Matrix(new double[rowNum][colNum]);

        for (int i = 0; i < this.rowNum; i++) {
            for (int j = 0; j < this.colNum; j++) {
                double value = this.matrix[i][j] * scalar;
                scalarMult.setValueAt(i, j, value);
            }
        }
        return scalarMult;
    }

    /**
     * Scalar multiplication of a matrix across another matrix
     *
     * @param otherMatrix the scalar multiplier
     * @return the final product of scalar multiplication across matrices
     */
    public Matrix scalarMultAcrossMatrices(Matrix otherMatrix) {
        Matrix scalarMult = new Matrix(new double[rowNum][colNum]);

        for (int i = 0; i < this.rowNum; i++) {
            for (int j = 0; j < this.colNum; j++) {
                double value = this.matrix[i][j] * otherMatrix.getValueAt(i, j);
                scalarMult.setValueAt(i, j, value);
            }
        }
        return scalarMult;
    }

    /**
     * Matrix multiplication
     *
     * @param otherMatrix the other matrix
     * @return the matrix multiplication of two matrices
     */
    public Matrix matrixMult(Matrix otherMatrix) {
        double value = 0;
        Matrix productMatrix = new Matrix(new double[rowNum][otherMatrix.getColNum()]);
        for (int i = 0; i < this.rowNum; i++) {
            for (int j = 0; j < productMatrix.colNum; j++) {
                for (int k = 0; k < this.colNum; k++) {
                    value += matrix[i][k] * otherMatrix.getValueAt(k, j);
                    productMatrix.setValueAt(i, j, value);
                }
                value = 0;
            }
        }
        return productMatrix;
    }

    /**
     * scale the vector to have magnitude of 1 by normalizing
     *
     * @return the normalized vector (magnitude = 1)
     */
    public Matrix normalize() {
        Matrix copy = this.copy();

        double norm = copy.getMagnitude();
        Matrix normalized = copy.scalarMult(1.0 / norm);
        return normalized;
    }

    /**
     * Randomize the current matrix with user defined bounds
     *
     * @param lowerBound the lower bound of numbers you want to generate
     * @param upperBound the upper bound of numbers you want to generate
     */
    public void randomizeValues(double lowerBound, double upperBound) {
        Random r = new Random();
        for (int i = 0; i < this.rowNum; i++) {
            for (int j = 0; j < this.colNum; j++) {
                this.matrix[i][j] = r.nextDouble() * upperBound - lowerBound;
            }
        }
    }

    /**
     * Returns the magnitude of a given vector
     *
     * @return the magnitude of that vector
     */
    public double getMagnitude() {
        Matrix copy = this.copy();
        return Math.sqrt(copy.transpose().matrixMult(copy).getValueAt(0, 0));
    }

    /**
     * Returns a copy of the existing matrix
     *
     * @return a copy of the matrix
     */
    public Matrix copy() {
        return new Matrix(this.matrix);
    }

    /**
     * adds a vector to the end of the given matrix
     *
     * @param m the vector you want to add
     * @return the new matrix with the added vector
     */
    public Matrix addColumn(Matrix m) {
        Matrix columnAdded = new Matrix(new double[this.rowNum][this.colNum + 1]);
        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < colNum + 1; j++) {
                if (j == colNum) {
                    columnAdded.setValueAt(i, j, m.getValueAt(i, 0));
                } else {
                    columnAdded.setValueAt(i, j, this.getValueAt(i, j));
                }
            }
        }
        return columnAdded;
    }

    /**
     * adds a matrix to the end of the given matrix
     *
     * @param m the matrix you want to add
     * @return the new matrix with the added vector
     */
    public Matrix addMatrix(Matrix m) {
        double[][] data = new double[this.rowNum][this.getColNum() + m.getColNum()];

        for (int i = 0; i < this.rowNum; i++) {
            for (int j = 0; j < (this.colNum + m.getColNum()); j++) {
                if (j >= this.colNum) {
                    data[i][j] = m.getValueAt(i, j - this.colNum);

                } else {
                    data[i][j] = this.getValueAt(i, j);
                }
            }
        }
        return new Matrix(data);
    }

    /**
     * remove a given column
     *
     * @param index the index of the column you want to remove
     * @return the matrix with the removed column
     */
    public Matrix removeColumn(int index) {
        double[][] data = new double[this.rowNum][this.colNum - 1];

        for (int i = 0; i < this.rowNum; i++) {
            for (int j = 0, currColumn = 0; j < this.colNum; j++) {
                if (j != index) {
                    data[i][currColumn++] = this.getValueAt(i, j);
                }
            }
        }

        return new Matrix(data);
    }

    /**
     * Replace a column of a matrix with another supplied column at the supplied index
     *
     * @param newColumn the column vector you want to add to the matrix
     * @param index     the index of the column that you want to replace
     */
    public void replaceColumn(Matrix newColumn, int index) {
        double[][] data = new double[this.rowNum][this.colNum];
        for (int i = 0; i < this.rowNum; i++) {
            for (int j = 0; j < this.colNum; j++) {
                if (j == index) {
                    data[i][j] = newColumn.getValueAt(i, 0);
                } else {
                    data[i][j] = this.matrix[i][j];
                }
            }
        }

        this.matrix = data;
    }

    public void replaceRow(Matrix newRow, int index) {
        double[][] data = new double[this.rowNum][this.colNum];

        for (int i = 0; i < this.rowNum; i++) {
            for (int j = 0; j < this.colNum; j++) {
                if (i == index) {
                    data[i][j] = newRow.getValueAt(0, j);
                } else {
                    data[i][j] = this.matrix[i][j];
                }
            }
        }

        this.matrix = data;
    }

    /**
     * Function for seeing if a matrix of row vectors contains a given row vector x
     *
     * @param x the vector you want to check
     * @return true if the matrix contains the row vector, false otherwise
     */
    public boolean containsRowVector(Matrix x) {

        for (int i = 0; i < this.rowNum; i++) {
            Matrix row = this.getRowAsVector(i);
            if (row.equals(x)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Performs gauss-jordan elimination on a matrix
     *
     * @param b the right hand side of Ax=b
     * @return both the LHS and RHS of the solution
     */
    public Matrix[] gaussJordanElimination(Matrix b) {
        Matrix augmentedMatrix = this.addMatrix(b);
//		Matrix augmentedMatrix = b;

        // pivot down
        for (int currRowIndex = 0; currRowIndex < augmentedMatrix.getRowNum(); currRowIndex++) {

            int indexMax = 0;
            double CpjMax = 0;

            // find CpjMax
            for (int i = currRowIndex; i < augmentedMatrix.getRowNum(); i++) {

                double Cpj = Math.abs(augmentedMatrix.getValueAt(i, currRowIndex));
                if (Cpj > CpjMax) {
                    indexMax = i;
                    CpjMax = Cpj;
                }
            }
            System.out.println("CpjMax: " + CpjMax + " In row: " + currRowIndex);

            // Shift CpjMax row to the current row position
            //------------- needs to be slightly optimized -------------
            Matrix tempRow = augmentedMatrix.getRowAsVector(currRowIndex);
            Matrix newPivotRow = augmentedMatrix.getRowAsVector(indexMax);
            double leadingTerm = newPivotRow.getValueAt(0, currRowIndex);
            System.out.println("scaling by (1 / " + leadingTerm + ")");
            System.out.println("Put the following row in position: " + currRowIndex);
            System.out.println(newPivotRow.scalarMult(1.0 / leadingTerm));
            augmentedMatrix.replaceRow(newPivotRow.scalarMult(1.0 / leadingTerm), currRowIndex);
            if (indexMax != currRowIndex) {
                augmentedMatrix.replaceRow(tempRow, indexMax);
            }

            System.out.println("After shifting and scaling: ");
            System.out.println(augmentedMatrix);

            // Pivot down to turn everything below Cpj to 0
            for (int j = currRowIndex + 1; j < augmentedMatrix.rowNum; j++) {
                Matrix pivotRow = augmentedMatrix.getRowAsVector(currRowIndex);
                Matrix rowToReplace = augmentedMatrix.getRowAsVector(j);
                leadingTerm = rowToReplace.getValueAt(0, currRowIndex);
                System.out.println("leadingterm: " + leadingTerm + " at: " + j);
                Matrix zeroOut = rowToReplace.add(pivotRow.scalarMult(-leadingTerm));
                augmentedMatrix.replaceRow(zeroOut, j);
            }
            System.out.println("Zero out everything below Cpj");
            System.out.println(augmentedMatrix);
        }
        System.out.println("-----------------------------------------------------------------");

        // pivot up
        for (int currRowIndex = augmentedMatrix.getRowNum() - 1; currRowIndex > 0; currRowIndex--) {
            // Pivot up to turn everything above Cpj to 0
            Matrix pivotRow = augmentedMatrix.getRowAsVector(currRowIndex);
//			System.out.println("Pivot row: " + pivotRow);

            for (int rowAboveCurrIndex = currRowIndex - 1; rowAboveCurrIndex >= 0; rowAboveCurrIndex--) {
                System.out.println(rowAboveCurrIndex + " is the next row to be killed by " + currRowIndex);
                Matrix rowToReplace = augmentedMatrix.getRowAsVector(rowAboveCurrIndex);
                System.out.println("Row that needs replacing: ");
                System.out.println(rowToReplace);

//				
                double leadingTerm = rowToReplace.getValueAt(0, currRowIndex);
                System.out.println("number that will be killed: " + leadingTerm);
//				
                Matrix zeroOut = rowToReplace.add(pivotRow.scalarMult(-leadingTerm));
                System.out.println("zerod out: ");
                System.out.println(zeroOut);

                augmentedMatrix.replaceRow(zeroOut, rowAboveCurrIndex);
                System.out.println("Zero out next row above Cpj");
                System.out.println(augmentedMatrix);
            }
        }
        return augmentedMatrix.splitAtIndex(this.rowNum);
    }

    /**
     * Performs Gaussian reduction on a matrix
     *
     * @return the upper diagonal reduced form and the determinant of a matrix
     */
    public ArrayList<Object> gaussianReduction() {
        Matrix reduced = new Matrix(this.matrix);
        ArrayList<Object> reducedAndDeterminant = new ArrayList<Object>();
        int sign = 1;
        int counter = 0;

        // pivot down
        for (int currRowIndex = 0; currRowIndex < reduced.getRowNum(); currRowIndex++) {

            int indexMax = 0;
            double CpjMax = 0;

            // find CpjMax
            for (int i = currRowIndex; i < reduced.getRowNum(); i++) {

                double Cpj = Math.abs(reduced.getValueAt(i, currRowIndex));
                if (Cpj > CpjMax) {
                    indexMax = i;
                    CpjMax = Cpj;
                }
            }
//			System.out.println("CpjMax: " + CpjMax + " In row: " + currRowIndex);

            // Shift CpjMax row to the current row position
            Matrix tempRow = reduced.getRowAsVector(currRowIndex);
            Matrix newPivotRow = reduced.getRowAsVector(indexMax);
            double leadingTerm = newPivotRow.getValueAt(0, currRowIndex);
//			System.out.println("scaling by (1 / " + leadingTerm + ")");
//			System.out.println("Put the following row in position: " + currRowIndex);
//			System.out.println(newPivotRow.scalarMult(1.0 / leadingTerm));
            reduced.replaceRow(newPivotRow, currRowIndex);
            if (indexMax != currRowIndex) {
                sign *= (-1);
                counter++;
                reduced.replaceRow(tempRow, indexMax);
            }
//			System.out.println("After shifting and scaling: ");
//			System.out.println(reduced);

            // Pivot down to turn everything below Cpj to 0
            for (int j = currRowIndex + 1; j < reduced.rowNum; j++) {
                Matrix pivotRow = reduced.getRowAsVector(currRowIndex);
                double leadingTerm1 = pivotRow.getValueAt(0, currRowIndex);
                Matrix rowToReplace = reduced.getRowAsVector(j);
                double leadingTerm2 = rowToReplace.getValueAt(0, currRowIndex);
                Matrix zeroOut = rowToReplace.add(pivotRow.scalarMult(-leadingTerm2 / leadingTerm1));
                reduced.replaceRow(zeroOut, j);
            }
//			System.out.println("Zero out everything below Cpj");
//			System.out.println(reduced);
        }

        // calculate the determinant
        double determinant = 1;
        for (int i = 0; i < reduced.rowNum; i++) {
            for (int j = 0; j < reduced.rowNum; j++) {
                if (i == j) {
                    determinant *= reduced.getValueAt(i, j);
                }
            }
        }
        reducedAndDeterminant.add(reduced);
        reducedAndDeterminant.add(determinant * (Math.pow(-1, counter)));
//		System.out.println("counter of row changes " + counter);
        return reducedAndDeterminant;
    }

    /**
     * Finds the condition number of a matrix
     *
     * @return the condition number of the matrix
     */
    public double getConditionNumber() {
        double conditionNumber = 0;

        for (int i = 0; i < this.rowNum; i++) {
            double tempSum = 0;
            for (int j = 0; j < this.rowNum; j++) {
                tempSum += Math.abs(this.getValueAt(i, j));
            }
            if (tempSum > conditionNumber) {
                conditionNumber = tempSum;
            }
        }
        return conditionNumber;
    }

    /**
     * Calculates the sum of the diagonal (the trace)
     *
     * @return the trace value
     */
    public double getTrace() {
        double trace = 0;
        for (int i = 0; i < this.rowNum; i++) {
            for (int j = 0; j < this.colNum; j++) {
                if (i == j) {
                    trace += this.matrix[i][j];
                }
            }
        }
        return trace;
    }

    /**
     * sets all values of a matrix = 0
     *
     * @return a zeroed out matrix
     */
    public Matrix setAllValuesTo(double value) {
        Matrix homogeneous = new Matrix(new double[this.rowNum][this.colNum]);

        for (int i = 0; i < this.rowNum; i++) {
            for (int j = 0; j < this.colNum; j++) {
                homogeneous.setValueAt(i, j, value);
            }
        }
        return homogeneous;
    }

    /**
     * Calculates the mean (row) vector from a matrix
     *
     * @return the mean vector
     */
    public Matrix getMeanVector() {
        Matrix mean = new Matrix(new double[1][this.colNum]).setAllValuesTo(0);

        for (int i = 0; i < this.rowNum; i++) {
            mean = mean.add(this.getRowAsVector(i));
        }
        return mean.scalarMult(1 / (double) this.rowNum);
    }

    /**
     * Split a matrix into two at a given index
     *
     * @param splitIndex the index you want to split at
     * @return the two new matrices
     */
    public Matrix[] splitAtIndex(int splitIndex) {
        Matrix LHS = new Matrix(new double[rowNum][splitIndex]);
        Matrix RHS = new Matrix(new double[rowNum][colNum - splitIndex]);

        for (int i = 0; i < this.rowNum; i++) {
            for (int j = 0; j < this.colNum; j++) {
                if (j >= splitIndex) {
                    RHS.setValueAt(i, j - splitIndex, this.getValueAt(i, j));
                } else {
                    LHS.setValueAt(i, j, this.getValueAt(i, j));

                }

            }
        }
        return new Matrix[]{LHS, RHS};
    }

    /**
     * converts the matrix to a string representation
     */
//	public String toString() {
//		String dimensions = "Dimensions: " + Integer.toString(rowNum)
//		+ " x " + Integer.toString(colNum) + System.lineSeparator();
//
//		String answer = "";
//		for(int i = 0; i < matrix.length; i++) {
//			for(int j = 0; j < matrix[i].length; j++) {
//				double value = matrix[i][j];
//				answer += (double)Math.round(value * 100000000d) / 100000000d + "	";
////				answer += value + " ";
//
//				}
//			answer += System.lineSeparator();
//		}
//		return dimensions + answer;
//	}

    /**
     * print the given row of the matrix
     *
     * @param i the row you want to print
     * @return the row you selected
     */
    public String printRow(int i) {
        double[] row = this.getRowAsArray(i);
        String answer = "";
        if (rowNum == 1 && colNum == 1) {
            return answer += row[i];
        }
        for (int j = 0; j < row.length - 1; j++) {
            answer += row[j] + ", ";
        }
        return answer + row[row.length - 1];
    }

    /**
     * tests if two matrices are equal or not
     *
     * @param otherMatrix another matrix
     * @return true if true, false if false
     */
    public Boolean equals(Matrix otherMatrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] != otherMatrix.getValueAt(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }


//	public static void main(String [] args) {
//		Matrix m = new Matrix(new double[] {1, 0, 1});
//		Matrix like = new Matrix(new double[] {9, 2, 8});
//		Matrix dislike = new Matrix(new double[] {2, 6, 1});
//
////		for(int i = 0; i < 5000; i++) {
//			m = m;
//			double cos1 = like.cos_sim(dislike);
//			System.out.println(cos1);
//			like = like.normalize();
//			dislike = dislike.normalize();
//			double cos2 = like.cos_sim(dislike);
//			System.out.println(cos2);
//			m = m.add(dislike.scalarMult(-1).add(like));
////		}
//
////		System.out.println(m);
//	}
}
