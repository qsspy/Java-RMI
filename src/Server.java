import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry(Configuration.RMI_REGISTRY_PORT);

            MatrixMultiplicationServiceImpl obj = new MatrixMultiplicationServiceImpl();
            MatrixMultiplicationService service = (MatrixMultiplicationService) UnicastRemoteObject.exportObject(obj, 0);

            registry.bind(Configuration.SERVER_SERVICE_IMPL_NAME, service);
            System.out.println("Server ready");
        } catch (Exception e) {
            System.out.println("Server exception: " + e);
            e.printStackTrace();
        }
    }
}
