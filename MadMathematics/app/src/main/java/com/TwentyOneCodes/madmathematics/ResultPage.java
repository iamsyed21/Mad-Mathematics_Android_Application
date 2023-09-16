package com.TwentyOneCodes.madmathematics;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ResultPage extends AppCompatActivity {

    private Button reviewQuestionsButton, playAgainButton;
    private TextView scoreTextView, questionsAttempted, questionsAnsweredCorrectly, questionsAnsweredIncorrectly, questionsSkipped, timeTakenTextView;
    private int theScore;

    private int skippedQuestions=0;
    private int wrongAnswers=0;
    private String timeTaken;
    private ArrayList<String> questionsArrayList;
    private ArrayList<String> answerArrayList;
    private ArrayList<String> questionHints;
    private int typeOfQuestions;
    private RecyclerView updatedHighScoreRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    DataBaseHelper myDB;
    private ArrayList<HighScores> scoresArrayList;
    private ArrayList<HighScores> unOrderedScoresArrayList;
    private final String separator ="--~~--";
    private DatabaseReference rootDatabaseReference;
    private boolean InternetWorking = false;
    private SharedPreferences shareLoginPreferences;
    private boolean wantsToSkip = false;
    AdView bannerAd;
    InterstitialAd mInterstitial;
    private Vibrator vibrator;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_page);
        //start of code->
        myDB = new DataBaseHelper(this);


        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        questionsAttempted = (TextView) findViewById(R.id.questionsAttemptedTextView);
        questionsAnsweredCorrectly = (TextView) findViewById(R.id.answeredCorrectlyTextView);
        questionsAnsweredIncorrectly = (TextView) findViewById(R.id.answeredInCorrectlyTextView);
        questionsSkipped = (TextView) findViewById(R.id.questionsSkippedTextView);
        timeTakenTextView =(TextView) findViewById(R.id.timeTakenTextView);
        questionsArrayList= new ArrayList<>();
        answerArrayList = new ArrayList<>();
        questionHints = new ArrayList<>();
        scoresArrayList = new ArrayList<>();
        unOrderedScoresArrayList= new ArrayList<>();
        shareLoginPreferences = getSharedPreferences(LOGIN_DATA, MODE_PRIVATE);
        wantsToSkip = shareLoginPreferences.getBoolean("wants_to_skip", false);
        AdRequest adRequest = new AdRequest.Builder().build();

        if(Build.VERSION.SDK_INT>=28){
            vibrator =(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        }

        Context context = getApplicationContext();
        InternetWorking = isDeviceConnected(context);

        try {
            if (InternetWorking) {

                //inter id =1
                InterstitialAd.load(this, "ca-app-pub-6952672354974833/3766847472", adRequest, new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);
                        mInterstitial = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        super.onAdLoaded(interstitialAd);
                        mInterstitial = interstitialAd;
                        mInterstitial.show(ResultPage.this);

                        mInterstitial.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdClicked() {
                                super.onAdClicked();
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                super.onAdDismissedFullScreenContent();
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                super.onAdFailedToShowFullScreenContent(adError);
                            }

                            @Override
                            public void onAdImpression() {
                                super.onAdImpression();
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                super.onAdShowedFullScreenContent();
                                mInterstitial = null;
                            }
                        });

                    }
                });

                //

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mInterstitial != null) {
                            mInterstitial.show(ResultPage.this);
                        } else {
                            mInterstitial = null;
                        }
                    }
                }, 3000);

            }

        }catch (Exception e){
            e.printStackTrace();
        }


        reviewQuestionsButton =(Button) findViewById(R.id.reviewQuestionsButton);
        reviewQuestionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT>=28){
                    vibrator.vibrate(VibrationEffect.createOneShot(15, VibrationEffect.DEFAULT_AMPLITUDE));
                }
                reviewQuestionsDialogOpener();
            }
        });

        Bundle dataBundle = getIntent().getExtras();

       theScore = dataBundle.getInt("THE_SCORE");
       skippedQuestions= dataBundle.getInt("SKIPPED_QUESTIONS");
       wrongAnswers= dataBundle.getInt("WRONG_ANSWERS");
       timeTaken = dataBundle.getString("THE_TIME_TAKEN");
       typeOfQuestions = dataBundle.getInt("TYPE_OF_QUESTIONS");
       int timeRemaining=0;
       if(typeOfQuestions ==1){
            timeRemaining = 90- Integer.parseInt(timeTaken);
       }else if(typeOfQuestions ==2){
           timeRemaining = 120- Integer.parseInt(timeTaken);
       }else if(typeOfQuestions ==3){
           timeRemaining = 150- Integer.parseInt(timeTaken);
       }


        //bannerId =4

        try {
            bannerAd = findViewById(R.id.bannerAdView);
            MobileAds.initialize(this);
            bannerAd.loadAd(adRequest);
        }catch (Exception e){
            e.printStackTrace();
        }

        timeTakenTextView.setText(timeTaken);
       int questionsAttemptedInt = 15-skippedQuestions;
       questionsAttempted.setText(Integer.toString(questionsAttemptedInt));
       questionsSkipped.setText(Integer.toString(skippedQuestions));
       questionsAnsweredCorrectly.setText(Integer.toString(theScore));
       int questionsIncorrect = wrongAnswers;
       questionsAnsweredIncorrectly.setText(Integer.toString(questionsIncorrect));
       int finalScore=0;

       if(timeRemaining == 0){
            finalScore = theScore*20;
        }else{
            finalScore = timeRemaining*(theScore-questionsIncorrect) - (timeRemaining*skippedQuestions+1)/3;
        }

       if(finalScore<0){
            finalScore =theScore*5;
        }

       scoreTextView.setText(Integer.toString(finalScore));
       questionsArrayList = dataBundle.getStringArrayList("ARRAY_LIST_OF_QUESTIONS");
       answerArrayList = dataBundle.getStringArrayList("ARRAYLIST_OF_ANSWERS");
       questionHints = dataBundle.getStringArrayList("ARRAYLIST_OF_HINTS");
       addScore(Integer.toString(finalScore), typeOfQuestions, timeTaken);
       getScoresFromDataBase();
       updatedHighScoreRecyclerView =(RecyclerView) findViewById(R.id.updatedHighScoreRecyclerView);
       mLayoutManager = new LinearLayoutManager(this);
       mAdapter = new scoreAdaptor(scoresArrayList);
       updatedHighScoreRecyclerView.setLayoutManager(mLayoutManager);
       updatedHighScoreRecyclerView.setAdapter(mAdapter);



        if(!wantsToSkip) {
            if (InternetWorking) {
                try {
                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    rootDatabaseReference = FirebaseDatabase.getInstance().getReference("Users").child(uid);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                uploadNewScoreOnFirebase(typeOfQuestions);
            } else {
                Toast.makeText(context, "Please Connect To The Internet\nTo Back Up Your Scores", Toast.LENGTH_SHORT).show();
            }
        }

        playAgainButton =(Button) findViewById(R.id.startTheGameAgain);
        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT>=28){
                    vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
                }
                openPlayAgainPage();
            }
        });
    }

    private void openPlayAgainPage() {
        mInterstitial = null;
        if(typeOfQuestions ==1){
            Intent openNewGameIntent = new Intent(this, EasyMode.class);
            startActivity(openNewGameIntent);
            this.finish();
        }
        if(typeOfQuestions ==2){
            Intent openNewGameIntent = new Intent(this, IntermediateMode.class);
            startActivity(openNewGameIntent);
            this.finish();
        }
        if(typeOfQuestions ==3){
            Intent openNewGameIntent = new Intent(this, HardMode.class);
            startActivity(openNewGameIntent);
            this.finish();
        }
    }

    private static boolean isDeviceConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if(netInfo !=null && netInfo.isConnected()){
            return true;
        }
        return false;
    }

    private void uploadNewScoreOnFirebase(int typeOfQuestions) {
        try{

            if(typeOfQuestions ==1){
                StringBuilder newFireBaseScore = new StringBuilder();
                StringBuilder newFireBaseTimeTaken = new StringBuilder();
                for(int i=0; i<unOrderedScoresArrayList.size(); i++){
                    if(i==unOrderedScoresArrayList.size()-1 || i==14){
                        newFireBaseScore.append(unOrderedScoresArrayList.get(i).getScore());
                        newFireBaseTimeTaken.append(unOrderedScoresArrayList.get(i).getTimeTaken());
                        break;
                    }else {
                        newFireBaseScore.append(unOrderedScoresArrayList.get(i).getScore() + separator);
                        newFireBaseTimeTaken.append(unOrderedScoresArrayList.get(i).getTimeTaken() + separator);
                    }

                }

                if(newFireBaseScore.length() == 0){
                    String newFirebaseFinalScore = "0";
                    rootDatabaseReference.child("easyScores").setValue(newFirebaseFinalScore).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                        }
                    });
                }else{
                    String newFirebaseFinalScore = newFireBaseScore.toString();
                    rootDatabaseReference.child("easyScores").setValue(newFirebaseFinalScore).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                        }
                    });
                }

                if(newFireBaseTimeTaken.length() == 0){
                    rootDatabaseReference.child("easyTime").setValue("0").addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                        }
                    });
                }else{
                    String newFirebaseFinalTime = newFireBaseTimeTaken.toString();
                    rootDatabaseReference.child("easyTime").setValue(newFirebaseFinalTime).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                        }
                    });
                }
            }

            if(typeOfQuestions == 2){
                StringBuilder newFireBaseScore = new StringBuilder();
                StringBuilder newFireBaseTimeTaken = new StringBuilder();
                for(int i=0; i<unOrderedScoresArrayList.size(); i++){
                    if(i==unOrderedScoresArrayList.size()-1 || i==14){
                        newFireBaseScore.append(unOrderedScoresArrayList.get(i).getScore());
                        newFireBaseTimeTaken.append(unOrderedScoresArrayList.get(i).getTimeTaken());
                        break;
                    }else {
                        newFireBaseScore.append(unOrderedScoresArrayList.get(i).getScore() + separator);
                        newFireBaseTimeTaken.append(unOrderedScoresArrayList.get(i).getTimeTaken() + separator);
                    }
                }

                if(newFireBaseScore.length() == 0){
                    rootDatabaseReference.child("interScores").setValue("0").addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                        }
                    });
                }else{
                    String newFirebaseFinalScore = newFireBaseScore.toString();
                    rootDatabaseReference.child("interScores").setValue(newFirebaseFinalScore).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                        }
                    });
                }

                if(newFireBaseTimeTaken.length() == 0){
                    rootDatabaseReference.child("interTime").setValue("0").addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                        }
                    });
                }else{
                    String newFirebaseFinalTime = newFireBaseTimeTaken.toString();
                    rootDatabaseReference.child("interTime").setValue(newFirebaseFinalTime).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                        }
                    });
                }
            }

            if(typeOfQuestions == 3){
                StringBuilder newFireBaseScore = new StringBuilder();
                StringBuilder newFireBaseTimeTaken = new StringBuilder();
                for(int i=0; i<unOrderedScoresArrayList.size(); i++){
                    if(i==unOrderedScoresArrayList.size()-1 || i==14){
                        newFireBaseScore.append(unOrderedScoresArrayList.get(i).getScore());
                        newFireBaseTimeTaken.append(unOrderedScoresArrayList.get(i).getTimeTaken());
                        break;
                    }else {
                        newFireBaseScore.append(unOrderedScoresArrayList.get(i).getScore() + separator);
                        newFireBaseTimeTaken.append(unOrderedScoresArrayList.get(i).getScore() + separator);
                    }
                }

                if(newFireBaseScore.length() == 0){
                    rootDatabaseReference.child("hardScores").setValue("0").addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                        }
                    });
                }else{
                    String newFirebaseFinalScore = newFireBaseScore.toString();
                    rootDatabaseReference.child("hardScores").setValue(newFirebaseFinalScore).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                        }
                    });
                }

                if(newFireBaseTimeTaken.length() == 0){
                    rootDatabaseReference.child("hardTime").setValue("0").addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                        }
                    });
                }else{
                    String newFirebaseFinalTime = newFireBaseTimeTaken.toString();
                    rootDatabaseReference.child("hardTime").setValue(newFirebaseFinalTime).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                        }
                    });
                }
            }

        }catch(Exception e){
            Toast.makeText(this, "Something Went Wrong While Backing Up The Data", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    private void addScore(String finalScore, int typeOfQuestions, String TimeTaken){
        try {
            boolean addThisScore = myDB.addData(finalScore, typeOfQuestions, TimeTaken);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getScoresFromDataBase(){
        try {
            Cursor data = myDB.getScores(typeOfQuestions);
            if(data.getCount() == 0){
            }else{
                while(data.moveToNext()){
                    scoresArrayList.add(new HighScores(data.getInt(0), data.getString(1), typeOfQuestions, data.getString(2)));
                }
            }
            int unorderedType = typeOfQuestions+3;
            Cursor UnorderedData = myDB.getScores(unorderedType);
            if(UnorderedData.getCount() == 0){
            }else{
                while(UnorderedData.moveToNext()){
                    unOrderedScoresArrayList.add(new HighScores(UnorderedData.getInt(0), UnorderedData.getString(1), 4, UnorderedData.getString(2)));
                }
            }


        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void reviewQuestionsDialogOpener(){
        Intent openReviewQuestionsDialog = new Intent(this, ReviewingQuestionsActivity.class);
        Bundle dataBundle = new Bundle();
        dataBundle.putStringArrayList("ARRAY_LIST_OF_QUESTIONS", questionsArrayList);
        dataBundle.putStringArrayList("ARRAYLIST_OF_ANSWERS", answerArrayList);
        dataBundle.putStringArrayList("ARRAYLIST_OF_HINTS", questionHints);
        openReviewQuestionsDialog.putExtras(dataBundle);
        startActivity(openReviewQuestionsDialog);
    }
}