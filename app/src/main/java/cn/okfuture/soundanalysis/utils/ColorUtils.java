package cn.okfuture.soundanalysis.utils;

import android.graphics.Color;
import android.util.Log;

/**
 * 颜色工具类
 * Created by shine on 18-10-16.
 */

public class ColorUtils {

    /**
     * 赤橙黄绿青蓝紫 七原色
     */
    public static final int[] COLOR_LIST = {0xff0000, 0xFF7D00, 0xFFFF00, 0x00FF00, 0x00FFFF, 0x0000FF, 0xFF00FF, 0xFF0000};
    /**
     * 渐变色70种
     */
    public static final int[] COLOR_LIST_70 = {0xffff0000, 0xffff0c00, 0xffff1900, 0xffff2500, 0xffff3200, 0xffff3e00, 0xffff4b00, 0xffff5700, 0xffff6400, 0xffff7000,
            0xffff7d00, 0xffff8a00, 0xffff9700, 0xffffa400, 0xffffb100, 0xffffbe00, 0xffffcb00, 0xffffd800, 0xffffe500, 0xfffff200,
            0xffffff00, 0xffe5ff00, 0xffccff00, 0xffb2ff00, 0xff99ff00, 0xff7fff00, 0xff66ff00, 0xff4cff00, 0xff33ff00, 0xff19ff00,
            0xff00ff00, 0xff00ff19, 0xff00ff33, 0xff00ff4c, 0xff00ff66, 0xff00ff7f, 0xff00ff99, 0xff00ffb2, 0xff00ffcc, 0xff00ffe5,
            0xff00ffff, 0xff00e5ff, 0xff00ccff, 0xff00b2ff, 0xff0099ff, 0xff007fff, 0xff0066ff, 0xff004cff, 0xff0033ff, 0xff0019ff,
            0xff0000ff, 0xff1900ff, 0xff3300ff, 0xff4c00ff, 0xff6600ff, 0xff7f00ff, 0xff9900ff, 0xffb200ff, 0xffcc00ff, 0xffe500ff,
            0xffff00ff, 0xffff00e5, 0xffff00cc, 0xffff00b2, 0xffff0099, 0xffff007f, 0xffff0066, 0xffff004c, 0xffff0033, 0xffff0019};
    /**
     * 渐变色140种
     */
    public static final int[] COLOR_LIST_140 = {0xffff0000, 0xffff0600, 0xffff0c00, 0xffff1200, 0xffff1900, 0xffff1f00, 0xffff2500, 0xffff2b00, 0xffff3200, 0xffff3800, 0xffff3e00, 0xffff4400, 0xffff4b00, 0xffff5100, 0xffff5700, 0xffff5d00, 0xffff6400, 0xffff6a00, 0xffff7000, 0xffff7600,
            0xffff7d00, 0xffff8300, 0xffff8a00, 0xffff9000, 0xffff9700, 0xffff9d00, 0xffffa400, 0xffffaa00, 0xffffb100, 0xffffb700, 0xffffbe00, 0xffffc400, 0xffffcb00, 0xffffd100, 0xffffd800, 0xffffde00, 0xffffe500, 0xffffeb00, 0xfffff200, 0xfffff800,
            0xffffff00, 0xfff2ff00, 0xffe5ff00, 0xffd8ff00, 0xffccff00, 0xffbfff00, 0xffb2ff00, 0xffa5ff00, 0xff99ff00, 0xff8cff00, 0xff7fff00, 0xff72ff00, 0xff66ff00, 0xff59ff00, 0xff4cff00, 0xff3fff00, 0xff33ff00, 0xff26ff00, 0xff19ff00, 0xff0cff00,
            0xff00ff00, 0xff00ff0c, 0xff00ff19, 0xff00ff26, 0xff00ff33, 0xff00ff3f, 0xff00ff4c, 0xff00ff59, 0xff00ff66, 0xff00ff72, 0xff00ff7f, 0xff00ff8c, 0xff00ff99, 0xff00ffa5, 0xff00ffb2, 0xff00ffbf, 0xff00ffcc, 0xff00ffd8, 0xff00ffe5, 0xff00fff2,
            0xff00ffff, 0xff00f2ff, 0xff00e5ff, 0xff00d8ff, 0xff00ccff, 0xff00bfff, 0xff00b2ff, 0xff00a5ff, 0xff0099ff, 0xff008cff, 0xff007fff, 0xff0072ff, 0xff0066ff, 0xff0059ff, 0xff004cff, 0xff003fff, 0xff0033ff, 0xff0026ff, 0xff0019ff, 0xff000cff,
            0xff0000ff, 0xff0c00ff, 0xff1900ff, 0xff2600ff, 0xff3300ff, 0xff3f00ff, 0xff4c00ff, 0xff5900ff, 0xff6600ff, 0xff7200ff, 0xff7f00ff, 0xff8c00ff, 0xff9900ff, 0xffa500ff, 0xffb200ff, 0xffbf00ff, 0xffcc00ff, 0xffd800ff, 0xffe500ff, 0xfff200ff,
            0xffff00ff, 0xffff00f2, 0xffff00e5, 0xffff00d8, 0xffff00cc, 0xffff00bf, 0xffff00b2, 0xffff00a5, 0xffff0099, 0xffff008c, 0xffff007f, 0xffff0072, 0xffff0066, 0xffff0059, 0xffff004c, 0xffff003f, 0xffff0033, 0xffff0026, 0xffff0019, 0xffff000c};

    /**
     * 获取渐变过程中特定点的颜色
     *
     * @param startColor 开始的颜色
     * @param endColor   结束的颜色
     * @param radio      特定比率
     * @return 返回特定点的颜色
     */
    public static int getColor(int startColor, int endColor, float radio) {
        int redStart = Color.red(startColor);
        int blueStart = Color.blue(startColor);
        int greenStart = Color.green(startColor);
        int redEnd = Color.red(endColor);
        int blueEnd = Color.blue(endColor);
        int greenEnd = Color.green(endColor);

        int red = (int) (redStart + ((redEnd - redStart) * radio));
        int greed = (int) (greenStart + ((greenEnd - greenStart) * radio));
        int blue = (int) (blueStart + ((blueEnd - blueStart) * radio));
        return Color.rgb(red, greed, blue);
    }

    /**
     * 初始化颜色
     */
    public static void initColors() {
        for (int i = 0; i < COLOR_LIST.length - 1; i++) {
            String clolrStr = "[";
            for (int j = 0; j < 20; j++) {
                clolrStr += Integer.toHexString(getColor(COLOR_LIST[i], COLOR_LIST[i + 1], 0.05f * j)) + ",";
            }

            clolrStr += "]";

            Log.i("xiaozhu------------", clolrStr);

        }

    }
}
