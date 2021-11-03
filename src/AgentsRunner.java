import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class AgentsRunner {

    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(Configuration.RMI_REGISTRY_PORT);
            for (int i = 0; i < Configuration.MAX_AGENT_ID; i++) {
                int agentIndex = i + 1;
                String[] arg = new String[]{
                        String.valueOf(agentIndex)
                };
                Agent.main(arg);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
