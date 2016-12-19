package com.TomyYan.TheFirstRankProject;

/**
 * Created by dell on 2016/10/12.
 */
public class GetTalkWord {
    private String[] OthersTalks;
    private String[] myTalks;
    public String[] getOthersWord(String user,String team){
        OthersTalks=new String[]{"你好","how are you?"};
        return OthersTalks;
    }
    public String[] getMyWord(String user,String team){
        myTalks=new String[]{"I am fine!"};
        return myTalks;
    }
}
