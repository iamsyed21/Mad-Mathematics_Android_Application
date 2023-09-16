package com.TwentyOneCodes.madmathematics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import hotchemi.android.rate.AppRate;


public class HomePage extends AppCompatActivity {


    private Button startTheGame, openSettingsButton;
    private Button easyModeHighScoreButton, InterModeHighScoreButton, HardModeHighScoreButton;
    private Button easyModeGame, intermediateModeGame, hardModeGame;
    private TextView howScoresAreCalculatedTextView;
    private boolean doubleClickedForBack = false;
    private FirebaseAuth mAuth;
    DataBaseHelper myDB;

    private final String separator ="--~~--";
    private DatabaseReference rootDatabaseReference;
    StringBuilder fireBaseEasyHighScores = new StringBuilder();
    StringBuilder fireBaseInterHighScores = new StringBuilder();
    StringBuilder fireBaseHardHighScores = new StringBuilder();

    StringBuilder easyTimesString = new StringBuilder();
    StringBuilder hardTimesString = new StringBuilder();
    StringBuilder interTimesString = new StringBuilder();
    Dialog difficultyDialog;


    private SharedPreferences shareLoginPreferences;
    private SharedPreferences.Editor loginEditor;
    private boolean wantsToSkip = false;
    private SharedPreferences sharedPreferences; //TO REMEMBER LOGIN DATA
    private boolean InternetWorking = false;
    private boolean Uploadable = true;

    private SharedPreferences prefs;
    private SharedPreferences.Editor prefs_editor;
    private int totalCount;
    private Vibrator vibrator;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        myDB = new DataBaseHelper(this);
        shareLoginPreferences = getSharedPreferences(LOGIN_DATA, MODE_PRIVATE);
        loginEditor = shareLoginPreferences.edit();

        wantsToSkip = shareLoginPreferences.getBoolean("wants_to_skip", false);
        difficultyDialog = new Dialog(this);
        Context context = getApplicationContext();
        InternetWorking = isDeviceConnected(context);

        AppRate.with(this)
                .setInstallDays(1)
                .setLaunchTimes(6)
                .setRemindInterval(2)
                .monitor();

        AppRate.showRateDialogIfMeetsConditions(this);

        if(Build.VERSION.SDK_INT>=28){
            vibrator =(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        }
        prefs = getSharedPreferences("first_one_count", MODE_PRIVATE);
        prefs_editor = prefs.edit();
        if(prefs.contains("counter")) {
            totalCount = prefs.getInt("counter", 0);
            if (totalCount == 0) {
                Uploadable = true;
            } else {
                Uploadable = false;
            }
        }

        if(!wantsToSkip) {
            if(InternetWorking){
                if(Uploadable) {
                    Toast.makeText(context, "Connected To Database", Toast.LENGTH_SHORT).show();
                    mAuth = FirebaseAuth.getInstance();
                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    rootDatabaseReference = FirebaseDatabase.getInstance().getReference("Users").child(uid);
                    sharedPreferences = getSharedPreferences(USER_DATA, MODE_PRIVATE);
                    rootDatabaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.child("easyScores").exists() && !snapshot.child("easyScores").getValue().toString().equals("0")) {
                                getFireBaseEasyValue(snapshot.child("easyScores").getValue().toString(), snapshot.child("easyTime").getValue().toString());
                            }

                            if (snapshot.child("hardScores").exists() && !snapshot.child("hardScores").getValue().toString().equals("0")) {
                                getFireBaseHardValue(snapshot.child("hardScores").getValue().toString(), snapshot.child("interTime").getValue().toString());
                            }

                            if (snapshot.child("interScores").exists() && !snapshot.child("interScores").getValue().toString().equals("0")) {
                                getFireBaseInterValue(snapshot.child("interScores").getValue().toString(), snapshot.child("hardTime").getValue().toString());
                            }
                            prefs_editor.putInt("counter", 1);
                            prefs_editor.apply();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }

            }else{
                Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }

        }


        openSettingsButton=(Button) findViewById(R.id.settingsButton);
        openSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettingsPage();
                if(Build.VERSION.SDK_INT>=28){
                    vibrator.vibrate(VibrationEffect.createOneShot(10, VibrationEffect.DEFAULT_AMPLITUDE));
                }
            }
        });


        easyModeHighScoreButton= (Button) findViewById(R.id.EasyModeHighScoreButton);
        easyModeHighScoreButton.setOnClickListener(view -> openEasyHighScoresPage());

        InterModeHighScoreButton= (Button) findViewById(R.id.InterModeHighScoreButton);
        InterModeHighScoreButton.setOnClickListener(v -> openInterHighScoresPage());

        HardModeHighScoreButton= (Button) findViewById(R.id.HardModeHighScoreButton);
        HardModeHighScoreButton.setOnClickListener(view -> openHardHighScoresPage());


        startTheGame =(Button) findViewById(R.id.startTheGame);
        startTheGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT>=28){
                    vibrator.vibrate(VibrationEffect.createOneShot(25, VibrationEffect.DEFAULT_AMPLITUDE));
                }
                openDialogLayout();
            }
        });

        howScoresAreCalculatedTextView = (TextView) findViewById(R.id.howScoresAreCalculatedTextView);
        howScoresAreCalculatedTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT>=28){
                    vibrator.vibrate(VibrationEffect.createOneShot(15, VibrationEffect.DEFAULT_AMPLITUDE));
                }
                AlertDialog.Builder scoresDialog = new AlertDialog.Builder(HomePage.this);
                scoresDialog.setTitle("How Scores Are Calculated");
                scoresDialog.setIcon(R.drawable.logo_no_background);
                scoresDialog.setMessage("The Final Score is Calculated after factoring not only the correctly answered questions, but also the time taken to answer these Questions.\n\n-More questions answered right in less time will result in a higher score.\n\nTip: More Points are deducted from the final score for a Question wrongly answered than a Question skipped. Therefore, if you are unsure of a Questions, it is better to skip it for now and review it later, as it saves time and results in a higher final score.\n-It is recommended to attempt Hard Mode when your foundations are solid and you are constantly scoring 500+ scores at the Intermediate level.");
                scoresDialog.setNeutralButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog dialog2 = scoresDialog.create();
                dialog2.show();
            }
        });
    }


    private static boolean isDeviceConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if(netInfo !=null && netInfo.isConnected()){
            return true;
        }
        return false;
    }

    private void openEasyHighScoresPage(){
        if(Build.VERSION.SDK_INT>=28){
            vibrator.vibrate(VibrationEffect.createOneShot(10, VibrationEffect.DEFAULT_AMPLITUDE));
        }
        Intent openEasyHighScoresPage = new Intent(this, EasyModeHighScores.class);
        openEasyHighScoresPage.putExtra("TYPE_OF_SCORES", "EASY");
        startActivity(openEasyHighScoresPage);
    }

    private void openInterHighScoresPage(){
        if(Build.VERSION.SDK_INT>=28){
            vibrator.vibrate(VibrationEffect.createOneShot(10, VibrationEffect.DEFAULT_AMPLITUDE));
        }
        Intent openInterHighScoresPage = new Intent(this, EasyModeHighScores.class);
        openInterHighScoresPage.putExtra("TYPE_OF_SCORES", "INTER");
        startActivity(openInterHighScoresPage);
    }

    private void openHardHighScoresPage(){
        if(Build.VERSION.SDK_INT>=28){
            vibrator.vibrate(VibrationEffect.createOneShot(10, VibrationEffect.DEFAULT_AMPLITUDE));
        }
        Intent openHardHighScoresPage = new Intent(this, EasyModeHighScores.class);
        openHardHighScoresPage.putExtra("TYPE_OF_SCORES", "HARD");
        startActivity(openHardHighScoresPage);
    }


    private void openSettingsPage() {
        Intent openSettingsPageIntent = new Intent(this, SettingActivity.class);
        startActivity(openSettingsPageIntent);
    }

    private void getFireBaseInterValue(String interScores, String interTimes) {
            fireBaseInterHighScores = new StringBuilder(interScores);
            interTimesString = new StringBuilder(interTimes);

        String tempTime = interTimesString.toString();
        String[] times = tempTime.split(separator);

            String temp = fireBaseInterHighScores.toString();
            String[] value = temp.split(separator);

            try {
                Cursor data = myDB.getScores(2);
                if(data.getCount() == 0) {
                    for (int i = 0; i < value.length; i++) {
                        boolean addThisScore = myDB.addData(value[i], 2, times[i]);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
    }

    private void getFireBaseHardValue(String hardScores, String hardTimes) {
            fireBaseHardHighScores = new StringBuilder(hardScores);
            hardTimesString = new StringBuilder(hardTimes);
            String tempTime = hardTimesString.toString();
            String[] times = tempTime.split(separator);


            String temp = fireBaseHardHighScores.toString();
            String[] value = temp.split(separator);

        try {
            Cursor data = myDB.getScores(3);
            if(data.getCount() == 0) {
                for (int i = 0; i < value.length; i++) {
                    boolean addThisScore = myDB.addData(value[i], 3, times[i]);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void getFireBaseEasyValue(String easyScores, String easyTimes) {
            fireBaseEasyHighScores = new StringBuilder(easyScores);
            easyTimesString = new StringBuilder(easyTimes);

            String temp = fireBaseEasyHighScores.toString();
            String tempTime = easyTimesString.toString();
            String[] times = tempTime.split(separator);
            String[] value = temp.split(separator);
        try {
            Cursor data = myDB.getScores(1);
            if(data.getCount() == 0) {
                for (int i = 0; i < value.length; i++) {
                    boolean addThisScore = myDB.addData(value[i], 1, times[i]);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }




    protected void openDialogLayout(){
        difficultyDialog.setContentView(R.layout.layout_dialog);
        difficultyDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        easyModeGame = difficultyDialog.findViewById(R.id.openEasyMode);
        intermediateModeGame = difficultyDialog.findViewById(R.id.openintermediateyMode);
        hardModeGame = difficultyDialog.findViewById(R.id.openHardMode);

        easyModeGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT>=28){
                    vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
                }
                Intent openEasyModeIntent = new Intent(view.getContext(), EasyMode.class);
                startActivity(openEasyModeIntent);
            }
        });


        intermediateModeGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT>=28){
                    vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
                }
                Intent openIntermediateModeIntent = new Intent(view.getContext(), IntermediateMode.class);
                startActivity(openIntermediateModeIntent);
            }
        });



        hardModeGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT>=28){
                    vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
                }
                Intent openHardModeIntent = new Intent(view.getContext(), HardMode.class);
                startActivity(openHardModeIntent);
            }
        });

        difficultyDialog.show();

    }


    @Override
    public void onBackPressed() {
        if(Build.VERSION.SDK_INT>=28){
            vibrator.vibrate(VibrationEffect.createOneShot(15, VibrationEffect.DEFAULT_AMPLITUDE));
        }
        if(doubleClickedForBack){
            super.onBackPressed();
            return;
        }
        Toast.makeText(this, "Please Press Back Again To Exit", Toast.LENGTH_SHORT).show();
        doubleClickedForBack = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleClickedForBack = false;
            }
        },2000);

    }
}