package com.example.sudoku;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView[][] board = new TextView[9][9];
    int[][] numberBoard = new int[9][9];

    TextView[] buttons = new TextView[11];

    boolean hasSelected = true;

    TextView selectedText = null; // "";
    String getTextNumber = "";
    String deleted = "";

    int getRow;
    int getCol;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                //https://www.folkstalk.com/tech/android-get-resource-id-by-string-with-example/
                int id = getResources().getIdentifier("textView" + col + "_" + row, "id", getPackageName());
                TextView current = findViewById(id);

                current.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectedText = current;


                        if(search(board, selectedText)){
                            System.out.println(selectedText);
                            current.setText(selectedText.getText());
                        }
                    }

                });

                board[row][col] = current;

                getTextNumber = current.getText().toString();
                if(getTextNumber.equals("")){
                    numberBoard[row][col] = 0;
                }else {
                    numberBoard[row][col] = Integer.parseInt(getTextNumber);
                }
            }
        }

        find();
    }

    protected boolean search(TextView[][] board, TextView searchTextView){

        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                if(board[i][j] == searchTextView){
                    getRow = i;
                    getCol = j;
                    edit(); /////////////////////////////////////////////////////
                    return true;
                }
            }
        }

        return false;
    }
    protected void find(){
        int id = getResources().getIdentifier("buttonSolve", "id", getPackageName());
        TextView currentBtn = findViewById(id);

        currentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                solveBoard(numberBoard);

                int number;
                String numberString;

                if(solveBoard(numberBoard)){
                    for(int row = 0; row < 9; row++){
                        for(int col = 0; col < 9; col++){

                            TextView current;

                            number = numberBoard[row][col];
                            numberString = Integer.toString(number);
                            current = board[row][col];

                            current.setText(numberString);
                            System.out.println(current.getText());

                        }
                    }
                }
                else {
                    System.out.println("No solution");
                }
            }
        });


    }

    protected boolean solveBoard(int[][] numberBoard){
        for(int row = 0; row < 9; row++){
            for(int column = 0; column < 9; column++){
                if(numberBoard[row][column] == 0){
                    for (int numberToTry = 1; numberToTry <= 9; numberToTry++){
                        if(isValidPlacement(numberBoard, numberToTry, row, column)){
                            numberBoard[row][column] = numberToTry;
                            if(solveBoard(numberBoard)){
                                return  true;
                            }
                            else {
                                numberBoard[row][column] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }

        return  true;
    }

    protected boolean isValidPlacement(int[][] numberBoard, int number, int row , int column ){
        return  !isNumberInRow(numberBoard, number, row) &&
                !isNumberInColumn(numberBoard, number, column) &&
                !isNumberIn3x3(numberBoard, number, row, column);
    }

    protected boolean isNumberInRow(int[][] numberBoard, int number, int row){

        for (int i = 0; i < 9; i++){
            if(numberBoard[row][i] == number){
                return true;
            }
        }
        return  false;
    }

    protected boolean isNumberInColumn(int[][] numberBoard, int number, int column){

        for (int i = 0; i < 9; i++){
            if(numberBoard[i][column] == number){
                return true;
            }
        }
        return  false;
    }

    protected boolean isNumberIn3x3(int[][] numberBoard, int number, int row , int column){

        int localBoxRow = row - row % 3;
        int localBoxColumn = column - column % 3;

        for (int i = localBoxRow; i < localBoxRow + 3; i++){
            for (int j = localBoxColumn; j < localBoxColumn + 3; j++){
                if(numberBoard[i][j] == number){
                    return true;
                }
            }
        }
        return  false;
    }

    protected void edit() {

        for(int i = 1; i <= 9; i++) {

            int id = getResources().getIdentifier("button" + i, "id", getPackageName());
            TextView currentBtn = findViewById(id);

            currentBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    selectedText.setText(currentBtn.getText());

                    System.out.println(selectedText.getText());

                    getTextNumber = selectedText.getText().toString();

                    numberBoard[getRow][getCol] = Integer.parseInt(getTextNumber);
                }
            });
        }
    }
}