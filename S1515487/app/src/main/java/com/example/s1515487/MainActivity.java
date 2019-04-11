package com.example.s1515487;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintJob;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;

import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.*;
import android.widget.*;

import org.me.gcu.s1515487.R;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView rawDataDisplay;
    private Button startButton;
    private String result;
    private String url1="";
    private String urlSource="http://quakes.bgs.ac.uk/feeds/MhSeismology.xml";
    private RelativeLayout mainLayout;
    private List<SmallLayout> responses = new ArrayList<>();

    public List<SmallLayout> getResponses() {
        return responses;
    }

    public void setResponses(List<SmallLayout> responses) {
        this.responses = new ArrayList<>(responses);
    }

    /**method that starts the trhead that gets the data - see task class
     *
     * @param context
     * @throws InterruptedException
     */

    public void startProgress(Context context) throws InterruptedException {
        // Run network access on a separate thread;
        Task t = new Task(urlSource,context);
        Thread thr =  new Thread(t);
        thr.start();
        while(thr.isAlive()){
            Thread.currentThread().sleep(500);
        }

        responses = new ArrayList<>(t.responses);
    }


    /**first method that it runs when the app starts
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            startProgress(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainLayout = (RelativeLayout) findViewById(R.id.mainInfo);

        ImageButton filterButton=(ImageButton) findViewById(R.id.openFilterButton);
        Button confirm = (Button) findViewById(R.id.confirmButton);
        Button reset = (Button) findViewById(R.id.resetFilters);
        reset.setOnClickListener(new ResetFilterListeners(responses,this));
        final EditText text = (EditText) findViewById(R.id.search_field);
        text.setOnKeyListener(new SearchListener(responses,this));
        text.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                text.setText("");
                return false;
            }
        });
        EditText between = (EditText) findViewById(R.id.betweenText);
        between.setOnTouchListener(new ResetGRoupSelection(this));
        EditText and = (EditText) findViewById(R.id.andText);
        and.setOnTouchListener(new ResetGRoupSelection(this));
        ImageButton search = (ImageButton) findViewById(R.id.searchButton);
        RadioGroup group = (RadioGroup) findViewById(R.id.radioGroup);

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                EditText between = (EditText) findViewById(R.id.betweenText);
                between.setText("dd/MM/YYYY");
                EditText and = (EditText) findViewById(R.id.andText);
                and.setText("dd/MM/YYYY");
            }
        });
        confirm.setOnClickListener(new ConfirmListener(responses,this));
        search.setOnClickListener(new SearchListener(responses,this));
        filterButton.setOnClickListener(new org.me.gcu.s1515487.ExpandFilterMenuListener(this));
        paintMainView(responses);

    }
    public void removeContentsOfScrollView(){
        mainLayout.removeAllViews();
    }
    /**
     * method that painst the mainView
     */
    public void paintMainView(List<SmallLayout> responses){
        int margin =150;
        System.out.println("The responses: "+responses.size());
        for(int i=0; i<responses.size(); i++){
            SmallLayout s = responses.get(i);
            mainLayout.addView(s,i);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)s.getLayoutParams();

            params.topMargin= (int) (margin*i)+ 5;
            s.setLayoutParams(params);
            Button button = (Button) ((SmallLayout) s).getButton();
            SmallLayout smallLayout = s;
            button.setOnClickListener(new ButtonListener(s,i,mainLayout));
        }
        mainLayout.invalidate();
    }
    /**class responsible for getting and passing the data to the view when the app starts
     *
     */

    public void resetFields(){

        EditText between = (EditText) findViewById(R.id.betweenText);
        between.setText("dd/MM/YYYY");
        EditText and = (EditText) findViewById(R.id.andText);
        and.setText("dd/MM/YYYY");
        RadioGroup group = (RadioGroup) findViewById(R.id.radioGroup);
        group.clearCheck();
    }

    public void popAlert() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Enter your date as dd/mm/yyyy using only numeric symbols");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private class Task implements Runnable
    {
        private String url;
        String response;
        Context context;

        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }

        private List<SmallLayout> responses = new ArrayList<>();
        public Task(String aurl, Context context)
        {
            url = aurl;
            this.context = context;
            response="";
        }

        @RequiresApi(api = Build.VERSION_CODES.FROYO)
        @Override
        public void run()
        {
            URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine = "";

            Log.e("MyTag","in run");
            try
            {
                Log.e("MyTag","in try");
                aurl = new URL(url);
                yc = aurl.openConnection();
                in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                //
                // Throw away the first 2 header lines before parsing
                //
                //
                //
                while ((inputLine = in.readLine()) != null)
                {
                    result = result + inputLine;
                    // Log.e("MyTag",inputLine);
                }
                in.close();
            }
            catch (IOException ae)
            {
                Log.e("MyTag", "ioexception");
            }
            //
            // Now that you have the xml data you can parse it

            try {
                printer();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
            // Now update the TextView to display raw XML data
            // Probably not the best way to update TextView
            // but we are just getting started !
            MainActivity.this.runOnUiThread(new Runnable()
            {
                public void run() {
                    Log.d("UI thread", "I am the UI thread");
                    response = response.replaceAll("null","");
                    //rawDataDisplay.setText(response);
                }
            });
        }



        @RequiresApi(api = Build.VERSION_CODES.FROYO)
        public void printer() throws IOException, SAXException, ParserConfigurationException {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder =  dbFactory.newDocumentBuilder();

            result=result.replaceAll("null","");
            InputSource source = new InputSource(new StringReader(result));
            Document doc = dBuilder.parse(source);

            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("item");



            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    String id = "view"+temp;
                    SmallLayout view = new SmallLayout(context,id);


                    Element eElement = (Element) nNode;
                    String overallResponse = eElement.getElementsByTagName("description").item(0).getTextContent();
                    view.setTitleValue(formatTitle(overallResponse));
                    view.setMagnitute(magnitude(overallResponse));
                    view.setDepth(depth(overallResponse));
                    view.setFullDate(fullDate(overallResponse));
                    view.setMoreInfo(eElement.getElementsByTagName("link").item(0).getTextContent());
                    view.setPubDate(eElement.getElementsByTagName("pubDate").item(0).getTextContent());
                    double lat = Double.parseDouble(eElement.getElementsByTagName("geo:lat").item(0).getTextContent());
                    double lon= Double.parseDouble(eElement.getElementsByTagName("geo:long").item(0).getTextContent());
                    view.setLongLat(lat,lon);
                   /* response+="Title : " + eElement.getElementsByTagName("title").item(0).getTextContent()+"\n";
                    response+="Description : " + eElement.getElementsByTagName("description").item(0).getTextContent()+"\n";
                    response+="Link : " + eElement.getElementsByTagName("link").item(0).getTextContent()+"\n";
                    response+="pubDate : " + eElement.getElementsByTagName("pubDate").item(0).getTextContent()+"\n";
                    response+="Category : " + eElement.getElementsByTagName("category").item(0).getTextContent()+"\n";
                    response+="geo:lat " + eElement.getElementsByTagName("geo:lat").item(0).getTextContent()+"\n";
                    response+="geo:long " + eElement.getElementsByTagName("geo:long").item(0).getTextContent()+"\n";*/
                    responses.add(view);
                }
                response+="============New entry===================\n\n";
            }
        }
        public String formatTitle(String overallResponse){
            String title="";
            String[] info = overallResponse.split(";");
            String date = info[0].replace("Origin date/time: ","");
            String city = info[1].replace("Location:","");
            String[] cityData = city.split(",");

            if(cityData.length==2){
                city = cityData[1];
            }else {
                city = cityData[0];
            }
            String[] smallDateData = date.split(" ");
            String smallDate = smallDateData[1]+" "+smallDateData[2]+" "+smallDateData[3];
            String lastTitle = city+" "+smallDate;
            title = lastTitle;
            return title;
        }

    }

    public String magnitude(String overallResponse){
        String[] info = overallResponse.split(";");
        String mag = info[4].replace("Magnitude: ","");
        return mag;
    }

    public String depth(String overallResponse){
        String[] info = overallResponse.split(";");
        String depth = info[3].replace("Depth: ","");
        return depth;
    }

    public String fullDate (String overallResponse){
        String[] info = overallResponse.split(";");
        String fulldate = info[0].replace("Origin date/time: ","");
        return fulldate;
    }



}
