package com.TwentyOneCodes.madmathematics;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class scoreAdaptor extends RecyclerView.Adapter<scoreAdaptor.scoreAdaptorHolder> {
    private ArrayList<HighScores> scores;
    private final int listLimit=15;

    public static class scoreAdaptorHolder extends RecyclerView.ViewHolder{
        TextView scoresTextView;

        public scoreAdaptorHolder(@NonNull View itemView) {
            super(itemView);
            scoresTextView = itemView.findViewById(R.id.scoreTextView);
        }
    }

    public scoreAdaptor(ArrayList<HighScores> scores){
        this.scores = scores;
    }


    @NonNull
    @Override
    public scoreAdaptorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_cell, parent, false);
        scoreAdaptorHolder scoreAdaptorHolder = new scoreAdaptorHolder(v);
        return scoreAdaptorHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull scoreAdaptorHolder holder, int position) {
        HighScores current = scores.get(position);
        holder.scoresTextView.setText(current.getScore());
    }

    @Override
    public int getItemCount() {
        if(scores.size() > listLimit){
            return listLimit;
        }else{
            return scores.size();
        }

    }
}
