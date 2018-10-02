package com.rustedbrain.diploma.journeyfeverservice.model.persistence.travel;

import com.rustedbrain.diploma.journeyfeverservice.model.persistence.DatabaseEntity;
import com.rustedbrain.diploma.journeyfeverservice.model.persistence.security.User;

import javax.persistence.*;

@Entity
@Table(name = "comment")
public class Comment extends DatabaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
    @Column(name = "text", length = 512)
    private String text;
    @ManyToOne(fetch = FetchType.LAZY)
    private Showplace showplace;

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

    public Showplace getShowplace() {
        return showplace;
    }

    public void setShowplace(Showplace showplace) {
        this.showplace = showplace;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "user=" + user +
                ", text='" + text + '\'' +
                ", showplace=" + showplace +
                '}';
    }
}
