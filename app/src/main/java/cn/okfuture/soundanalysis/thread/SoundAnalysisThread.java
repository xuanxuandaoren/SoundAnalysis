package cn.okfuture.soundanalysis.thread;

import android.os.Handler;

/**
 * 声音分析工具
 * Created by shine on 18-10-10.
 */

public class SoundAnalysisThread extends Thread {
    /**
     *
     */
    private Handler handler;

    public SoundAnalysisThread(Handler handler) {

        this.handler = handler;
    }





    /**
     * 停止解析
     */
    public void close() {

    }
}


