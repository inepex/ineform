package com.inepex.ineFrame.client.navigation.messagepanel;

public class Message {
    private String message;
    private boolean isError;
    private int delayMillis;

    public Message(String message, boolean isError, int delayMillis) {
        super();
        this.message = message;
        this.isError = isError;
        this.delayMillis = delayMillis;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isError() {
        return isError;
    }

    public void setAlert(boolean isAlert) {
        this.isError = isAlert;
    }

    public int getDelayMillisec() {
        return delayMillis;
    }

    public void setDelayMillisec(int delayMillisec) {
        this.delayMillis = delayMillisec;
    }

}
