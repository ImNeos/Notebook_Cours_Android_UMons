package com.umons.projet.creactif.note;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.umons.projet.creactif.notebook_2.R;

public class WriteSimpleNoteActivity extends AppCompatActivity {

    TextView tv_title;
    EditText et_note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_simple_note);
        InitializeFields();
        SetTitle();
    }

    private void SetTitle() {
        String title = "";
        try {
            title = getIntent().getExtras().getString("Titre");
            tv_title.setText(title);
            }
        catch (Exception e){}


    }

    private void InitializeFields() {
        tv_title = (TextView) findViewById(R.id.tv_title);
    }
}