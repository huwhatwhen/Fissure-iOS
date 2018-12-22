package com.gmail.studios.co.fiish.fissuregame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/*
    Screen displaying Fiish Co logo.
 */

public class FiishCoScreen extends ScreenAdapter {
    public float mElapsedTime;

    private Viewport mViewport;
    private SpriteBatch mBatch;
    private Texture mTexture;
    private float mWidth, mHeight;

    public FiishCoScreen() {
        mViewport = new ScreenViewport();
        mTexture = new Texture(Gdx.files.internal("fiishco.png"));
    }

    @Override
    public void show() {
        mBatch = new SpriteBatch();
        mElapsedTime = 0f;

        Gdx.gl.glClearColor(1, 1, 1, 1);
    }

    @Override
    public void resize(int width, int height) {
        mViewport.update(width, height, true);
        mWidth = mViewport.getScreenWidth() * 0.6f;
        mHeight = mWidth * 1093f / 3285f;
    }

    @Override
    public void render(float delta) {
        mElapsedTime += delta;
        mViewport.apply(true);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        mBatch.begin();
        mBatch.draw(mTexture, mViewport.getScreenWidth() / 2f - mWidth / 2,
                mViewport.getScreenHeight() / 2f - mHeight / 2f, mWidth, mHeight);
        mBatch.end();
    }

    @Override
    public void dispose() {
        mTexture.dispose();
        mBatch.dispose();
    }
}
