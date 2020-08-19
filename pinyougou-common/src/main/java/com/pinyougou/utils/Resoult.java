package com.pinyougou.utils;

import java.io.Serializable;

public class Resoult implements Serializable {

    private String message;

    private int items;

    private int come;  //1代表成功，0代表失败

    public int getCome() {
        return come;
    }

    public void setCome(int come) {
        this.come = come;
    }

    public int getItems() {
        return items;
    }

    public void setItems(int items) {
        this.items = items;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Resoult(String message) {
        this.message = message;
    }

    public Resoult() {
    }

    public Resoult(String message, int items) {
        this.message = message;
        this.items = items;
    }



}
