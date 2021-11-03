import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

    public static void main(String[] args) {
        final MatrixHelper helper = new MatrixHelper(2,2);
        helper.promptForMatrixInput();

        helper.showMatrixes();

        try {
            // Getting the registry
            final Registry registry = LocateRegistry.getRegistry(Configuration.RMI_REGISTRY_PORT);

            // Looking up the registry for the remote object
            final MatrixMultiplicationService service = (MatrixMultiplicationService) registry.lookup(Configuration.SERVER_SERVICE_IMPL_NAME);

            final long startTime = System.currentTimeMillis();
            // Calling the remote method using the obtained object
            int[][] outputMatrix = service.multiplyMatrixes(helper.getFirstMatrix(), helper.getSecondMatrix());
            final long stopTime = System.currentTimeMillis();

            System.out.println("Macierz wynikowa :");
            helper.printMatrix(outputMatrix);
            System.out.println("Czas (ms) wykonania zadania: " + (stopTime - startTime));

        } catch (final Exception exception) {
            exception.printStackTrace();
        }
    }
}
