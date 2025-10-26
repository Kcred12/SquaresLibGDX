package com.squares;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class Enemy {

    private final int SIZE = 15;

    public float x, y;         // position
    private float speedX, speedY; // velocity
    private final float maxSpeed;
    public Texture texture;

    public Enemy(float startX, float startY, float maxSpeed) {
        this.x = startX;
        this.y = startY;
        this.speedX = maxSpeed;
        this.speedY = maxSpeed;
        this.maxSpeed = maxSpeed;
        this.texture = createTexture(SIZE, SIZE, Color.RED); // red square
    }

    private Texture createTexture(int width, int height, Color color) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();
        Texture tex = new Texture(pixmap);
        pixmap.dispose();
        return tex;
    }

    public void update(float deltaTime) {

        // Normalize diagonal movement
        if (speedX != 0 && speedY != 0) {
            float length = (float) Math.sqrt(speedX * speedX + speedY * speedY);
            speedX = (speedX / length) * maxSpeed;
            speedY = (speedY / length) * maxSpeed;
        }

        float newX = this.x + speedX * deltaTime;
        float newY = this.y + speedY * deltaTime;

        // Bounce off screen edges
        if (newX < 0) this.speedX = -this.speedX;
        if (newX + texture.getWidth() > Gdx.graphics.getWidth()) this.speedX = -this.speedX;
        if (newY < 0) this.speedY = -this.speedY;
        if (newY + texture.getHeight() > Gdx.graphics.getHeight()) this.speedY = -this.speedY;

        newX = Math.max(0, Math.min(newX, Gdx.graphics.getWidth() - this.texture.getWidth()));
        newY = Math.max(0, Math.min(newY, Gdx.graphics.getHeight() - this.texture.getHeight()));

        this.x = newX;
        this.y = newY;
    }

    public void dispose() {
        texture.dispose();
    }
}
