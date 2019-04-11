package com.example.s1515487;

import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.s1515487.MainActivity;

import org.me.gcu.s1515487.R;

public class ResetGRoupSelection implements View.OnTouchListener {
    private MainActivity activity;

    public ResetGRoupSelection(MainActivity activity){
        this.activity = activity;
    }




    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        EditText text = (EditText) view;
        text.setText("");
        RadioGroup group = (RadioGroup) activity.findViewById(R.id.radioGroup);
        if(group.getCheckedRadioButtonId()!=-1) {
            group.clearCheck();
        }

        return false;
    }
}
