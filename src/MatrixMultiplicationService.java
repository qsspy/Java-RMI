import java.rmi.Remote;

public interface MatrixMultiplicationService extends Remote {

    int[][] multiplyMatrixes(int[][] firstMatrix, int[][] secondMatrix);
}
