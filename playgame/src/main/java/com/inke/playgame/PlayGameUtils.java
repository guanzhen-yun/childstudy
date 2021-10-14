package com.inke.playgame;

import android.content.Context;
import android.widget.Toast;

/**
 * https://blog.csdn.net/ocean20/article/details/108093223
 */
public class PlayGameUtils {
    //nexus-3.34.0-01/bin download目录下 ./nexus start  http://localhost:8081
    public static void playGame(Context context, String playGame){
        Toast.makeText(context, "玩" + playGame, Toast.LENGTH_SHORT).show();
    }
}
