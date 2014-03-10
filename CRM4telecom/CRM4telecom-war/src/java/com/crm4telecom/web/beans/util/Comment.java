package com.crm4telecom.web.beans.util;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable {
    String text;
    Date date;

    public Comment() {
    }

    public Comment(String text) {
        this.text = text;
        this.date = new Date();
    }

    public String getText() {
        return text;
    }

    public void setText(String comment) {
        this.text = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
