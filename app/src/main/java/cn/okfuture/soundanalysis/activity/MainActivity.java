package cn.okfuture.soundanalysis.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.okfuture.soundanalysis.R;
import cn.okfuture.soundanalysis.domain.Sound;
import cn.okfuture.soundanalysis.thread.SoundAnalysisThread;
import cn.okfuture.soundanalysis.utils.ColorUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * 声音请求权限信息
     */
    private static final int PERMISSION_AUDIORECORD = 2;
    /**
     * 音乐跳变临界点
     */
    private static final int FREQUENCY_CRITICAL = 500;
    /**
     * 最大的声音
     */
    private static final double MAX_SOUND = 3000;

    /**
     * 声音信息
     */
    public static int SOUND_MESSAGE = 1;

    /**
     * 采集声音开关
     */
    private Button btn_start;
    /**
     * 展示结果
     */
    private TextView tv_show;
    /**
     * 声音分析
     */
    private SoundAnalysisThread soundAnalysisThread;
    /**
     * 当前的频率
     */

    private int currentFrequency;

    /**
     * 线程之间通讯
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Sound sound = (Sound) msg.obj;
                    updateBackground(sound);
                    break;
            }
        }
    };
    private LinearLayout ll_content;

    /**
     * 更新背景
     *
     * @param sound
     */
    private void updateBackground(Sound sound) {
        int frequency = (int) sound.mFrequency;

        if (frequency <= 0) {
            return;
        } else if (Math.abs(frequency - currentFrequency) < FREQUENCY_CRITICAL) {
            currentFrequency++;
        } else {
            currentFrequency = frequency;
        }

        int alpha = (int) (255 * sound.mVolume / MAX_SOUND);
        if (alpha > 255) {
            alpha = 255;
        }
        int color = ColorUtils.COLOR_LIST_140[currentFrequency % 140];
        ll_content.setBackgroundColor(Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color)));

        tv_show.setText(String.format(getString(R.string.show_sound), currentFrequency * 1.0f, sound.mVolume));

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btn_start = (Button) findViewById(R.id.btn_start);
        tv_show = (TextView) findViewById(R.id.tv_show);

        btn_start.setOnClickListener(this);
        ll_content = (LinearLayout) findViewById(R.id.ll_content);
        ll_content.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                if (btn_start.isSelected()) {
                    btn_start.setText(R.string.start);
                    btn_start.setSelected(false);
                    stopAnalysis();

                } else {
                    btn_start.setText(R.string.stop);
                    btn_start.setSelected(true);
                    //判断是否有权限
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                        //如果应用之前请求过此权限但用户拒绝的请求 ,此方法返回true
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)) {
                            //这里可以写个对话框之类的项向用户解释为什么要申请权限，并在对话框的确认键后续再次申请权限
                        } else {
                            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSION_AUDIORECORD);
                        }

                    } else {
                        startAnalysis();
                    }

                }

                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_AUDIORECORD) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    startAnalysis();
                }

            }
        }
    }

    /**
     * 开始采集
     */
    private void startAnalysis() {

        soundAnalysisThread = new SoundAnalysisThread(handler);
        soundAnalysisThread.start();
    }

    /**
     * 停止采集音频
     */
    private void stopAnalysis() {
        if (soundAnalysisThread != null) {
            soundAnalysisThread.close();
        }

    }
}
