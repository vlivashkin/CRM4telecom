package com.crm4telecom.web.beans;

import com.crm4telecom.ejb.OrderManagerLocal;
import com.crm4telecom.jpa.Order;
import com.crm4telecom.web.beans.util.Comment;
import com.google.gson.Gson;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;

@ManagedBean
@SessionScoped
public class OrderCommentBean implements Serializable {

    @EJB
    private OrderManagerLocal om;

    private Order order;
    private List<Comment> comments;
    private String text;

    public void init(Order order) {
        this.order = order;
        if (order != null && order.getComments() != null) {
            Gson gson = new Gson();
            String json = order.getComments().replace("\'", "\"");
            comments = gson.fromJson(json, List.class);
        } else {
            comments = new ArrayList<>();
        }
    }

    public List<Comment> getComments() {
        return comments;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void addComment() {
        String json;
        Gson gson = new Gson();

        if (text != null && !"".equals(text)) {
            Comment comment = new Comment(text);

            comments.add(comment);
            json = gson.toJson(comments);
            order.setComments(json);
            System.out.println(json);
            System.out.println(order.toString());
            om.modifyOrder(order);
            text = "";
        }
    }
}
