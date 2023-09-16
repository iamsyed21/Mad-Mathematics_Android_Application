package com.TwentyOneCodes.madmathematics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class MyAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<MyModel> modelArrayList;



    public MyAdapter(Context context, ArrayList<MyModel> modelArrayList){
        this.context = context;
        this.modelArrayList = modelArrayList;
    }

    @Override
    public int getCount() {
        return modelArrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.on_boarding_card, container, false);

        ImageView imageView = view.findViewById(R.id.welcomeTile);
        TextView heading = view.findViewById(R.id.Header_Text);
        TextView description = view.findViewById(R.id.descriptionText);
        TextView conclusion = view.findViewById(R.id.conclusionText);

        MyModel model = modelArrayList.get(position);
        String headingText = model.getHeading();
        String desc = model.getDescription();
        String conclusionText = model.getConclusion();
        int image_placer = model.getImage();


        imageView.setImageResource(image_placer);
        heading.setText(headingText);
        description.setText(desc);
        conclusion.setText(conclusionText);


        container.addView(view, position);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
