package com.example.xogame;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import java.util.Random;


public class MainActivity extends AppCompatActivity {
    private static final int SIZE = 3;
    private int counterO = 0;
    public enum Type {X, O, NOT_SET}
    private final Button[][] buttons = new Button[SIZE][SIZE];
    private Type[][] cells;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initArray();
        setListenerOnButtons();
        setListenerOnNewGame();
    }

    private boolean isTie() {
        for (Type[] row : cells) {
            for (Type c : row) {
                if (c.equals(Type.NOT_SET)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isGetLine(Type x) {
        return ((cells[0][0] == x && cells[0][1] == x && cells[0][2] == x) ||
                (cells[1][0] == x && cells[1][1] == x && cells[1][2] == x) ||
                (cells[2][0] == x && cells[2][1] == x && cells[2][2] == x) ||
                (cells[0][0] == x && cells[1][0] == x && cells[2][0] == x) ||
                (cells[0][1] == x && cells[1][1] == x && cells[2][1] == x) ||
                (cells[0][2] == x && cells[1][2] == x && cells[2][2] == x) ||
                (cells[0][0] == x && cells[1][1] == x && cells[2][2] == x) ||
                (cells[2][0] == x && cells[1][1] == x && cells[0][2] == x));
    }
    private void setListenerOnButtons() {

        View.OnClickListener listener = myView -> {
            Button clickedButton = (Button) myView;
            Point clickedPoint = getClickedPoint(clickedButton);
            doUserShoot(clickedButton, clickedPoint);
            checkAmountCompPointAndSetIt();
        };
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Button button = buttons[i][j];
                button.setOnClickListener(listener);
            }
        }
    }

    private void checkAmountCompPointAndSetIt() {
        if (counterO < 4) {
            counterO = 0;
            getComputerPoint("O");
            for (Type[] row : cells) {
                for (Type c : row) {
                    if (c.equals(Type.O)) {
                        counterO++;
                    }
                }
            }
        }
    }


    private void setListenerOnNewGame() {
        Button newGameButton = findViewById(R.id.newGameButton);
        View.OnClickListener oclNegGameButton = v -> recreate();
        newGameButton.setOnClickListener(oclNegGameButton);

    }

    private void doUserShoot(Button clickedButton, Point clickedPoint) {
        if (clickedPoint != null) {
            String numberStr = clickedButton.getText().toString();
            if (numberStr.equals("")) {
                cells[clickedPoint.getX()][clickedPoint.getY()] = Type.X;
                //Toast.makeText(this, "Установил в cеLLs X ["+clickedPoint.getX()+"]["+clickedPoint.getY()+"]", Toast.LENGTH_SHORT).show();
                clickedButton.setText("X");
                if (isGetLine(Type.X)) {
                    Toast.makeText(this, "Крестики победили", Toast.LENGTH_SHORT).show();
                } else if (isTie()) {
                    Toast.makeText(this, "Ничья", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private Point getClickedPoint(Button clickedButton) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (clickedButton == buttons[i][j]) {
                    Point point = new Point();
                    point.setX(i);
                    point.setY(j);
                    return point;
                }
            }
        }
        return null;
    }


    private void getComputerPoint(String point) {
        Random random = new Random();
        int randomGetYY;
        int randomGetXX;
        do {
            randomGetXX = (random.nextInt(SIZE));
            randomGetYY = (random.nextInt(SIZE));

        } while ((!cells[randomGetXX][randomGetYY].equals(Type.NOT_SET)));
        cells[randomGetXX][randomGetYY] = Type.O;
        //Toast.makeText(this, "Установил в cеLLs O ["+randomGetXX+"]["+randomGetYY+"]", Toast.LENGTH_SHORT).show();
        buttons[randomGetXX][randomGetYY].setText(point);
        if (isGetLine(Type.O)) {
            Toast.makeText(this, "Нолики победили", Toast.LENGTH_SHORT).show();
        } else if (isTie()) {
            Toast.makeText(this, "Ничья", Toast.LENGTH_SHORT).show();
        }
    }

    private void initArray() {

        Button b1, b2, b3, b4, b5, b6, b7, b8, b9;
        b1 = findViewById(R.id.button1);
        b2 = findViewById(R.id.button2);
        b3 = findViewById(R.id.button3);
        b4 = findViewById(R.id.button4);
        b5 = findViewById(R.id.button5);
        b6 = findViewById(R.id.button6);
        b7 = findViewById(R.id.button7);
        b8 = findViewById(R.id.button8);
        b9 = findViewById(R.id.button9);
        buttons[0][0] = b1;
        b1.setText("");
        buttons[0][1] = b2;
        b2.setText("");
        buttons[0][2] = b3;
        b3.setText("");
        buttons[1][0] = b4;
        b4.setText("");
        buttons[1][1] = b5;
        b5.setText("");
        buttons[1][2] = b6;
        b6.setText("");
        buttons[2][0] = b7;
        b7.setText("");
        buttons[2][1] = b8;
        b8.setText("");
        buttons[2][2] = b9;
        b9.setText("");

        cells = new Type[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                cells[i][j] = Type.NOT_SET;
            }
        }
    }
}


