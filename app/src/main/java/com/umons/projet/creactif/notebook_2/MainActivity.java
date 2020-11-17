package com.umons.projet.creactif.notebook_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.umons.projet.creactif.database_int.DB_Notes;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GoToHomeActivity();
        //TEST
    }
    private void GoToHomeActivity()
    {
       Intent intent = new Intent(MainActivity.this, HomeActivity.class);
       startActivity(intent);
       finish();
    }
}