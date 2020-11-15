package com.umons.projet.creactif.notebook_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.widgets.Helper;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.umons.projet.creactif.Util.HelperClass;
import com.umons.projet.creactif.database_int.DB_Notes;
import com.umons.projet.creactif.model.NoteListObject;
import com.umons.projet.creactif.note.WriteSimpleNoteActivity;
import com.yarolegovich.lovelydialog.LovelyTextInputDialog;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity {

    FloatingActionButton fab_add;
    NoteListAdapter noteListAdapter;
    RecyclerView recyclerView;

    List<NoteListObject> listObjects = new ArrayList<>();
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
                            int type = 0;
                            String date = Long.toString(System.currentTimeMillis());
                            NoteListObject noteListObject = new NoteListObject(text, date, type);
                            listObjects.add(noteListObject);
                            addItemToDB(text, date,type);
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

    private void addItemToDB(String text, String date, int type)
    {
        DB_Notes.getInstance(this).addElementTodB(text,date,type);
    }

    private void InitializeFields()
    {
        fab_add = (FloatingActionButton) findViewById(R.id.fab_add);

        FillInList();

        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(layoutManager);



        noteListAdapter = new NoteListAdapter(this, listObjects);
        recyclerView.setAdapter(noteListAdapter);
    }

    private void FillInList()
    {
        DB_Notes.getInstance(this).fillInlist(listObjects);
    }

    private void DiffuserMessage(String message)
    {
        Toast.makeText(HomeActivity.this, message, Toast.LENGTH_SHORT).show();
    }


}
class NoteListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    Context context;
    List<NoteListObject> listObjects = new ArrayList<>();
    public NoteListAdapter (Context context, List<NoteListObject> listObjects)
    {
        this.listObjects = listObjects;
        this.context = context;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.model_notelist, parent, false);
        return new ItemMessageSentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        final String name= listObjects.get(position).getName();
        final String date = listObjects.get(position).getDate();
        if (!TextUtils.isEmpty(name))
        {
            ((ItemMessageSentHolder)holder).lbl_name.setText(name);
            int color = HelperClass.ChooseColor(name);
            ((ItemMessageSentHolder)holder).im_color.setImageResource(color);
            HelperClass.ChooseColor(name);
        }
        if (!TextUtils.isEmpty(date))
        {

            ((ItemMessageSentHolder) holder).lbl_date.setText(HelperClass.convertMillisIntoDate(Long.parseLong(date)));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("ClickHolder", name);
                Intent intent = new Intent(context, WriteSimpleNoteActivity.class);
                intent.putExtra("Titre", name);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return listObjects.size();
    }
    class ItemMessageSentHolder extends RecyclerView.ViewHolder {

        public TextView lbl_name, lbl_date;
        public CircleImageView im_color;


        public ItemMessageSentHolder(View itemView) {
            super(itemView);

            lbl_name = itemView.findViewById(R.id.txt_name);
            lbl_date = itemView.findViewById(R.id.txt_date);
            im_color = itemView.findViewById(R.id.im_note);
        }
    }
}