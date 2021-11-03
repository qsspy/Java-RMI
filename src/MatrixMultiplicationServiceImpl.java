import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MatrixMultiplicationServiceImpl implements MatrixMultiplicationService {

    List<CalculationService> agents = new ArrayList<>();
    private final int agentsInUse;

    public MatrixMultiplicationServiceImpl() {
        try {
            Registry registry = LocateRegistry.getRegistry(Configuration.RMI_REGISTRY_PORT);
            System.out.println(registry.list().length);
            Arrays.stream(registry.list())
                    .filter(stub -> stub.contains("Agent"))
                    .forEach(name -> {
                        try {
                            agents.add((CalculationService) registry.lookup(name));
                        } catch (RemoteException | NotBoundException e) {
                            e.printStackTrace();
                        }
                    });
            System.out.println("Wykryto " + agents.size() + " Agentów");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        agentsInUse = agents.size();
    }

    @Override
    public int[][] multiplyMatrixes(int[][] firstMatrix, int[][] secondMatrix) {

        // TODO: Hard-coded. To fix.
        OperationDto[] multiplications = new OperationDto[]{
                new OperationDto(firstMatrix[0][0], secondMatrix[0][0], Operator.MULTIPLY),
                new OperationDto(firstMatrix[0][1], secondMatrix[1][0], Operator.MULTIPLY),
                new OperationDto(firstMatrix[0][0], secondMatrix[0][1], Operator.MULTIPLY),
                new OperationDto(firstMatrix[0][1], secondMatrix[1][1], Operator.MULTIPLY),
                new OperationDto(firstMatrix[1][0], secondMatrix[0][0], Operator.MULTIPLY),
                new OperationDto(firstMatrix[1][1], secondMatrix[1][0], Operator.MULTIPLY),
                new OperationDto(firstMatrix[1][0], secondMatrix[0][1], Operator.MULTIPLY),
                new OperationDto(firstMatrix[1][1], secondMatrix[1][1], Operator.MULTIPLY)
        };

        int[] multiplicationResults = executeOperations(multiplications);

        OperationDto[] additions = new OperationDto[]{
                new OperationDto(multiplicationResults[0], multiplicationResults[1], Operator.ADD),
                new OperationDto(multiplicationResults[2], multiplicationResults[3], Operator.ADD),
                new OperationDto(multiplicationResults[4], multiplicationResults[5], Operator.ADD),
                new OperationDto(multiplicationResults[6], multiplicationResults[7], Operator.ADD),
        };
        int[] additionsResults = executeOperations(additions);

        return new int[][]{
                {
                        additionsResults[0],
                        additionsResults[1]
                },
                {
                        additionsResults[2],
                        additionsResults[3]
                }
        };
    }

    private int[] executeOperations(OperationDto[] operations) {
        Executor[] executors = new Executor[operations.length];
        int[] results = new int[operations.length];
        for (int i = 0; i < operations.length; i++) {
            executors[i] = new Executor(agents.get(i % agentsInUse), operations[i]);
            final int index = i;
            executors[i].setOnCalculationFinishedListener(result -> results[index] = result);
            executors[i].start();
        }

        for (int i = 0; i < operations.length; i++) {
            try {
                executors[i].join();
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }
        return results;
    }
}
