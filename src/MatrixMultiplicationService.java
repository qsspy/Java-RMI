import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MatrixMultiplicationService extends Remote {

    int[][] multiplyMatrixes(int[][] firstMatrix, int[][] secondMatrix) throws RemoteException;
}
