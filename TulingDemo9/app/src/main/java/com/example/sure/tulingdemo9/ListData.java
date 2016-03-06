package com.example.sure.tulingdemo9;

/**
 * Created by SURE on 2016/3/6.
 */
public class ListData {
    public static final int SEND = 1;
    public static final int  RECIVER = 2;
    private String content;
    private int flag;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public ListData(String content,int flag){
        setContent(content);
        setFlag(flag);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
