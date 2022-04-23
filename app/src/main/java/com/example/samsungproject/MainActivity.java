package com.example.samsungproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
<<<<<<< HEAD

public class MainActivity extends AppCompatActivity {

=======
import android.util.Log;


public class MainActivity extends AppCompatActivity {

    private Game game;
>>>>>>> dima

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< HEAD
        setContentView(new Game(this));

        //but = findViewById(R.id.buttt);

//        but.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "Зачем вы нажали?", Toast.LENGTH_SHORT).show();
//            }
//        });
=======
        game = new Game(this);
        setContentView(game);
        Log.d("dd", "ddd");
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onPause() {
        game.pause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
>>>>>>> dima
    }
}