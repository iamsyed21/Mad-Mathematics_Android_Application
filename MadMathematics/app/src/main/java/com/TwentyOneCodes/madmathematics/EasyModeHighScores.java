package com.TwentyOneCodes.madmathematics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import java.util.ArrayList;


public class EasyModeHighScores extends AppCompatActivity {

    private TextView pageDifficultyTextView, playAtLeastThree, totalAttemptsTextView,
            highestScoreTextView, averageScoreTextView, leastTimeTakenTextView, averagetimetakenTextView;
    private RecyclerView scoresRecyclerView;
    private RecyclerView.Adapter easyAdapter;
    private RecyclerView.LayoutManager easyLayoutManager;
    DataBaseHelper myDB;
    private ArrayList<HighScores> easyScoresArrayList;
    private ArrayList<HighScores> UnorderedEasyScoresArrayList;
    private ArrayList<String> graphScores;
    private ArrayList<String> graphTimes;
    private LineChart graphTracker;
    int queryType =1;
    int UnorderedQueryType=4;
    AdView bannerAd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_mode_high_scores);
        myDB = new DataBaseHelper(this);
        Intent intent = getIntent();
        pageDifficultyTextView = (TextView) findViewById(R.id.pageDifficultyTextView);

        try {
            //bannerId =1
            bannerAd = findViewById(R.id.bannerAdView);
            MobileAds.initialize(this);
            AdRequest adRequest = new AdRequest.Builder().build();
            bannerAd.loadAd(adRequest);
        }catch (Exception e){
            e.printStackTrace();
        }





        String pageType;
        if(intent !=  null && intent.getStringExtra("TYPE_OF_SCORES") !=null ){
            pageType= intent.getStringExtra("TYPE_OF_SCORES");
        }else{
            pageType = "EASY";
        }

        if(pageType.equals("Easy")){
            queryType =1;
            UnorderedQueryType=4;
        }else if(pageType.equals("INTER")){
            queryType=2;
            UnorderedQueryType=5;
            pageDifficultyTextView.setText("INTERMEDIATE MODE");

        }else if(pageType.equals("HARD")){
            queryType=3;
            UnorderedQueryType=6;
            pageDifficultyTextView.setText("HARD MODE");
        }


        easyScoresArrayList = new ArrayList<>();
        UnorderedEasyScoresArrayList = new ArrayList<>();
        graphScores = new ArrayList<>();
        graphTimes = new ArrayList<>();


        scoresRecyclerView = (RecyclerView) findViewById(R.id.scoresRecyclerView);
        graphTracker = (LineChart) findViewById(R.id.trackerGraph);
        totalAttemptsTextView =(TextView) findViewById(R.id.totalAttemptsTextView);
        highestScoreTextView =(TextView) findViewById(R.id.highestScoreTextView);
        averageScoreTextView =(TextView) findViewById(R.id.averageScoreTextView);
        leastTimeTakenTextView=(TextView) findViewById(R.id.leastTimeTakenTextView);
        averagetimetakenTextView=(TextView) findViewById(R.id.averagetimetakenTextView);
        playAtLeastThree = (TextView) findViewById(R.id.playAtLeastThree);

        graphTracker.setTouchEnabled(true);
        graphTracker.setPinchZoom(true);
        graphTracker.getDescription().setEnabled(true);
        Description desc = new Description();
        desc.setText("Progress>");
        desc.setTextSize(15f);
        desc.setTextColor(Color.BLACK);
        graphTracker.getDescription().setPosition(0f, 0f);
        graphTracker.setDescription(desc);
        graphTracker.setAlpha(0.1f);

        getScoresFromDataBase();
        String HighestScore;
        String LeastTime;

        if (easyScoresArrayList.isEmpty()) {
            ArrayList<HighScores> emptyList = new ArrayList<>();
            emptyList.add(new HighScores(1, "No Scores Available", 1, "No Time Available."));
            easyLayoutManager = new LinearLayoutManager(this);
            easyAdapter = new scoreAdaptor(emptyList);
            HighestScore="0";
            LeastTime ="0";
        } else {
            easyLayoutManager = new LinearLayoutManager(this);
            easyAdapter = new scoreAdaptor(easyScoresArrayList);
            HighestScore = easyScoresArrayList.get(0).getScore();
            LeastTime = easyScoresArrayList.get(easyScoresArrayList.size()-1).getTimeTaken();
        }
        scoresRecyclerView.setLayoutManager(easyLayoutManager);
        scoresRecyclerView.setAdapter(easyAdapter);
        scoresRecyclerView.setHasFixedSize(true);
        scoresRecyclerView.setItemViewCacheSize(15);
        graphTracker.animateX(1000);
        graphTracker.setScaleXEnabled(true);
        YAxis y = graphTracker.getAxisLeft();
        y.removeAllLimitLines();

        if (UnorderedEasyScoresArrayList.size() > 2) {
            playAtLeastThree.setVisibility(View.GONE);
            graphTracker.setAlpha(1.0f);

        }

        if (UnorderedEasyScoresArrayList.size() != 0) {
            for (int i = 0; i < UnorderedEasyScoresArrayList.size(); i++) {
                graphScores.add(UnorderedEasyScoresArrayList.get(i).getScore());
                graphTimes.add(UnorderedEasyScoresArrayList.get(i).getTimeTaken());
            }
        }

        if(UnorderedEasyScoresArrayList.size() > 2) {
            XAxis x = graphTracker.getXAxis();
            x.setTextSize(10f);
            x.removeAllLimitLines();
            x.setGranularity(1f);
            ArrayList<String> xLabels = new ArrayList<>();
            for (int i = 0; i < UnorderedEasyScoresArrayList.size(); i++) {
                xLabels.add(Integer.toString(i + 1));
            }
            x.setValueFormatter(new IndexAxisValueFormatter(xLabels));
            setGraphData();
        }else{
            setDefaultGraphData();
        }

        int totalAttempts = UnorderedEasyScoresArrayList.size();
        totalAttemptsTextView.setText(Integer.toString(totalAttempts));
        highestScoreTextView.setText(HighestScore);
        int averageScores = getAverageScores();
        averageScoreTextView.setText(Integer.toString(averageScores));
        leastTimeTakenTextView.setText(LeastTime);
        int averageTimeTaken = getAverageTime();
        averagetimetakenTextView.setText(Integer.toString(averageTimeTaken));
    }

    private int getAverageTime() {
        if(graphTimes.size()==0) return 0;
        int Sum =0;
        for(int i=0;i<graphTimes.size();i++){
            Sum += Integer.parseInt(graphTimes.get(i));
        }

        return Sum/graphTimes.size();
    }

    private int getAverageScores() {
        if(graphScores.size()==0) return 0;
        int Sum =0;
        for(int i=0;i<graphScores.size();i++){
            Sum += Integer.parseInt(graphScores.get(i));
        }

        return Sum/graphScores.size();

    }

    private void setDefaultGraphData() {
        ArrayList<Entry> yScores = new ArrayList<>();
        ArrayList<Entry> yTimes = new ArrayList<>();

       yScores.add(new Entry(1, 50f));
        yScores.add(new Entry(2, 150f));
        yScores.add(new Entry(3, 250f));
        yScores.add(new Entry(4, 40f));
        yScores.add(new Entry(5, 10f));
        yScores.add(new Entry(6, 150f));


        yTimes.add(new Entry(1, 10f));
        yTimes.add(new Entry(2, 20f));
        yTimes.add(new Entry(3, 30f));
        yTimes.add(new Entry(4, 40f));
        yTimes.add(new Entry(5, 50f));
        yTimes.add(new Entry(6, 60f));
        LineDataSet set1, set2;

        set1 = new LineDataSet(yScores, "Game Scores");
        set1.setColor(Color.BLACK);
        set2 = new LineDataSet(yTimes, "Time Taken Trend in Seconds");
        LineData data = new LineData(set1, set2);
        graphTracker.setData(data);

    }


    private void setGraphData() {
        ArrayList<Entry> yScores = new ArrayList<>();
        ArrayList<Entry> yTimes = new ArrayList<>();

        for(int i=0; i<graphScores.size() && i<graphTimes.size() ;i++){
            yScores.add(new Entry(i, Float.parseFloat(graphScores.get(i))));
            yTimes.add(new Entry(i, Float.parseFloat(graphTimes.get(i))));
        }

        LineDataSet set1, set2;

        set1 = new LineDataSet(yScores, "Game Scores");
        set1.setDrawCircles(true);
        set1.setValueTextSize(05f);
        set1.setValueTextColor(R.color.BackgroundBlue);
        set1.setCircleColor(Color.RED);
        set1.setFormSize(20f);
        set1.setLineWidth(2f);
        set1.setDrawCircleHole(true);
        set1.setColor(Color.BLACK);
        set1.setCircleRadius(4f);
        set1.setCircleHoleRadius(2f);
        set1.setDrawCircleHole(true);
        set1.setCircleHoleColor(R.color.white);
        set1.setDrawFilled(true);
        set1.setFillAlpha(25);
        set1.setFillColor(R.color.white);



        set2 = new LineDataSet(yTimes, "Time in Seconds");
        set2.setColor(Color.WHITE);
        set2.setValueTextSize(0f);
        set2.setLineWidth(2f);
        set2.enableDashedLine(10f, 15f, 0f);
        set2.setFormSize(20f);
        set2.setDrawCircles(false);
        set2.setValueTextColor(R.color.ComponentRed);



        LineData data = new LineData(set1, set2);
        graphTracker.setData(data);
    }


    private void getScoresFromDataBase() {
        try {
            Cursor easyData = myDB.getScores(queryType);
            if (easyData.getCount() == 0) {
                Toast.makeText(this, "No Data is Available!", Toast.LENGTH_SHORT).show();
            } else {
                while (easyData.moveToNext()) {
                    easyScoresArrayList.add(new HighScores(easyData.getInt(0), easyData.getString(1), queryType, easyData.getString(2)));
                }
            }


                Cursor unOrderedEasyData = myDB.getScores(UnorderedQueryType);
                if (unOrderedEasyData.getCount() == 0) {
                    Toast.makeText(this, "No Data is Available!", Toast.LENGTH_SHORT).show();
                } else {
                    while (unOrderedEasyData.moveToNext()) {
                        UnorderedEasyScoresArrayList.add(new HighScores(unOrderedEasyData.getInt(0), unOrderedEasyData.getString(1), UnorderedQueryType, unOrderedEasyData.getString(2)));
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

