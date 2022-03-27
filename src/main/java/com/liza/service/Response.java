package com.liza.service;

public class Response {
    private int status;
    private Body main;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Body getMain() {
        return main;
    }

    public void setMain(Body main) {
        this.main = main;
    }
}
