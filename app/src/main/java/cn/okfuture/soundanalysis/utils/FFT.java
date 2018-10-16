package cn.okfuture.soundanalysis.utils;

import android.util.Log;

/**
 * 快速傅里叶变换
 * Created by shine on 18-10-9.
 */

public class FFT {

    public Complex[] fft(Complex[] x) {
        int n = x.length;

        // 因为exp(-2i*n*PI)=1，n=1时递归原点
        if (n == 1) {
            return x;
        }

        // 如果信号数为奇数，使用dft计算
        if (n % 2 != 0) {
            return dft(x);
        }

        // 提取下标为偶数的原始信号值进行递归fft计算
        Complex[] even = new Complex[n / 2];
        for (int k = 0; k < n / 2; k++) {
            even[k] = x[2 * k];
        }
        Complex[] evenValue = fft(even);

        // 提取下标为奇数的原始信号值进行fft计算
        // 节约内存
        Complex[] odd = even;
        for (int k = 0; k < n / 2; k++) {
            odd[k] = x[2 * k + 1];
        }
        Complex[] oddValue = fft(odd);

        // 偶数+奇数
        Complex[] result = new Complex[n];
        for (int k = 0; k < n / 2; k++) {
            // 使用欧拉公式e^(-i*2pi*k/N) = cos(-2pi*k/N) + i*sin(-2pi*k/N)
            double p = -2 * k * Math.PI / n;
            Complex m = new Complex(Math.cos(p), Math.sin(p));
            result[k] = evenValue[k].plus(m.multiple(oddValue[k]));
            // exp(-2*(k+n/2)*PI/n) 相当于 -exp(-2*k*PI/n)，其中exp(-n*PI)=-1(欧拉公式);
            result[k + n / 2] = evenValue[k].minus(m.multiple(oddValue[k]));
        }
        return result;
    }

    public Complex[] dft(Complex[] x) {
        int n = x.length;

        // 1个信号exp(-2i*n*PI)=1
        if (n == 1)
            return x;

        Complex[] result = new Complex[n];
        for (int i = 0; i < n; i++) {
            result[i] = new Complex(0, 0);
            for (int k = 0; k < n; k++) {
                //使用欧拉公式e^(-i*2pi*k/N) = cos(-2pi*k/N) + i*sin(-2pi*k/N)
                double p = -2 * k * Math.PI / n;
                Complex m = new Complex(Math.cos(p), Math.sin(p));
                result[i].plus(x[k].multiple(m));
            }
        }
        return result;
    }

    /**
     * 获取最大的频率
     *
     * @param data
     * @param SAMPLE_RATE
     * @param FFT_N
     * @return
     */
    public double getFrequency(byte[] data, int SAMPLE_RATE, int FFT_N) {

        if (data.length < FFT_N) {
            throw new RuntimeException("Data length lower than " + FFT_N);
        }
        Complex[] f = new Complex[FFT_N];
        for (int i = 0; i < FFT_N; i++) {
            f[i] = new Complex(data[i], 0); //实部为正弦波FFT_N点采样，赋值为1
            //虚部为0
        }
        String dataStr = "[";
        for (int i = 0; i < f.length; i++) {
            dataStr += f[i].getReal() + "-" + f[i].getImage() + ",   ";
        }
        dataStr += "]";

//        Log.i("xiaozhu----------before", "data==" + dataStr + ", SAMPLE_RATE==" + SAMPLE_RATE + ", FFT_N" + FFT_N);

        f = fft(f);                                        //进行快速福利叶变换

        dataStr = "[";
        for (int i = 0; i < f.length; i++) {
            dataStr += f[i].getReal() + "-" + f[i].getImage() + ",   ";
        }
        dataStr += "]";

//        Log.i("xiaozhu----------afters", "data==" + dataStr + ", SAMPLE_RATE==" + SAMPLE_RATE + ", FFT_N" + FFT_N);


//        String str = "";
//        for(int i = 0;i<FFT_N;i++){
//            str+=f[i].toString()+" ";
//        }
//        Log.i("FFT","fft: "+str);
        double[] s = new double[FFT_N / 2];
//        str = "";
        for (int i = 0; i < FFT_N / 2; i++) {
            s[i] = f[i].getMod();
//            str += ""+s[i]+" ";
        }
//        Log.i("FFT","s: "+str);

        int fmax = 0;
        for (int i = 1; i < FFT_N / 2; i++) {  //利用FFT的对称性，只取前一半进行处理
            if (s[i] > s[fmax])
                fmax = i;                          //计算最大频率的序号值
        }
//        Log.i("FFT","max index:"+fmax+" fft:"+f[fmax]+" s:"+s[fmax]);
        double fre = fmax * (double) SAMPLE_RATE / FFT_N;
        Log.i("FFT", "fre:" + fre);
        return fre;
    }

}
