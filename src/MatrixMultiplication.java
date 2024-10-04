import java.io.BufferedReader; //Imports all the necessary classes for the methods in this class to function
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class MatrixMultiplication { //Establishes the class that will house all the methods used in this project

    public static void main(String[] args) throws Exception {
        Scanner scnr = new Scanner(System.in); //Establishes the scanner object to get user input
        
        int loop = 0; //Establishes variable for the loop
        while (loop != 3) { //Sets to loop until the value is not three
            System.out.println("Enter 1 if you would like to enter filenames, 2 if you would like to generate matrices to multiply, or 3 to quit: ");
            int choice = scnr.nextInt(); //Gets the user input for their choice 
            scnr.nextLine();//Consumes the open nextLine() to prevent any conflicts
            if (choice == 1) {
                System.out.println("Please enter your first filename including the extension: "); //Gets the two filenames from the user
                String file1 = scnr.nextLine();
                System.out.println("Please enter your second filename including the extension: ");
                String file2 = scnr.nextLine();
                int[][] m1 = readMatrixFromFile(file1); //Reads the two files and stores the matrices
                int[][] m2 = readMatrixFromFile(file2);
                int[][] m3 = matrixMultiply(m1, m2); //Multiplies the two matrices and stores the result
                writeMatrixToFile("result.txt", m3); //Writes the result to a new file
                System.out.println("Your multiplied matrix has been written to file result.txt.");
                
            } else if (choice == 2) {
                System.out.println("Please enter an integer for your matrix: ");
                int mCount = scnr.nextInt(); //Gets the value the user wants for the square matrices
                int[][] m1 = generateMatrix(mCount); //Generates the first two matrixes and writes them to files matrix1.txt and matrix2.txt
                writeMatrixToFile("matrix1.txt", m1);
                int[][] m2 = generateMatrix(mCount);
                writeMatrixToFile("matrix2.txt", m2);
                int[][] m3 = matrixMultiply(m1, m2); //Multiplies the two matrices and writes the product to matrix3.txt
                writeMatrixToFile("matrix3.txt", m3);
                System.out.println("Your two matrices have been written to matrix1.txt and matrix2.txt, and their product has been written to matrix3.txt");
            } else if (choice == 3) {
                System.out.println("Goodbye!");
                loop = 3; //Sets the loop variable to 3, ending the loop
            } else {
                System.out.println("Please enter a valid number."); //Reminds the user to enter the correct number and resets the loop
            }
        }
    }



    public static int[][] readMatrixFromFile(String filePath) throws IOException { //Establishes a method that will read and return a matrix from a text file
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) { //Try statement that created a BufferedReader object to read through the specified text file
            int rows = 0; //Establishes variables to keep count of how many rows and columns the matrix has 
            int col = 0;
            String line;
            while ((line = reader.readLine()) != null) { //While loop that keeps looping through the lines of the matrix until there is nothing
                rows++; //Adds to the row counter
                String[] elements = line.split(" "); //Creates a list of each element in a single row in the matrix
                if (col == 0) {
                    col = elements.length; //Writes the length of a row to the variable keeping track of the column count
                }
            }

            int[][] matrix = new int[rows][col]; //Initializes a new matrix to write to

            try (BufferedReader resetReader = new BufferedReader(new FileReader(filePath))) { //Establishing another try statement with a BufferedReader object
                int currentRow = 0; //A counter to keep track of the row the loop is currently on
                while ((line = resetReader.readLine()) != null) { //While loop that keeps going through the rows of the matrix until there is nothing
                    String[] elements = line.split(" "); //Creates a list to store the elements in each row
                    for (int i = 0; i < elements.length; i++) { //For loop to loop through each element on each row
                        matrix[currentRow][i] = Integer.parseInt(elements[i]); //Converts the current element to an integer and adds it to the matrix
                    }
                    currentRow++; //Advances the row
                }
            }

            return matrix; //Outputs the matrix
        }

    }

    public static void writeMatrixToFile(String filePath, int[][] matrix) throws IOException { //Establishes a void method that returns nothing but writes a matrix to a file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) { //Establishing the try statement that creates a BufferedWriter object to write to the specified file
            for (int[] row : matrix) { //Outer for loop that controls which row is being written to
                for (int element : row) { //Inner for loop that writes the values in each row
                    writer.write(element + " "); //Writes the number and adds a space after for formatting
                }
                writer.newLine(); //Goes to the next line
            }
        }
    }

    public static int[][] matrixMultiply(int[][] matrix1, int[][] matrix2) { //Establishes a method that will multiply two matrices together and return the output

        int[][] matrix3 = new int[matrix1.length][matrix2[0].length]; //Initializes a matrix with the number of rows from the first matrix and number of columns from the second matrix

        for (int row = 0; row < matrix1.length; row++) { //Iterates over each row of the first matrix
            for (int col = 0; col < matrix2[0].length; col++) { //Iterates over each column of the second matrix
                for (int place = 0; place < matrix1[0].length; place++) {
                    matrix3[row][col] += matrix1[row][place] * matrix2[place][col]; //Assigns each place in the product matrix the calculated dot product
                }
            }
        }

        return matrix3; //Outputs the product matrix
    }

    public  static int[][] generateMatrix(int rowNum) { //Establishes a method that wil generate a matrix with its size based on the inputted integer
        int[][] matrix = new int[rowNum][rowNum]; //Establishes an integer with the specified width and length from the user
        for (int row = 0; row < matrix.length; row++) { //Two loops to loop through the established matrix
            for (int col = 0; col < matrix.length; col++) {
                Random random = new Random();
                matrix[row][col] += (random.nextInt(99 - 1) + 1); //Assigns each spot in the matrix a random integer from 1 to 99
            }
        }
        return matrix; //Outputs the matrix
    }
}
