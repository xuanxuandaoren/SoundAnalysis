package cn.okfuture.soundanalysis.utils;

/**
 * 声音计算工具类
 * Created by shine on 18-10-15.
 */

public class VoiceUtil {
    /**
     * 获取声音的分贝
     *
     * @param bufferRead
     * @param lenght
     * @return
     */
    public static double getVolume(byte[] bufferRead, int lenght) {
        int volume = 0;

        for (int i = 0; i < bufferRead.length; i++) {
            volume += bufferRead[i] * bufferRead[i];
        }

        double mean = volume / (float) lenght;
        return mean;//10 * Math.log10(mean);
    }
}
