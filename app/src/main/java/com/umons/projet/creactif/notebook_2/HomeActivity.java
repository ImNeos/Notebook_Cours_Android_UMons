package com.umons.projet.creactif.notebook_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.umons.projet.creactif.database_int.DB_Notes;
import com.umons.projet.creactif.note.WriteSimpleNoteActivity;
import com.yarolegovich.lovelydialog.LovelyTextInputDialog;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    List<String> list_of_items = new ArrayList<>();
    ListView listView;
    FloatingActionButton fab_add;
    ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        InitializeFields();

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //DiffuserMessage("Working !");
                OpenDialog();
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SendUserToNoteActivity(i);
            }
        });

    }

    private void SendUserToNoteActivity(int i)
    {
       // Toast.makeText(this, Integer.toString(i), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(HomeActivity.this, WriteSimpleNoteActivity.class);
        intent.putExtra("Titre", list_of_items.get(i));
        startActivity(intent);
    }


    private void OpenDialog()
    {
        new LovelyTextInputDialog(this)
                .setTopColorRes(R.color.colorPrimary)
                .setTitle("Ajouter un élément à la liste")
                .setMessage("Entrer votre élément")
                .setIcon(R.drawable.ic_input_add)
                .setInputFilter("Erreur", new LovelyTextInputDialog.TextFilter() {
                    @Override
                    public boolean check(String text) {
                        return text.length() > 3 && text.length() < 30;
                    }
                })
                .setConfirmButton(android.R.string.ok, new LovelyTextInputDialog.OnTextInputConfirmListener() {
                    @Override
                    public void onTextInputConfirmed(String text) {
                        addItemToDB(text);
                        list_of_items.add(text);
                        arrayAdapter.notifyDataSetChanged();
                        DiffuserMessage(text);
                    }
                })
                .show();
    }

    private void addItemToDB(String text)
    {
        DB_Notes.getInstance(this).addElementTodB(text);
    }

    private void InitializeFields()
    {
        listView = (ListView) findViewById(R.id.list_view);
        fab_add = (FloatingActionButton) findViewById(R.id.fab_add);


        FillInList();
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,  list_of_items);
        listView.setAdapter(arrayAdapter);


    }

    private void FillInList()
    {
        DB_Notes.getInstance(this).fillInlist(list_of_items);
    }

    private void DiffuserMessage(String message)
    {
        Toast.makeText(HomeActivity.this, message, Toast.LENGTH_SHORT).show();
    }


}