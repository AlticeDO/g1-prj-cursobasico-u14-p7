package com.example.erick.connect4;

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
    }

    @Override
    public void onClick(View v) {
        boolean refresh = false;

        switch (v.getId()) {
        case R.id.col1:
            Toast.makeText(this, "col1", Toast.LENGTH_SHORT).show();
            refresh = true;
            setPieceInColumn(1, 1);
            break;
        case R.id.col2:
            Toast.makeText(this, "col2", Toast.LENGTH_SHORT).show();
            refresh = true;
            setPieceInColumn(2, 1);
            break;
        case R.id.col3:
            Toast.makeText(this, "col3", Toast.LENGTH_SHORT).show();
            refresh = true;
            setPieceInColumn(3, 1);
            break;
        case R.id.col4:
            Toast.makeText(this, "col4", Toast.LENGTH_SHORT).show();
            refresh = true;
            setPieceInColumn(4, 1);
            break;
        case R.id.col5:
            Toast.makeText(this, "col5", Toast.LENGTH_SHORT).show();
            refresh = true;
            setPieceInColumn(5, 1);
            break;
        case R.id.col6:
            refresh = true;
            setPieceInColumn(6, 1);
            Toast.makeText(this, "col6", Toast.LENGTH_SHORT).show();
            break;
        case R.id.col7:
            refresh = true;
            setPieceInColumn(7, 1);
            Toast.makeText(this, "col7", Toast.LENGTH_SHORT).show();
//            ((ImageView) ((TableRow) findViewById(R.id.row6)).getChildAt(6)).setImageResource(R.drawable.red);
            break;
        }
    }

    public int checkWinner() {
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
            player = getResources().getIdentifier(getPackageName() + ":drawable/red", null, null);
        } else {
            player = getResources().getIdentifier(getPackageName() + ":drawable/yellow", null, null);
        }

        ((ImageView) ((TableRow) ((TableLayout) findViewById(R.id.dashboard)).getChildAt(y)).getChildAt(x)).setImageResource(player);
    }
}
