package com.example.erick.connect4;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
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
    private MediaPlayer mp;

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

        mp = MediaPlayer.create(this, R.raw.bg);
        mp.setVolume(100, 100);
        mp.start();

        player1 = getResources()
                .getIdentifier("red", "drawable", getPackageName());
        player2 = getResources()
                .getIdentifier("yellow", "drawable", getPackageName());
        playerTurn = 1;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mp.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mp.start();
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

        int winner = checkWinner();

        if (winner > 0) {
            Toast.makeText(this, "Ganador: Player " + winner, Toast.LENGTH_SHORT).show();
        } else {
            togglePlayer();
        }
    }

    public int checkWinner() {
        int winner = -1;
        int last = -1;
        int count = 0;
        int lastVertical = -1;
        int countVertical = 0;
        boolean firstTime = true;
        boolean firstTimeVertical = true;

        for(int z = dashboard.length - 1; z >= 0; z--) {
            // Horizontal check
            for (int y = dashboard[z].length - 1; y >= 0; y--) {
                for (int x = dashboard.length - 1; x >= 0; x--) {
                    if (firstTime && dashboard[x][y] != 0) {
                        last = dashboard[x][y];
                        firstTime = false;
                    }

                    if (dashboard[x][y] == last) {
                        count++;
                    } else {
                        if (dashboard[x][y] != 0) {
                            count = 1;
                            last = dashboard[x][y];
                        } else {
                            count = 0;
                            last = -1;
                        }
                    }

                    if (count == 4) {
                        winner = dashboard[x][y];
                        return winner;
                    }

                    if (dashboard[x][y] != 0) {
                        winner = getDiagonalWinner(x, y, dashboard[x][y]);
                        if (winner > 0) {
                            return winner;
                        }
                    }
                }

                // Vertical check
                if (firstTimeVertical && dashboard[z][y] != 0) {
                    lastVertical = dashboard[z][y];
                    firstTimeVertical = false;
                }

                if (dashboard[z][y] == lastVertical) {
                    countVertical++;
                } else {
                    if (dashboard[z][y] != 0) {
                        countVertical = 1;
                        lastVertical = dashboard[z][y];
                    } else {
                        countVertical = 0;
                        lastVertical = -1;
                    }
                }

                if (countVertical == 4) {
                    winner = dashboard[z][y];
                    return winner;
                }
            }
        }
        return winner > 0 ? winner : -1;
    }

    private int getDiagonalWinner(int startX, int startY, int player) {

        int y = startY;
        int count = 1;

        // If x or y is less than 4 isn't possible
        if (startY < 3 )  {
            return -1;
        }

        // Check left diagonal
        if (startX > 0) {
            for (int x = startX - 1; x >= 0; x--) {
                y--;
                if (y < 0) {
                    break;
                }
                if (dashboard[x][y] == player) {
                    count++;
                } else {
                    break;
                }

                if (count == 4) {
                    return player;
                }
            }
        }

        count = 1;
        y = startY;

        // Check right diagonal
        if (startX < dashboard.length - 1) {
            for (int x = startX + 1; x < dashboard.length; x++) {
                y--;

                if (y < 0) {
                    break;
                }

                if (dashboard[x][y] == player) {
                    count++;
                } else {
                    break;
                }

                if (count == 4) {
                    return player;
                }
            }
        }

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
        //Toast.makeText(this, "Turno de: " + playerTurn, Toast.LENGTH_SHORT).show();
    }
}
