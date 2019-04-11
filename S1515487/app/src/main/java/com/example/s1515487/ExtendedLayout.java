package org.me.gcu.s1515487;

import android.content.Context;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ExtendedLayout extends RelativeLayout {

    public ExtendedLayout(Context context) {
        super(context);
        init();
    }

    public void init(){
        inflate(getContext(),R.layout.extended,this);
    }

    public void setInfoValue(String info){
        TextView fullDView = (TextView) findViewById(R.id.textView16);
        fullDView.setText(info);
    }

    public void setlinkValue(String info){
        TextView linkView = (TextView) findViewById(R.id.textView17);
        linkView.setText(info);
    }

    public void setPubDate(String pubDate){
        TextView pubView = (TextView) findViewById(R.id.textView18);
        pubView.setText(pubDate);
    }




    public void setLongLat(double lat, double lon) {
        TextView text = (TextView) findViewById(R.id.longLat);
        text.setText(lat+", "+lon);
    }
}
