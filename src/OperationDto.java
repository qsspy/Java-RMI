import java.io.Serializable;

public class OperationDto implements Serializable {

    private final int arg1;
    private final int arg2;
    private final Operator operator;

    public OperationDto(int arg1, int arg2, Operator operator) {
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.operator = operator;
    }

    public int getArg1() {
        return arg1;
    }

    public int getArg2() {
        return arg2;
    }

    public Operator getOperator() {
        return operator;
    }
}
