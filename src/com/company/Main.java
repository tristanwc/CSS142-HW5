package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    private static double[][] grades;
    private static String[] names;
    private static String[] nameMax;
    private static String[] nameMin;
    private static double[] average;
    private static int row;
    private static int col;
    private static int counter = 0;

    public static void main(String[] args) throws FileNotFoundException {
        File f = new File("Input.txt");
        Scanner reader = new Scanner(f);
        PrintWriter output = new PrintWriter(new FileOutputStream("Output.txt"));

        row = reader.nextInt();
        col = reader.nextInt();

        names = new String[row];
        grades = new double[row][col - 1];

        double[] min = new double[col - 1];
        double[] max = new double[col - 1];
        double[] avg = new double[col - 1];
        nameMax = new String[col - 1];
        nameMin = new String[col - 1];
        average = new double[col - 1]

        String name = "";
        int count = 0;
        for (int i = 0; i < row; i++) {
            names[i] = reader.next();
            for (int j = 0; j < col - 1; j++) {
                grades[i][j] = reader.nextDouble();

            }
        }
        for (int i = 0; i < row; i++) {
            count++;
            if (count > 1)
                output.write("\n");
            output.write(names[i] + " ");
            for (int j = 0; j < col - 1; j++) {
                output.write(grades[i][j] + " ");


            }
        }
        for (int i = 0; i < col - 1; i++) {
            System.out.println("Q" + (i + 1) + ":");
            System.out.println("  Minimum: " + getMinCol());
            System.out.println("\t" + nameMin[i]);
            System.out.println("  Maximum: " + getMaxCol());
            System.out.println("\t" + nameMax[i]);
            System.out.println("  Average: " + getAverageCol(i));
        }
        reader.close();
        output.close();
    }

    public static double getMaxCol() {
        double max = Integer.MIN_VALUE;

        for (int i = 0; i < col - 1; i++) {
            max = 0;
            for (int j = 0; j < row; j++) {
                if (grades[j][i] > max) {
                    max = grades[j][i];
                    nameMax[i] = names[j];
                } else if (grades[j][i] == max) {
                    nameMax[i] += " " + names[j];
                }
            }
        }
        return max;
    }

    public static double getMinCol() {
        double min = 0;

        for (int i = 0; i < col - 1; i++) {
            min = Integer.MAX_VALUE;
            for (int j = 0; j < row; j++) {
                if (grades[j][i] < min) {
                    min = grades[j][i];
                    nameMin[i] = names[j];
                } else if (grades[j][i] == min) {
                    nameMin[i] += " " + names[j];
                }
            }
        }
        return min;
    }

    public static double getAverageCol(int n) {
        double averageScore;
        if (n == 0) {
            for (int i = 0; i < col - 1; i++) {
                averageScore = 0;
                for (int j = 0; j < row; j++) {
                    averageScore += grades[j][i];
                }

                average[i] = Math.round((averageScore / (row)) * 100.00) / 100.00;
            }
        }
        return average[n];
    }
}