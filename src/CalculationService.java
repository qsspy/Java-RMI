import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CalculationService extends Remote {

    int calculate(final OperationDto operation) throws RemoteException;
}
