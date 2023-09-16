package com.TwentyOneCodes.madmathematics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

public class ReviewingQuestionsActivity extends AppCompatActivity {

    private Button nextQuestionButton, previousQuestionButton, goBackButton;
    private TextView theCurrentQuestion, theCurrentAnswer, getTheCurrentQuestionExplanation;

    private ArrayList<String> questionsArraylist;
    private ArrayList<String> finalAnswers;
    private ArrayList<String> questionHints;
    private int currentQuestion=0;

    AdView bannerAd;
    private Vibrator vibrator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviewing_questions);

        if(Build.VERSION.SDK_INT>=28){
            vibrator =(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        }
        try {
            //bannerId =5
            bannerAd = findViewById(R.id.bannerAdView);
            MobileAds.initialize(this);
            AdRequest adRequest = new AdRequest.Builder().build();
            bannerAd.loadAd(adRequest);
        }catch (Exception e){
            e.printStackTrace();
        }

        nextQuestionButton= (Button) findViewById(R.id.theNextQuestionButton);
        nextQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT>=28){
                    vibrator.vibrate(VibrationEffect.createOneShot(5, VibrationEffect.DEFAULT_AMPLITUDE));
                }
                currentQuestion++;
                nextQuestion();
            }
        });
        previousQuestionButton =(Button) findViewById(R.id.thePreviousQuestionButton);
        previousQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT>=28){
                    vibrator.vibrate(VibrationEffect.createOneShot(5, VibrationEffect.DEFAULT_AMPLITUDE));
                }
                currentQuestion--;
                previousQuestion();
            }
        });

        goBackButton =(Button) findViewById(R.id.goBackButton);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT>=28){
                    vibrator.vibrate(VibrationEffect.createOneShot(15, VibrationEffect.DEFAULT_AMPLITUDE));
                }
              onBackPressed();
            }
        });

        theCurrentAnswer =(TextView) findViewById(R.id.theCurrentAnswerTextView);
        theCurrentQuestion=(TextView) findViewById(R.id.theCurrentQuestionTextView);
        getTheCurrentQuestionExplanation =(TextView) findViewById(R.id.hint_notes_TextView);

        questionsArraylist= new ArrayList<>();
        finalAnswers = new ArrayList<>();
        questionHints = new ArrayList<>();
        Bundle dataBundle = getIntent().getExtras();
        questionsArraylist = dataBundle.getStringArrayList("ARRAY_LIST_OF_QUESTIONS");
        finalAnswers = dataBundle.getStringArrayList("ARRAYLIST_OF_ANSWERS");
        questionHints = dataBundle.getStringArrayList("ARRAYLIST_OF_HINTS");
        theCurrentQuestion.setText(questionsArraylist.get(currentQuestion));
        theCurrentAnswer.setText(finalAnswers.get(currentQuestion));
        getTheCurrentQuestionExplanation.setText(questionHints.get(currentQuestion));


    }

    private void nextQuestion() {
        if (currentQuestion >= finalAnswers.size()) {
            currentQuestion=0;
        } else if(currentQuestion<0){
            currentQuestion=finalAnswers.size()-1;
        }

        theCurrentQuestion.setText(questionsArraylist.get(currentQuestion));
        theCurrentAnswer.setText(finalAnswers.get(currentQuestion));
        getTheCurrentQuestionExplanation.setText(questionHints.get(currentQuestion));

    }

    private void previousQuestion(){
        if (currentQuestion >= finalAnswers.size()) {
            currentQuestion=0;
        } else if(currentQuestion<0){
            currentQuestion=finalAnswers.size()-1;
        }

        theCurrentQuestion.setText(questionsArraylist.get(currentQuestion));
        theCurrentAnswer.setText(finalAnswers.get(currentQuestion));
        getTheCurrentQuestionExplanation.setText(questionHints.get(currentQuestion));
    }
}