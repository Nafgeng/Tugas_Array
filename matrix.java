import java.util.*;

public class matrix {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--- PROGRAM OPERASI MATRIKS ---");
        
        System.out.print("Masukkan jumlah BARIS: ");
        int rows = scanner.nextInt();
        System.out.print("Masukkan jumlah KOLOM: ");
        int cols = scanner.nextInt();

        int[][] matrixData = new int[rows][cols];
        System.out.println("\nMasukkan elemen-elemen matriks (" + rows + "x" + cols + "):");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print("Matriks[" + i + "][" + j + "]: ");
                matrixData[i][j] = scanner.nextInt();
            }
        }

        while (true) {
            System.out.println("\n=== MENU OPERASI MATRIKS ===");
            System.out.println("1-a. Sort the matrix row-wise");
            System.out.println("1-b. Sort the matrix column-wise");
            System.out.println("2-a. Rotate Matrix Clockwise by 1");
            System.out.println("2-b. Rotate Matrix Counter-Clockwise by 1");
            System.out.println("2-c. Rotate a matrix by 90");
            System.out.println("2-d. Rotate a matrix by 180");
            System.out.println("3-a. Row-wise traversal of matrix");
            System.out.println("3-b. Column-wise traversal of matrix");
            System.out.println("4.   Print matrix in spiral form");
            System.out.println("5.   Transpose matrix");
            System.out.println("6.   Quit");
            System.out.print("Pilih menu: ");
            String choice = scanner.next().toLowerCase();

            if (choice.equals("6")) {
                System.out.println("Keluar dari program. Bye!");
                break;
            }

            int[][] temp = cloneMatrix(matrixData);

            switch (choice) {
                case "1-a":
                    sortRowWise(temp);
                    printMatrix(temp);
                    break;
                case "1-b":
                    sortColumnWise(temp);
                    printMatrix(temp);
                    break;
                case "2-a":
                    rotateClockwiseBy1(temp);
                    printMatrix(temp);
                    break;
                case "2-b":
                    rotateCounterClockwiseBy1(temp);
                    printMatrix(temp);
                    break;
                case "2-c":
                    printMatrix(rotate90(matrixData));
                    break;
                case "2-d":
                    printMatrix(rotate180(matrixData));
                    break;
                case "3-a":
                    rowWiseTraversal(matrixData);
                    break;
                case "3-b":
                    columnWiseTraversal(matrixData);
                    break;
                case "4":
                    printSpiral(matrixData);
                    break;
                case "5":
                    printMatrix(transpose(matrixData));
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }
        scanner.close();
    }

    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int val : row) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }

    public static int[][] cloneMatrix(int[][] matrix) {
        int[][] temp = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            temp[i] = matrix[i].clone();
        }
        return temp;
    }

    public static void sortRowWise(int[][] matrix) {
        for (int[] row : matrix) Arrays.sort(row);
    }

    public static void sortColumnWise(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        for (int j = 0; j < cols; j++) {
            int[] col = new int[rows];
            for (int i = 0; i < rows; i++) col[i] = matrix[i][j];
            Arrays.sort(col);
            for (int i = 0; i < rows; i++) matrix[i][j] = col[i];
        }
    }

    public static void rotateClockwiseBy1(int[][] matrix) {
        int top = 0, bottom = matrix.length - 1;
        int left = 0, right = matrix[0].length - 1;
        while (left < right && top < bottom) {
            int prev = matrix[top + 1][left];
            for (int i = left; i <= right; i++) {
                int curr = matrix[top][i]; matrix[top][i] = prev; prev = curr;
            }
            top++;
            for (int i = top; i <= bottom; i++) {
                int curr = matrix[i][right]; matrix[i][right] = prev; prev = curr;
            }
            right--;
            for (int i = right; i >= left; i--) {
                int curr = matrix[bottom][i]; matrix[bottom][i] = prev; prev = curr;
            }
            bottom--;
            for (int i = bottom; i >= top; i--) {
                int curr = matrix[i][left]; matrix[i][left] = prev; prev = curr;
            }
            left++;
        }
    }

    public static void rotateCounterClockwiseBy1(int[][] matrix) {
        int top = 0, bottom = matrix.length - 1;
        int left = 0, right = matrix[0].length - 1;
        while (left < right && top < bottom) {
            int prev = matrix[top][left + 1];
            for (int i = top; i <= bottom; i++) {
                int curr = matrix[i][left]; matrix[i][left] = prev; prev = curr;
            }
            left++;
            for (int i = left; i <= right; i++) {
                int curr = matrix[bottom][i]; matrix[bottom][i] = prev; prev = curr;
            }
            bottom--;
            for (int i = bottom; i >= top; i--) {
                int curr = matrix[i][right]; matrix[i][right] = prev; prev = curr;
            }
            right--;
            for (int i = right; i >= left - 1; i--) {
                
                int curr = matrix[top][i]; matrix[top][i] = prev; prev = curr;
            }
            top++;
        }
    }

    public static int[][] rotate90(int[][] matrix) {
        int[][] transposed = transpose(matrix);
        for (int[] row : transposed) reverseArray(row);
        return transposed;
    }

    public static int[][] rotate180(int[][] matrix) {
        int rows = matrix.length, cols = matrix[0].length;
        int[][] result = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[rows - 1 - i][cols - 1 - j] = matrix[i][j];
            }
        }
        return result;
    }

    public static void rowWiseTraversal(int[][] matrix) {
        System.out.print("Row-wise Traversal: ");
        for (int[] row : matrix) {
            for (int val : row) System.out.print(val + " ");
        }
        System.out.println();
    }

    public static void columnWiseTraversal(int[][] matrix) {
        System.out.print("Column-wise Traversal: ");
        for (int j = 0; j < matrix[0].length; j++) {
            for (int i = 0; i < matrix.length; i++) System.out.print(matrix[i][j] + " ");
        }
        System.out.println();
    }

    public static void printSpiral(int[][] matrix) {
        int top = 0, bottom = matrix.length - 1;
        int left = 0, right = matrix[0].length - 1;
        System.out.print("Spiral Form: ");
        while (left <= right && top <= bottom) {
            for (int i = left; i <= right; i++) System.out.print(matrix[top][i] + " ");
            top++;
            for (int i = top; i <= bottom; i++) System.out.print(matrix[i][right] + " ");
            right--;
            if (top <= bottom) {
                for (int i = right; i >= left; i--) System.out.print(matrix[bottom][i] + " ");
                bottom--;
            }
            if (left <= right) {
                for (int i = bottom; i >= top; i--) System.out.print(matrix[i][left] + " ");
                left++;
            }
        }
        System.out.println();
    }

    public static int[][] transpose(int[][] matrix) {
        int rows = matrix.length, cols = matrix[0].length;
        int[][] result = new int[cols][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) result[j][i] = matrix[i][j];
        }
        return result;
    }

    private static void reverseArray(int[] arr) {
        int i = 0, j = arr.length - 1;
        while (i < j) {
            int temp = arr[i]; arr[i] = arr[j]; arr[j] = temp;
            i++; j--;
        }
    }
}