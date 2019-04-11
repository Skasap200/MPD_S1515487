package com.example.s1515487;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ConfirmListener implements View.OnClickListener  {
    private List<SmallLayout> responses;
    private MainActivity mainActivity;
    public ConfirmListener(List<SmallLayout> responses,MainActivity mainActivity){
        this.responses = new ArrayList<>(responses);
        this.mainActivity = mainActivity;
    }
    @Override
    public void onClick(View view) {
        for(SmallLayout s: responses){
            s.removeBackground();
        }
        RadioGroup group = mainActivity.findViewById(R.id.radioGroup);
        EditText between = (EditText) mainActivity.findViewById(R.id.betweenText);
        EditText and = (EditText) mainActivity.findViewById(R.id.andText);
        if (between.getText().toString().equals("dd/MM/YYYY") || and.getText().toString().equals("dd/MM/YYYY")) {
            int id = group.getCheckedRadioButtonId();
            if (id == R.id.magnituteCheckBox) {
                setIdentifier("magnitute");
                Collections.sort(responses);
                mainActivity.removeContentsOfScrollView();
                mainActivity.paintMainView(responses);
            } else if (id == R.id.dateCheckBox) {
                setIdentifier("date");
                Collections.sort(responses);
                mainActivity.removeContentsOfScrollView();
                mainActivity.paintMainView(responses);
            } else if (id == R.id.depthCheckBox) {
                setIdentifier("depth");
                Collections.sort(responses);
                mainActivity.removeContentsOfScrollView();
                mainActivity.paintMainView(responses);
            }
        }else {
            // between session
            String andText = and.getText().toString();
            String betweenText = between.getText().toString();

            if(isDateValid(andText)&& isDateValid(betweenText)) {
                try {
                    search(andText,betweenText);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }else{
                mainActivity.popAlert();
            }
        }
    }
    private boolean isDateValid(String date){
        String [] tokens=date.split("/");
        if(tokens.length==3){
            int numbersFound=0;
            for(String string:tokens){
                boolean isNumber = android.text.TextUtils.isDigitsOnly(string);
                if(isNumber){
                    numbersFound++;
                }
            }
            if(numbersFound==3){
                return true;
            }
        }

        return false;
    }
    private void search(String andText, String betweenText) throws ParseException {
        List<SmallLayout> responsesFound = new ArrayList<>();
        EditText searchField = mainActivity.findViewById(R.id.search_field);
        if (!searchField.getText().toString().equals("")) {
            System.out.println("Searching....");
            for (SmallLayout s : responses) {
                SimpleDateFormat dateDFormatter= new SimpleDateFormat("dd/MM/yyyy");
                Date between = dateDFormatter.parse(betweenText);

                Date and = dateDFormatter.parse(andText);

                Date actuallDate = dateDFormatter.parse(s.getSearchableDate());
                if (actuallDate.after(between) && actuallDate.before(and)) {
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
    private void setIdentifier(String identifier){
        for(SmallLayout smallLayout:responses){
            smallLayout.setWhatToSort(identifier);
        }
    }
}
