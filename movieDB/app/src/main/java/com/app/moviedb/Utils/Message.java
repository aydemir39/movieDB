package com.app.moviedb.Utils;

public class Message {

    int stateNavigationAnimation;

    public Message(int state) {
        stateNavigationAnimation = state;
    }

    public int getStateNavigationAnimation() {
        return stateNavigationAnimation;
    }

    public void setStateNavigationAnimation(int stateNavigationAnimation) {
        this.stateNavigationAnimation = stateNavigationAnimation;
    }
}
