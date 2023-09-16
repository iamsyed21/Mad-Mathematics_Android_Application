package com.TwentyOneCodes.madmathematics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RegisterNewUser extends AppCompatActivity {

    EditText NewUserEmailIDEditText, NewUserPasswordEditText, newUsernameEditText;
    Button registerNewUserButton;
    ImageButton goBackToLogin;
    ProgressBar registerActivityProgressBar;
    private FirebaseAuth mAuth;
    DataBaseHelper myDB;
    private ArrayList<HighScores> easyScoresArrayList;
    private ArrayList<HighScores> interScoresArrayList;
    private ArrayList<HighScores> hardScoresArrayList;
    private final String separator ="--~~--";
    StringBuilder availableEasyScores;
    StringBuilder availableEasyTime;
    StringBuilder availableInterScores;
    StringBuilder availableInterTime;
    StringBuilder availableHardScores;
    StringBuilder availableHardTime;
    private boolean InternetWorking = false;
    TextView termsAndConditionsTextView;
    private Vibrator vibrator;
    String globalEmail;
    String globalPassWord;

    private SharedPreferences sharedPreferences;

    private SharedPreferences.Editor editor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_user);
        NewUserEmailIDEditText =(EditText) findViewById(R.id.NewUserEmailIdEditText);
        NewUserPasswordEditText=(EditText) findViewById(R.id.NewUserPasswordEditText);
        newUsernameEditText =(EditText) findViewById(R.id.NewUserNameEditText);
        registerActivityProgressBar = findViewById(R.id.registerUserPageProgressBar);
        sharedPreferences = getSharedPreferences(USER_DATA, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if(Build.VERSION.SDK_INT>=28){
            vibrator =(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        }


        myDB = new DataBaseHelper(this);
        easyScoresArrayList = new ArrayList<>();
        interScoresArrayList = new ArrayList<>();
        hardScoresArrayList = new ArrayList<>();
        Context context = getApplicationContext();
        InternetWorking = isDeviceConnected(context);

        getScoresFromDataBase();

        registerNewUserButton =(Button) findViewById(R.id.NewUserRegisterButton);
        registerNewUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT>=28){
                    vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
                }
                registerUser();
            }
        });

        goBackToLogin =(ImageButton) findViewById(R.id.goBacktologin);
        goBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT>=28){
                    vibrator.vibrate(VibrationEffect.createOneShot(15, VibrationEffect.DEFAULT_AMPLITUDE));
                }
                onBackPressed();
            }
        });

        termsAndConditionsTextView = (TextView) findViewById(R.id.termsAndConditionsTextView);
        termsAndConditionsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT>=28){
                    vibrator.vibrate(VibrationEffect.createOneShot(15, VibrationEffect.DEFAULT_AMPLITUDE));
                }
                AlertDialog.Builder externalDialog = new AlertDialog.Builder(RegisterNewUser.this);
                externalDialog.setTitle("Terms and Conditions");
                externalDialog.setMessage("Terms and Conditions(End User License Agreement) \n" +
                        "\n" +
                        "Last updated November 18, 2022\n" +
                        "\n" +
                        "Mad Mathematics is licensed to You (End-User) by the21codes (Licensor), for use only under the terms of this License\n" +
                        "By downloading the licensed Application from Google's software distribution platform (\"Play Store'), and any update thereto (as permitted by this Licence Agreement). You indicate that You agree to be bound by all of the terms and conditions of this Licence Agreement, and that You accept this Licence Agreement Play Store is referred to in this Licence Agreement as 'Services\n" +
                        "The parties to this licence agreement acknowledge that the Services are not a party to it and are not subject to any of its terms or obligations regarding the Licensed Application, including those relating to warranty, liability, maintenance, and support. The Licensed Application and its contents are solely the responsibility of the21codes, not the Services.\n" +
                        "The21codes acknowledges that it had the chance to review the Usage Rules and that this Licensing Agreement is not in conflict with them. This Licensing Agreement may not contain usage rules for the Licensed Application that are inconsistent with the most recent Google Play Terms of Service (Usage Rules).\n" +
                        "Mad Mathematics is licenced to You for use solely in accordance with the terms of this Licence Agreement when purchased or downloaded through the Services. All rights not specifically granted to you are reserved by the licensor. Mad Mathematics is intended for usage on gadgets running the Google operating system (\"Android\").\n" +
                        "\n" +
                        "LIST OF CONTENTS:-\n" +
                        "•\tTHE APPLICATION\n" +
                        "•\tSCOPE OF LICENCE\n" +
                        "•\tTECHNICAL REQUIREMENTS\n" +
                        "•\tNO MAINTENANCE AND SUPPORT\n" +
                        "•\tUSE OF DATA\n" +
                        "•\tUSER-GENERATED CONTRIBUTIONS\n" +
                        "•\tCONTRIBUTION LICENCE\n" +
                        "•\tLIABILITY\n" +
                        "•\tWARRANTY\n" +
                        "•\tPRODUCT CLAIMS\n" +
                        "•\tLEGAL COMPLIANCE\n" +
                        "•\tCONTACT INFORMATION\n" +
                        "•\tTERMINATION\n" +
                        "•\tTHIRD-PARTY TERMS OF AGREEMENTS AND BENEFICIARY\n" +
                        "•\tINTELLECTUAL PROPERTY RIGHTS\n" +
                        "•\tAPPLICABLE LAW\n" +
                        "•\tMISCELLANEOUS\n" +
                        "\n" +
                        "\n" +
                        "1. THE APPLICATION\n" +
                        "Mad Mathematics is a piece of software that is a Quiz Application that can be used to train and develop Arithmetic Aptitude and is customised for Android mobile devices (\"Devices). It is used to answer a variety of mathematical problems. Additionally, it is employed to monitor and record user progress when it comes to mastering mathematical reasoning.\n" +
                        "You cannot use the Licensed Application if your interactions would be subject to rules like the Federal Information Security Management Act (FISMA) or the Health Insurance Portability and Accountability Act (HIPAA), which are modified to comply with industry-specific regulations. The Licensed Application may not be used in a manner that would contravene the Gramm-Leach-Bliley Act (GLBA).\n" +
                        "\n" +
                        "2. SCOPE OF LICENCE\n" +
                        "\n" +
                        "Unless a separate licence is issued for such update, in which case the terms of that new licence will apply, this licence will also apply to any updates of the Licensed Application provided by Licensor that replace, repair, and/or augment the original Licensed Application.\n" +
                        "Except as expressly permitted by the Usage Rules and with the21codes's prior written agreement, you are not permitted to share, make the Licensed Application available to third parties, sell, rent, lend, lease, or otherwise disseminate the Licensed Application.\n" +
                        "Except with the21codes's prior written authorization, you are not allowed to attempt to reverse engineer, translate, disassemble, integrate, decompile, remove, edit, combine, create derivative works or updates of, adapt, or extract the source code of the Licensed Application, in whole or in part.\n" +
                        "Except as specifically permitted by this licence and the Usage Rules, you are not permitted to reproduce or modify the Licensed Application in whole or in part. In accordance with the terms of this licence, the Usage Rules, and any other terms and conditions that apply to the device or software used, you may only generate and store copies on devices that You own or control for backup purposes. Any intellectual property notices cannot be removed. You agree that these copies will never be accessed by anyone else without your permission. You must delete the Licensed Application from the Devices before selling them to a third party.\n" +
                        "Violations of the obligations mentioned above, as well as the attempt of such infringement, may be subject to prosecution and damages.\n" +
                        "Licensor reserves the right to modify the terms and conditions of licensing\n" +
                        "Except as specifically permitted by this licence and the Usage Rules, you are not permitted to reproduce or modify the Licensed Application in whole or in part. In accordance with the terms of this licence, the Usage Rules, and any other terms and conditions that apply to the device or software used, you may only generate and store copies on devices that You own or control for backup purposes. Any intellectual property notices cannot be removed. You agree that these copies will never be accessed by anyone else without your permission. You must delete the Licensed Application from the Devices before selling them to a third party.\n" +
                        "\n" +
                        "3. TECHNICAL REQUIREMENTS\n" +
                        "The Licensed Application needs Android firmware version 8.0 or later. The licensor advises utilizing the most recent firmware version.\n" +
                        "Licensor makes an effort to maintain the Licensed Application up to date in order to ensure compatibility with updated/modified firmware and new hardware. You lack the authority to demand such an update.\n" +
                        "You acknowledge that it is Your duty to verify and determine that the app end-user device on which You plan to run the licenced application complies with the technical requirements listed above.\n" +
                        "The licensee retains the right to change the technical requirements whenever it sees fit.\n" +
                        "\n" +
                        "4. NO MAINTENANCE OR SUPPORT\n" +
                        "The21codes is under no express or implied obligation to offer any technical or other support for the Licensed Application. The21codes and the End-User agree that the Services are under no obligation to provide any maintenance and support services with regard to the Licensed Application.\n" +
                        "\n" +
                        "5. USE OF DATA\n" +
                        "You agree that the use of the downloaded content from the Licensed Application and your personal information (such as the login information) by the Licensor is subject to your legal agreements with the Licensor and the Licensor's privacy policy, both of which can be found on the Settings page under About Mad Mathematics>Privacy Policy.\n" +
                        "You agree that the Licensor may periodically collect and use technical data and related information about your device, system, application software, and peripherals for purposes of offering product support, facilitating software updates, and for purposes of providing other services to you (if any) associated with the Licensed Application. As long as it's in a form that doesn't allow for personal identification, the licensor may also use this information to enhance its offerings, offer you services, or develop new technologies.\n" +
                        "\n" +
                        "6. USER-GENERATED CONTRIBUTIONS\n" +
                        "I.\tUsers cannot submit or post material using the Licensed Application. We may offer you the chance to create, submit, post, display, perform, publish, distribute, or broadcast content and materials to us or in the Licensed Application, such as but not limited to text, writings, video, audio, photographs, graphics, comments, suggestions, or other material (collectively, \"Contributions\"). The Licensed Application's other users as well as third-party websites and applications may be able to access contributions. As a result, the Licensed Application Privacy Policy may be applied to any Contributions you transmit. When you develop or make any Contributions available, you guarantee and represent that\n" +
                        "II.\tThe creation, distribution, transmission, public display, or performance, and the accessing, downloading, or copying of your Contributions do not and will not infringe the proprietary rights, including but not limited to the copyright, patent, trademark, trade secret, or moral nights of any third party \n" +
                        "III.\tYou are the creator and owner of or have the necessary licences, rights, consents, releases, and permissions to use and to authorise us, the Licensed Application, and other users of the Licensed Application to use your Contributions in any manner contemplated by the Licensed Application and this Licence Agreement.\n" +
                        "IV.\tTo use the name or likeness of each and every such identifiable individual person to enable inclusion and use of your Contributions in any manner contemplated by the Licensed Application and this Licence Agreement, you have the written consent, release, and/or permission of each and every identifiable individual person in your Contributions.\n" +
                        "V.\tYour Contributions are not false, inaccurate, or misleading.\n" +
                        "VI.\tYour Contributions are not unsolicited or unauthorized advertising promotional materials, pyramid schemes, chain letters, spam, mass mailings, or other forms of solicitation\n" +
                        "VII.\tYour contributions are not inappropriate in any other way, including being obscene, vulgar, lascivious, filthy, violent, harassing, libellous, or slanderous (as determined by us). You do not mock, denigrate, abuse, intimidate, or include anyone in Your Contributions.\n" +
                        "VIII.\tYour Contributions are not used to harass or threaten (in the legal sense of those terms) any other person and to promote violence against a specific person or class of people\n" +
                        "IX.\tYour Contributions do not violate any applicable law regulation or rule 10. Your Contributions do not violate the privacy or publicity rights of any third party\n" +
                        "X.\tYour Contributions do not contravene any laws pertaining to child pornography or other laws that aim to safeguard the health or welfare of minors. There are no offensive racial, ethnic, national, gendered, sexual, or physical preference remarks in your Submissions.\n" +
                        "XI.\tYour Contributions do not otherwise violate or link to material that violates, any provision of this Licence Agreement, or any applicable law or regulation.\n" +
                        "\n" +
                        "Your permission to use the Licensed Application may be terminated or suspended, among other things, if you use it in violation of the aforementioned.\n" +
                        "\n" +
                        "7. CONTRIBUTION LICENCE\n" +
                        "\n" +
                        "You acknowledge and consent that we may access, store, process, and utilise any information and personal data you give in accordance with the Privacy Policy's guidelines and your preferences (including settings). You acknowledge and agree that we may use and share any recommendations or other feedback you provide regarding the Licensed Application for any purpose without paying you any remuneration.\n" +
                        "Your Contributions are not our property, according to us. All of your contributions, as well as any associated intellectual property rights or other proprietary rights, remain wholly yours. We disclaim all responsibility for any assertions or representations made by you in any part of the Licensed Application through your Contributions. You expressly agree to absolve us of all liability and desist from taking any legal action against us in relation to your Contributions, which you are entirely liable for to the Licensed Application.\n" +
                        "\n" +
                        "8. LIABILITY\n" +
                        "The extent of the licensor's liability for breach of contract and tort shall be confined to willful misconduct and egregious carelessness. Licensor will only be held accountable for even the slightest negligence if there has been a violation of a contract's fundamental obligations (cardinal obligations). Anyhow, liability will be restricted to reasonably foreseeable and customary damages. The aforementioned restriction does not cover harm to life, limb, or health.\n" +
                        "As stated in Section 2 of this License Agreement, the Licensor disclaims all liability and responsibility for any damages that may result from a breach of those obligations. To the extent permitted by any third-party terms and conditions of use, You must use the backup features of the Licensed Application to prevent data loss. You are aware of this in the event that the Licensed Application is changed or interfered with. Your use of the Licensed Application will be prohibited.\n" +
                        "\n" +
                        "9. WARRANTY\n" +
                        "At the time of Your download, the licensor guarantees that the licenced application is free of viruses, spyware, trojan horses, and other malware. The developer guarantees that the Licensed Application performs as specified in the user manual.\n" +
                        "No warranty is given for the Licensed Application if it cannot be used on the device, if it has been altered by a third party, if it has been handled improperly or culpably, if it has been combined or installed with inappropriate hardware or software, if it has been used with inappropriate accessories, or if there are any other factors outside of the21codes's control that affect the executability of the Licensed Application.\n" +
                        "The Licensed Application must be checked out right away after installation, and any problems must be reported to the21codes right away using the email provided in the Contact Information. If the defect report is sent by email within ten (10) days after finding, it will be taken into account and further investigated. The21codes has the right to choose how to address the problem if we determine that the Licensed Application is flawed, including by fixing the issue or providing a replacement product.\n" +
                        "In the event that any applicable warranty is not met by the Licensed Application. Your Licensed Application purchase fee will be returned to You upon Notification of the Services Store Operator. The Services Store Operator will have no additional warranty obligations with regard to the Licensed Application, and any other losses, claims, damages, liabilities, expenditures, and costs due to any negligence to uphold any warranty, to the fullest extent authorized by applicable law.\n" +
                        "If the user is an entrepreneur, any claim based on flaws expires after a legal deadline of twelve (12) months following the user's receipt of the Licensed Application. Users who are customers are subject to the statutory limitations stipulated by law.\n" +
                        "10. PRODUCT CLAIMS\n" +
                        "The End-User and the21codes agree that the21codes, not the Services, is in charge of resolving any disputes arising from the End-or User's any third party's possession and/or use of the Licensed Application, including but not limited to::\n" +
                        "i.\tproduct liability claims,\n" +
                        "ii.\tany claim that the Licensed Application fails to conform to any applicable legal or regulatory requirement, and\n" +
                        "iii.\tclaims arising under consumer protection, privacy, or similar legislation\n" +
                        "\n" +
                        "11. LEGAL COMPLIANCE\n" +
                        "You affirm and warrant that You are not a citizen of a nation that the US Government has placed under an embargo or that it has identified as a nation that supports terrorism, and that You are not included on any US Government list of prohibited or restricted parties.\n" +
                        "\n" +
                        "12. CONTACT INFORMATION\n" +
                        "For general inquiries, complaints, questions or claims concerning the Licensed Application, please contact: the21codes@gmail.com\n" +
                        "\n" +
                        "13. TERMINATION\n" +
                        "\n" +
                        "The licence is in effect until it is terminated by either You or the21codes. If you violate any of the terms of this licence, the21codes will instantly and without prior notification terminate your rights under this licence. Upon expiration of the License. You must stop using the licenced application and get rid of all copies you may have made, entire or partial.\n" +
                        "\n" +
                        "14. THIRD-PARTY TERMS OF AGREEMENTS AND BENEFICIARY\n" +
                        "The21Codes represents and warrants that when using Licensed Application, The21Codes will abide by all applicable third-party terms of agreement.\n" +
                        "According to Section 9 of the Minimum Terms of Developer's End-User Licence Agreement, Google's subsidiaries shall be third-party beneficiaries of this End User Licence Agreement, and upon Your acceptance of its terms and conditions, Google shall have the right (and shall be deemed to have accepted the right) to enforce this End User Licence Agreement against You.\n" +
                        "\n" +
                        "15. INTELLECTUAL PROPERTY RIGHTS\n" +
                        "The21codes and the End User acknowledge that the21codes, and not the Services, will be solely responsible for the investigation, defence, settlement, and discharge of any such intellectual property infringement claims in the event of any third-party claim that the Licensed Application or the End-Users possession and use of That Licensed Application infringes on the third party's intellectual property rights.\n" +
                        "\n" +
                        "16. APPLICABLE LAW\n" +
                        "\n" +
                        "This Licence Agreement is governed by the laws of excluding its conflicts of law rules.\n" +
                        "\n" +
                        "17. MISCELLANEOUS\n" +
                        "The validity of the remaining elements of this agreement will not be impacted if any of the terms are found to be or turn out to be invalid. Invalid phrases will be swapped out for ones that are constructed to accomplish the main goal and are valid.\n" +
                        "Changes and adjustments to collateral agreements must be made in writing to be enforceable. It is only possible to waive the previous clause in writing.\n");
                externalDialog.setNeutralButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                externalDialog.setIcon(R.drawable.logo_no_background);
                AlertDialog external = externalDialog.create();
                external.show();
            }
        });
    }

    private void registerUser() {
        String userName = newUsernameEditText.getText().toString().trim();
        String EmailID = NewUserEmailIDEditText.getText().toString().trim();
        String password = NewUserPasswordEditText.getText().toString().trim();

        String easyScores = availableEasyScores.toString();
        String interScores = availableInterScores.toString();
        String hardScores = availableHardScores.toString();

        String EasyTimeTaken = availableEasyTime.toString();
        String InterTimeTaken = availableInterTime.toString();
        String HardTimeTaken = availableHardTime.toString();

        if(userName.isEmpty()){
            newUsernameEditText.setError("Please choose a username");
            newUsernameEditText.requestFocus();
            return;
        }

        if(password.isEmpty() || (password.length()>12 || password.length()<6)){
            NewUserPasswordEditText.setError("Please pick a valid password of length between 6-12");
            NewUserPasswordEditText.requestFocus();
            return;
        }

        if(EmailID.isEmpty()){
            NewUserEmailIDEditText.setError("Please Enter your Email Address");
            NewUserEmailIDEditText.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(EmailID).matches()){
            NewUserEmailIDEditText.setError("Please enter a Email Address");
            NewUserEmailIDEditText.requestFocus();
            return;
        }

        globalEmail = EmailID;
        globalPassWord = password;

        if(InternetWorking){
            mAuth = FirebaseAuth.getInstance();
        try {
            mAuth.fetchSignInMethodsForEmail(EmailID)
                    .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                        @Override
                        public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                            if (task.getResult().getSignInMethods().size() == 0) {
                                registerActivityProgressBar.setVisibility(View.VISIBLE);
                                mAuth.createUserWithEmailAndPassword(EmailID, password)
                                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if(task.isSuccessful()){
                                                    UserData newUser = new UserData(userName, EmailID, password, easyScores, interScores, hardScores, EasyTimeTaken, InterTimeTaken,HardTimeTaken);

                                                    FirebaseDatabase.getInstance().getReference("Users")
                                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                            .setValue(newUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if(task.isSuccessful()){
                                                                        LoginTheNewRegisteredUser(EmailID, password);
                                                                        registerActivityProgressBar.setVisibility(View.GONE);
                                                                    }else{
                                                                        Toast.makeText(RegisterNewUser.this, "Failed, Please Try Again", Toast.LENGTH_LONG).show();
                                                                        registerActivityProgressBar.setVisibility(View.GONE);
                                                                    }
                                                                }
                                                            });

                                                }
                                            }
                                        });

                            } else {
                                NewUserEmailIDEditText.setError("Email Already Exists");
                                NewUserEmailIDEditText.requestFocus();
                                return;
                            }
                        }
                    });

        }catch (Exception e) {
            Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            e.printStackTrace();

        }
        }else{
            Toast.makeText(this, "Please Connect to Internet\n To Register", Toast.LENGTH_SHORT).show();
        }
    }

    private void getScoresFromDataBase(){
        try {
            Cursor UnorderedEasyData = myDB.getScores(4);
            if(UnorderedEasyData.getCount() == 0){
                availableEasyScores= new StringBuilder("0");
                availableEasyTime = new StringBuilder("0");
            }else{
                while(UnorderedEasyData.moveToNext()){
                    easyScoresArrayList.add(new HighScores(UnorderedEasyData.getInt(0), UnorderedEasyData.getString(1), 4, UnorderedEasyData.getString(2)));
                }
                scoreAndTimeGetter(easyScoresArrayList, 1);
            }

            Cursor UnorderedInterData = myDB.getScores(5);
            if(UnorderedInterData.getCount() == 0){
                availableInterScores = new StringBuilder("0");
                availableInterTime = new StringBuilder("0");
            }else{
                while(UnorderedInterData.moveToNext()){
                    interScoresArrayList.add(new HighScores(UnorderedInterData.getInt(0), UnorderedInterData.getString(1), 4, UnorderedInterData.getString(2)));
                }
                scoreAndTimeGetter(interScoresArrayList, 2);
            }

            Cursor UnorderedHardData = myDB.getScores(6);
            if(UnorderedHardData.getCount() == 0){
                availableHardScores = new StringBuilder("0");
                availableHardTime = new StringBuilder("0");
            }else{
                while(UnorderedHardData.moveToNext()){
                    hardScoresArrayList.add(new HighScores(UnorderedHardData.getInt(0), UnorderedHardData.getString(1), 4, UnorderedHardData.getString(2)));
                }
                scoreAndTimeGetter(hardScoresArrayList, 3);
            }

        }catch(Exception e){

            availableEasyScores= new StringBuilder("0");
            availableEasyTime = new StringBuilder("0");

            availableInterScores = new StringBuilder("0");
            availableInterTime = new StringBuilder("0");

            availableHardScores = new StringBuilder("0");
            availableHardTime = new StringBuilder("0");

            e.printStackTrace();
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

    private void scoreAndTimeGetter(ArrayList<HighScores> arrayList, int type) {
            StringBuilder newFireBaseScore = new StringBuilder();
            StringBuilder newFireBaseTimeTaken = new StringBuilder();

            for (int i = 0; i < arrayList.size(); i++) {
                if (i == arrayList.size() - 1 || i == 14) {
                    newFireBaseScore.append(arrayList.get(i).getScore().toString());
                    newFireBaseTimeTaken.append(arrayList.get(i).getTimeTaken().toString());
                    break;
                } else {
                    newFireBaseScore.append(arrayList.get(i).getScore().toString() + separator);
                    newFireBaseTimeTaken.append(arrayList.get(i).getTimeTaken().toString() + separator);
                }
            }

            if(type ==1){
                availableEasyScores = new StringBuilder(newFireBaseScore);
                availableEasyTime = new StringBuilder(newFireBaseTimeTaken);
            }else if(type==2){
                availableInterScores = new StringBuilder(newFireBaseScore);
                availableInterTime = new StringBuilder(newFireBaseTimeTaken);
            }else if(type ==3){
                availableHardScores = new StringBuilder(newFireBaseScore);
                availableHardTime = new StringBuilder(newFireBaseTimeTaken);
            }


        }

    private void LoginTheNewRegisteredUser(String emailId, String passWord) {
        registerActivityProgressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(emailId, passWord).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    openHomePageActivity();
                    registerActivityProgressBar.setVisibility(View.GONE);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
                registerActivityProgressBar.setVisibility(View.GONE);
                Toast.makeText(RegisterNewUser.this, "Unable to Login", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void openHomePageActivity() {
        AlertDialog.Builder logoutDialog = new AlertDialog.Builder(RegisterNewUser.this);
        logoutDialog.setTitle("Remember Your Login Details");
        logoutDialog.setMessage("So you don't have to login everytime you launch The app?");
        logoutDialog.setPositiveButton("Remember Me", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(Build.VERSION.SDK_INT>=28){
                    vibrator.vibrate(VibrationEffect.createOneShot(15, VibrationEffect.DEFAULT_AMPLITUDE));
                }
                storeUserDataInSharedPreferences(globalEmail, globalPassWord);
                Intent openHomePageIntent = new Intent(getApplicationContext(), HomePage.class);
                startActivity(openHomePageIntent);
            }
        });
        logoutDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(Build.VERSION.SDK_INT>=28){
                    vibrator.vibrate(VibrationEffect.createOneShot(15, VibrationEffect.DEFAULT_AMPLITUDE));
                }
                Intent openHomePageIntent = new Intent(getApplicationContext(), HomePage.class);
                startActivity(openHomePageIntent);
            }
        });
        AlertDialog dialog2 = logoutDialog.create();
        dialog2.show();
    }

    private void storeUserDataInSharedPreferences(String emailId, String passWord) {
        editor = getSharedPreferences(USER_DATA, MODE_PRIVATE).edit();
        editor.putBoolean("checked", true);
        editor.putString("userPassword", passWord);
        editor.putString("userEmail", emailId);
        editor.apply();
    }
}