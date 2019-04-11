package com.example.s1515487;

import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResetFilterListeners implements View.OnClickListener{

    private List<SmallLayout> responses;
    private MainActivity mainActivity;
    public ResetFilterListeners (List<SmallLayout> responses, MainActivity mainActivity){
        this.responses = new ArrayList<>(responses);
        this.mainActivity = mainActivity;
    }

    @Override
    public void onClick(View view) {
        mainActivity.removeContentsOfScrollView();
        for(SmallLayout smallLayout: responses){
            smallLayout.removeBackground();
        }
        mainActivity.paintMainView(responses);
        mainActivity.resetFields();
    }


}
