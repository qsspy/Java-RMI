import java.rmi.RemoteException;

public class CalculationServiceImpl implements CalculationService{

    @Override
    synchronized public int calculate(final OperationDto operation) throws RemoteException {
        System.out.println("Started Working...");
        try {
            Thread.sleep(Utils.getRandomLong(1000, 1000));
        } catch (final InterruptedException exception) {
            exception.printStackTrace();
        }

        int result;
        switch (operation.getOperator()) {
            case ADD -> result = add(operation.getArg1(), operation.getArg2());
            case MULTIPLY -> result = multiply(operation.getArg1(), operation.getArg2());
            default -> {
                System.out.println("Wykryto nie wspieraną operację, zwracam 0!");
                result = 0;
            }
        }

        System.out.println("Finished Working...");
        return result;
    }

    private int add(final int arg1,final int arg2) {
        return arg1 + arg2;
    }

    private int multiply(final int arg1, final int arg2) {
        return arg1 * arg2;
    }
}
