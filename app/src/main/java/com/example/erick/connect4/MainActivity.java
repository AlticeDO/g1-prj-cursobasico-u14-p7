package com.example.erick.connect4;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private int dashboard[][] = new int[7][6];
    private int player0;
    private int player1;
    private int player2;
    private int mute;
    private int sound;
    private ImageView txtSound;
    private MediaPlayer mp;
    private boolean gameOver = false;
    private int winner = 0;
    private boolean isMute = false;

    private int playerTurn;

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
        findViewById(R.id.txtReset).setOnClickListener(this);
        txtSound = (ImageView) findViewById(R.id.txtSound);
        txtSound.setOnClickListener(this);

        mp = MediaPlayer.create(this, R.raw.bg);
        mp.setVolume(50, 50);
        mp.start();
        mp.setLooping(true);

        player0 = getResources()
                .getIdentifier("white", "drawable", getPackageName());
        player1 = getResources()
                .getIdentifier("red", "drawable", getPackageName());
        player2 = getResources()
                .getIdentifier("yellow", "drawable", getPackageName());
        sound = getResources()
                .getIdentifier("sound", "drawable", getPackageName());
        mute = getResources()
                .getIdentifier("mute", "drawable", getPackageName());
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
        boolean updated = false;

        switch (v.getId()) {
        case R.id.col1:
            updated = setPieceInColumn(1, playerTurn);
            break;
        case R.id.col2:
            updated = setPieceInColumn(2, playerTurn);
            break;
        case R.id.col3:
            updated = setPieceInColumn(3, playerTurn);
            break;
        case R.id.col4:
            updated = setPieceInColumn(4, playerTurn);
            break;
        case R.id.col5:
            updated = setPieceInColumn(5, playerTurn);
            break;
        case R.id.col6:
            updated = setPieceInColumn(6, playerTurn);
            break;
        case R.id.col7:
            updated = setPieceInColumn(7, playerTurn);
            break;
        case R.id.txtReset:
            if (gameOver) {
                resetGame(winner);
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("Confirmar")
                        .setMessage("¿Esta seguro que quiere reiniciar la partida?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                resetGame();
                            }
                        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();
            }
            return;
        case R.id.txtSound:
            if (isMute) {
                isMute = false;
                txtSound.setImageResource(mute);
                mp.reset();
                mp = MediaPlayer.create(this, R.raw.bg);
                mp.start();
            } else {
                isMute = true;
                txtSound.setImageResource(sound);
                mp.stop();
            }
            return;
        }

        if (updated) {
            winner = checkWinner();

            if (winner > 0) {
                if (!isMute) {
                    mp.stop();
                    mp = MediaPlayer.create(this, R.raw.tada);
                    mp.start();
                }
                playerTurn = winner;
                Toast.makeText(this, "Ganador: Jugador " + winner, Toast.LENGTH_SHORT).show();
                gameOver = true;
            } else {
                togglePlayer();
            }
        } else {
            if (gameOver) {
                Toast.makeText(this, "El juego ha finalizado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Esta columna esta llena", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void resetGame() {
        dashboard = new int[7][6];
        for(int x = 0; x < dashboard.length; x++) {
            for(int y = 0; y < dashboard[x].length; y++) {
                updateDashboard(x, y, 0);
            }
        }

        playerTurn = 1;
        mp.reset();
        if (!isMute) {
            mp = MediaPlayer.create(this, R.raw.bg);
            mp.start();
            mp.setLooping(true);
        }
        gameOver = false;
        Toast.makeText(this, "Juego reiniciado", Toast.LENGTH_SHORT).show();
        setPlayer(playerTurn);
    }

    private void resetGame(int player) {
        resetGame();
        playerTurn = player;
        setPlayer(player);
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

    public boolean setPieceInColumn(int x, int player) {
        if (gameOver) {
            return false;
        }
        
        boolean changed = false;
        x--;
        try {
            for (int y = 5; y >= 0; y--) {

                if (dashboard[x][y] == 0) {
                    dashboard[x][y] = player;
                    updateDashboard(x, y, player);
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
        } else if (player == 2) {
            player = player2;
        } else {
            player = player0;
        }

        (
                (ImageView) (
                        (TableRow) (
                                (TableLayout) findViewById(R.id.dashboard)
                        ).getChildAt(y)
                ).getChildAt(x)
        ).setImageResource(player);
    }

    private void togglePlayer() {
        if (playerTurn == 1) {
            playerTurn = 2;
            ((TextView) findViewById(R.id.txtPlayer)).setText(R.string.player2);
        } else {
            playerTurn = 1;
            ((TextView) findViewById(R.id.txtPlayer)).setText(R.string.player1);
        }
    }

    private void setPlayer(int player) {
        if (player == 1) {
            ((TextView) findViewById(R.id.txtPlayer)).setText(getString(R.string.player1));
        } else {
            ((TextView) findViewById(R.id.txtPlayer)).setText(R.string.player2);
        }
    }
}
