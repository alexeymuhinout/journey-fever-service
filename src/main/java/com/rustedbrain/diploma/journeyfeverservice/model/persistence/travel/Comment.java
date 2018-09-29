package com.rustedbrain.diploma.journeyfeverservice.model.persistence.travel;

import com.rustedbrain.diploma.journeyfeverservice.model.persistence.DatabaseEntity;
import com.rustedbrain.diploma.journeyfeverservice.model.persistence.security.User;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Comment extends DatabaseEntity {

    private User user;
    @Column(name = "text")
    private String text;
    private TravelPoint point;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public TravelPoint getPoint() {
        return point;
    }

    public void setPoint(TravelPoint point) {
        this.point = point;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "user=" + user +
                ", text='" + text + '\'' +
                ", point=" + point +
                '}';
    }
}
