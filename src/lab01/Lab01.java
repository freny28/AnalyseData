package lab01;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Date - 24th September, 2017
 * Author - @Freny @Patel
 * Student ID - 000744054 
 * Statement of Authorship - I had done this lab by my own 
 * and didn't copied anything without acknowledgment.
 */
public class Lab01 {

    public static void main(String[] args) {
        int[][] data = null; // array to store whole data of file ELEVATIONS.TXT
        int numRow = 0, numCol = 0; // to store the row and column of data.
        try {
            Scanner fileInput = new Scanner(new File("Resources/ELEVATIONS.TXT"));
            numRow = fileInput.nextInt();
            numCol = fileInput.nextInt();
            data = new int[numRow][numCol];
            while (fileInput.hasNext()) {
                for (int row = 0; row < data.length; row++) {
                    for (int col = 0; col < data[row].length; col++) {
                        data[row][col] = fileInput.nextInt();
                    }
                }
                System.out.println("");
            }
            fileInput.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Lab01.class.getName()).log(Level.SEVERE, null, ex);
        }
        long start = System.nanoTime(); // to cslculate the starting time
        int lowestNumber = data[0][0]; //lowest number in the data
        int lowestNumberCount = 0; // counter of lowest number
        int nearRow = 0, nearCol = 0; // nearest row and column to (0,0)
        double distance = 0.0, lowestDistance = 0.0; // distance to calculate all distances and lowestDistance for calculating lowest distance.
        int peaks = 0;// to count number of peaks
        for (int row = 0; row < data.length; row++) {
            for (int col = 0; col < data[row].length; col++) {
                if (lowestNumber > data[row][col]) {
                    lowestNumber = data[row][col];
                    lowestDistance = (double) (Math.pow(row, 2) + Math.pow(col, 2));
                    nearRow = row;
                    nearCol = col;
                }
            }
        }

        for (int row = 0; row < data.length; row++) {
            for (int col = 0; col < data[row].length; col++) {
                if (data[row][col] == lowestNumber) {
                    distance = 0.0;
                    distance = (double) (Math.pow(row, 2) + Math.pow(col, 2));

                    if (distance < lowestDistance) {
                        lowestDistance = distance;
                        nearRow = row;
                        nearCol = col;

                    }
                    lowestNumberCount++;
                }

            }
        }
        int arrayLength = (numRow - 2) * (numCol - 2);//array length for arrays having row and column of peaks
        int[] peakRows = new int[arrayLength]; // all rows of peaks will be store in this array
        int[] peakCols = new int[arrayLength]; // all columns of peaks will be store in this array
        int count = 0; // index for peakRows and peakCols array
        for (int row = 2; row < data.length - 2; row++) {
            for (int col = 2; col < data[row].length - 2; col++) {
                boolean peak = true; // to check peak is true or not.
                if (data[row][col] >= 8900 && data[row][col] <= 9000) {
                    for (int i = row - 2; i < row + 3; i++) {
                        for (int j = col - 2; j < col + 3; j++) {
                            if (((i != row && j == col) || (i == row && col != col)) && data[row][col] == data[i][j]) {
                                peak = false;
                                break;
                            }
                            if ((data[row][col] < data[i][j])) {
                                peak = false;
                                break;
                            }
                            if (i != row && j != col && data[row][col] == data[i][j]) {
                                peak = false;
                                break;
                            }

                        }
                        if (peak == false) {
                            break;
                        }

                    }
                    if (peak == true) {
                        peaks++;
                        peakRows[count] = row;
                        peakCols[count] = col;
                        count++;
                    }
                }
            }
        }
        int row1 = 0, col1 = 0, row2 = 0, col2 = 0; // row and colum of 2 peaks nearest to each other
        double lowPeakDistance = Math.pow((peakRows[0] - peakRows[1]), 2) + Math.pow((peakCols[0] - peakCols[1]), 2); // lowest distance between two peaks taking a reference distance. 
        double peakDistance; // to count all peaks distances
        for (int i = 0; i < peaks; i++) {
            for (int j = 0; j < peaks; j++) {
                if (i != j) {
                    peakDistance = Math.pow((peakRows[i] - peakRows[j]), 2) + Math.pow((peakCols[i] - peakCols[j]), 2);
                    if (lowPeakDistance > peakDistance) {
                        lowPeakDistance = peakDistance;
                        row1 = peakRows[i];
                        row2 = peakRows[j];
                        col1 = peakCols[i];
                        col2 = peakCols[j];
                    }
                }
            }
        }
        int largerNumber = data[0][0];// largest number in the data.
        for (int row = 0; row < data.length; row++) {
            for (int col = 0; col < data[row].length; col++) {
                if (largerNumber < data[row][col]) {
                    largerNumber = data[row][col];
                }
            }
        }
        int[] fq = new int[largerNumber + 1]; // array to count the frequency of each element in data
        for (int row = 0; row < data.length; row++) {
            for (int col = 0; col < data[row].length; col++) {
                fq[data[row][col]]++;
            }
        }
        int highf = 0; // to count the highest frequency in the data
        int mostRepeatedNumber = 0; // to store the number repeating the most in the data 
        for (int col = 0; col < fq.length; col++) {
            if (highf < fq[col]) {
                highf = fq[col];
                mostRepeatedNumber = col;
            }
        }
        long end = System.nanoTime(); // to calculate ending time
        System.out.println("The Lowest elevation is " + lowestNumber + " It occurs " + lowestNumberCount + " times in data");
        System.out.printf("The nearest distance is %.2f of (0,0) with the lowest Number is at (Row,Col) = (%d,%d) \n", Math.sqrt(lowestDistance), nearRow, nearCol);
        System.out.println("The Total number is peaks found in data is " + peaks);
        System.out.println("The minimum distance between two picks is " + Math.sqrt(lowPeakDistance));
        System.out.printf("They are located at (Row,Col) -  (%d,%d) and (%d,%d)", row1, col1, row2, col2);
        System.out.println();
        System.out.println("The most common height in the terrain is " + mostRepeatedNumber + " It occurs " + highf + " times");
        System.out.println("Time to execute = " + (end - start) / 1000 + " us");
        System.out.println("---------------------------------------------------------------------------------------------------\n\nProgram done by Freny Patel , 000744054 ");
    }
}
