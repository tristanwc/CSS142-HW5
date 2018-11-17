import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * The ExamStats program takes an Input file of exam scores from a list of students
 * and takes two integers to determine matrix. The program calls methods which
 * do calculations detailed in the homework documentation, getting the min, max, and
 * average of a column for all questions, all sections and total. The program was
 * first written with many global variables but was made to be more future proof by
 * having it pass parameters instead.
 *
 * @author Tristan Cortez
 * @version 11-17-2018
 */
public class ExamStats {
    /* Global variables declared */
    private static String[] names;

    /* Main declares and passes variables and calls methods to output statistics */
    public static void main(String[] args) throws FileNotFoundException {
        File f = new File("Input.txt");
        Scanner reader = new Scanner(f);
        PrintWriter output = new PrintWriter(new FileOutputStream("Output.txt"));

        int row = reader.nextInt();
        int col = reader.nextInt();

        names = new String[row];
        double[][] grades = new double[row][col - 1];
        double[][] sectionScores = new double[row][3];
        double[][] totalSectionScores = new double[row][1];
        double[] maxArr = new double[col - 1];
        double[] minArr = new double[col - 1];

        double[] average = new double[col - 1];
        String[] nameMax = new String[col - 1];
        String[] nameMin = new String[col - 1];

        /* Get's all grades into a 2D array (Grades) */
        for (int i = 0; i < row; i++) {
            names[i] = reader.next();
            for (int j = 0; j < col - 1; j++) {
                grades[i][j] = reader.nextDouble();
            }
        }

        /* Prints the input into the output and calls calculations methods */
        printLine(output, col);
        output.write("\nScore per section per student\n");
        printLine(output, col);
        output.write("\n");
        printScore(output, col, row, grades);
        calculateMinCol(col, row, grades, minArr, nameMin);
        calculateMaxCol(col, row, grades, maxArr, nameMax);
        average = calculateAverage(col, row, grades, average);
        printTwoLines(output, col);

        /* Prints all questions (columns) min, max and average with names of the same score */
        output.write("\nMinimum, Maximum and Average per question\n");
        printLine(output, col);
        for (int i = 0; i < col - 1; i++) {
            output.write("\nQ" + (i + 1));
            output.write("\n  Minimum: " + getMinCol(i, minArr) + "\n");
            output.write("\t " + nameMin[i]);
            output.write("\n  Maximum: " + getMaxCol(i, maxArr) + "\n");
            output.write("\t " + nameMax[i]);
            output.write("\n  Average: " + average[i] + "\n");
        }
        printTwoLines(output, col);

        /* Prints all section scores, [1], [2-5], [6-10]*/
        output.write("\nScore per section per student\n");
        printLine(output, col);
        output.write("\n");
        printSectionScore(output, col, row, grades, sectionScores);
        printTwoLines(output, col);

        /* Prints all sections min, max and average */
        output.write("\nMinimum, Maximum and Average per Section\n");
        printLine(output, col);
        printSectionStats(output, row, col, sectionScores, nameMin, nameMax);
        printTwoLines(output, col);

        /* Prints total score of the exam per student */
        output.write("\nTotal overall score per student\n");
        printLine(output, col);
        printTotalSection(output, row, sectionScores, totalSectionScores);
        printTwoLines(output, col);

        /* Prints min, max and average of total scores */
        output.write("\nMinimum, Maximum and Average per Total\n");
        printLine(output, col);
        printTotalSectionStats(output, row, col, totalSectionScores, nameMin, nameMax);
        printLine(output, col);
        output.close();
    }

    /* Prints a line to beautify output */
    public static void printLine(PrintWriter output, int col) {
        for (int i = 0; i < (col * 4.5); i++)
            output.write("-");
    }

    /* Prints two lines to beautify output */
    public static void printTwoLines(PrintWriter output, int col) {
        output.write("\n");
        for (int i = 0; i < (col * 4.5); i++)
            output.write("-");
        output.write("\n\n");
        for (int i = 0; i < (col * 4.5); i++)
            output.write("-");
    }

    /* Takes int col, int row, double[][] grades, double[] maxArr, String[] nameMax and returns a double array
     *  The method calculates the max of each column of the 2-D array [][]grades is passed */
    public static double[] calculateMaxCol(int col, int row, double[][] grades, double[] maxArr,
                                           String[] nameMax) {
        double max;

        for (int i = 0; i < col - 1; i++) {
            max = Integer.MIN_VALUE;
            for (int j = 0; j < row; j++) {
                if (grades[j][i] > max) {
                    max = grades[j][i];
                    maxArr[i] = grades[j][i];
                    nameMax[i] = names[j];
                } else if (grades[j][i] == max) {
                    nameMax[i] += " " + names[j];
                }
            }
        }
        return maxArr;
    }

    /* Takes int col, int row, double[][] grades, double[] minArr, String[] nameMin and returns a double array
     * The method calculates the min of each column of the 2-D array [][]grades is passed */
    public static double[] calculateMinCol(int col, int row, double[][] grades, double[] minArr,
                                           String[] nameMin) {
        double min;
        for (int i = 0; i < col - 1; i++) {
            min = Integer.MAX_VALUE;
            for (int j = 0; j < row; j++) {
                if (grades[j][i] < min) {
                    min = grades[j][i];
                    nameMin[i] = names[j];
                    minArr[i] = grades[j][i];
                } else if (grades[j][i] == min) {
                    nameMin[i] += " " + names[j];
                }
            }
        }
        return minArr;
    }

    /* Takes int col, int row, double[][] grades, double[] average and returns a double array
     * The method calculates the average of each column of the 2-D array [][]grades is passed */
    public static double[] calculateAverage(int col, int row, double[][] grades, double[] average) {
        double averageScore;
        for (int i = 0; i < col - 1; i++) {
            averageScore = 0;
            for (int j = 0; j < row; j++) {
                averageScore += grades[j][i];
            }
            average[i] = Math.round((averageScore / (row)) * 100.00) / 100.00;
        }
        return average;
    }

    /* Takes int n, double[] arr and returns a double (average)
     * The array that is passed is the average and returns the index passed */
    public static double getAverageCol(int n, double[] arr) {
        return arr[n];
    }

    /* Takes int n, double[] arr and returns a double (max val in col)
     * The array that is passed is the maxArray and returns the index passed */
    public static double getMaxCol(int n, double[] arr) {
        return arr[n];
    }

    /* Takes int n, double[] arr and returns a double (min val in col)
     * The array that is passed is the minArray and returns the index passed */
    public static double getMinCol(int n, double[] arr) {
        return arr[n];
    }

    /* Takes PrintWriter output, int col, int row, double[][] grades
     * Prints each and every users score */
    public static void printScore(PrintWriter output, int col, int row, double[][] grades) {
        int count = 0;
        for (int i = 0; i < row; i++) {
            count++;
            if (count > 1)
                output.write("\n");
            output.write(names[i] + " ");
            for (int j = 0; j < col - 1; j++) {
                output.write(grades[i][j] + " ");

            }
        }
    }

    /* Takes PrintWriter output, int col, int row, double[][] grades, double[][] sectionScores
     * Prints the three section scores */
    public static void printSectionScore(PrintWriter output, int col, int row, double[][] grades,
                                         double[][] sectionScores) {
        int count = 0;
        double sec1;
        double sec2;
        double sec3;
        for (int i = 0; i < row; i++) {
            count++;
            sec2 = 0;
            sec3 = 0;
            if (count > 1)
                output.write("\n");
            output.write(names[i] + " ");
            for (int j = 0; j < col - 1; j++) {
                if (j == 0) {
                    sec1 = grades[i][j];
                    sectionScores[i][j] = sec1;
                    output.write(sec1 + " ");
                } else if (j > 0 && j < 6) {
                    sec2 += grades[i][j];
                    if (j == 5) {
                        sectionScores[i][j - 4] = sec2;
                        output.write(sec2 + " ");
                    }
                } else {
                    sec3 += grades[i][j];
                    if (j == 10) {
                        sectionScores[i][j - 8] = sec3;
                        output.write(sec3 + " ");
                    }
                }
            }
        }
    }

    /* Takes PrintWriter output, int col, int row, double[][] sectionScores, double[][] totalSectionScores
     * Prints the total exam grades of each person */
    public static void printTotalSection(PrintWriter output, int row, double[][] sectionScores, double[][] totalSectionScores) {
        output.write("\n");
        int count = 0;
        double total;
        for (int i = 0; i < row; i++) {
            count++;
            total = 0;
            if (count > 1)
                output.write("\n");
            output.write(names[i] + " ");
            for (int j = 0; j < 3; j++) {
                total += sectionScores[i][j];
                if (j == 2) {
                    output.write(total + " ");
                    totalSectionScores[i][j - 2] = total;
                }
            }
        }
    }

    /* Takes PrintWriter output, int col, int row, double[][] sectionScores, String[] nameMin, String[] nameMax
     * Prints the min, max and average of the three sections of each person */
    public static void printSectionStats(PrintWriter output, int row, int col, double[][] sectionScores,
                                         String[] nameMin, String[] nameMax) {
        col = 4;
        double[] newMax = new double[3];
        double[] newMin = new double[3];
        double[] newAvg = new double[3];
        newMin = calculateMinCol(col, row, sectionScores, newMin, nameMin);
        newMax = calculateMaxCol(col, row, sectionScores, newMax, nameMax);
        newAvg = calculateAverage(col, row, sectionScores, newAvg);

        for (int i = 0; i < 3; i++) {
            output.write("\nSection: " + (i + 1));
            output.write("\n  Minimum: " + getMinCol(i, newMin) + "\n");
            output.write("\t " + nameMin[i]);
            output.write("\n  Maximum: " + getMaxCol(i, newMax) + "\n");
            output.write("\t " + nameMax[i]);
            output.write("\n  Average: " + newAvg[i] + "\n");
        }
    }

    /* Takes PrintWriter output, int row, int col, double[][] totalSectionScores, String[] nameMin, String[] nameMax
     * Prints the min, max and average of the total exam grades of each person */
    public static void printTotalSectionStats(PrintWriter output, int row, int col, double[][] totalSectionScores,
                                              String[] nameMin, String[] nameMax) {
        col = 2;
        double[] totalMin = new double[1];
        double[] totalMax = new double[1];
        double[] totalAvg = new double[1];
        totalMin = calculateMinCol(col, row, totalSectionScores, totalMin, nameMin);
        totalMax = calculateMaxCol(col, row, totalSectionScores, totalMax, nameMax);
        totalAvg = calculateAverage(col, row, totalSectionScores, totalAvg);
        output.write("\nTotal: ");
        output.write("\n  Minimum: " + getMinCol(0, totalMin) + "\n");
        output.write("\t " + nameMin[0]);
        output.write("\n  Maximum: " + getMaxCol(0, totalMax) + "\n");
        output.write("\t " + nameMax[0]);
        output.write("\n  Average: " + totalAvg[0] + "\n");
    }
}