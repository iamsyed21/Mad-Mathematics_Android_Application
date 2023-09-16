package com.TwentyOneCodes.madmathematics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import hotchemi.android.rate.AppRate;

public class SettingActivity extends AppCompatActivity {

    private Button logoutUserButton, goBackButton;

    DataBaseHelper myDB;
    private SharedPreferences shareLoginPreferences;
    private SharedPreferences.Editor loginEditor;
    EditText recoveryEmailID;
    private Button changePassowrdButton, closeForgotButton, clearHighScoresButton, deleteMyAccountButton;

    private boolean wantsToSkip = false;
    private ListView aboutUsListView , openSourceListView;
    private TextView aboutUsTextView, pleaseCheckYourEmailTextView, pleaseLoginInTextView, hiUSerTextView;
    private FirebaseAuth mAuth;
    Dialog aboutUsDialog;
    Dialog ForgotPasswordDialog;
    private DatabaseReference rootDatabaseReference;

    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;
    private SharedPreferences prefs;
    AdView bannerAd;
    private boolean InternetWorking = false;
    InterstitialAd mInterstitial;
    private int typeOfIntent = 0;
    private Vibrator vibrator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        myDB = new DataBaseHelper(this);
        mAuth = FirebaseAuth.getInstance();
        if(Build.VERSION.SDK_INT>=28){
            vibrator =(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        }

        try{
            //bannerId =2

            Context context2 = getApplicationContext();
            InternetWorking = isDeviceConnected(context2);
            bannerAd = findViewById(R.id.bannerAdView);

            if(InternetWorking) {
                MobileAds.initialize(this);
                AdRequest adRequest = new AdRequest.Builder().build();
                bannerAd.loadAd(adRequest);
                //inter id =2
                InterstitialAd.load(this,"ca-app-pub-6952672354974833/3766847472" , adRequest, new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);
                        mInterstitial = null;
                        if(typeOfIntent ==0){
                            return;
                        }else if(typeOfIntent ==1){
                            backToLoginPage();
                        }
                    }

                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        super.onAdLoaded(interstitialAd);
                        mInterstitial = interstitialAd;
                        mInterstitial.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdClicked() {
                                super.onAdClicked();
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                super.onAdDismissedFullScreenContent();
                                mInterstitial = null;
                                if(typeOfIntent ==0){
                                    return;
                                }else if(typeOfIntent ==1){
                                    backToLoginPage();
                                }
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                mInterstitial = null;
                                if(typeOfIntent ==0){
                                    return;
                                }else if(typeOfIntent ==1){
                                    backToLoginPage();
                                }
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
            }


        }catch (Exception e){
            e.printStackTrace();
        }




        logoutUserButton = (Button) findViewById(R.id.logoutUserButton);
        changePassowrdButton =(Button) findViewById(R.id.changePassowordButton);
        deleteMyAccountButton =(Button) findViewById(R.id.delteAccountButton);
        clearHighScoresButton =(Button) findViewById(R.id.clear_HighScores);
        pleaseLoginInTextView=(TextView) findViewById(R.id.pleaseLogInTextView);
        shareLoginPreferences = getSharedPreferences(LOGIN_DATA, MODE_PRIVATE);
        prefs = getSharedPreferences("first_one_count", MODE_PRIVATE);
        loginEditor = shareLoginPreferences.edit();
        wantsToSkip = shareLoginPreferences.getBoolean("wants_to_skip", false);
        sharedPreferences = getSharedPreferences(USER_DATA, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        aboutUsDialog = new Dialog(this);
        ForgotPasswordDialog = new Dialog(this);
        hiUSerTextView =(TextView) findViewById(R.id.hiUSerTextView);
        aboutUsTextView =(TextView) findViewById(R.id.aboutUsTextView);
        aboutUsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT>=28){
                    vibrator.vibrate(VibrationEffect.createOneShot(10, VibrationEffect.DEFAULT_AMPLITUDE));
                }
                openAboutUsDialogLayout();
            }
        });

        if(wantsToSkip){
            logoutUserButton.setText("Log In");
            changePassowrdButton.setEnabled(false);
            changePassowrdButton.setAlpha(0.5f);
            deleteMyAccountButton.setEnabled(false);
            deleteMyAccountButton.setAlpha(0.5f);

            clearHighScoresButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(Build.VERSION.SDK_INT>=28){
                        vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
                    }
                    AlertDialog.Builder logoutDialog = new AlertDialog.Builder(SettingActivity.this);
                    logoutDialog.setTitle("Are You Sure?");
                    logoutDialog.setMessage("This will delete all your HighScores!");
                    logoutDialog.setPositiveButton("Delete Scores", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(Build.VERSION.SDK_INT>=28){
                                vibrator.vibrate(VibrationEffect.createOneShot(15, VibrationEffect.DEFAULT_AMPLITUDE));
                            }
                            myDB.deleteAllTheData();
                            Context context2 = getApplicationContext();
                            InternetWorking = isDeviceConnected(context2);
                            if(InternetWorking) {
                                if (mInterstitial != null) {
                                    mInterstitial.show(SettingActivity.this);
                                } else {
                                    mInterstitial = null;
                                }
                            }
                            typeOfIntent =0;

                        }
                    });
                    logoutDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            dialogInterface.dismiss();
                        }
                    });

                    AlertDialog dialog2 = logoutDialog.create();
                    dialog2.show();

                }
            });

            logoutUserButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(Build.VERSION.SDK_INT>=28){
                        vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
                    }
                    backToLoginPage();
                    loginEditor.putBoolean("wants_to_skip", false);
                    loginEditor.apply();
                }
            });

        }else{
            pleaseLoginInTextView.setText("");
            changePassowrdButton.setEnabled(true);
            Context context = getApplicationContext();
            InternetWorking = isDeviceConnected(context);
            if(InternetWorking) {
                FirebaseUser mFirebaseUser = mAuth.getCurrentUser();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                String UserID = mFirebaseUser.getUid();

                pleaseLoginInTextView.setText("You are connected to the database and your data is backed up.");
                pleaseLoginInTextView.setTextColor(Color.parseColor("#DD94F1BD"));

                reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.child("username").exists() && !snapshot.child("username").getValue().toString().isEmpty()) {
                            StringBuilder usernameCurrent = new StringBuilder(snapshot.child("username").getValue().toString());

                            if (usernameCurrent != null && usernameCurrent.length() != 0) {
                                hiUSerTextView.setText("Hi, " + usernameCurrent.toString() + "!");
                            } else {
                                hiUSerTextView.setText("Hi, User!");
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            changePassowrdButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(Build.VERSION.SDK_INT>=28){
                        vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
                    }
                    Context context = getApplicationContext();
                    InternetWorking = isDeviceConnected(context);
                    if(InternetWorking){
                        startForgotPassWord();
                    }else{
                        Toast.makeText(SettingActivity.this, "Please Connect to the Internet", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            logoutUserButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(Build.VERSION.SDK_INT>=28){
                        vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
                    }
                    Context context = getApplicationContext();
                    InternetWorking = isDeviceConnected(context);
                    if(InternetWorking){
                        AlertDialog.Builder logoutDialog = new AlertDialog.Builder(SettingActivity.this);
                        logoutDialog.setTitle("Are You Sure?");
                        logoutDialog.setMessage("You Will be logged out of the session. However, your data will remain backed up so you can continue where you stopped after logging in.");
                        logoutDialog.setPositiveButton("Log Out", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(Build.VERSION.SDK_INT>=28){
                                    vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
                                }
                                getSharedPreferences(USER_DATA, MODE_PRIVATE).edit().clear().apply();
                                getSharedPreferences("first_one_count", MODE_PRIVATE).edit().clear().apply();
                                myDB.deleteAllTheData();
                                mAuth.signOut();
                                if (mInterstitial != null) {
                                    mInterstitial.show(SettingActivity.this);
                                    typeOfIntent =1;
                                } else {
                                    backToLoginPage();
                                }

                            }
                        });
                        logoutDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });

                        AlertDialog dialog2 = logoutDialog.create();
                        dialog2.show();
                    }else{
                        Toast.makeText(SettingActivity.this, "Please Connect to the Internet", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            deleteMyAccountButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(Build.VERSION.SDK_INT>=28){
                        vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
                    }
                    Context context = getApplicationContext();
                    InternetWorking = isDeviceConnected(context);
                    if (InternetWorking) {
                        AlertDialog.Builder deleteDialog = new AlertDialog.Builder(SettingActivity.this);
                        deleteDialog.setTitle("Are You Sure?");
                        deleteDialog.setMessage("Deleting the account will result in completely removing your account from the system and all the High Scores will be permanently deleted.");
                        deleteDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(Build.VERSION.SDK_INT>=28){
                                    vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
                                }
                                try{
                                    FirebaseUser fu = FirebaseAuth.getInstance().getCurrentUser();
                                    fu.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()) {
                                                getSharedPreferences(USER_DATA, MODE_PRIVATE).edit().clear().apply();
                                                getSharedPreferences("first_one_count", MODE_PRIVATE).edit().clear().apply();
                                                getSharedPreferences(LOGIN_DATA, MODE_PRIVATE).edit().clear().apply();
                                                myDB.deleteAllTheData();
                                                Toast.makeText(context, "Account was Deleted", Toast.LENGTH_LONG).show();
                                                if (mInterstitial != null) {
                                                    mInterstitial.show(SettingActivity.this);
                                                    typeOfIntent =1;
                                                } else {
                                                  backToLoginPage();
                                                }

                                            }else{
                                                Toast.makeText(context, "Something Went Wrong, please try again", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        });

                        deleteDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        AlertDialog dialog1 = deleteDialog.create();
                        dialog1.show();
                    }else{
                        Toast.makeText(SettingActivity.this, "Please Connect to the Internet", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            clearHighScoresButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(Build.VERSION.SDK_INT>=28){
                        vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
                    }
                    Context context = getApplicationContext();
                    InternetWorking = isDeviceConnected(context);
                    if(InternetWorking){
                        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        rootDatabaseReference = FirebaseDatabase.getInstance().getReference("Users").child(uid);
                        AlertDialog.Builder logoutDialog = new AlertDialog.Builder(SettingActivity.this);
                        logoutDialog.setTitle("Are You Sure?");
                        logoutDialog.setMessage("This will delete all your HighScores!\nNote: All the High Scores will be permanently deleted from the Database as well.");
                        logoutDialog.setPositiveButton("Delete Scores", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(Build.VERSION.SDK_INT>=28){
                                    vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
                                }
                                myDB.deleteAllTheData();
                                clearAllFirebaseScores();
                                if (mInterstitial != null) {
                                    mInterstitial.show(SettingActivity.this);
                                    typeOfIntent =0;
                                }

                            }
                        });
                        logoutDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });

                        AlertDialog dialog2 = logoutDialog.create();
                        dialog2.show();
                    }else{
                        Toast.makeText(context, "Please connect to Internet to proceed further", Toast.LENGTH_LONG).show();
                    }

                }
            });

        }

        goBackButton =(Button) findViewById(R.id.goBackButton);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT>=28){
                    vibrator.vibrate(VibrationEffect.createOneShot(10, VibrationEffect.DEFAULT_AMPLITUDE));
                }
                onBackPressed();
            }
        });

    }

    private void clearAllFirebaseScores() {
    try{
        rootDatabaseReference.child("easyScores").setValue("0").addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                            }
        });
        rootDatabaseReference.child("easyTime").setValue("0").addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                            }
        });
        rootDatabaseReference.child("interScores").setValue("0").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                    }
        });
        rootDatabaseReference.child("interTime").setValue("0").addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                            }
        });
        rootDatabaseReference.child("hardScores").setValue("0").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(SettingActivity.this, "Deleted Scores Successfully", Toast.LENGTH_SHORT).show();
                    }
        });
        rootDatabaseReference.child("hardTime").setValue("0").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                    }
        });
    }catch(Exception e){
                Toast.makeText(this, "Exception Occurred", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
    }

    private void startForgotPassWord() {
        ForgotPasswordDialog.setContentView(R.layout.forgot_password_layout);
        recoveryEmailID =(EditText) ForgotPasswordDialog.findViewById(R.id.recoveryEmailId);
        changePassowrdButton =(Button) ForgotPasswordDialog.findViewById(R.id.changePassowrdButton);
        closeForgotButton =(Button) ForgotPasswordDialog.findViewById(R.id.goBackFromForgotPassword);
        pleaseCheckYourEmailTextView =(TextView) ForgotPasswordDialog.findViewById(R.id.pleaseCheckYourEmailTextView);
        ForgotPasswordDialog.show();


        changePassowrdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT>=28){
                    vibrator.vibrate(VibrationEffect.createOneShot(10, VibrationEffect.DEFAULT_AMPLITUDE));
                }
                String email = recoveryEmailID.getText().toString().trim();
                if(email.isEmpty()){
                    recoveryEmailID.setError("Email is required!");
                    recoveryEmailID.requestFocus();
                    return;
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    recoveryEmailID.setError("Please enter Valid Email");
                    recoveryEmailID.requestFocus();
                    return;
                }

                mAuth.fetchSignInMethodsForEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                            @Override
                            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                if(task.getResult().getSignInMethods().size() ==0){
                                    recoveryEmailID.setError("Email Does Not Exists, Please Register");
                                    recoveryEmailID.requestFocus();
                                    return;
                                }else{
                                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()) {
                                                Toast.makeText(SettingActivity.this, "The Recovery Link Has been Sent to Your Email", Toast.LENGTH_LONG).show();
                                                pleaseCheckYourEmailTextView.setText("Please Check your Email For Password Changing Link");
                                            }else{
                                                Toast.makeText(SettingActivity.this, "Something Went Wrong, Please Try Again", Toast.LENGTH_LONG).show();
                                                pleaseCheckYourEmailTextView.setText("Please Try Again");
                                            }
                                        }
                                    });
                                }
                            }
                        });
            }
        });

        closeForgotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ForgotPasswordDialog.dismiss();
            }
        });

    }

    protected void openAboutUsDialogLayout(){
        aboutUsDialog.setContentView(R.layout.about_us_dialog);
        aboutUsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        aboutUsListView = aboutUsDialog.findViewById(R.id.aboutListView);
        List<String> list = new ArrayList<>();
        list.add("Rate Us");
        list.add("About 21Codes");
        list.add("Privacy Policy");
        list.add("Terms of Services");
        list.add("Open Source Licenses");
        ArrayAdapter adaptor = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, list);
        aboutUsListView.setAdapter(adaptor);
        aboutUsDialog.show();

        aboutUsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               switch(i){
                   case 0:
                       if(Build.VERSION.SDK_INT>=28){
                           vibrator.vibrate(VibrationEffect.createOneShot(5, VibrationEffect.DEFAULT_AMPLITUDE));
                       }
                       openRateUsDialog();
                       break;
                   case 1:
                       if(Build.VERSION.SDK_INT>=28){
                           vibrator.vibrate(VibrationEffect.createOneShot(5, VibrationEffect.DEFAULT_AMPLITUDE));
                       }
                       gotoUrl("http://the21codes.com/");
                       break;

                   case 2:
                       if(Build.VERSION.SDK_INT>=28){
                           vibrator.vibrate(VibrationEffect.createOneShot(5, VibrationEffect.DEFAULT_AMPLITUDE));
                       }
                       gotoUrl("http://the21codes.com/mad-mathematics-policy");
                       break;

                   case 3:
                       if(Build.VERSION.SDK_INT>=28){
                           vibrator.vibrate(VibrationEffect.createOneShot(5, VibrationEffect.DEFAULT_AMPLITUDE));
                       }
                       gotoUrl("http://the21codes.com/mad-mathematics-t-%26-c");
                       break;

                   case 4:
                       if(Build.VERSION.SDK_INT>=28){
                           vibrator.vibrate(VibrationEffect.createOneShot(5, VibrationEffect.DEFAULT_AMPLITUDE));
                       }
                       openOpenSourceLicenses();
                       break;

               }
            }
        });

    }

    private void gotoUrl(String s) {
        AlertDialog.Builder externalDialog = new AlertDialog.Builder(SettingActivity.this);
        externalDialog.setTitle("Are You Sure?");
        externalDialog.setMessage("This will open the Link Externally");
        externalDialog.setPositiveButton("Open", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse(s);
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }
        });

        externalDialog.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog1 = externalDialog.create();
        dialog1.show();

    }


    private void openOpenSourceLicenses() {
        Dialog openSourceDialog = new Dialog(this);
        openSourceDialog.setContentView(R.layout.open_source_licenses_dialog);
        openSourceDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        openSourceListView = openSourceDialog.findViewById(R.id.openSourceLicenses);

        List<String> list = new ArrayList<>();
        list.add("MP Android Chart by Phil Jay");
        list.add("Android-Rate by hotchemi");
        list.add("Drawable Icons");

        ArrayAdapter adaptor = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, list);
        openSourceListView.setAdapter(adaptor);
        openSourceDialog.show();

        openSourceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch(i){
                    case 0:
                        if(Build.VERSION.SDK_INT>=28){
                            vibrator.vibrate(VibrationEffect.createOneShot(5, VibrationEffect.DEFAULT_AMPLITUDE));
                        }
                        openForAndroidMP();
                        break;
                    case 1:
                        if(Build.VERSION.SDK_INT>=28){
                            vibrator.vibrate(VibrationEffect.createOneShot(5, VibrationEffect.DEFAULT_AMPLITUDE));
                        }
                        openForAndroidRate();
                        break;
                    case 2:
                        if(Build.VERSION.SDK_INT>=28){
                            vibrator.vibrate(VibrationEffect.createOneShot(5, VibrationEffect.DEFAULT_AMPLITUDE));
                        }
                        openForAndroidDrawables();
                        break;
                }
            }
        });
    }

    private void openForAndroidDrawables() {
        Intent openIntent = new Intent(this, BoilerPlates.class);
        openIntent.putExtra("document", 3);
        startActivity(openIntent);
    }

    private void openForAndroidRate() {
        Intent openIntent = new Intent(this, BoilerPlates.class);
        openIntent.putExtra("document", 2);
        startActivity(openIntent);
    }

    private void openForAndroidMP() {
        Intent openIntent = new Intent(this, BoilerPlates.class);
        openIntent.putExtra("document", 1);
        startActivity(openIntent);
    }

    private void openRateUsDialog() {
        AppRate.with(this).showRateDialog(this);
    }

    private void backToLoginPage() {
        Intent backToLoginPage = new Intent(this, MainActivity.class);
        backToLoginPage.putExtra("ManualLogOut", true);
        backToLoginPage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(backToLoginPage);
        finish();
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