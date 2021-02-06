package com.automation.testapplication;

public class SessionData {

    private String csrf;
    private String session;

    public SessionData(String csrf, String session) {
        this.csrf = csrf;
        this.session = session;
    }

    public String getCsrf() {
        return csrf;
    }

    public void setCsrf(String csrf) {
        this.csrf = csrf;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

}
