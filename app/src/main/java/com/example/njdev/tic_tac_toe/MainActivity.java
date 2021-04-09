package com.example.njdev.tic_tac_toe;

import java.util.Random;


import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {
    private int grid[][];
    Button buttons[][];
    Button reset;
    TextView textView;
    AI computer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        makeBoard();

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeBoard();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuItem item = menu.add("New Game");
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        makeBoard();
        return true;
    }

    //Sets up the game
    private void makeBoard() {
        computer = new AI();
        buttons = new Button[3][3];
        grid = new int[3][3];
        reset = findViewById(R.id.Reset);

        textView = findViewById(R.id.display_info);

        buttons[0][0] = findViewById(R.id.square00);
        buttons[0][1] = findViewById(R.id.square01);
        buttons[0][2] = findViewById(R.id.square02);

        buttons[1][0] = findViewById(R.id.square10);
        buttons[1][1] = findViewById(R.id.square11);
        buttons[1][2] = findViewById(R.id.square12);

        buttons[2][0] = findViewById(R.id.square20);
        buttons[2][1] = findViewById(R.id.square21);
        buttons[2][2] = findViewById(R.id.square22);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j] = 2;
            }
        }

        textView.setText("Click a square to play the game!");

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setOnClickListener(new clickListener(i, j));
                if(!buttons[i][j].isEnabled()) {
                    buttons[i][j].setText(" ");
                    buttons[i][j].setEnabled(true);
                }
            }
        }
    }

    private void markSquare(int x, int y){
        buttons[x][y].setEnabled(false);
        buttons[x][y].setText("X");
        grid[x][y] = 1;
        winORlose();
    }

    private class AI{
        public void compTurn(){
            if(grid[0][0]==2 &&
                    ((grid[0][1]==0 && grid[0][2]==0) ||
                            (grid[1][1]==0 && grid[2][2]==0) ||
                            (grid[1][0]==0 && grid[2][0]==0))) {
                markSquare(0,0);
            }
            else if (grid[0][1]==2 &&
                    ((grid[1][1]==0 && grid[2][1]==0) ||
                            (grid[0][0]==0 && grid[0][2]==0))) {
                markSquare(0,1);
            }
            else if(grid[0][2]==2 &&
                    ((grid[0][0]==0 && grid[0][1]==0) ||
                            (grid[2][0]==0 && grid[1][1]==0) ||
                            (grid[1][2]==0 && grid[2][2]==0))) {
                markSquare(0,2);
            }
            else if(grid[1][0]==2 &&
                    ((grid[1][1]==0 && grid[1][2]==0) ||
                            (grid[0][0]==0 && grid[2][0]==0))){
                markSquare(1,0);
            }
            else if(grid[1][1]==2 &&
                    ((grid[0][0]==0 && grid[2][2]==0) ||
                            (grid[0][1]==0 && grid[2][1]==0) ||
                            (grid[2][0]==0 && grid[0][2]==0) ||
                            (grid[1][0]==0 && grid[1][2]==0))) {
                markSquare(1,1);
            }
            else if(grid[1][2]==2 &&
                    ((grid[1][0]==0 && grid[1][1]==0) ||
                            (grid[0][2]==0 && grid[2][2]==0))) {
                markSquare(1,2);
            }
            else if(grid[2][0]==2 &&
                    ((grid[0][0]==0 && grid[1][0]==0) ||
                            (grid[2][1]==0 && grid[2][2]==0) ||
                            (grid[1][1]==0 && grid[0][2]==0))){
                markSquare(2,0);
            }
            else if(grid[2][1]==2 &&
                    ((grid[0][1]==0 && grid[1][1]==0) ||
                            (grid[2][0]==0 && grid[2][2]==0))) {
                markSquare(2,1);
            }
            else if( grid[2][2]==2 &&
                    ((grid[0][0]==0 && grid[1][1]==0) ||
                            (grid[0][2]==0 && grid[1][2]==0) ||
                            (grid[2][0]==0 && grid[2][1]==0))) {
                markSquare(2,2);
            }
            else {
                Random rand = new Random();

                int a = rand.nextInt(3);
                int b = rand.nextInt(3);
                while(a==0 || b==0 || grid[a][b]!=2) {
                    a = rand.nextInt(3);
                    b = rand.nextInt(3);
                }
                markSquare(a,b);
            }
        }
    }

    class clickListener implements View.OnClickListener{
        int x;
        int y;

        public clickListener(int x, int y){
            this.x = x;
            this.y = y;
        }
        @Override
        public void onClick(View v) {
            if(buttons[x][y].isEnabled()){
                buttons[x][y].setEnabled(false);
                buttons[x][y].setText("O");
                grid[x][y] = 0;
                textView.setText("");
                if(!winORlose()){
                    computer.compTurn();
                }
            }
        }
    }

    private boolean winORlose() {
        boolean gameOver = false;
        if ((grid[0][0] == 0 && grid[1][1] == 0 && grid[2][2] == 0)
                || (grid[0][2] == 0 && grid[1][1] == 0 && grid[2][0] == 0)
                || (grid[0][1] == 0 && grid[1][1] == 0 && grid[2][1] == 0)
                || (grid[0][2] == 0 && grid[1][2] == 0 && grid[2][2] == 0)
                || (grid[0][0] == 0 && grid[0][1] == 0 && grid[0][2] == 0)
                || (grid[1][0] == 0 && grid[1][1] == 0 && grid[1][2] == 0)
                || (grid[2][0] == 0 && grid[2][1] == 0 && grid[2][2] == 0)
                || (grid[0][0] == 0 && grid[1][0] == 0 && grid[2][0] == 0)) {
            textView.setText("You win!");
            gameOver = true;
        } else if ((grid[0][0] == 1 && grid[1][1] == 1 && grid[2][2] == 1)
                || (grid[0][2] == 1 && grid[1][1] == 1 && grid[2][0] == 1)
                || (grid[0][1] == 1 && grid[1][1] == 1 && grid[2][1] == 1)
                || (grid[0][2] == 1 && grid[1][2] == 1 && grid[2][2] == 1)
                || (grid[0][0] == 1 && grid[0][1] == 1 && grid[0][2] == 1)
                || (grid[1][0] == 1 && grid[1][1] == 1 && grid[1][2] == 1)
                || (grid[2][0] == 1 && grid[2][1] == 1 && grid[2][2] == 1)
                || (grid[0][0] == 1 && grid[1][0] == 1 && grid[2][0] == 1)) {
            textView.setText("You lost!");
            gameOver = true;
        } else {
            boolean empty = false;
            for(int i=0; i<3; i++) {
                for(int j=0; j<3; j++) {
                    if(grid[i][j]==2) {
                        empty = true;
                        break;
                    }
                }
            }
            if(!empty) {
                gameOver = true;
                textView.setText("Draw!");
            }
        }
        return gameOver;
    }


}