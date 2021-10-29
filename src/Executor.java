import java.rmi.RemoteException;

public class Executor extends Thread {
    private final CalculationService agent;
    private final OperationDto operation;
    private int result;

    public Executor(CalculationService agent, OperationDto operation) {
        this.agent = agent;
        this.operation = operation;
        this.result = 0;
    }

    @Override
    public void run() {
        try {
            result = agent.calculate(operation);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public int getResult() {
        return this.result;
    }
}
