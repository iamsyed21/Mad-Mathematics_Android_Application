package com.TwentyOneCodes.madmathematics;


public class HighScores {
    private int ID;
    private String Score;
    private int type;
    private String timeTaken;

    public HighScores(int ID, String score, int type, String timeTaken) {
        this.ID = ID;
        Score = score;
        this.type = type;
        this.timeTaken = timeTaken;

    }

    public String getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(String timeTaken) {
        this.timeTaken = timeTaken;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
