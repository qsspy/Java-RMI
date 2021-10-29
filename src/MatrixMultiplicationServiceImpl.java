import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MatrixMultiplicationServiceImpl implements MatrixMultiplicationService {

    CalculationService[] agents;
    private int agentsInUse;

    public MatrixMultiplicationServiceImpl(int agentsNumber) {
        this.agentsInUse = agentsNumber;
        try {
            Registry registry = LocateRegistry.getRegistry(Configuration.RMI_REGISTRY_PORT);
            agents = new CalculationService[Configuration.MAX_AGENT_ID];
            for (int i = 0; i < Configuration.MAX_AGENT_ID; i++) {
                int agentIndex = i + 1;
                String[] args = new String[]{
                        String.valueOf(agentIndex)
                };
                Agent.main(args);
                agents[i] = (CalculationService) registry.lookup("Agent" + agentIndex);
            }
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
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
            executors[i] = new Executor(agents[i % agentsInUse], operations[i]);
            executors[i].start();

            try {
                executors[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            results[i] = executors[i].getResult();
        }

        return results;
    }
}
