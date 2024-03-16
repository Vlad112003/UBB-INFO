public class ExpressionEvaluator {
    private Operatie operatie;

    public ExpressionEvaluator(Operatie operatie) {
        this.operatie = operatie;
    }

    public NumarComplex evaluate(NumarComplex operand1, NumarComplex operand2) {
        return operatie.aplica(operand1, operand2);
    }
}
