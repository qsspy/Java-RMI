public class Client {

    public static void main(String[] args) {
        final MatrixHelper helper = new MatrixHelper(2,2);
        helper.promptForMatrixInput();

        //for debug
        helper.showMatrixes();
    }
}
