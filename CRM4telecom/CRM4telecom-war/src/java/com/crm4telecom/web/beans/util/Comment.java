package com.crm4telecom.web.beans.util;

import com.crm4telecom.web.util.JSFHelper;
import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable {

    String owner;
    String text;
    Date date;

    public Comment() {
    }

    public Comment(String text) {
        this.text = text;
        this.date = new Date();
        JSFHelper helper = new JSFHelper();
        this.owner = helper.getSessionAttribute(String.class, "login");
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
