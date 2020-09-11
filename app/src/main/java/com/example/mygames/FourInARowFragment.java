package com.example.mygames;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mygames.model.GameButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;

import static com.google.android.material.snackbar.Snackbar.make;


/**
 * A simple {@link Fragment} subclass.
 */
public class FourInARowFragment extends Fragment {

    private static final String TAG = "FourInARowFragment";
    private static final String BUNDLE_ARRAY_BUTTONS = "Bundle_Button";
    private static final String BUNDLE_TURN = "bundle_turn";
    private static final String BUNDLE_IS_BUTTON_CLICKED = "bundle_isButtonClicked";


    private GameButton[][] mGameButtons = new GameButton[5][5];

    int mTurn;

    boolean[] isButtonClicked = new boolean[25];

    private Button[][] button = new Button[5][5];

    private boolean[][] isClicked = new boolean[5][5];

    String mWinner = "Winner is player: ";


    final static int[][] sButtonId = {{R.id.button_4R0C0, R.id.button_4R0C1, R.id.button_4R0C2, R.id.button_4R0C3, R.id.button_4R0C4},
            {R.id.button_4R1C0, R.id.button_4R1C1, R.id.button_4R1C2, R.id.button_4R1C3, R.id.button_4R1C4},
            {R.id.button_4R2C0, R.id.button_4R2C1, R.id.button_4R2C2, R.id.button_4R2C3, R.id.button_4R2C4},
            {R.id.button_4R3C0, R.id.button_4R3C1, R.id.button_4R3C2, R.id.button_4R3C3, R.id.button_4R3C4},
            {R.id.button_4R4C0, R.id.button_4R4C1, R.id.button_4R4C2, R.id.button_4R4C3, R.id.button_4R4C4}};


    public FourInARowFragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_four_in_arow, container, false);

        Arrays.fill(isButtonClicked, false);


        initGame(view);

        startGame();

        if (savedInstanceState != null) {

            button = ((Button[][]) savedInstanceState.getSerializable(BUNDLE_ARRAY_BUTTONS));
            isClicked = ((boolean[][]) savedInstanceState.getSerializable(BUNDLE_IS_BUTTON_CLICKED));
            mTurn = savedInstanceState.getInt(BUNDLE_TURN);
            for (int i = 4; i >= 0; i--) {
                for (int j = 4; j >= 0; j--) {
                    mGameButtons[i][j].setState(button[i][j], isClicked[i][j]);
                    ShowSnackBar(isGameFinished());
                    if (i >= 1) {
                        enableRow(mGameButtons[i][j], i);
                    }
                }
            }
        }



        return view;
    }

    public void setTurn(Button button) {

        if (button.getText() != "")
            return;

        if (mTurn % 2 == 0) {
            button.setText("Red");
            button.setBackgroundResource(R.color.ForInaRowPlayer1);
        } else {
            button.setText("Blue");
            button.setBackgroundResource(R.color.ForInaRowPlayer2);
        }

        mTurn++;

    }

    public void initGame(View view) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                final Button button = view.findViewById(sButtonId[i][j]);
                final int finalI = i;
                final int finalJ = j;
                button.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(View view) {

                        setTurn(button);
                        mGameButtons[finalI][finalJ].setState(button);
                        if (finalI >= 1)
                            enableRow(mGameButtons[finalI][finalJ], finalI);
                        ShowSnackBar(isGameFinished());
                    }
                });
                mGameButtons[i][j] = new GameButton(button);
            }
        }
    }

    public boolean isGameFinished() {

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j <5-3; j++) {
                if (!mGameButtons[i][j].getText().equals("") && mGameButtons[i][j].getText().equals(mGameButtons[i][j + 1].getText()) &&
                        mGameButtons[i][j + 1].getText().equals(mGameButtons[i][j + 2].getText()) && mGameButtons[i][j + 2].getText().equals(mGameButtons[i][j + 3].getText())) {
                    mWinner += mGameButtons[i][j].getText();
                    return true;
                }
            }
        }

        for (int i = 0; i < 5-3; i++) {
            for (int j = 0; j <5; j++) {
                if (!mGameButtons[i][j].getText().equals("") && mGameButtons[i][j].getText().equals(mGameButtons[i + 1][j].getText()) &&
                        mGameButtons[i + 1][j].getText().equals(mGameButtons[i + 2][j].getText()) && mGameButtons[i + 2][j].getText().equals(mGameButtons[i + 3][j].getText())) {
                    mWinner += mGameButtons[i][j].getText();
                    return true;
                }
            }
        }

        for (int i = 0; i < 5-3; i++) {
            for (int j = 3; j <5; j++) {
                if (!mGameButtons[i][j].getText().equals("") && mGameButtons[i][j].getText().equals(mGameButtons[i + 1][j - 1].getText()) &&
                        mGameButtons[i + 1][j - 1].getText().equals(mGameButtons[i + 2][j - 2].getText()) && mGameButtons[i + 2][j - 2].getText().equals(mGameButtons[i + 3][j - 3].getText())) {
                    mWinner += mGameButtons[i][j].getText();
                    return true;
                }
            }
        }

        //if row and column items were more than 5
        for (int i = 4; i < 5 - 3; i--) {
            for (int j = 3; j < 5 ; j--) {
                if (!mGameButtons[i][j].getText().equals("") && mGameButtons[i][j].getText().equals(mGameButtons[i + 1][j + 1].getText()) &&
                        mGameButtons[i + 1][j + 1].getText().equals(mGameButtons[i + 2][j + 2].getText()) && mGameButtons[i + 2][j + 2].getText().equals(mGameButtons[i + 3][j + 3].getText())) {
                    mWinner += mGameButtons[i][j].getText();
                    return true;
                }
            }
        }

        return false;

    }

    public void ShowSnackBar(boolean isFinished) {

        final Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.main_container), mWinner, BaseTransientBottomBar.LENGTH_INDEFINITE);
        snackbar.setDuration(5000000);
        snackbar.setAction("Dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();
            }
        });

        if (isFinished) {
            setDisable();
            snackbar.show();
            return;

        } else {
            if (isAllButtonsClicked()) {
                setDisable();
                mWinner += "No one";
                snackbar.setText(mWinner);
                snackbar.show();

                return;
            }
        }
    }

    public boolean isAllButtonsClicked() {
        int count = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (mGameButtons[i][j].isButtonClicked())
                    count++;
            }
        }

        if (count == 25)
            return true;
        return false;

    }

    public void setDisable() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                mGameButtons[i][j].getButton().setEnabled(false);
            }
        }
    }

    public void startGame() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                mGameButtons[i][j].getButton().setEnabled(false);
            }
        }
    }

    public void enableRow(GameButton Btn, int Row) {
        if (Btn.isButtonClicked()) {
            for (int i = 0; i < 5; i++)
                mGameButtons[Row - 1][i].getButton().setEnabled(true);
        }
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

        super.onSaveInstanceState(outState);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                button[i][j] = mGameButtons[i][j].getButton();
                isClicked[i][j] = mGameButtons[i][j].isButtonClicked();
            }
        }
        outState.putSerializable(BUNDLE_ARRAY_BUTTONS, button);
        outState.putSerializable(BUNDLE_IS_BUTTON_CLICKED, isClicked);
        outState.putInt(BUNDLE_TURN, mTurn);
    }
}
