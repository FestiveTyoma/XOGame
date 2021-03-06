package com.example.xogame;

import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Random;


public class MainActivity extends AppCompatActivity {
    Button b1, b2, b3, b4, b5, b6, b7, b8, b9;
    private static final int SIZE = 3;
    private int locCountX;
    private int locCountO;
    private int counterO;
    private int tempI;
    private int tempJ;

    public int LevelComp = 1;

    public enum Type {X, O, NOT_SET}

    private final Button[][] buttons = new Button[SIZE][SIZE];
    private Type[][] cells;
    // RadioButton user;
    RadioButton easy;
    RadioButton medium;
    RadioButton hard;
    // RadioGroup compAndUser;
    RadioGroup levelComp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initArray();
        setListenerOnButtons();
        setListenerOnNewGame();
        setListenerOnRadioGroup();
    }

    private void setListenerOnRadioGroup() {
        // compAndUser = findViewById(R.id.compAndUser);
        // user = findViewById(R.id.user);
        levelComp = findViewById(R.id.levelComp);
        easy = findViewById(R.id.easy);
        medium = findViewById(R.id.medium);
        hard = findViewById(R.id.hard);
        CompoundButton.OnCheckedChangeListener levelListener = (buttonView, isChecked) -> {

            if (easy.isChecked()) {
                LevelComp = 1;
            }
            if (medium.isChecked()) {
                LevelComp = 2;

            }
            if (hard.isChecked()) {
                LevelComp = 3;
            }
        };
        easy.setOnCheckedChangeListener(levelListener);
        medium.setOnCheckedChangeListener(levelListener);
        hard.setOnCheckedChangeListener(levelListener);
    }


    private boolean isTie() {
        boolean p = !isGetLine(Type.X) && !isGetLine(Type.O);
        if (p) {
            for (Type[] row : cells) {
                for (Type c : row) {
                    if (c.equals(Type.NOT_SET)) {
                        return false;
                    }
                }
            }
        } else {
            return false;
        }
        return true;
    }

    public boolean isAlmostWins(Type pointForCheck, Type pointForSet) {
        if (cells[0][0].equals(pointForCheck) && cells[1][1].equals(pointForCheck) && cells[2][2].equals(Type.NOT_SET)) {
            cells[2][2] = pointForSet;
            tempI = 2;
            tempJ = 2;
            return true;
        }
        else if (cells[0][0].equals(pointForCheck) && cells[2][2].equals(pointForCheck) && cells[1][1].equals(Type.NOT_SET)) {
            cells[1][1] = pointForSet;
            tempI = 1;
            tempJ = 1;
            return true;
        }
        else if (cells[1][1].equals(pointForCheck) && cells[2][2].equals(pointForCheck) && cells[0][0].equals(Type.NOT_SET)) {
            cells[0][0] = pointForSet;
            tempI = 0;
            tempJ = 0;
            return true;
        }
        else if (cells[0][2].equals(pointForCheck) && cells[1][1].equals(pointForCheck) && cells[2][0].equals(Type.NOT_SET)) {
            cells[2][0] = pointForSet;
            tempI = 2;
            tempJ = 0;
            return true;
        }
        else if (cells[0][2].equals(pointForCheck) && cells[2][0].equals(pointForCheck) && cells[1][1].equals(Type.NOT_SET)) {
            cells[1][1] = pointForSet;
            tempI = 1;
            tempJ = 1;
            return true;
        }
        else if (cells[1][1].equals(pointForCheck) && cells[2][0].equals(pointForCheck) && cells[0][2].equals(Type.NOT_SET)) {
            cells[0][2] = pointForSet;
            tempI = 0;
            tempJ = 2;
            return true;
        }

        for (int i = 0; i < SIZE; i++) {
            int count = 0;
            for (int j = 0; j < SIZE; j++) {
                if (cells[i][j].equals(pointForCheck)) {
                    count++;
                } else if (cells[i][j].equals(Type.NOT_SET)) {
                    tempI = i;
                    tempJ = j;
                } else if( cells[i][j].equals(pointForSet)) {
                    tempI = i;
                    tempJ = j;
                }

            }
            if (count == 2 && cells[tempI][tempJ].equals(Type.NOT_SET)) {
                cells[tempI][tempJ] = pointForSet;
                return true;
            }
        }
        for (int i = 0; i < SIZE; i++) {
            int count = 0;
            for (int j = 0; j < SIZE; j++) {
                if (cells[j][i].equals(pointForCheck)) {
                    count++;
                }else if (cells[j][i].equals(Type.NOT_SET)) {
                    tempI = j;
                    tempJ = i;
                } else if( cells[j][i].equals(pointForSet)) {
                    tempI = j;
                    tempJ = i;
                }

            }
            if (count == 2 && cells[tempI][tempJ].equals(Type.NOT_SET)) {
                cells[tempI][tempJ] = pointForSet;
                return true;
            }
        }
        return false;
    }

       /* (((cells[0][0] == x && (cells[0][1] == x  || cells[0][2] == x)) || cells[0][1] == x && cells[0][2] == x) ||
                ((cells[1][0] == x && (cells[1][1] == x  || cells[1][2] == x)) || cells[1][1] == x && cells[1][2] == x) ||
                ((cells[2][0] == x && (cells[2][1] == x  || cells[2][2] == x)) || cells[2][1] == x && cells[2][2] == x) ||
                ((cells[0][0] == x && (cells[1][0] == x  || cells[2][0] == x)) || cells[1][0] == x && cells[2][0] == x) ||
                ((cells[0][1] == x && (cells[1][1] == x  || cells[2][1] == x)) || cells[1][1] == x && cells[2][1] == x) ||
                ((cells[0][2] == x && (cells[1][2] == x  || cells[2][2] == x)) || cells[1][2] == x && cells[2][2] == x) ||
                ((cells[0][0] == x && (cells[1][1] == x  || cells[2][2] == x)) || cells[1][1] == x && cells[2][2] == x) ||
                ((cells[0][2] == x && (cells[1][1] == x  || cells[2][0] == x)) || cells[1][1] == x && cells[2][0] == x));
    */

    private boolean isGetLine(Type x) {
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
            if ((locCountX > locCountO) && !isGetLine(Type.X)) {
                checkAmountCompPointAndSetIt();
            }
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
        View.OnClickListener oclNegGameButton = v -> {
            freeze(true);
            initArray();

        };
        newGameButton.setOnClickListener(oclNegGameButton);
    }

    private void doUserShoot(Button clickedButton, Point clickedPoint) {
        if (clickedPoint != null) {
            String numberStr = clickedButton.getText().toString();
            if (numberStr.equals("")) {
                cells[clickedPoint.getX()][clickedPoint.getY()] = Type.X;
                locCountX++;
                //Toast.makeText(this, "Установил в cеLLs X ["+clickedPoint.getX()+"]["+clickedPoint.getY()+"]", Toast.LENGTH_SHORT).show();
                clickedButton.setText("X");
                if (isGetLine(Type.X)) {
                    endDialog();
                }
                if (isTie()) {
                    endDialog();
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
        switch (LevelComp) {
            case 1:
                getRandomComputerPoint(point);
                break;
            case 2:
                setBlockOrRandomPoint(point);
                break;
            case 3:
                setSmartPoint(point);
                break;
        }
        locCountO++;
        if (isGetLine(Type.O)) {
            endDialog();
        }
        if (isTie()) {
            endDialog();
        }
    }

    private void setSmartPoint(String point) {
        if (isAlmostWins(Type.O, Type.O)) {
            buttons[tempI][tempJ].setText(point);
        }
        else {
           if (isAlmostWins(Type.X, Type.O)) {
               buttons[tempI][tempJ].setText(point);
               return;
           }
                if (cells[1][1].equals(Type.NOT_SET)) {
                    cells[1][1] = Type.O;
                    tempI = 1;
                    tempJ = 1;
                } else if (cells[0][0].equals(Type.NOT_SET)) {
                    cells[0][0] = Type.O;
                    tempI = 0;
                    tempJ = 0;
                } else if (cells[0][2].equals(Type.NOT_SET)) {
                    cells[0][2] = Type.O;
                    tempI = 0;
                    tempJ = 2;
                } else if (cells[2][2].equals(Type.NOT_SET)) {
                    cells[2][2] = Type.O;
                    tempI = 2;
                    tempJ = 2;
                } else if (cells[2][0].equals(Type.NOT_SET)) {
                    cells[2][0] = Type.O;
                    tempI = 2;
                    tempJ = 0;
                }
            }

        buttons[tempI][tempJ].setText(point);
    }

    private void setBlockOrRandomPoint(String point) {
        if (isAlmostWins(Type.X, Type.O)) {
            buttons[tempI][tempJ].setText(point);
        } else {
            getRandomComputerPoint(point);
        }
    }

    private void getRandomComputerPoint(String point) {
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
    }


    private void initArray() {
        locCountX = 0;
        locCountO = 0;
        counterO = 0;
        tempI = 0;
        tempJ = 0;
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

    public void endDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (isGetLine(Type.X)) {
            builder.setMessage("Крестики победили! Будешь ещё?");
        }
        if (isGetLine(Type.O)) {
            builder.setMessage("Нолики победили! Будешь ещё?");
        }
        if (isTie()) {
            builder.setMessage("Ничья! Будешь ещё?");
        }
        builder.setPositiveButton("Да", (dialog, which) -> initArray());
        builder.setNegativeButton("Нет", (dialog, which) -> freeze(false));
        builder.create().show();
    }

    private void freeze(boolean clickable) {
        for (Button[] button : buttons) {
            for (Button button1 : button) {
                button1.setClickable(clickable);
            }
        }
    }
}


