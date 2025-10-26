package com.squares;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class Enemy {

    private final int SIZE = 15;
    private final float maxSpeed;

    public float x, y;          // Position
    public float speedX, speedY; // Velocity

    public Texture texture;
    private static final Random random = new Random();

    public Enemy(float maxSpeed) {
        this.maxSpeed = maxSpeed;

        // Random starting position
        this.x = random.nextFloat() * (Gdx.graphics.getWidth() - SIZE);
        this.y = random.nextFloat() * (Gdx.graphics.getHeight() - SIZE);

        // Random movement direction
        float angle = (float) (random.nextFloat() * Math.PI * 2);
        this.speedX = (float) Math.cos(angle) * maxSpeed;
        this.speedY = (float) Math.sin(angle) * maxSpeed;

        this.texture = createTexture(SIZE, SIZE, Color.RED);
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

        float newX = this.x + speedX * deltaTime;
        float newY = this.y + speedY * deltaTime;

        // Bounce horizontally
        if (newX < 0 || newX + texture.getWidth() > Gdx.graphics.getWidth()) {
            speedX = -speedX;
        }

        // Bounce vertically
        if (newY < 0 || newY + texture.getHeight() > Gdx.graphics.getHeight()) {
            speedY = -speedY;
        }

        // Clamp position after bounce
        this.x = Math.max(0, Math.min(newX, Gdx.graphics.getWidth() - texture.getWidth()));
        this.y = Math.max(0, Math.min(newY, Gdx.graphics.getHeight() - texture.getHeight()));
    }

    public boolean collidesWith(Enemy other) {
    // Check overlap using AABB (Axis-Aligned Bounding Box) collision
    return this.x < other.x + other.texture.getWidth() &&
           this.x + this.texture.getWidth() > other.x &&
           this.y < other.y + other.texture.getHeight() &&
           this.y + this.texture.getHeight() > other.y;
}


    public void dispose() {
        texture.dispose();
    }
}
