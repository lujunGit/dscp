package com.sinohealth.dscp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Qcc implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String text;
    private Date create_time;
    private Date update_time;
    private Integer userId;

    public Qcc(Integer id, String text, Date create_time, Date update_time, Integer userId) {
        this.id = id;
        this.text = text;
        this.create_time = create_time;
        this.update_time = update_time;
        this.userId = userId;
    }

    public Qcc() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Qcc{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", create_time=" + create_time +
                ", update_time=" + update_time +
                ", userId=" + userId +
                '}';
    }
}
