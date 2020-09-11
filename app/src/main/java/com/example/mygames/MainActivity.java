package com.example.mygames;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final String MAIN_LAYOUT_COLOR = "main_layout_color";
    Fragment fragment = new Fragment();
    FragmentManager fragmentManager = getSupportFragmentManager();
    View view;
    int mColor;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = findViewById(R.id.main_container);
        fragmentManager = getSupportFragmentManager();
        fragment = fragmentManager.findFragmentById(R.id.fragment_container);

    }

    public void TicTacToeGame(View view){

        MainActivity.this.view.setBackgroundResource(R.color.colorTicTacToe);
        if (fragment == null) {
            fragmentManager.beginTransaction().replace(R.id.fragment_container, new TicTacToeFragment()).commit();
        }
    }

    public void FourInaRowGame(View view){

        MainActivity.this.view.setBackgroundResource(R.color.colorFourInaRow);
        if (fragment == null) {
            fragmentManager.beginTransaction().replace(R.id.fragment_container, new FourInARowFragment()).commit();
        }
    }

}
