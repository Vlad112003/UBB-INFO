public interface Operatie {
    NumarComplex aplica(NumarComplex operand1, NumarComplex operand2);

    public class Adunare implements Operatie {
        @Override
        public NumarComplex aplica(NumarComplex operand1, NumarComplex operand2) {
            return operand1.add(operand2);
        }
    }

    public class Scadere implements Operatie {
        @Override
        public NumarComplex aplica(NumarComplex operand1, NumarComplex operand2) {
            return operand1.subtract(operand2);
        }
    }

    public class Inmultire implements Operatie {
        @Override
        public NumarComplex aplica(NumarComplex operand1, NumarComplex operand2) {
            return operand1.multiply(operand2);
        }
    }

    public class Impartire implements Operatie {
        @Override
        public NumarComplex aplica(NumarComplex operand1, NumarComplex operand2) {
            return operand1.divide(operand2);
        }
    }

}


