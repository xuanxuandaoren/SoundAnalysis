package cn.okfuture.soundanalysis.thread;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import cn.okfuture.soundanalysis.activity.MainActivity;
import cn.okfuture.soundanalysis.domain.Sound;
import cn.okfuture.soundanalysis.utils.ColorUtils;
import cn.okfuture.soundanalysis.utils.FFT;
import cn.okfuture.soundanalysis.utils.VoiceUtil;

/**
 * 声音分析工具
 * Created by shine on 18-10-10.
 */

public class SoundAnalysisThread extends Thread {
    /**
     * 线程通讯的handler
     */
    private Handler handler;
    /**
     * 傅里叶变化工具类
     */
    private FFT fft = new FFT();

    /**
     * 可能存在的采样频率
     */
    private static final int[] SAMPLE_RATES_LIST = {11025, 8000, 22050, 44100, 16000};
    /**
     * 采样频率对应的采样点数
     */
    private static final int[] SAMPLE_COUNT = {8 * 1024,
            4 * 1024, 16 * 1024, 32 * 1024, 8 * 1024};
    /**
     * 采样频率
     */
    private int sampleRate = 44100;
    /**
     * 采样点数
     */
    private int sampleCount = 32 * 1024;
    /**
     * 声音的信息
     */
    private Sound sound = new Sound();
    /**
     * 当前的频率
     */
    private double currentFrequency;
    /**
     * 当前的声音
     */
    private double currentVolume;
    /**
     * 声音采集频率
     */
    private AudioRecord audioRecord;

    public SoundAnalysisThread(Handler handler) {

        this.handler = handler;
        initAudioRecord();
//        ColorUtils.initColors();
    }

    /**
     * 初始化
     */
    private void initAudioRecord() {
        Log.i("xiaozhu----------", "initAudioRecord");
        for (int i = 0; i < SAMPLE_RATES_LIST.length; i++) {

            audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATES_LIST[i],
                    AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT, AudioRecord.getMinBufferSize(SAMPLE_RATES_LIST[i],
                    AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT));

            Log.i("xiaozhu----------", "STATE_INITIALIZED" + audioRecord.getState());
            if (audioRecord.getState() == AudioRecord.STATE_INITIALIZED) {
                Log.i("xiaozhu----------", "STATE_INITIALIZED");
                sampleRate = SAMPLE_RATES_LIST[i];
                sampleCount = 1024;//SAMPLE_COUNT[i];
                break;
            }
        }

    }

    @Override
    public void run() {
        super.run();
        Log.i("xiaozhu----------", "run" + sampleCount);
        audioRecord.startRecording();
        byte[] bufferRead = new byte[sampleCount];
        int lenght;

        while ((lenght = audioRecord.read(bufferRead, 0, sampleCount)) > 0) {

            currentFrequency = fft.getFrequency(bufferRead, sampleRate, sampleCount);
            currentVolume = VoiceUtil.getVolume(bufferRead, lenght);


            sound.mFrequency = currentFrequency;
            sound.mVolume = currentVolume;
            Message message = Message.obtain();
            message.obj = sound;
            message.what = MainActivity.SOUND_MESSAGE;

            handler.sendMessage(message);

            Log.i("xiaozhu----------", "currentFrequency" + currentFrequency + "---" + currentVolume);
            if (currentFrequency > 0) {


                try {
                    if (audioRecord.getState() == AudioRecord.STATE_INITIALIZED)
                        audioRecord.stop();
                    Thread.sleep(20);
                    audioRecord.startRecording();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 停止解析
     */
    public void close() {
        if (audioRecord != null && audioRecord.getState() == AudioRecord.STATE_INITIALIZED) {
            audioRecord.stop();
            audioRecord.release();
        }

    }
}


