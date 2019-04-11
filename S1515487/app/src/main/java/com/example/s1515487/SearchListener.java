package com.example.s1515487;

import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.me.gcu.s1515487.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SearchListener implements View.OnClickListener, View.OnKeyListener {
    private List<SmallLayout> responses;
    private MainActivity mainActivity;

    public SearchListener(List<SmallLayout> responses, MainActivity mainActivity) {
        this.responses = new ArrayList<>(responses);
        this.mainActivity = mainActivity;
    }
    private void search(){
        List<SmallLayout> responsesFound = new ArrayList<>();
        EditText searchField = mainActivity.findViewById(R.id.search_field);
        if (!searchField.getText().toString().equals("")) {
            System.out.println("Searching....");
            for (SmallLayout s : responses) {
                if (s.getTitle().toLowerCase().contains(searchField.getText().toString().toLowerCase())) {
                    s.paintBackground();
                    responsesFound.add(s);
                }
            }

            for (SmallLayout s : responses) {
                if (!responsesFound.contains(s)) {
                    responsesFound.add(s);
                }
            }


            mainActivity.removeContentsOfScrollView();
            mainActivity.paintMainView(responsesFound);
        }
    }
    @Override
    public void onClick(View view) {
        search();
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if(keyEvent.getAction()==KeyEvent.ACTION_DOWN) {
            if (i == keyEvent.KEYCODE_ENTER) {
                search();
            }
        }
        return false;
    }
}
