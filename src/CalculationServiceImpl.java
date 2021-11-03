import java.rmi.RemoteException;

public class CalculationServiceImpl implements CalculationService{

    @Override
    synchronized public int calculate(final OperationDto operation) throws RemoteException {
        //simulatedWaitTime
        System.out.println("Started Working....");
        try {
            Thread.sleep(Utils.getRandomLong(Configuration.AGENT_MIN_SLEEP_TIME, Configuration.AGENT_MAX_SLEEP_TIME));
        } catch (final InterruptedException exception) {
            exception.printStackTrace();
        }

        //TODO jakoś zmienić
        System.out.println("Finished Working....");

        switch (operation.getOperator()) {
            case ADD -> {
                return add(operation.getArg1(), operation.getArg2());
            }
            case MULTIPLY -> {
                return multiply(operation.getArg1(), operation.getArg2());
            }
            default -> {
                System.out.println("Wykryto nie wspieraną operację, zwracam 0!");
                return 0;
            }
        }
    }

    private int add(final int arg1,final int arg2) {
        return arg1 + arg2;
    }

    private int multiply(final int arg1, final int arg2) {
        return arg1 * arg2;
    }
}
