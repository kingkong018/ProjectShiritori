package com.dreamershk.projectshiritori;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.dreamershk.projectshiritori.model.Player;

/**
 * Created by Windows7 on 26/6/2016.
 */
public class SinglePlayerGameWindow extends GameWindow {
    String log_name = "SINGLEGAMEWINDOW";
    private SinglePlayerGameManager gameManager;
    public static boolean isSinglePlayerGameWindowActivityRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isSinglePlayerGameWindowActivityRunning = true;
        super.onCreate(savedInstanceState);
        final GameView g = this;
        final Context c = getApplicationContext();
        SinglePlayerGameManager.releaseGameManagerInstance();
        gameManager = SinglePlayerGameManager.getGameManagerInstance();
        Player player = new Player(g, "玩家");
        player.setIconResId(R.drawable.character_janice);
        player.setPlayerType(Player.TYPE_HUMAN);
        gameManager.addGameView(g, player);
        //context should be set after host player is in the player queue.
        gameManager.setContext(c);
        //set AI players
        final Intent intent = getIntent();
        int numberOfAi = intent.getExtras().getInt("numberOfAi");
        for (int i = 0; i < numberOfAi; i++){
            Player computer = new Player(null, "電腦" + (i+1));
            switch (i){
                case 0:
                    computer.setIconResId(R.drawable.character_angus);
                    break;
                case 1:
                    computer.setIconResId(R.drawable.character_kelvin);
                    break;
                case 2:
                    computer.setIconResId(R.drawable.character_christy);
                    break;
                default:
                    computer.setIconResId(R.drawable.character_janice);
            }
            computer.setPlayerType(Player.TYPE_COMPUTER);
            gameManager.addGameView(null, computer); //add computer player
        }
        /*new AsyncTask<Void, Void, String>(){
            @Override
            protected void onPreExecute() {
                //Add loading screen?
                loadingDialog = new ProgressDialog(SinglePlayerGameWindow.this);
                loadingDialog.setMessage("Loading...");
                loadingDialog.setCancelable(false);
                loadingDialog.setInverseBackgroundForced(false);
                loadingDialog.show();
            }

            @Override
            protected String doInBackground(Void... params) {
                gameManager.addGameView(g, "Player1");
                //context should be set after host player is in the player queue.
                gameManager.setContext(c);
                gameManager.addGameView(null, "電腦"); //add computer player
                return null;
            }
        }.execute();*/
        //after adding the computer player, the game starts.
    }

    @Override
    protected void onDestroy() {
        Log.d(log_name, "Activity destroyed.");
        isSinglePlayerGameWindowActivityRunning = false;
        super.onDestroy();
    }
}
