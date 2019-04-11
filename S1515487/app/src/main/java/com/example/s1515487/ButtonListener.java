package com.example.s1515487;

import android.view.View;
import android.widget.RelativeLayout;

public class ButtonListener implements View.OnClickListener {
    private SmallLayout layout ;
    private RelativeLayout topClass;
    private int index;
    public ButtonListener(SmallLayout layout,int index,RelativeLayout topClass){
        this.layout = layout;
        this.topClass = topClass;
        this.index = index;
    }

    @Override
    public void onClick(View view) {
        if(!layout.isOpen()){
            layout.open();
            push(360);
        }else{
            layout.close();
            push(-360);
        }
    }

    public void push(int amount){
        System.out.println(index);
        int childCount = topClass.getChildCount();
        for(int i=index+1; i<childCount; i++){
            View view = topClass.getChildAt(i);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)view.getLayoutParams();

            params.topMargin= params.topMargin+amount;
            view.setLayoutParams(params);
        }
    }
}
