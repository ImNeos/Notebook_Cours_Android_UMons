package com.umons.projet.creactif.notebook_2;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.azeesoft.lib.colorpicker.ColorPickerDialog;
import com.umons.projet.creactif.database_int.DB_Notes;
import com.yarolegovich.lovelydialog.LovelyChoiceDialog;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CreateNewActivity extends AppCompatActivity {

    EditText et_name;
    TextView txt_color, txt_type;
    int color_choose;
    String color;
    String type;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new);
        InitializeFields();


        txt_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            /*    try {
                    OpenColorDialog_2();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }*/
                OpenColorDialog_3();
            }
        });
        txt_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenTypeDialog();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItemToDB();
                finish();
            }
        });
    }


    private void OpenTypeDialog() {

        String[] items = getResources().getStringArray(R.array.TypeOfNote);
        new LovelyChoiceDialog(this)
                .setTopColorRes(R.color.colorPrimary)
                .setTitle("Choissisez le type")
                .setItems(items, new LovelyChoiceDialog.OnItemSelectedListener<String>() {
                    @Override
                    public void onItemSelected(int position, String item) {
                        type = item;
                        txt_type.setText(type);
                    }
                })
                .show();


    }
    private void addItemToDB()
    {
        DB_Notes.getInstance(this).addElementTodB(et_name.getText().toString(),Long.toString(System.currentTimeMillis()),Integer.parseInt(type), color_choose);
    }

    private void test() throws ClassNotFoundException, IllegalAccessException {

        Log.i("TEST", getPackageName()+".R$color");
        Field[] fields = Class.forName(getPackageName()+".R$color").getDeclaredFields();
        List <String> colorNameList = new ArrayList<>();
        final List <Integer> colorIDList = new ArrayList<>();

        for(Field field : fields) {
            String colorName = field.getName();
            colorNameList.add(colorName);
            int colorId = field.getInt(null);
            int color = getResources().getColor(colorId);
            Log.i("test", colorName + " => " + colorId + " => " + color);
            colorIDList.add(color);
        }

    }
    private void OpenColorDialog_3()
    {
        ColorPickerDialog colorPickerDialog= ColorPickerDialog.createColorPickerDialog(this,ColorPickerDialog.DARK_THEME);
        colorPickerDialog.setOnColorPickedListener(new ColorPickerDialog.OnColorPickedListener() {
            @Override
            public void onColorPicked(int color, String hexVal) {
                //Your code here
                color_choose = color;
                txt_color.setHintTextColor(color_choose);
             /*   Log.i("COLOR", color + "test"+ hexVal);
                int hex = Integer.parseInt(hexVal);
                int r = (hex & 0xFF0000) >> 16;
                int g = (hex & 0xFF00) >> 8;
                int b = (hex & 0xFF);

                int r_c = 255-r;
                int g_c = 255-g;
                int b_c = 255-b;
                Color myColour = new Color();

                myColour.red(r_c);
                myColour.green(g_c);
                myColour.blue(b_c);



                txt_color.setColor(myColour);*/

            }
        });
        colorPickerDialog.setHexaDecimalTextColor(Color.parseColor("#ffffff")); //There are many functions like this
        colorPickerDialog.show();

    }
    private void OpenColorDialog_2() throws ClassNotFoundException, IllegalAccessException
    {
        Field[] fields = Class.forName(getPackageName()+".R$color").getDeclaredFields();
        List <String> colorNameList = new ArrayList<>();
        final List <Integer> colorIDList = new ArrayList<>();

        for(Field field : fields) {
            String colorName = field.getName();
            colorNameList.add(colorName);
            int colorId = field.getInt(null);
            int color = getResources().getColor(colorId);
            Log.i("test", colorName + " => " + colorId + " => " + color);
            colorIDList.add(color);
        }

        new LovelyChoiceDialog(this)
                .setTopColorRes(R.color.colorPrimary)
                .setTitle("Choissisez la couleur")
                .setItems(colorNameList, new LovelyChoiceDialog.OnItemSelectedListener<String>() {
                    @Override
                    public void onItemSelected(int position, String item) {
                        color = item;
                        txt_color.setText(item);
                        int desiredColour = getResources().getColor(getResources().getIdentifier(item, "color", getPackageName()));
                        txt_color.setBackgroundColor(desiredColour);
                    }
                })
                .show();
    }
    private void OpenColorDialog() {
        String[] items = getResources().getStringArray(R.array.ColorOfNote);

        new LovelyChoiceDialog(this)
                .setTopColorRes(R.color.colorPrimary)
                .setTitle("Choissisez la couleur")
                .setItems(items, new LovelyChoiceDialog.OnItemSelectedListener<String>() {
                    @Override
                    public void onItemSelected(int position, String item) {
                        color = item;
                        txt_color.setText(item);
                        int desiredColour = getResources().getColor(getResources().getIdentifier(item, "color", getPackageName()));
                        txt_color.setBackgroundColor(desiredColour);
                    }
                })
                .show();
       // Field[] fields = Class.forName(getPackageName()+".R$color").getDeclaredFields();
        //
        //et_name.setBackgroundColor(desiredColour);

       // Log.i("TEST", getPackageName());
       // Log.i("TEST", getResources().getXml(R.xml.colors));

      /* Field[] fields = Class.forName(getPackageName()+".R$color").getDeclaredFields();
        List <String> colorNameList = new ArrayList<>();
        final List <Integer> colorIDList = new ArrayList<>();

        for(Field field : fields) {
            String colorName = field.getName();
            colorNameList.add(colorName);
            int colorId = field.getInt(null);
            int color = getResources().getColor(colorId);
            Log.i("test", colorName + " => " + colorId + " => " + color);
            colorIDList.add(color);
        }*/

    }

    private void InitializeFields() {
        et_name = (EditText) findViewById(R.id.et_name);
        txt_color = (TextView) findViewById(R.id.txt_color);
        txt_type = (TextView) findViewById(R.id.txt_type);
        btn = (Button) findViewById(R.id.btn_ok);

    }
}