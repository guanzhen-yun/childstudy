package com.inke.playgame;

import android.content.Context;
import android.widget.Toast;

public class PlayGameUtils {
    public static void playGame(Context context, String playGame){
        Toast.makeText(context, "玩" + playGame, Toast.LENGTH_SHORT).show();
    }
}
