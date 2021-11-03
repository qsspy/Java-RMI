import java.rmi.RemoteException;
import java.util.Optional;

public class Executor extends Thread {
    private final CalculationService agent;
    private final OperationDto operation;
    private int result;

    private OnCalculationFinished onCalculationFinished;

    public Executor(CalculationService agent, OperationDto operation) {
        this.agent = agent;
        this.operation = operation;
        this.result = 0;
    }

    @Override
    public void run() {
        try {
            result = agent.calculate(operation);
            Optional.of(onCalculationFinished)
                    .ifPresent(action -> action.onCalculationFinished(result));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @FunctionalInterface
    interface OnCalculationFinished {
        void onCalculationFinished(final int result);
    }

    public void setOnCalculationFinishedListener(final OnCalculationFinished callback) {
        this.onCalculationFinished = callback;
    }
}
