package com.penelope.ketodiet.data.user;

public class User {

    private String uid;
    private String id;
    private long created;

    public User() {
    }

    public User(String uid, String id) {
        this.uid = uid;
        this.id = id;
        this.created = System.currentTimeMillis();
    }

    public String getUid() {
        return uid;
    }

    public String getId() {
        return id;
    }

    public long getCreated() {
        return created;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCreated(long created) {
        this.created = created;
    }
}
