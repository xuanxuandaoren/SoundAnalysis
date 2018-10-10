package cn.okfuture.soundanalysis.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cn.okfuture.soundanalysis.R;
import cn.okfuture.soundanalysis.domain.Sound;
import cn.okfuture.soundanalysis.thread.SoundAnalysisThread;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
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

    /**
     * 更新背景
     *
     * @param sound
     */
    private void updateBackground(Sound sound) {
        tv_show.setText(String.format(getString(R.string.show_sound), sound.mFrequency, sound.mVolume));

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
                    startAnalysis();
                }

                break;
        }
    }

    /**
     * 开始采集
     */
    private void startAnalysis() {

        soundAnalysisThread=new SoundAnalysisThread(handler);
        soundAnalysisThread.start();
    }

    /**
     * 停止采集音频
     */
    private void stopAnalysis() {
        if (soundAnalysisThread!=null){
            soundAnalysisThread.close();
        }

    }
}
