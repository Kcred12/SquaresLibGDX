package com.squares;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class Player {

    public float dx = 0, dy = 0; // velocity
    public float x, y;
    private float speed;
    public Texture texture;
    public final int SIZE = 30;
    public final float radius;

    // Movement flags
    public boolean movingLeft, movingRight, movingUp, movingDown;

    public Player(float startX, float startY, float speed) {
        this.x = startX;
        this.y = startY;
        this.dx = dx;
        this.dy = dy;
        this.speed = speed;
        this.texture = createTexture(SIZE, SIZE, Color.CYAN); // electric cyan square
        this.radius = SIZE / 2f + 3;
    }

    // Generate a simple colored square texture
    private Texture createTexture(int width, int height, Color color) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();
        Texture tex = new Texture(pixmap);
        pixmap.dispose();
        return tex;
    }

    // Update player position
 public void update(float deltaTime) {
    this.dx = 0;
    this.dy = 0;

    if (movingLeft)  this.dx -= 1;
    if (movingRight) this.dx += 1;
    if (movingUp)    this.dy += 1;
    if (movingDown)  this.dy -= 1;

    // Normalize if moving diagonally
    float length = (float) Math.sqrt(this.dx * this.dx + this.dy * this.dy);
    if (length > 0) {
        this.dx = (this.dx / length) * speed * deltaTime;
        this.dy = (this.dy / length) * speed * deltaTime;
    }

    // Candidate new positions
    float newX = x + this.dx;
    float newY = y + this.dy;

    // Clamp to screen bounds
    newX = Math.max(0, Math.min(newX, Gdx.graphics.getWidth() - this.texture.getWidth()));
    newY = Math.max(0, Math.min(newY, Gdx.graphics.getHeight() - this.texture.getHeight()));

    this.x = newX;
    this.y = newY;
}


    // Free GPU memory
    public void dispose() {
        texture.dispose();
    }
}
