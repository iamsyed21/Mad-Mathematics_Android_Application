package com.TwentyOneCodes.madmathematics;

public class UserData {
    private String Username, Email, PassWord;
    private String EasyScores, InterScores, HardScores, easyTime, interTime, hardTime;


    public UserData(String username, String email, String passWord, String easyScores, String interScores, String hardScores, String EasyTimeTaken, String InterTimeTaken, String HardTimeTaken ) {
        Username = username;
        Email = email;
        PassWord = passWord;
        EasyScores = easyScores;
        InterScores = interScores;
        HardScores = hardScores;
        easyTime = EasyTimeTaken;
        interTime = InterTimeTaken;
        hardTime = HardTimeTaken;
    }



    public String getEasyTime() {
        return easyTime;
    }

    public void setEasyTime(String easyTime) {
        this.easyTime = easyTime;
    }

    public String getInterTime() {
        return interTime;
    }

    public void setInterTime(String interTime) {
        this.interTime = interTime;
    }

    public String getHardTime() {
        return hardTime;
    }

    public void setHardTime(String hardTime) {
        this.hardTime = hardTime;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassWord() {
        return PassWord;
    }

    public void setPassWord(String passWord) {
        PassWord = passWord;
    }

    public String getEasyScores() {
        return EasyScores;
    }

    public void setEasyScores(String easyScores) {
        EasyScores = easyScores;
    }

    public String getInterScores() {
        return InterScores;
    }

    public void setInterScores(String interScores) {
        InterScores = interScores;
    }

    public String getHardScores() {
        return HardScores;
    }

    public void setHardScores(String hardScores) {
        HardScores = hardScores;
    }
}
