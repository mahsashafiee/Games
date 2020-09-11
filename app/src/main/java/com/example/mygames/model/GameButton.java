package com.example.mygames.model;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.Button;

import androidx.annotation.RequiresApi;

import com.example.mygames.R;

import java.io.Serializable;

public class GameButton implements Serializable {

    private Button mButton;
    private String mText = "";
    private boolean mButtonClicked = false;

    public GameButton(Button button) {
        mButton = button;
    }


    public Button getButton() {
        return mButton;
    }

    public String getText() {
        return mText;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void setState(Button button) {
        mButton.setBackground(button.getBackground());
        mButton.setText(button.getText());
        mText = button.getText().toString();
        mButtonClicked = true;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void setState(Button button, boolean isClicked) {
        this.setState(button);
        mButtonClicked = isClicked;
    }


    public boolean isButtonClicked() {
        return mButtonClicked;
    }

    @Override
    public String toString() {
        return "GameButton{" +
                "mButton=" + mButton +
                ", mText='" + mText + '\'' +
                ", mButtonClicked=" + mButtonClicked +
                '}';
    }
}
