package com.example.location.Base;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


import com.afollestad.materialdialogs.MaterialDialog;


public class BaseActivity extends AppCompatActivity {

    MaterialDialog dialog;
    protected BaseActivity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity=this;
    }


    public MaterialDialog showMessageString(String titleResId, String contentResId){
        dialog= new MaterialDialog.Builder(this)
                .title(titleResId)
                .content(contentResId)
                .show();
        return dialog;
    }

    public MaterialDialog showMessageInt(int titleResId, int contentResId){
        dialog= new MaterialDialog.Builder(this)
                .title(titleResId)
                .content(contentResId)
                .show();
        return dialog;
    }

    public MaterialDialog showConfirmationMessageInt(int titleResId,
                                                     int contentResId,
                                                     int posTextResId,
                                                     MaterialDialog.SingleButtonCallback onPos
    ){
        dialog= new MaterialDialog.Builder(this)
                .title(titleResId)
                .content(contentResId)
                .positiveText(posTextResId)
                .onPositive(onPos)
                .show();
        return dialog;
    }

    public MaterialDialog showConfirmationMessageString(String titleResId,
                                                        String contentResId,
                                                        String posTextResId,
                                                        MaterialDialog.SingleButtonCallback onPos
    ){
        dialog= new MaterialDialog.Builder(this)
                .title(titleResId)
                .content(contentResId)
                .positiveText(posTextResId)
                .onPositive(onPos)
                .show();
        return dialog;
    }


    public MaterialDialog showProgressBar(int titleResId, int contentResId){
        dialog= new MaterialDialog.Builder(this)
                .title(titleResId)
                .content(contentResId)
                .progress(true,0)
                .cancelable(false)
                .show();
        return dialog;
    }

    public void hideProgressBar(){
        if(dialog!=null&&dialog.isShowing())
            dialog.dismiss();
    }

    public void saveStringValue(String key,String value){
        SharedPreferences.Editor editor=
                getSharedPreferences("chatAppFile",MODE_PRIVATE)
                        .edit();
        editor.putString(key,value);
        editor.apply();

    }
    public String getStringValue(String key){
        SharedPreferences sharedPreferences= getSharedPreferences("chatAppFile",
                MODE_PRIVATE);
        return sharedPreferences.getString(key,null);
    }


    public void clearStringValue (String key){

        SharedPreferences.Editor editor =
                getSharedPreferences("chatAppFile", MODE_PRIVATE)
                        .edit();

        editor.remove(key);

        editor.apply();
    }
}
