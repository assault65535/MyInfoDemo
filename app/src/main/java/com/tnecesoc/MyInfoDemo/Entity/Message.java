package com.tnecesoc.MyInfoDemo.Entity;

import java.util.Date;

/**
 * Created by Tnecesoc on 2016/12/24.
 */
public class Message {

    private String from, to, content;

    private Date date;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "[message from: " + from + ", to: " + to + ", at: " + date + ", content: " + content + "]";
    }

    public String theManTalkWith(String perspective) {
        return to.equals(perspective) ? from : to;
    }

}
