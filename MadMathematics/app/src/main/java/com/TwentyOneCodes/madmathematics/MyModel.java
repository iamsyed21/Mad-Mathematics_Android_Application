package com.TwentyOneCodes.madmathematics;

public class MyModel {
    String heading, description, conclusion;
    int image;

    public MyModel(String heading, String description, String conclusion, int image) {
        this.heading = heading;
        this.description = description;
        this.conclusion = conclusion;
        this.image = image;
    }


    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
