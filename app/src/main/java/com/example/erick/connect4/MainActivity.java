package com.example.erick.connect4;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private int dashboard[][] = new int[7][6];
    private int player1;
    private int player2;

    private byte playerTurn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.col1).setOnClickListener(this);
        findViewById(R.id.col2).setOnClickListener(this);
        findViewById(R.id.col3).setOnClickListener(this);
        findViewById(R.id.col4).setOnClickListener(this);
        findViewById(R.id.col5).setOnClickListener(this);
        findViewById(R.id.col6).setOnClickListener(this);
        findViewById(R.id.col7).setOnClickListener(this);

        player1 = getResources()
                .getIdentifier("red", "drawable", getPackageName());
        player2 = getResources()
                .getIdentifier("yellow", "drawable", getPackageName());
        playerTurn = 1;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
        case R.id.col1:
            setPieceInColumn(1, playerTurn);
            break;
        case R.id.col2:
            setPieceInColumn(2, playerTurn);
            break;
        case R.id.col3:
            setPieceInColumn(3, playerTurn);
            break;
        case R.id.col4:
            setPieceInColumn(4, playerTurn);
            break;
        case R.id.col5:
            setPieceInColumn(5, playerTurn);
            break;
        case R.id.col6:
            setPieceInColumn(6, playerTurn);
            break;
        case R.id.col7:
            setPieceInColumn(7, playerTurn);
            break;
        }

        togglePlayer();
    }

    public int checkWinner() {
//        int winner;
//        int count = 0;
//
//        for(int x = dashboard.length - 1; x > 0; x--) {
//
//        }
        return -1;


    }

    public boolean setPieceInColumn(int col, int player) {
        boolean changed = false;
        col--;
        try {
            for (int x = 5; x >= 0; x--) {

                if (dashboard[col][x] == 0) {
                    dashboard[col][x] = player;
                    updateDashboard(col, x, player);
                    changed = true;
                    break;
                }
            }
            return changed;
        } catch(ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    private void updateDashboard(int x, int y, int player) {

        if (player == 1) {
            player = player1;
        } else {
            player = player2;
        }

        ((ImageView) ((TableRow) ((TableLayout) findViewById(R.id.dashboard)).getChildAt(y)).getChildAt(x)).setImageResource(player);
    }

    private void togglePlayer() {
        if (playerTurn == 1) {
            playerTurn = 2;
        } else {
            playerTurn = 1;
        }
        Toast.makeText(this, "Turno de: " + playerTurn, Toast.LENGTH_SHORT).show();
    }
}
