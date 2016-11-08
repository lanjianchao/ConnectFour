package com.android.lanjianchao.connectfour;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {
    private int redWinTimes = 0;
    private int greenWinTimes = 0;
    private TableLayout table;
    private ChessBoard chessBoard;
    private ImageView image1;
    private int[] coloumScope = new int[7];
    private int count = 0;
    private ArrayList<Integer> retract = new ArrayList<Integer>();
    private boolean retractIsAvailable = true;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_game);
        chessBoard = new ChessBoard();
        initTable();
        initNewGame();
        retractGame();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    //retract button action
    private void retractGame() {
        final Button retractBut = (Button) findViewById(R.id.retract_game);
        retractBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView turn= (ImageView) findViewById(R.id.turn);
                if (retract.size() > 0 && retractIsAvailable) {
                    int coloum = retract.get(retract.size() - 1);
                    retract.remove(retract.size() - 1);
                    int row = retract.get(retract.size() - 1);
                    retract.remove(retract.size() - 1);
                    removePiece(row, coloum);
                    chessBoard.changeEmptyState(row, coloum);
                    if(chessBoard.isGreenMove()){
                        turn.setImageResource(R.drawable.green_t);
                    }else{
                        turn.setImageResource(R.drawable.red_t);
                    }
                }

            }
        });


    }

    public void removePiece(int i, int j) {
        TableRow row = null;
        row = (TableRow) table.getChildAt(i);
        ImageView image = (ImageView) row.getChildAt(j);
        image.setImageResource(R.drawable.empty_t);
    }

    //new game button action
    private void initNewGame() {


        Button newGame = (Button) findViewById(R.id.new_game);
        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView turn= (ImageView) findViewById(R.id.turn);
                turn.setImageResource(R.drawable.red_t);
                retract.clear();
                retractIsAvailable = true;
                chessBoard.initBoard();
                TableRow row = null;
                table = (TableLayout) findViewById(R.id.table_view);
                int tableRowCount = table.getChildCount();
                for (int i = 0; i < tableRowCount; i++) {
                    row = (TableRow) table.getChildAt(i);
                    for (int j = 0; j < row.getChildCount(); j++) {
                        ImageView image = (ImageView) row.getChildAt(j);
                        image.setImageResource(R.drawable.empty_t);
                    }
                }
            }
        });

    }

    //init the activity and add clickListener to  each imageview
    private void initArray() {
        count = 1;
        ImageView image01 = (ImageView) findViewById(R.id.image01);
        coloumScope[0] = image01.getLeft();
        ImageView image02 = (ImageView) findViewById(R.id.image02);
        coloumScope[1] = image02.getLeft();
        ImageView image03 = (ImageView) findViewById(R.id.image03);
        coloumScope[2] = image03.getLeft();
        ImageView image04 = (ImageView) findViewById(R.id.image04);
        coloumScope[3] = image04.getLeft();
        ImageView image05 = (ImageView) findViewById(R.id.image05);
        coloumScope[4] = image05.getLeft();
        ImageView image06 = (ImageView) findViewById(R.id.image06);
        coloumScope[5] = image06.getLeft();
        ImageView image07 = (ImageView) findViewById(R.id.image07);
        coloumScope[6] = image07.getLeft();

    }

    //set imageView listener
    protected void initTable() {
        chessBoard.initBoard();
        TableRow row = null;
        table = (TableLayout) findViewById(R.id.table_view);
        int tableRowCount = table.getChildCount();
        for (int i = 0; i < tableRowCount; i++) {
            row = (TableRow) table.getChildAt(i);
            for (int j = 0; j < row.getChildCount(); j++) {
                ImageView image = (ImageView) row.getChildAt(j);
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(isEqual()){
                            Toast.makeText(GameActivity.this, "game draw!", Toast.LENGTH_LONG).show();
                        }else{
                            if (count == 0) {
                                initArray();
                            }
                            ImageView imageNew = (ImageView) v;
                            int coloum = 0;
                            for (int i = 0; i < 7; i++) {
                                if (imageNew.getLeft() == coloumScope[i]) {
                                    coloum = i;
                                    break;
                                }

                            }

                            if (chessBoard.isAvailable(coloum)) {
                                ImageView turn = (ImageView) findViewById(R.id.turn);
                                int i = chessBoard.addPosition(coloum);
                                if (chessBoard.isRedMove()) {
                                    if (!chessBoard.isStop()) {
                                        addRedPiece(i, coloum);
                                        turn.setImageResource(R.drawable.green_t);
                                        chessBoard.changeRedState(i, coloum);
                                        retract.add(i);
                                        retract.add(coloum);
                                        if (chessBoard.isRedWinGame(i, coloum)) {
                                            retractIsAvailable = false;
                                            redWinTimes++;
                                            TextView redText = (TextView) findViewById(R.id.redScore);
                                            redText.setText(String.valueOf(redWinTimes));
                                            for (int k = 0; k < 6; k++) {
                                                for (int m = 0; m < 7; m++) {
                                                    if (chessBoard.getChess()[k][m] == 3) {
                                                        addRedWinPiece(k, m);
                                                    }
                                                }
                                            }
//                                            for(int s=0;s<6;s++){
//                                                for(int j=0;j<7;j++){
//                                                    System.out.print(chessBoard.getChess()[s][j]+"  ");
//                                                }
//                                                System.out.println();
//                                            }
                                            Toast.makeText(GameActivity.this, "RED WIN!", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                } else {
                                    if (!chessBoard.isStop()) {
                                        addGreenPiece(i, coloum);
                                        turn.setImageResource(R.drawable.red_t);
                                        chessBoard.changeGreenState(i, coloum);
                                        retract.add(i);
                                        retract.add(coloum);
                                        if (chessBoard.isGreenWinGame(i, coloum)) {
                                            retractIsAvailable = false;
                                            greenWinTimes++;
                                            TextView redText = (TextView) findViewById(R.id.greenScore);
                                            redText.setText(String.valueOf(greenWinTimes));
                                            for (int k = 0; k < chessBoard.getChess().length; k++) {
                                                for (int m = 0; m < chessBoard.getChess()[k].length; m++) {
                                                    if (chessBoard.getChess()[k][m] == 4) {
                                                        addGreenWinPiece(k, m);
                                                    }
                                                }
                                            }
//                                            for(int s=0;s<6;s++){
//                                                for(int j=0;j<7;j++){
//                                                    System.out.print(chessBoard.getChess()[s][j]+"  ");
//                                                }
//                                                System.out.println();
//                                            }
                                            Toast.makeText(GameActivity.this, "Green WIN!", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                }

                            } else {
                                Toast.makeText(GameActivity.this, "CAN NOT PUT!", Toast.LENGTH_SHORT).show();
                            }


                        }
                        }

                });
            }
        }


    }

    public void addRedPiece(int i, int j) {
        TableRow row = null;
        row = (TableRow) table.getChildAt(i);
        ImageView image = (ImageView) row.getChildAt(j);
        image.setImageResource(R.drawable.red_t);


    }

    public void addRedWinPiece(int i, int j) {

        TableRow row = null;
        row = (TableRow) table.getChildAt(i);
        ImageView image = (ImageView) row.getChildAt(j);
        image.setImageResource(R.drawable.red_wint);


    }

    public void addGreenPiece(int i, int j) {
        TableRow row = null;
        row = (TableRow) table.getChildAt(i);
        ImageView image = (ImageView) row.getChildAt(j);
        image.setImageResource(R.drawable.green_t);


    }

    public void addGreenWinPiece(int i, int j) {
        TableRow row = null;
        row = (TableRow) table.getChildAt(i);
        ImageView image = (ImageView) row.getChildAt(j);
        image.setImageResource(R.drawable.green_wint);


    }
    public boolean isEqual(){
        boolean flag=false;
        for(int i=0;i<6;i++){
            for(int j=0;j<7;j++){
                if(chessBoard.getChess()[i][j]==0){
                    return flag;
                }
            }
        }
        flag=true;
        return flag;
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Game Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
