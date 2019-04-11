package org.me.gcu.s1515487;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class ExpandFilterMenuListener implements View.OnClickListener {
    private AppCompatActivity app;
    public ExpandFilterMenuListener(AppCompatActivity app) {
        this.app = app;
    }

    @Override
    public void onClick(View view) {
        RelativeLayout filterLayout =  app.findViewById(R.id.filterLayout);
        ScrollView scrollView = (ScrollView) app.findViewById(R.id.scrollView);
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)filterLayout.getLayoutParams();

        if(params.height==0){
            params.height=400;
            filterLayout.setLayoutParams(params);
            ConstraintLayout.LayoutParams paramiters = (ConstraintLayout.LayoutParams) scrollView.getLayoutParams();
            paramiters.topMargin = paramiters.topMargin+400;
        }else{
            params.height=0;
            filterLayout.setLayoutParams(params);
            ConstraintLayout.LayoutParams paramiters = (ConstraintLayout.LayoutParams) scrollView.getLayoutParams();
            paramiters.topMargin = paramiters.topMargin-400;
        }
    }
}

