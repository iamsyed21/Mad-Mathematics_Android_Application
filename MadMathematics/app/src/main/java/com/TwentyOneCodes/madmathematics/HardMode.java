package com.TwentyOneCodes.madmathematics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class HardMode extends AppCompatActivity {

    private TextView attemptedTextView, timerTextView, questionTextView, hint_notes_TextView;
    private Button optionA_Button, optionB_Button, optionC_Button, optionD_Button, optionE_Button, optionF_Button, skipThisQuestionButton;
    private int questionCounter =1;
    private StringBuilder questionString;
    private StringBuilder questionHintString;
    private CountDownTimer theTimer;
    private double theDoubleAnswer;
    private int theAnswer;
    private ArrayList<Integer> options;
    private ArrayList<Double> doubleOptions;
    private int theCorrectOption;
    private int theScore;
    private int skippedQuestions=0;
    private int wrongAnswers=0;
    private String timeTaken;
    private ArrayList<String> questionsArraylist;
    private ArrayList<String> finalAnswers;
    private ArrayList<String> finalHints;
    private ProgressBar timerProgressBar;
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hard_mode);
        HardQuestionGenerator generate = new HardQuestionGenerator();

        questionsArraylist= new ArrayList<>();
        finalAnswers = new ArrayList<>();
        finalHints = new ArrayList<>();
        questionString = generate.finalQuestion;
        if(generate.questionType == 0) {
            options = generate.options;
            theAnswer = generate.theAnswer;
        }else{
            theDoubleAnswer = generate.theDoubleAnswer;
            doubleOptions = generate.doubleOptions;
        }
        questionHintString = generate.questionHint;
        theScore =0;

        if(Build.VERSION.SDK_INT>=28){
            vibrator =(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        }

        questionTextView = (TextView) findViewById(R.id.theQuestionTextview);
        questionTextView.setText(questionString);
        attemptedTextView =(TextView) findViewById(R.id.attemptedQuestionsTextView);
        attemptedTextView.setText("1/15");
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        timerTextView.setText("150s");
        timerProgressBar =(ProgressBar) findViewById(R.id.timerProgressBar);

        optionA_Button = (Button) findViewById(R.id.optionA_Button);
        optionA_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT>=28){
                    vibrator.vibrate(VibrationEffect.createOneShot(15, VibrationEffect.DEFAULT_AMPLITUDE));
                }
                optionSelected(1);
            }
        });

        optionB_Button = (Button) findViewById(R.id.optionB_Button);
        optionB_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT>=28){
                    vibrator.vibrate(VibrationEffect.createOneShot(15, VibrationEffect.DEFAULT_AMPLITUDE));
                }
                optionSelected(2);
            }
        });

        optionC_Button = (Button) findViewById(R.id.optionC_Button);
        optionC_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT>=28){
                    vibrator.vibrate(VibrationEffect.createOneShot(15, VibrationEffect.DEFAULT_AMPLITUDE));
                }
                optionSelected(3);
            }
        });

        optionD_Button = (Button) findViewById(R.id.optionD_Button);
        optionD_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT>=28){
                    vibrator.vibrate(VibrationEffect.createOneShot(15, VibrationEffect.DEFAULT_AMPLITUDE));
                }
                optionSelected(4);
            }
        });

        optionE_Button =(Button) findViewById(R.id.optionE_Button);
        optionE_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT>=28){
                    vibrator.vibrate(VibrationEffect.createOneShot(15, VibrationEffect.DEFAULT_AMPLITUDE));
                }
                optionSelected(5);
            }
        });

        optionF_Button = (Button) findViewById(R.id.optionF_Button);
        optionF_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT>=28){
                    vibrator.vibrate(VibrationEffect.createOneShot(15, VibrationEffect.DEFAULT_AMPLITUDE));
                }
                optionSelected(6);
            }
        });

        skipThisQuestionButton =(Button) findViewById(R.id.skipThisButton);
        skipThisQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT>=28){
                    vibrator.vibrate(VibrationEffect.createOneShot(15, VibrationEffect.DEFAULT_AMPLITUDE));
                }
                optionSelected(7);
            }
        });



        theTimer = new CountDownTimer(150100, 1000){

            @Override
            public void onTick(long l) {
                timerTextView.setText(l/1000+"s");
                long time = 100 - (l/1500);
                timerProgressBar.setProgress((int) time);
                if(questionCounter > 15) {
                    int timetaken = 150- ((int)l/1000);
                    timeTaken = Integer.toString(timetaken);
                    theTimer.cancel();
                    theTimer = null;
                    if(Build.VERSION.SDK_INT>=28){
                        vibrator.vibrate(VibrationEffect.createOneShot(15, VibrationEffect.DEFAULT_AMPLITUDE));
                    }
                    openResultsPage();
                }
            }

            @Override
            public void onFinish() {
                timerTextView.setText("00");
                cancel();
                timeTaken = new String("150");
                if(Build.VERSION.SDK_INT>=28){
                    vibrator.vibrate(VibrationEffect.createOneShot(15, VibrationEffect.DEFAULT_AMPLITUDE));
                }
                openResultsPage();
            }
        }.start();
        hint_notes_TextView = (TextView) findViewById(R.id.hint_notes_TextView);
        hint_notes_TextView.setText(questionHintString.toString());

        if(generate.questionType == 0) {
            OptionAssigner(options, theAnswer);
        }else{
            OptionAssigner(doubleOptions, theDoubleAnswer);
        }

        //End of onCreateOka
    }

    private void optionSelected(int option){
        if(option == theCorrectOption){
            theScore++;
        }else if(option == 7){
            skippedQuestions++;
        }else{
            wrongAnswers++;
        }

        HardQuestionGenerator obj = new HardQuestionGenerator();
        questionString = new StringBuilder(obj.finalQuestion);
        questionHintString = new StringBuilder(obj.questionHint);
        if(questionCounter <= 15) {
            if (questionCounter == 15) {
                questionTextView.setText("Calculating Results!");
                questionCounter++;
                hint_notes_TextView.setText("Calculating Results!");
            }else{
                if (obj.questionType == 0) {
                    questionCounter++;
                    attemptedTextView.setText(questionCounter + "/15");
                    questionTextView.setText(Integer.toString(questionCounter) + ". " + questionString);
                    OptionAssigner(obj.options, obj.theAnswer);
                } else {
                    questionCounter++;
                    attemptedTextView.setText(questionCounter + "/15");
                    questionTextView.setText(Integer.toString(questionCounter) + ". " + questionString);
                    hint_notes_TextView.setText(questionHintString);
                    OptionAssigner(obj.doubleOptions, obj.theDoubleAnswer);
                }
            }
        }

    }

    private void OptionAssigner(ArrayList<Double> options, double theAnswer){
        questionsArraylist.add(String.valueOf(questionString));
        finalAnswers.add(String.valueOf(theAnswer));
        int randomPlace = (int) ((Math.random()*6)+1);
        theCorrectOption = randomPlace;
        finalHints.add(questionHintString.toString());

        switch(theCorrectOption){
            case 1:
                optionA_Button.setText(Double.toString(theAnswer));
                optionB_Button.setText(Double.toString(options.get(0)));
                optionC_Button.setText(Double.toString(options.get(1)));
                optionD_Button.setText(Double.toString(options.get(2)));
                optionE_Button.setText(Double.toString(options.get(3)));
                optionF_Button.setText(Double.toString(options.get(4)));
                break;
            case 2:
                optionA_Button.setText(Double.toString(options.get(0)));
                optionB_Button.setText(Double.toString(theAnswer));
                optionC_Button.setText(Double.toString(options.get(1)));
                optionD_Button.setText(Double.toString(options.get(2)));
                optionE_Button.setText(Double.toString(options.get(3)));
                optionF_Button.setText(Double.toString(options.get(4)));
                break;
            case 3:
                optionA_Button.setText(Double.toString(options.get(0)));
                optionB_Button.setText(Double.toString(options.get(1)));
                optionC_Button.setText(Double.toString(theAnswer));
                optionD_Button.setText(Double.toString(options.get(2)));
                optionE_Button.setText(Double.toString(options.get(3)));
                optionF_Button.setText(Double.toString(options.get(4)));
                break;
            case 4:
                optionA_Button.setText(Double.toString(options.get(0)));
                optionB_Button.setText(Double.toString(options.get(1)));
                optionC_Button.setText(Double.toString(options.get(2)));
                optionD_Button.setText(Double.toString(theAnswer));
                optionE_Button.setText(Double.toString(options.get(3)));
                optionF_Button.setText(Double.toString(options.get(4)));
                break;

            case 5:
                optionA_Button.setText(Double.toString(options.get(0)));
                optionB_Button.setText(Double.toString(options.get(1)));
                optionC_Button.setText(Double.toString(options.get(2)));
                optionD_Button.setText(Double.toString(options.get(3)));
                optionE_Button.setText(Double.toString(theAnswer));
                optionF_Button.setText(Double.toString(options.get(4)));
                break;

            case 6:
                optionA_Button.setText(Double.toString(options.get(0)));
                optionB_Button.setText(Double.toString(options.get(1)));
                optionC_Button.setText(Double.toString(options.get(2)));
                optionD_Button.setText(Double.toString(options.get(3)));
                optionE_Button.setText(Double.toString(options.get(4)));
                optionF_Button.setText(Double.toString(theAnswer));
                break;
        }

    }

    private void OptionAssigner(ArrayList<Integer> options, int theAnswer){
        questionsArraylist.add(String.valueOf(questionString));
        finalAnswers.add(String.valueOf(theAnswer));
        int randomPlace = (int) ((Math.random()*6)+1);
        theCorrectOption = randomPlace;
        finalHints.add(questionHintString.toString());

        switch(theCorrectOption){
            case 1:
                optionA_Button.setText(Integer.toString(theAnswer));
                optionB_Button.setText(Integer.toString(options.get(0)));
                optionC_Button.setText(Integer.toString(options.get(1)));
                optionD_Button.setText(Integer.toString(options.get(2)));
                optionE_Button.setText(Integer.toString(options.get(3)));
                optionF_Button.setText(Integer.toString(options.get(4)));
                break;
            case 2:
                optionA_Button.setText(Integer.toString(options.get(0)));
                optionB_Button.setText(Integer.toString(theAnswer));
                optionC_Button.setText(Integer.toString(options.get(1)));
                optionD_Button.setText(Integer.toString(options.get(2)));
                optionE_Button.setText(Integer.toString(options.get(3)));
                optionF_Button.setText(Integer.toString(options.get(4)));
                break;
            case 3:
                optionA_Button.setText(Integer.toString(options.get(0)));
                optionB_Button.setText(Integer.toString(options.get(1)));
                optionC_Button.setText(Integer.toString(theAnswer));
                optionD_Button.setText(Integer.toString(options.get(2)));
                optionE_Button.setText(Integer.toString(options.get(3)));
                optionF_Button.setText(Integer.toString(options.get(4)));
                break;
            case 4:
                optionA_Button.setText(Integer.toString(options.get(0)));
                optionB_Button.setText(Integer.toString(options.get(1)));
                optionC_Button.setText(Integer.toString(options.get(2)));
                optionD_Button.setText(Integer.toString(theAnswer));
                optionE_Button.setText(Integer.toString(options.get(3)));
                optionF_Button.setText(Integer.toString(options.get(4)));
                break;
            case 5:
                optionA_Button.setText(Integer.toString(options.get(0)));
                optionB_Button.setText(Integer.toString(options.get(1)));
                optionC_Button.setText(Integer.toString(options.get(2)));
                optionD_Button.setText(Integer.toString(options.get(3)));
                optionE_Button.setText(Integer.toString(theAnswer));
                optionF_Button.setText(Integer.toString(options.get(4)));
                break;
            case 6:
                optionA_Button.setText(Integer.toString(options.get(0)));
                optionB_Button.setText(Integer.toString(options.get(1)));
                optionC_Button.setText(Integer.toString(options.get(2)));
                optionD_Button.setText(Integer.toString(options.get(3)));
                optionE_Button.setText(Integer.toString(options.get(4)));
                optionF_Button.setText(Integer.toString(theAnswer));
                break;
        }

    }

    private void openResultsPage(){
        Intent openResultsPageIntent = new Intent(this, ResultPage.class);
        Bundle dataBundle = new Bundle();
        dataBundle.putString("THE_TIME_TAKEN", timeTaken);
        dataBundle.putInt("THE_SCORE", theScore);
        dataBundle.putInt("SKIPPED_QUESTIONS", skippedQuestions);
        dataBundle.putInt("WRONG_ANSWERS", wrongAnswers);
        dataBundle.putStringArrayList("ARRAY_LIST_OF_QUESTIONS", questionsArraylist);
        dataBundle.putStringArrayList("ARRAYLIST_OF_HINTS", finalHints);
        dataBundle.putStringArrayList("ARRAYLIST_OF_ANSWERS", finalAnswers);
        dataBundle.putInt("TYPE_OF_QUESTIONS", 3);
        openResultsPageIntent.putExtras(dataBundle);
        startActivity(openResultsPageIntent);
    }

    @Override
    public void onBackPressed() {
        if(Build.VERSION.SDK_INT>=28){
            vibrator.vibrate(VibrationEffect.createOneShot(15, VibrationEffect.DEFAULT_AMPLITUDE));
        }
        if(theTimer!=null) {
            theTimer.cancel();
            theTimer = null;
        }
        super.onBackPressed();
        this.finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(Build.VERSION.SDK_INT>=28){
            vibrator.vibrate(VibrationEffect.createOneShot(15, VibrationEffect.DEFAULT_AMPLITUDE));
        }
        if(theTimer!=null) {
            theTimer.cancel();
            theTimer = null;
        }
        if((keyCode == KeyEvent.KEYCODE_BACK)){
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}