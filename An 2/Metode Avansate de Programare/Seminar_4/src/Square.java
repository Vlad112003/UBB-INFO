public class Square {

    private double side;

    public Square(double side){
        this.side = side;
    }

    @Override
    public String toString() {
        return "Square{" +
                "side=" + side +
                '}';
    }

    public void setSide(double side) {
        this.side = side;
    }

    public double getSide() {
        return side;
    }
}
