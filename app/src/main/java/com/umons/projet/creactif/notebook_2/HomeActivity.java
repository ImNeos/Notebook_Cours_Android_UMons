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
import android.graphics.Color;
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
import com.umons.projet.creactif.note.CheckBoxNotesActivity;
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
                OpenDialog();
            }
        });
    }
    private void OpenDialog()
    {
        startActivity(new Intent(HomeActivity.this, CreateNewActivity.class));
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

    @Override
    protected void onResume() {
        super.onResume();

        FillInList();
        noteListAdapter.notifyDataSetChanged();
    }

    private void FillInList()
    {
        DB_Notes.getInstance(this).fillInlist(listObjects);
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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position)
    {
        final String name= listObjects.get(position).getName() + " ("+ listObjects.get(position).getType() + ")";
        final String date = listObjects.get(position).getDate();
        if (!TextUtils.isEmpty(name))
        {
            ((ItemMessageSentHolder)holder).lbl_name.setText(name);
            ((ItemMessageSentHolder)holder).im_color.setColorFilter(listObjects.get(position).getColor());

            //  ((ItemMessageSentHolder)holder).im_color.setImageResource(context.getResources().getIdentifier(listObjects.get(position).getColor(), "color", context.getPackageName()));
        }
        if (!TextUtils.isEmpty(date))
        {

            ((ItemMessageSentHolder) holder).lbl_date.setText(HelperClass.convertMillisIntoDate(Long.parseLong(date)));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                int type = listObjects.get(position).getType();
                switch (type)
                {
                    case 0:
                    {
                        Log.i("ClickHolder", name);
                        Intent intent = new Intent(context, WriteSimpleNoteActivity.class);
                        intent.putExtra("Titre", name);
                        context.startActivity(intent);
                        break;
                    }
                    case 1: {
                        Log.i("ClickHolder", name);
                        Intent intent1 = new Intent(context, CheckBoxNotesActivity.class);
                        intent1.putExtra("Titre", name);
                        context.startActivity(intent1);
                        break;
                    }
                    default:{
                        Toast.makeText(context, ""+type, Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
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