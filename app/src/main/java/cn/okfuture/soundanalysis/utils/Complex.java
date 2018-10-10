package cn.okfuture.soundanalysis.utils;

/**
 * 复数
 * Created by shine on 18-10-9.
 */

public class Complex {
    /**
     * 实部
     */
    private double real;
    /**
     * 虚部
     */
    private double image;


    public Complex() {
        this(0.0, 0.0);
    }


    public Complex(double real, double image) {
        this.real = real;
        this.image = image;
    }

    public double getReal() {
        return real;
    }

    public double getImage() {
        return image;
    }


    @Override
    public String toString() {
        return "Complex{" +
                "real=" + real +
                ", image=" + image +
                '}';
    }

    /**
     * 加法
     *
     * @param other
     * @return
     */
    public Complex plus(Complex other) {
        return new Complex(getReal() + other.getReal(), getImage() + other.getImage());
    }

    /**
     * 乘法
     *
     * @param other
     * @return
     */
    public Complex multiple(Complex other) {
        return new Complex(getReal() * other.getReal() - getImage() * other.getImage(), getReal() * other.getImage() + getImage() * other.getReal());
    }

    /**
     * 减法
     *
     * @param other
     * @return
     */
    public Complex minus(Complex other) {

        return new Complex(getReal() - other.getReal(), getImage() - other.getImage());
    }

    /**
     * 求模值
     *
     * @return
     */
    public final double getMod() {
        return Math.sqrt(this.getReal() * this.getReal() + this.getImage() * this.getImage());
    }


}
