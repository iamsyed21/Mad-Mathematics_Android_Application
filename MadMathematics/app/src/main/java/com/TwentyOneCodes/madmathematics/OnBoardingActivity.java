package com.TwentyOneCodes.madmathematics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;

public class OnBoardingActivity extends AppCompatActivity {

    SharedPreferences firstLoginSharedPreferences;
    SharedPreferences.Editor firstLoginEditor;
    private ViewPager viewPager;
    private ArrayList<MyModel> modelArrayList;
    private MyAdapter myAdapter;
    private Button skipButton, getStartedButton;
    private ImageButton nextButton, previousButton;
    ImageView firstDot, secondDot, thirdDot, fourthDot, fifthDot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);
        firstLoginSharedPreferences = getSharedPreferences(FIRST_LOGIN, MODE_PRIVATE);
        firstLoginEditor = getSharedPreferences(FIRST_LOGIN, MODE_PRIVATE).edit();
        firstLoginEditor.putBoolean("First_Login", false);
        firstLoginEditor.apply();
        viewPager = (ViewPager) findViewById(R.id.onBoardingViewPager);
        skipButton =(Button) findViewById(R.id.skipTheSlidesButton);
        getStartedButton=(Button) findViewById(R.id.getStartedButton);

        nextButton =(ImageButton) findViewById(R.id.nextSlideButton);
        previousButton=(ImageButton) findViewById(R.id.previousSlideButton);

        firstDot = (ImageView) findViewById(R.id.firstSlideIndicator);
        secondDot = (ImageView) findViewById(R.id.secondSlideIndicator);
        thirdDot = (ImageView) findViewById(R.id.thirdSlideIndicator);
        fourthDot = (ImageView) findViewById(R.id.fourthSlideIndicator);
        fifthDot = (ImageView) findViewById(R.id.fifthSlideIndicator);



        loadCards();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position){
                    case 0:
                        firstDot.setImageResource(R.drawable.selected);
                        previousButton.setEnabled(false);
                        previousButton.setAlpha(0.4f);
                        secondDot.setImageResource(R.drawable.unselected);
                        thirdDot.setImageResource(R.drawable.unselected);
                        fourthDot.setImageResource(R.drawable.unselected);
                        fifthDot.setImageResource(R.drawable.unselected);
                        nextButton.setEnabled(true);
                        nextButton.setAlpha(1.0f);
                        break;
                    case 1:
                        previousButton.setEnabled(true);
                        previousButton.setAlpha(1.0f);
                        firstDot.setImageResource(R.drawable.unselected);
                        secondDot.setImageResource(R.drawable.selected);
                        thirdDot.setImageResource(R.drawable.unselected);
                        fourthDot.setImageResource(R.drawable.unselected);
                        fifthDot.setImageResource(R.drawable.unselected);
                        nextButton.setEnabled(true);
                        nextButton.setAlpha(1.0f);

                        break;
                    case 2:
                        previousButton.setEnabled(true);
                        previousButton.setAlpha(1.0f);
                        firstDot.setImageResource(R.drawable.unselected);
                        secondDot.setImageResource(R.drawable.unselected);
                        thirdDot.setImageResource(R.drawable.selected);
                        fourthDot.setImageResource(R.drawable.unselected);
                        fifthDot.setImageResource(R.drawable.unselected);
                        nextButton.setEnabled(true);
                        nextButton.setAlpha(1.0f);

                        break;
                    case 3:
                        previousButton.setEnabled(true);
                        previousButton.setAlpha(1.0f);
                        firstDot.setImageResource(R.drawable.unselected);
                        secondDot.setImageResource(R.drawable.unselected);
                        thirdDot.setImageResource(R.drawable.unselected);
                        fourthDot.setImageResource(R.drawable.selected);
                        fifthDot.setImageResource(R.drawable.unselected);
                        nextButton.setEnabled(true);
                        nextButton.setAlpha(1.0f);
                        break;
                    case 4:
                        previousButton.setEnabled(true);
                        previousButton.setAlpha(1.0f);
                        firstDot.setImageResource(R.drawable.unselected);
                        nextButton.setEnabled(false);
                        nextButton.setAlpha(0.4f);
                        secondDot.setImageResource(R.drawable.unselected);
                        thirdDot.setImageResource(R.drawable.unselected);
                        fourthDot.setImageResource(R.drawable.unselected);
                        fifthDot.setImageResource(R.drawable.selected);
                        getStartedButton.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int x= viewPager.getCurrentItem();
                viewPager.setCurrentItem(x+1, true);
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int x= viewPager.getCurrentItem();
                viewPager.setCurrentItem(x-1, true);
            }
        });

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(3, true);
                viewPager.setCurrentItem(4, true);

            }
        });

        getStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              startLoginPageActivity();
            }
        });



    }

    private void startLoginPageActivity() {
        Intent startLoginIntent = new Intent(this, MainActivity.class);
        startActivity(startLoginIntent);
    }

    private void loadCards() {
        modelArrayList = new ArrayList<>();
        modelArrayList.add(new MyModel("WELCOME","DEVELOP YOUR ARITHMETIC SKILLS\nWITH","MAD MATHEMATICS", R.drawable.logo_no_background));
        modelArrayList.add(new MyModel("TRAIN YOUR BRAIN","By Solving Unlimited Questions\nin","RAPID QUIZ ROUNDS", R.drawable.brain));
        modelArrayList.add(new MyModel("THREE LEVELS","Of Arithmetic Questions\n Each Tougher Than The Other","ENDLESS QUESTIONS", R.drawable.progress_three));
        modelArrayList.add(new MyModel("DATA IS BACKED UP","Your Scores Are Always Safe\n In The Cloud","LOGIN FROM ANYWHERE", R.drawable.cloud_picture));
        modelArrayList.add(new MyModel("GROW","Your In-Game Statistics Are\n Represented In Details, So You Can","TRACK YOUR PROGRESS", R.drawable.graph_pics));

        myAdapter = new MyAdapter(this, modelArrayList);
        viewPager.setAdapter(myAdapter);
        viewPager.setPadding(50,0,50,0);

    }


}