public class NumarComplex {
    private double re;
    private double im;

    public NumarComplex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public NumarComplex(double re){
        this.re = re;
        this.im = 0;
    }

    //getters
    public double getRe() {
        return re;
    }

    public double getIm() {
        return im;
    }
    //overrides the output of the object
    @Override
    public String toString() {
        return re + " + " + im + "i";
    }

    public NumarComplex add(NumarComplex other)
    {
        return new NumarComplex(this.re + other.re, this.im + other.im);
    }

    public NumarComplex subtract(NumarComplex other)
    {
        return new NumarComplex(this.re - other.re, this.im - other.im);
    }

    public NumarComplex multiply(NumarComplex other)
    {
        return new NumarComplex(this.re * other.re - this.im * other.im, this.re * other.im + this.im * other.re);
    }

    public NumarComplex divide(NumarComplex other)
    {
        double denominator = other.re * other.re + other.im * other.im;
        return new NumarComplex((this.re * other.re + this.im * other.im) / denominator, (this.im * other.re - this.re * other.im) / denominator);
    }
}
