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

public class IntermediateMode extends AppCompatActivity {

    private TextView attemptedTextView, timerTextView, questionTextView, hint_notes_TextView;
    private Button optionA_Button, optionB_Button, optionC_Button, optionD_Button, skipThisQuestionButton;
    private int questionCounter =1;
    private StringBuilder questionString;
    private StringBuilder questionHintString;
    private CountDownTimer theTimer;
    private int theAnswer;
    private ArrayList<Integer> options;
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
        setContentView(R.layout.activity_intermediate_mode);
        IntermediateQuestionGenerator generate = new IntermediateQuestionGenerator();

        questionsArraylist= new ArrayList<>();
        finalAnswers = new ArrayList<>();
        finalHints = new ArrayList<>();

        questionString = generate.finalQuestion;
        options = generate.options;
        theAnswer = generate.theAnswer;
        questionHintString = generate.questionHint;

        if(Build.VERSION.SDK_INT>=28){
            vibrator =(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        }

        theScore =0;
        questionTextView = (TextView) findViewById(R.id.theQuestionTextview);
        questionTextView.setText(questionString);

        attemptedTextView =(TextView) findViewById(R.id.attemptedQuestionsTextView);
        attemptedTextView.setText("1/15");

        timerTextView = (TextView) findViewById(R.id.timerTextView);
        timerTextView.setText("120");
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

        skipThisQuestionButton =(Button) findViewById(R.id.skipThisButton);
        skipThisQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT>=28){
                    vibrator.vibrate(VibrationEffect.createOneShot(15, VibrationEffect.DEFAULT_AMPLITUDE));
                }
                optionSelected(5);
            }
        });



        theTimer = new CountDownTimer(120100, 1000){
            @Override
            public void onTick(long l) {
                timerTextView.setText(l/1000+"s");
                long time = 100 - (l/1200);
                timerProgressBar.setProgress((int) time);
                if(questionCounter > 15) {
                    int timetaken = 120- ((int)l/1000);
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
                timeTaken = new String("120");
                if(Build.VERSION.SDK_INT>=28){
                    vibrator.vibrate(VibrationEffect.createOneShot(15, VibrationEffect.DEFAULT_AMPLITUDE));
                }
                openResultsPage();
            }
        }.start();

        hint_notes_TextView = (TextView) findViewById(R.id.hint_notes_TextView);
        hint_notes_TextView.setText(questionHintString.toString());
        OptionAssigner(options, theAnswer);

        //End of OncCreate
    }

    private void optionSelected(int option){
        if(option == theCorrectOption){
            theScore++;
        }else if(option == 5){
            skippedQuestions++;
        }else{
            wrongAnswers++;
        }

        IntermediateQuestionGenerator obj = new IntermediateQuestionGenerator();
        questionString = new StringBuilder(obj.finalQuestion);
        questionHintString = new StringBuilder(obj.questionHint);
        if(questionCounter <= 15) {
            if (questionCounter == 15) {
                questionTextView.setText("Calculating Results!");
                questionCounter++;
            }else{
                questionCounter++;
                attemptedTextView.setText(questionCounter + "/15");
                questionTextView.setText(Integer.toString(questionCounter) + ". " + questionString);
                hint_notes_TextView.setText(questionHintString);
                OptionAssigner(obj.options, obj.theAnswer);
            }
        }
    }


    private void OptionAssigner(ArrayList<Integer> options, int theAnswer){
        questionsArraylist.add(String.valueOf(questionString));
        finalAnswers.add(String.valueOf(theAnswer));
        finalHints.add(questionHintString.toString());

        int randomPlace = (int) ((Math.random()*4)+1);
        theCorrectOption = randomPlace;

        switch(theCorrectOption){
            case 1: optionA_Button.setText(Integer.toString(theAnswer));
                optionB_Button.setText(Integer.toString(options.get(0)));
                optionC_Button.setText(Integer.toString(options.get(1)));
                optionD_Button.setText(Integer.toString(options.get(2)));
                break;
            case 2: optionA_Button.setText(Integer.toString(options.get(0)));
                optionB_Button.setText(Integer.toString(theAnswer));
                optionC_Button.setText(Integer.toString(options.get(1)));
                optionD_Button.setText(Integer.toString(options.get(2)));
                break;
            case 3: optionA_Button.setText(Integer.toString(options.get(0)));
                optionB_Button.setText(Integer.toString(options.get(1)));
                optionC_Button.setText(Integer.toString(theAnswer));
                optionD_Button.setText(Integer.toString(options.get(2)));
                break;
            case 4: optionA_Button.setText(Integer.toString(options.get(0)));
                optionB_Button.setText(Integer.toString(options.get(1)));
                optionC_Button.setText(Integer.toString(options.get(2)));
                optionD_Button.setText(Integer.toString(theAnswer));
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
        dataBundle.putStringArrayList("ARRAYLIST_OF_ANSWERS", finalAnswers);
        dataBundle.putStringArrayList("ARRAYLIST_OF_HINTS", finalHints);
        dataBundle.putInt("TYPE_OF_QUESTIONS", 2);
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