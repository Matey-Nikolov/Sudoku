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

    boolean hasSelected = false;

    TextView selected = null; // "";
    String getTextNumber = "";
    String deleted = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                //https://www.folkstalk.com/tech/android-get-resource-id-by-string-with-example/
                int id = getResources().getIdentifier("textView" + col + "_" + row, "id", getPackageName());
                TextView current = findViewById(id);
                board[row][col] = current;

                getTextNumber = current.getText().toString();
                if(getTextNumber.equals("")){
                    numberBoard[row][col] = 0;
                }else {
                    numberBoard[row][col] = Integer.parseInt(getTextNumber);
                }

            }
        }


        Find();
        Edit();
        Deleted();

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
            System.out.println("Works");

        }
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



    /*
    protected void Check(TextView current) {

        TextView currentPrev;
        getColumnsNumbers = new ArrayList<>();
        getRowsNumbers = new ArrayList<>();
        //String currentStringPrev;

       // TextView currentNext;
      //  String currentStringNext;

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {

                if (board[row][col] == current){
                    getRow = row;
                    getCol = col;
                }
            }
        }
        System.out.println(getRow);

        for (int i = 0; i < 9; i++) {

            currentPrev = board[getRow][i];
          //  currentNext = board[getRow][i++];



           // currentStringPrev = currentPrev.getText().toString();
           // currentStringNext =  currentNext.getText().toString();

            getRowsNumbers.add(currentPrev.getText().toString());
        }

        System.out.println(getRowsNumbers);

        for (int j = 0; j < 9; j++) {
            currentPrev = board[j][getCol];

           // currentPrev.setBackgroundResource(R.drawable.borderselect);

            getColumnsNumbers.add(currentPrev.getText().toString());
        }

        System.out.println(getColumnsNumbers);

        System.out.println(getRow);
        System.out.println(getCol);


        matrix3x3[0][0] = board[getRow][getCol];
        matrix3x3[0][1] = board[getRow][getCol];
        matrix3x3[0][2] = board[getRow][getCol];

        matrix3x3[1][0] = board[getRow][getCol];
        matrix3x3[1][1] = board[getRow][getCol];
        matrix3x3[1][2] = board[getRow][getCol];

        matrix3x3[2][0] = board[getRow][getCol];
        matrix3x3[2][1] = board[getRow][getCol];
        matrix3x3[2][2] = board[getRow][getCol];

    }
*/
    protected void Deleted(){
        int id = getResources().getIdentifier("button9", "id", getPackageName());
        TextView currentBtn = findViewById(id);

        currentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hasSelected) {
                    selected.setText(deleted);
                    hasSelected = false;
                }
            }
        });
    }

    protected void Edit() {
        int id = getResources().getIdentifier("button10", "id", getPackageName());
        TextView currentBtn = findViewById(id);

        currentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hasSelected){
                    hasSelected = false;
                    //checkSelect = true;
                }
            }
        });
    }

    protected void Find(){



        for (int j = 0; j < 9; j++) {
            int id = getResources().getIdentifier("button" + j, "id", getPackageName());
            TextView currentBtn = findViewById(id);


            if(id == 11){

            }else {
                currentBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (hasSelected) {
                          //  checkSelect = false;
                            selected.setText(currentBtn.getText());
                        }
                    }
                });
            }
            buttons[j] = currentBtn;
        }

    }
}