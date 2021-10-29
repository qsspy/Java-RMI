import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server extends MatrixMultiplicationServiceImpl {

    public Server() {}

    public static void main(String[] args) {
        try {
            MatrixMultiplicationServiceImpl obj = new MatrixMultiplicationServiceImpl();
            MatrixMultiplicationService service = (MatrixMultiplicationService) UnicastRemoteObject.exportObject(obj, 0);

            Registry registry = LocateRegistry.createRegistry(Configuration.RMI_REGISTRY_PORT);
            registry.bind(Configuration.SERVER_SERVICE_IMPL_NAME, service);
            System.out.println("Server ready");
        } catch (Exception e) {
            System.out.println("Server exception: " + e);
            e.printStackTrace();
        }
    }
}
