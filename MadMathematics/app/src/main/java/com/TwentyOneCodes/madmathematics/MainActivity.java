package com.TwentyOneCodes.madmathematics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.SignInMethodQueryResult;

//This Activity is basically Login Activity and first to pop up when app is installed newly

public class MainActivity extends AppCompatActivity {


    EditText userEmailIDEditText, userPasswordEditText, recoveryEmailID;
    Button userLoginButton, skipLoginProcedure, changePassowrdButton, closeForgotButton;
    CheckBox rememberMeCheckBox;
    ProgressBar loginActivityProgressBar;
    TextView forgotPasswordTextView, registerUserTextView, pleaseCheckYourEmailTextView;
    DataBaseHelper myDB;
    private FirebaseAuth mAuth;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;
    private SharedPreferences shareLoginPreferences;
    private SharedPreferences.Editor loginEditor;
    private boolean wantsToSkip = false;
    private CheckBox rememberMeDialogCheckBox;
    private Button rememberMeCancelDialogButton;
    private Vibrator vibrator;


    Dialog rememberMeDialog;
    Dialog ForgotPasswordDialog;



    private boolean InternetWorking = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new DataBaseHelper(this);
        mAuth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences(USER_DATA, MODE_PRIVATE);
        shareLoginPreferences = getSharedPreferences(LOGIN_DATA, MODE_PRIVATE);
        loginEditor = shareLoginPreferences.edit();
        wantsToSkip = shareLoginPreferences.getBoolean("wants_to_skip", false);
        rememberMeDialog = new Dialog(this);
        ForgotPasswordDialog = new Dialog(this);

        if(Build.VERSION.SDK_INT>=28){
            vibrator =(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        }

        if(wantsToSkip){
            openHomePageActivity();
        }

        userEmailIDEditText =(EditText) findViewById(R.id.userEmailIdEditText);
        userPasswordEditText=(EditText) findViewById(R.id.userPasswordEditText);
        loginActivityProgressBar = findViewById(R.id.loginPageProgressBar);
        forgotPasswordTextView=(TextView) findViewById(R.id.userForgotPasswordTextView);
        userLoginButton =(Button) findViewById(R.id.userLogInButton);
        rememberMeCheckBox =(CheckBox) findViewById(R.id.rememberMeCheckBox);
        registerUserTextView= (TextView) findViewById(R.id.registerNewUserTextView);
        boolean hasManualLoggedOutFlag = getIntent().getBooleanExtra("ManualLogOut", false);
        if (hasManualLoggedOutFlag){
            rememberMeCheckBox.setChecked(false);
        }
        editor = sharedPreferences.edit();

        rememberMeCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(Build.VERSION.SDK_INT>=28){
                    vibrator.vibrate(VibrationEffect.createOneShot(15, VibrationEffect.DEFAULT_AMPLITUDE));
                }

            }
        });

        skipLoginProcedure =(Button) findViewById(R.id.skipLoginButton);
        skipLoginProcedure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT>=28){
                    vibrator.vibrate(VibrationEffect.createOneShot(15, VibrationEffect.DEFAULT_AMPLITUDE));
                }

                wantsToSkip = true;
                loginEditor.putBoolean("wants_to_skip", true);
                loginEditor.apply();
                openHomePageActivity();
            }
        });


        if(!wantsToSkip) {

            forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = getApplicationContext();
                    InternetWorking = isDeviceConnected(context);

                    if(InternetWorking) {
                        startForgotPassWord();
                    }else{
                        Toast.makeText(context, "Please Connect To the Internet", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            userLoginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = getApplicationContext();
                    InternetWorking = isDeviceConnected(context);

                    if(InternetWorking) {
                        userSignInActivity();
                    }else{
                        Toast.makeText(context, "Please Connect To the Internet", Toast.LENGTH_SHORT).show();
                    }
                }
            });



            registerUserTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(Build.VERSION.SDK_INT>=28){
                        vibrator.vibrate(VibrationEffect.createOneShot(15, VibrationEffect.DEFAULT_AMPLITUDE));
                    }

                    openRegisterUserPage();
                }
            });

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
                                                Toast.makeText(MainActivity.this, "The Link to Change Password Has been Sent to Your Email", Toast.LENGTH_LONG).show();
                                                pleaseCheckYourEmailTextView.setText("Please Check your Email For The Link To Change The Password");
                                            }else{
                                                Toast.makeText(MainActivity.this, "Something Went Wrong, Please Try Again", Toast.LENGTH_LONG).show();
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

    private static boolean isDeviceConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if(netInfo !=null && netInfo.isConnected()){
            return true;
        }
        return false;
    }

    private void userSignInActivity() {
        String emailId= userEmailIDEditText.getText().toString().trim();
        String passWord = userPasswordEditText.getText().toString().trim();

        if(emailId.isEmpty()){
            userEmailIDEditText.setError("Please enter the email");
            userEmailIDEditText.requestFocus();
            return;
        }

        if(passWord.isEmpty()){
            userPasswordEditText.setError("Please enter the password");
            userPasswordEditText.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(emailId).matches()){
            userEmailIDEditText.setError("Please enter a Email Address");
            userEmailIDEditText.requestFocus();
            return;

        }


        if(rememberMeCheckBox.isChecked()){
            editor.putBoolean("checked", true);
            editor.apply();
            storeUserDataInSharedPreferences(emailId, passWord);
            startTheLoginProcess(emailId, passWord);
        }else{
            rememberMeDialog.setContentView(R.layout.remember_me_dialog);
            rememberMeDialogCheckBox = rememberMeDialog.findViewById(R.id.rememberMeDialogCheckBox);
            rememberMeCancelDialogButton = rememberMeDialog.findViewById(R.id.cancelButtonDialog);
            rememberMeDialog.show();
            rememberMeDialogCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b){
                         editor.putBoolean("checked", true);
                         editor.apply();
                        if(Build.VERSION.SDK_INT>=28){
                            vibrator.vibrate(VibrationEffect.createOneShot(15, VibrationEffect.DEFAULT_AMPLITUDE));
                        }

                        storeUserDataInSharedPreferences(emailId, passWord);
                        rememberMeCheckBox.setChecked(true);
                        rememberMeDialog.dismiss();
                        startTheLoginProcess(emailId, passWord);

                    }
                    if(!b){
                        if(Build.VERSION.SDK_INT>=28){
                            vibrator.vibrate(VibrationEffect.createOneShot(15, VibrationEffect.DEFAULT_AMPLITUDE));
                        }

                        getSharedPreferences(USER_DATA, MODE_PRIVATE).edit().clear().apply();
                        rememberMeCheckBox.setChecked(false);
                        rememberMeDialog.dismiss();
                        startTheLoginProcess(emailId, passWord);
                    }
                }
            });


            rememberMeCancelDialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getSharedPreferences(USER_DATA, MODE_PRIVATE).edit().clear().apply();
                    rememberMeCheckBox.setChecked(false);
                    rememberMeDialog.dismiss();
                    startTheLoginProcess(emailId, passWord);
                }
            });

        }

    }

    private void startTheLoginProcess(String emailId, String passWord){
        if(Build.VERSION.SDK_INT>=28){
            vibrator.vibrate(VibrationEffect.createOneShot(15, VibrationEffect.DEFAULT_AMPLITUDE));
        }

        loginActivityProgressBar.setVisibility(View.VISIBLE);
        if(InternetWorking) {
            try {
                mAuth.fetchSignInMethodsForEmail(emailId)
                        .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                            @Override
                            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                if (task.getResult().getSignInMethods().size() == 0) {
                                    userEmailIDEditText.setError("Email Does Not Exists, Please Register");
                                    userEmailIDEditText.requestFocus();
                                    loginActivityProgressBar.setVisibility(View.GONE);

                                } else {
                                    mAuth.signInWithEmailAndPassword(emailId, passWord).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                openHomePageActivity();
                                                loginActivityProgressBar.setVisibility(View.GONE);
                                            } else {
                                                if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                                    userPasswordEditText.setError("Invalid Password");
                                                    userPasswordEditText.requestFocus();
                                                } else {
                                                    Toast.makeText(MainActivity.this, "Could Not LogIn", Toast.LENGTH_SHORT).show();
                                                }
                                                loginActivityProgressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                                }
                            }
                        });

            } catch (Exception e) {
                Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }else{
            loginActivityProgressBar.setVisibility(View.GONE);
        }

    }


    private void storeUserDataInSharedPreferences(String emailId, String passWord) {
        editor = getSharedPreferences(USER_DATA, MODE_PRIVATE).edit();
        editor.putString("userPassword", passWord);
        editor.putString("userEmail", emailId);
        editor.apply();
    }

    private void openHomePageActivity() {
        Intent openHomePageIntent = new Intent(this, HomePage.class);
        startActivity(openHomePageIntent);
    }

    private void openRegisterUserPage(){
        Intent openRegisterPage = new Intent(this, RegisterNewUser.class);
        startActivity(openRegisterPage);
    }


}