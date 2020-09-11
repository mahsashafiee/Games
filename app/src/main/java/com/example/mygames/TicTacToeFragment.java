package com.example.mygames;


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


/**
 * A simple {@link Fragment} subclass.
 */
public class TicTacToeFragment extends Fragment {

    private static final String TAG = "TicTacToeFragment";
    private static final String BUNDLE_ARRAY_BUTTONS = "Bundle_Button";
    private static final String BUNDLE_TURN = "bundle_turn";
    private static final String BUNDLE_IS_BUTTON_CLICKED = "bundle_isButtonClicked";

    private int mTurn;
    private GameButton[][] mGameButtons = new GameButton[3][3];
    private Button[][] button = new Button[3][3];
    private boolean[][] isClicked = new boolean[3][3];
    private String mWinner = "Winner is player: ";

    private final static int[][] sButtonId = {{R.id.button_R0C0, R.id.button_R0C1, R.id.button_R0C2},
            {R.id.button_R1C0, R.id.button_R1C1, R.id.button_R1C2},
            {R.id.button_R2C0, R.id.button_R2C1, R.id.button_R2C2}};


    public TicTacToeFragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tic_tac_toe, container, false);


        initGame(view);

        if (savedInstanceState != null) {

            button = ((Button[][]) savedInstanceState.getSerializable(BUNDLE_ARRAY_BUTTONS));
            isClicked = ((boolean[][]) savedInstanceState.getSerializable(BUNDLE_IS_BUTTON_CLICKED));
            mTurn = savedInstanceState.getInt(BUNDLE_TURN);
            for (int i=0; i<3; i++) {
                for (int j = 0; j < 3; j++) {
                    mGameButtons[i][j].setState(button[i][j], isClicked[i][j]);
                    ShowSnackBar(isGameFinished());
                }
            }
        }

        return view;
    }

    public void setTurn(Button button) {

        if (button.getText() != "")
            return;

        if (mTurn % 2 == 0) button.setText("X");
        else button.setText("O");
        mTurn++;

    }

    public void initGame(View view) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                final Button button = view.findViewById(sButtonId[i][j]);
                final int finalI = i;
                final int finalJ = j;
                button.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(View view) {
                        setTurn(button);
                        mGameButtons[finalI][finalJ].setState(button);
                        ShowSnackBar(isGameFinished());
                    }
                });
                mGameButtons[i][j] = new GameButton(button);
            }
        }
    }

    public boolean isGameFinished() {

        for (int i = 0; i < 3; i++) {

            Log.i(TAG, "isGameFinished: " + mGameButtons[i][0].getText() + " " + mGameButtons[i][1].getText() + " " + mGameButtons[i][2].getText());

            if (mGameButtons[i][0].getText() == mGameButtons[i][1].getText() && mGameButtons[i][1].getText() == mGameButtons[i][2].getText() && mGameButtons[i][0].getText() != "") {
                mWinner += mGameButtons[i][0].getText();
                return true;

            } else if (mGameButtons[0][i].getText() == mGameButtons[1][i].getText() && mGameButtons[1][i].getText() == mGameButtons[2][i].getText() && mGameButtons[i][0].getText() != "") {
                mWinner += mGameButtons[0][i].getText();
                return true;
            } else if (mGameButtons[0][0].getText() == mGameButtons[1][1].getText() && mGameButtons[1][1].getText() == mGameButtons[2][2].getText() && mGameButtons[0][0].getText() != "") {
                mWinner += mGameButtons[0][0].getText();
                return true;

            } else if (mGameButtons[0][2].getText() == mGameButtons[1][1].getText() && mGameButtons[1][1].getText() == mGameButtons[2][0].getText() && mGameButtons[1][1].getText() != "") {
                mWinner += mGameButtons[1][1].getText();
                return true;
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
        } else {
            if (isAllButtonsClicked()) {
                setDisable();
                mWinner += "No one";
                snackbar.setText(mWinner);
                snackbar.show();
            }
        }
    }

    public boolean isAllButtonsClicked() {
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (mGameButtons[i][j].isButtonClicked())
                    count++;
            }
        }

        return count == 9;

    }

    public void setDisable() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                mGameButtons[i][j].getButton().setEnabled(false);
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

        super.onSaveInstanceState(outState);
        for(int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                button[i][j] = mGameButtons[i][j].getButton();
                isClicked[i][j] = mGameButtons[i][j].isButtonClicked();
            }
        }
        outState.putSerializable(BUNDLE_ARRAY_BUTTONS, button);
        outState.putSerializable(BUNDLE_IS_BUTTON_CLICKED, isClicked);
        outState.putInt(BUNDLE_TURN, mTurn);
    }

}
