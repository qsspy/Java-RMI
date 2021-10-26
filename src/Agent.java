import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Agent {
    public static void main(String[] args) {
        if(args.length == 0) {
            System.err.println("Nie podano id agenta. Zamykam!");
            return;
        }

        int agentId;

        try {
            agentId = Integer.parseInt(args[0]);
        } catch (final NumberFormatException exception) {
            System.err.println("Podane id nie jest liczbą. Zamykam!");
            return;
        }

        if(agentId < 1 || agentId > Configuration.MAX_AGENT_ID) {
            System.err.println("Maksymalne id agenta to " + Configuration.MAX_AGENT_ID + ". Zamykam!");
            return;
        }

        try {
            // Instantiating the implementation class
            final CalculationServiceImpl service = new CalculationServiceImpl();

            // Exporting the object of implementation class
            // (here we are exporting the remote object to the stub)
            final CalculationService serviceStub = (CalculationService) UnicastRemoteObject.exportObject(service, Configuration.REMOTE_OBJECT_PORT);

            // Binding the remote object (stub) in the registry
            final Registry registry = LocateRegistry.getRegistry(Configuration.RMI_REGISTRY_PORT);

            registry.bind("Agent" + agentId, serviceStub);
            System.out.println("Agent(id=" + agentId + ") gotowy!");
        } catch (final AlreadyBoundException exception) {
            System.err.println("Agent(id=" + agentId + ") jest już zbindowany!");
        } catch (final Exception exception) {
            System.err.println("Agent(id=" + agentId + ") exception: " + exception.toString());
            exception.printStackTrace();
        }
    }
}
