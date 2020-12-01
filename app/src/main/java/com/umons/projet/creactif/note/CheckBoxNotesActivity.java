package com.umons.projet.creactif.note;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.umons.projet.creactif.Util.HelperClass;
import com.umons.projet.creactif.database_int.DB_NotesCheckBox;
import com.umons.projet.creactif.model.CheckBoxNotesModel;
import com.umons.projet.creactif.model.NoteListObject;
import com.umons.projet.creactif.notebook_2.R;

import java.util.ArrayList;
import java.util.List;

public class CheckBoxNotesActivity extends AppCompatActivity {


    ListItemsAdapter noteListAdapter;
    RecyclerView recyclerView;
    List<CheckBoxNotesModel> listObjects = new ArrayList<>();

    Button btn_add;
    EditText et_item;
    String note_name="";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_box_notes);
        Init();

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddToList();
            }
        });
    }

    private void AddToList()
    {
        String item = et_item.getText().toString();
        long id= System.currentTimeMillis();
        DB_NotesCheckBox.getInstance(this).addElementTodB(note_name,item,false,id, id);
        listObjects.add(new CheckBoxNotesModel(item, item, false,id,id));
        noteListAdapter.notifyDataSetChanged();
        et_item.setText("");
        recyclerView.smoothScrollToPosition(listObjects.size());
    }



    private void Init() {

        try {
            note_name = getIntent().getExtras().getString("Titre");
        }
        catch (Exception e){}


        DB_NotesCheckBox.getInstance(this).fillInlist(listObjects, note_name);
        btn_add = (Button) findViewById(R.id.btn_add);
        et_item = (EditText) findViewById(R.id.et_item);

        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(layoutManager);
        noteListAdapter = new ListItemsAdapter(this, listObjects);
        recyclerView.setAdapter(noteListAdapter);




    }
}
class ListItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<CheckBoxNotesModel> item_list;
    private LayoutInflater inflater;

    private int mRecentlyDeletedItemPosition;



    public ListItemsAdapter(Context context, List<CheckBoxNotesModel> Name_List) {

        this.context = context;
        this.item_list = Name_List;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.model_checkbox, parent, false);
        return new ItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position)
    {
        final String name= item_list.get(position).getItemname();
        final boolean ischeck = item_list.get(position).isCheckeck();

        final int pos = position;

        ((ItemsViewHolder)holder).lbl_article_name.setText(name);
        ((ItemsViewHolder)holder).checkBox.setChecked(ischeck);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (item_list.get(pos).isCheckeck())
                {
                    item_list.get(pos).setCheckeck(false);
                    Log.i("Checked", ""+item_list.get(pos).isCheckeck());
                    DB_NotesCheckBox.getInstance(context).UpdateCheckBox(item_list.get(pos));
                    notifyDataSetChanged();
                }
                else
                {
                    item_list.get(pos).setCheckeck(true);
                    Log.i("Checked", ""+item_list.get(pos).isCheckeck());
                    DB_NotesCheckBox.getInstance(context).UpdateCheckBox(item_list.get(pos));
                    notifyDataSetChanged();
                }
            }
        });
    }

    public void deleteItem(int position)
    {


    }



    @Override
    public int getItemCount() {
        return item_list.size();
    }
}

class ItemsViewHolder extends RecyclerView.ViewHolder {

    public TextView lbl_article_name;
    public CheckBox checkBox;

    public ItemsViewHolder(View itemView) {
        super(itemView);

        lbl_article_name = (TextView) itemView.findViewById(R.id.item_name);
        checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
    }
}
/*class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {

    private ListItemsAdapter mAdapter;
    private List <ListOfItems> ListOfUpdates;
    private List <ListOfItems> ListArticles;

    public SwipeToDeleteCallback(ListItemsAdapter adapter,List <ListOfItems> ListArticles,List <ListOfItems> ListOfUpdates) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        mAdapter = adapter;
        this.ListArticles=ListArticles;
        this.ListOfUpdates=ListOfUpdates;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1)
    {

        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i)
    {
        mAdapter.deleteItem(viewHolder.getAdapterPosition());
    }
}*/