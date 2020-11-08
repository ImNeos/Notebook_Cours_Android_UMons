package com.umons.projet.creactif.Util;

import android.util.Log;

import com.umons.projet.creactif.notebook_2.R;


public class HelperClass {

    public static int ChooseColor(String name)
    {
        int totalnumber=0;
        for (int i = 0; i<name.length(); i++)
        {
            char ch = name.charAt(i);
            totalnumber += ch - 'a' + 1;
        }
        int modulo_num = Math.abs(totalnumber)%4;
        Log.i("ColorChooseModulo", ""+modulo_num);


        switch (modulo_num)
        {
            case 0 :
                return R.color.colorAccent;
            case 1 :
                return R.color.colorPrimary;
            case 2 :
                return R.color.colorPrimaryDark;
            case 3:
                return R.color.design_default_color_on_secondary;
            default:
                return R.color.design_default_color_background;
        }
    }
}
