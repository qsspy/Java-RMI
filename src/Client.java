import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

    public static void main(String[] args) {
        final MatrixHelper helper = new MatrixHelper(2,2);
        helper.promptForMatrixInput();


        //TODO for debug, delete later
        helper.showMatrixes();

        try {
            // Getting the registry
            Registry registry = LocateRegistry.getRegistry(Configuration.RMI_REGISTRY_PORT);

            // Looking up the registry for the remote object
            MatrixMultiplicationService service = (MatrixMultiplicationService) registry.lookup(Configuration.SERVER_SERVICE_IMPL_NAME);

            // Calling the remote method using the obtained object
            int[][] outputMatrix = service.multiplyMatrixes(helper.getFirstMatrix(), helper.getSecondMatrix());
            System.out.println("Macierz wynikowa :");
            helper.printMatrix(outputMatrix);

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
