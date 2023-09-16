package com.TwentyOneCodes.madmathematics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

public class SplashScreenActivity extends AppCompatActivity {

    VideoView videoView;

    private SharedPreferences sharedPreferences;
    private SharedPreferences shareLoginPreferences;
    SharedPreferences firstLoginSharedPreferences;
    private boolean wantsToSkip = false;
    private boolean rememberMeChecked;
    private boolean firstLogin = true;
    private FirebaseAuth mAuth;
    ProgressBar splashScreenProgressBar;
    boolean loginReady;
    private boolean InternetWorking = false;
    private boolean oldUser = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        videoView = (VideoView) findViewById(R.id.loadingScreenVideoView);
        splashScreenProgressBar =(ProgressBar) findViewById(R.id.spalshScreenProgressBar);

        sharedPreferences = getSharedPreferences(USER_DATA, MODE_PRIVATE);
        shareLoginPreferences = getSharedPreferences(LOGIN_DATA, MODE_PRIVATE);
        firstLoginSharedPreferences = getSharedPreferences(FIRST_LOGIN, MODE_PRIVATE);
        mAuth = FirebaseAuth.getInstance();
        firstLogin = firstLoginSharedPreferences.getBoolean("First_Login", true);
        wantsToSkip = shareLoginPreferences.getBoolean("wants_to_skip", false);
        rememberMeChecked = sharedPreferences.getBoolean("checked", false);
        Uri video = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.loading_screen_animation);
        videoView.setVideoURI(video);
        loginReady = false;
        videoView.start();
        Context context = getApplicationContext();
        InternetWorking = isDeviceConnected(context);

        if(!InternetWorking){
            if(sharedPreferences.contains("checked") && sharedPreferences.getBoolean("checked", false)){
                oldUser = true;
            }

        }

        if(InternetWorking && !wantsToSkip){
        if(sharedPreferences.contains("checked") && sharedPreferences.getBoolean("checked", false)) {
            String emailId = sharedPreferences.getString("userEmail", "");
            String passWord = sharedPreferences.getString("userPassword", "");
            mAuth.fetchSignInMethodsForEmail(emailId)
                    .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                        @Override
                        public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                            if (task.getResult().getSignInMethods().size() == 0) {
                                return;
                            } else {
                                mAuth.signInWithEmailAndPassword(emailId, passWord).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            setTheLoginReady();

                                        } else {
                                            Toast.makeText(SplashScreenActivity.this, "Could Not LogIn", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }
                    });
        }
        }



        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                splashScreenProgressBar.setVisibility(View.VISIBLE);
                if(firstLogin){
                    splashScreenProgressBar.setVisibility(View.GONE);
                    openOnboardingPages();
                }else if (wantsToSkip) {
                    splashScreenProgressBar.setVisibility(View.GONE);
                    startHomePageActivity();
                }else if (loginReady) {
                    splashScreenProgressBar.setVisibility(View.GONE);
                    startHomePageActivity();
                    }else if(oldUser){
                    splashScreenProgressBar.setVisibility(View.GONE);
                    startHomePageActivity();
                }else{
                    splashScreenProgressBar.setVisibility(View.GONE);
                    startNextActivity(); //login screen
                }

            }
        });


    }

    private void setTheLoginReady() {
        loginReady = true;
    }

    private void openOnboardingPages() {
        Intent openOnBoardingPages = new Intent(this, OnBoardingActivity.class);
        startActivity(openOnBoardingPages);
        overridePendingTransition(R.anim.in, R.anim.out );
    }

    private void startHomePageActivity() {
        if(isFinishing()) return;
        Intent openHomeActivityPage = new Intent(this, HomePage.class);
        startActivity(openHomeActivityPage);
    }

    private void startNextActivity() {
        //login
        if(isFinishing()) return;
        Intent openMainActivityPage = new Intent(this, MainActivity.class);
        startActivity(openMainActivityPage);

    }

    private static boolean isDeviceConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if(netInfo !=null && netInfo.isConnected()){
            return true;
        }
        return false;
    }
}