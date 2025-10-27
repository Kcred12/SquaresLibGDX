package com.squares;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Enemy {

    public final int SIZE = 15;
    private final float maxSpeed;

    public float x, y;          // Position
    public float dx, dy; // Velocity
    public final float radius;
    public Color color = new Color(1f, 0f, 0.9f, 1f);

    public Texture texture;
    public int MAX_TRAIL = 5;
    public Array<Vector2> trail = new Array<>();

    private static final Random random = new Random();

    public Enemy(float maxSpeed) {
        this.maxSpeed = maxSpeed;

        // Random starting position
        this.x = random.nextFloat() * (Gdx.graphics.getWidth() - SIZE);
        this.y = random.nextFloat() * (Gdx.graphics.getHeight() - SIZE);

        // Random movement direction
        float angle = (float) (random.nextFloat() * Math.PI * 2);
        this.dx = (float) Math.cos(angle) * maxSpeed;
        this.dy = (float) Math.sin(angle) * maxSpeed;

        this.texture = createTexture(SIZE, SIZE, color); // neon magenta

        this.radius = SIZE / 2f + 2;
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

        float newX = this.x + this.dx * deltaTime;
        float newY = this.y + this.dy * deltaTime;

        // Bounce horizontally
        if (newX < 0 || newX + texture.getWidth() > Gdx.graphics.getWidth()) {
            this.dx = -this.dx;
        }

        // Bounce vertically
        if (newY < 0 || newY + texture.getHeight() > Gdx.graphics.getHeight()) {
            this.dy = -this.dy;
        }

        // Clamp position after bounce
        this.x = Math.max(0, Math.min(newX, Gdx.graphics.getWidth() - texture.getWidth()));
        this.y = Math.max(0, Math.min(newY, Gdx.graphics.getHeight() - texture.getHeight()));

        // Record trail
        trail.add(new Vector2(x, y));
        if (trail.size > MAX_TRAIL) trail.removeIndex(0);
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
