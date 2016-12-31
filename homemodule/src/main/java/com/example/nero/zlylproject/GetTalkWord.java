package com.example.nero.zlylproject;


public class GetTalkWord {
    private String[] OthersTalks;
    private String[] myTalks;
    public String[] getOthersWord(String user,String team){
        OthersTalks=new String[]{"你好"};
        return OthersTalks;
    }
    public String[] getMyWord(String user,String team){
        myTalks=new String[]{"hallo"};
        return myTalks;
    }
}
