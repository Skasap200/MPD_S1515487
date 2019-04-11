package com.example.s1515487;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SmallLayout  extends RelativeLayout implements Comparable {
    private Button button ;
    private org.me.gcu.s1515487.ExtendedLayout extendedLayout;
    private boolean isOpen;
    private String whatToSort;
    private String depth;
    private String magnitute;
    private String date;
    private String title;
    public SmallLayout(Context context,String id) {
        super(context);
        extendedLayout = new org.me.gcu.s1515487.ExtendedLayout(this.getContext());
        isOpen=false;
        whatToSort = "";
        date="";
        depth="0";
        magnitute="0.0";
        title="";
        init(id);
    }

    public void removeBackground(){
        this.setBackgroundColor(Color.WHITE);
    }
    public void paintBackground(){
        this.setBackgroundColor(Color.GREEN);
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWhatToSort() {
        return whatToSort;
    }

    public void setWhatToSort(String whatToSort) {
        this.whatToSort = whatToSort;
    }

    public void setDateValue(String date){

    }

    public void setTitleValue(String title){
        this.title = title;
        TextView titleView = (TextView) findViewById(R.id.textView7);
        titleView.setText(title);
    }
    public void setMagnitute(String magnitute){
        TextView magView = (TextView) findViewById(R.id.textView9);
        magView.setText(magnitute);
        this.magnitute=magnitute;
    }
    public void setDepth(String depth){
        TextView depView = (TextView) findViewById(R.id.textView11);
        depView.setText(depth);
        this.depth=depth.replace(" km","");
    }

    public void setFullDate (String fullDate){
        date = fullDate.split(", ")[1];
        extendedLayout.setInfoValue(fullDate);
    }
    public void setPubDate(String pubDate) {
        extendedLayout.setPubDate(pubDate);
    }

    public void setMoreInfo(String info){
        extendedLayout.setlinkValue(info);
    }


    public Button getButton() {
        return button;
    }

    public org.me.gcu.s1515487.ExtendedLayout getExtendedLayout() {
        return extendedLayout;
    }

    public void setExtendedLayout(org.me.gcu.s1515487.ExtendedLayout extendedLayout) {
        this.extendedLayout = extendedLayout;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public void init(String id){
        inflate(getContext(),R.layout.small_layout,this);

        this.addView(extendedLayout);
        this.setId(id.hashCode());
        button =(Button) findViewById(R.id.imageButton3);
        button.setId(id.hashCode()-1);
    }

    public void open(){
        isOpen = true;
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.allDataLayout);
        ViewGroup.LayoutParams params = layout.getLayoutParams();
        params.height=500;
        layout.setLayoutParams(params);
    }

    public void close(){
        isOpen = false;
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.allDataLayout);
        ViewGroup.LayoutParams params = layout.getLayoutParams();
        params.height=0;
        layout.setLayoutParams(params);
    }

    public String getDepth() {
        return depth;
    }

    public String getMagnitute() {
        return magnitute;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        SmallLayout s = (SmallLayout) o;
        if(whatToSort.equals("depth")){
            Double depthD = Double.parseDouble(depth);
            Double depthO = Double.parseDouble(s.getDepth());
            return depthD.compareTo(depthO);
        }else if(whatToSort.equals("magnitute")){
            Double magniD = Double.parseDouble(magnitute);
            Double magniO = Double.parseDouble(s.getMagnitute());
            return magniD.compareTo(magniO);
        }else if(whatToSort.equals("date")){
            SimpleDateFormat dateDFormatter= new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
            Date dateD = null;
            Date dateS = null;
            try {
                dateD = dateDFormatter.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                dateS = dateDFormatter.parse(s.getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return dateD.compareTo(dateS);
        }
        return 0;
    }
    private String mapMonthToNumber(String month){
        String number="";
        switch (month.toUpperCase()){
            case "JAN":
                number= "01";
                break;
            case "FEB":
                number= "02";
                break;
            case "MAR":
                number= "03";
                break;
            case "APR":
                number= "04";
                break;
            case "MAY":
                number= "05";
                break;
            case "JUN":
                number= "06";
                break;
            case "JUL":
                number= "07";
                break;
            case "AUG":
                number= "08";
                break;
            case "SEP":
                number= "09";
                break;
            case "OCT":
                number= "10";
                break;
            case "NOV":
                number= "11";
                break;

            case "DEC":
                number= "12";
                break;

        }
        return number;
    }
    public String getSearchableDate(){
        String[] tokens = date.split(" ");

        String date = tokens[0]+"/"+mapMonthToNumber(tokens[1])+"/"+tokens[2];

        return date;
    }
    @Override
    public boolean equals(Object o){
        SmallLayout s = (SmallLayout) o;
        SimpleDateFormat dateDFormatter= new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
        Date dateD = null;
        Date dateS = null;
        try {
            dateD = dateDFormatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            dateS = dateDFormatter.parse(s.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateD.equals(dateS);
    }


    public void setLongLat(double lat, double lon) {
        extendedLayout.setLongLat(lat,lon);
    }
}
