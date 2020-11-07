package com.umons.projet.creactif.note;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.umons.projet.creactif.database_int.DB_WriteNotes;
import com.umons.projet.creactif.notebook_2.R;

public class WriteSimpleNoteActivity extends AppCompatActivity {

    TextView tv_title;
    EditText et_note;
    String note_name="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_simple_note);

        InitializeFields();
        SetTitle();

        et_note.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                DB_WriteNotes.getInstance(WriteSimpleNoteActivity.this).addElementTodB(note_name, charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable)
            {
            }
        });

    }

    private void SetTitle() {
        try {
            note_name = getIntent().getExtras().getString("Titre");
            tv_title.setText(note_name);
            }
        catch (Exception e){}

        String text = DB_WriteNotes.getInstance(this).getNote(note_name);
        et_note.setText(text);
    }



    private void InitializeFields() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        et_note= (EditText) findViewById(R.id.et_notes);


    }
}