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
    private Place place;

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

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        return user.equals(comment.user) && text.equals(comment.text) && place.equals(comment.place);
    }

    @Override
    public int hashCode() {
        int result = user.hashCode();
        result = 31 * result + text.hashCode();
        result = 31 * result + place.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "user=" + user +
                ", text='" + text + '\'' +
                ", place=" + place +
                '}';
    }
}
