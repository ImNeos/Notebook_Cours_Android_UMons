package com.umons.projet.creactif.notebook_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
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
    FloatingActionButton fab_add;
    NoteListAdapter noteListAdapter;
    RecyclerView recyclerView;
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


        //TODO 1 : Create a new package called "Model"
        //TODO 2 : Create a class called "NoteListClass" -> In this class we will represent an object NoteList, so we need to declare some variable (Name, type,...)
        //TODO 3 : Create getters and setters + Constructor
        //TODO 4 : Create a layout model for the recyclerview

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
                        if (!DB_Notes.getInstance(HomeActivity.this).checkAlreadyExist(text))
                        {
                            //TODO 5 : Create a list of object and att it to this list

                            //TODO 7 Rewrite this function the right way : FillInList(); addItemToDB(text);



                            noteListAdapter.notifyDataSetChanged();

                            DiffuserMessage(text);
                        }else
                            {
                            Toast.makeText(HomeActivity.this, "Ce nom existe déjà ! ", Toast.LENGTH_SHORT).show();
                        }
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
        fab_add = (FloatingActionButton) findViewById(R.id.fab_add);

        //TODO 6 Rewrite this function the right way : FillInList();


        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));

        recyclerView.setLayoutManager(layoutManager);
        noteListAdapter = new NoteListAdapter(this);
        recyclerView.setAdapter(noteListAdapter);
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
class NoteListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{


    public NoteListAdapter (Context context)
    {
        //TODO In the constructor, pass the list of object and use it
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        //TODO We will do this together
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        //TODO We will do this together
    }

    @Override
    public int getItemCount()
    {
        //TODO We will do this together
        return 0;
    }
}