package com.test.simpleproject2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    private static final int GRID_SIZE = 3;

    private Button[][] buttons;
    private boolean playerXTurn;
    private int movesCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        buttons = new Button[GRID_SIZE][GRID_SIZE];
        playerXTurn = true;
        movesCount = 0;

        GridLayout gridLayout = findViewById(R.id.gridLayout);

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                buttons[i][j] = (Button) gridLayout.getChildAt(i * GRID_SIZE + j);
            }
        }
    }

    public void onCellClick(View view) {
        Button cell = (Button) view;

        if (cell.getText().toString().isEmpty()) {
            if (playerXTurn) {
                cell.setText("X");
            } else {
                cell.setText("O");
            }

            movesCount++;

            if (checkWin(cell)) {
                String winner = playerXTurn ? "Player X" : "Player O";
                Toast.makeText(this, winner + " wins!", Toast.LENGTH_SHORT).show();
                restartGame();
            } else if (movesCount == GRID_SIZE * GRID_SIZE) {
                Toast.makeText(this, "It's a draw!", Toast.LENGTH_SHORT).show();
                restartGame();
            } else {
                playerXTurn = !playerXTurn;
            }
        }
    }

    private boolean checkWin(Button lastButton) {
        String symbol = playerXTurn ? "X" : "O";
        int row = -1, col = -1;

        for (int i = 0; i < GRID_SIZE; i++) {
            if (lastButton.getId() == buttons[i][0].getId()) {
                row = i;
                col = 0;
                break;
            } else if (lastButton.getId() == buttons[0][i].getId()) {
                row = 0;
                col = i;
                break;
            }
        }

        if (row != -1 && col != -1) {
            return (buttons[row][0].getText().equals(symbol) &&
                    buttons[row][1].getText().equals(symbol) &&
                    buttons[row][2].getText().equals(symbol)) ||
                    (buttons[0][col].getText().equals(symbol) &&
                            buttons[1][col].getText().equals(symbol) &&
                            buttons[2][col].getText().equals(symbol)) ||
                    (buttons[0][0].getText().equals(symbol) &&
                            buttons[1][1].getText().equals(symbol) &&
                            buttons[2][2].getText().equals(symbol)) ||
                    (buttons[0][2].getText().equals(symbol) &&
                            buttons[1][1].getText().equals(symbol) &&
                            buttons[2][0].getText().equals(symbol));
        }

        return false;
    }

    private void restartGame() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                buttons[i][j].setText("");
            }
        }

        playerXTurn = true;
        movesCount = 0;
    }
}
