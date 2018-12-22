package com.gmail.studios.co.fiish.fissuregame;

import com.badlogic.gdx.Preferences;

public interface IOSLink {

    public float getHighScore();
    public void setHighScore(float highScore);

    public void showBanner(boolean show);
    public void showInterstitial();
}
