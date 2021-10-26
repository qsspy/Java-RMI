import java.util.*;
import java.util.stream.IntStream;

public class MatrixHelper {

    private final int matrixWidth;
    private final int matrixHeight;

    private final Scanner scanner = new Scanner(System.in);

    private final int[][] firstMatrix;
    private final int[][] secondMatrix;

    public MatrixHelper(int matrixWidth, int matrixHeight) {

        if(matrixWidth < 1 || matrixHeight < 1) {
            throw new IllegalArgumentException("Wymiary macierzy muszą być dodatnie!");
        }

        this.matrixWidth = matrixWidth;
        this.matrixHeight = matrixHeight;

        firstMatrix = new int[matrixHeight][matrixWidth];
        secondMatrix = new int[matrixWidth][matrixHeight];
    }

    public MatrixHelper() {
        this.matrixWidth = Configuration.DEFAULT_MATRIX_DIMENSION;
        this.matrixHeight = Configuration.DEFAULT_MATRIX_DIMENSION;

        firstMatrix = new int[matrixHeight][matrixWidth];
        secondMatrix = new int[matrixWidth][matrixHeight];
    }

    public void promptForMatrixInput() {
        System.out.println("Wprowadź pierszą macierz(ilość kolumn : " + matrixWidth + ", ilość wierszy : " + matrixHeight + ")");
        showExampleMatrix(1);
        System.out.println("Wprowadź poniżej :");
        askForMatrixTillInputIsCorrect(1);
        System.out.println("Wprowadź drugą macierz(ilość kolumn : " + matrixHeight + ", ilość wierszy : " + matrixWidth + ")");
        showExampleMatrix(2);
        System.out.println("Wprowadź poniżej :");
        askForMatrixTillInputIsCorrect(2);
    }

    private void askForMatrixTillInputIsCorrect(final int numOfMatrix) {
        boolean inputCorrect = false;
        do {
            try {
                extractDataFromConsoleToMatrix(numOfMatrix);
                inputCorrect = true;
            } catch (final RuntimeException exception) {
                System.out.println("Nieprawidłowe dane, wprowadź macierz ponownie : ");
                System.out.println();
            }
        } while (!inputCorrect);
    }

    private void extractDataFromConsoleToMatrix(int numOfMatrix) {
        final int targetHeight;
        final int targetWidth;
        final int[][] targetMatrix;
        if(numOfMatrix == 1) {
            targetHeight = matrixHeight;
            targetWidth = matrixWidth;
            targetMatrix = firstMatrix;
        } else {
            targetHeight = matrixWidth;
            targetWidth = matrixHeight;
            targetMatrix = secondMatrix;
        }
        final List<String> listOfInputLines = new ArrayList<>();
        IntStream.range(0, targetHeight).forEach(y -> listOfInputLines.add(scanner.nextLine()));

        for(int i = 0; i < listOfInputLines.size(); i++) {
            targetMatrix[i] = stringLineToIntegerArray(listOfInputLines.get(i), targetWidth);
        }
    }

    private int[] stringLineToIntegerArray(final String line, final int targetWidth) {
        final String[] stringNums = line.split("\\s+");
        if(stringNums.length != targetWidth) {
            throw new RuntimeException("Jedna z wprowadzonych linii jest zbyt krótka!");
        }
        try{
            return Arrays.stream(stringNums)
                    .mapToInt(Integer::parseInt)
                    .toArray();
        } catch (final Exception exception) {
            throw new RuntimeException("Błąd na wejsćiu! Wprowadź tylko liczby całkowite");
        }
    }

    private void showExampleMatrix(int numOfMatrix) {
        System.out.println("Przykładowa macierz : ");
        System.out.println();
        if(numOfMatrix == 1) {
            IntStream.range(0, matrixHeight).forEach(y -> {
                IntStream.range(0, matrixWidth).forEach(x -> {
                    System.out.print(Utils.getRandomInt(1,9) + " ");
                });
                System.out.println();
            });
            System.out.println();
        } else if(numOfMatrix == 2) {
            IntStream.range(0, matrixWidth).forEach(y -> {
                IntStream.range(0, matrixHeight).forEach(x -> {
                    System.out.print(Utils.getRandomInt(1,9) + " ");
                });
                System.out.println();
            });
            System.out.println();
        }
    }

    void showMatrixes() {
        System.out.println("Wprowadzone macierze : ");
        System.out.println();
        printMatrix(firstMatrix);
        System.out.println();
        printMatrix(secondMatrix);
        System.out.println();
    }

    void printMatrix(final int[][] matrix) {
        for (final int[] ints : matrix) {
            for (final int anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println();
        }
    }

    public int[][] getFirstMatrix() {
        return firstMatrix;
    }

    public int[][] getSecondMatrix() {
        return secondMatrix;
    }
}