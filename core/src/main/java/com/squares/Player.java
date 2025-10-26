package com.squares;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class Player {

    public float x, y;
    private float speed;
    public Texture texture;

    // Movement flags
    public boolean movingLeft, movingRight, movingUp, movingDown;

    public Player(float startX, float startY, float speed) {
        this.x = startX;
        this.y = startY;
        this.speed = speed;
        this.texture = createTexture(50, 50, Color.CYAN); // electric cyan square
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
    float dx = 0, dy = 0;

    if (movingLeft)  dx -= 1;
    if (movingRight) dx += 1;
    if (movingUp)    dy += 1;
    if (movingDown)  dy -= 1;

    // Normalize if moving diagonally
    float length = (float) Math.sqrt(dx * dx + dy * dy);
    if (length > 0) {
        dx = (dx / length) * speed * deltaTime;
        dy = (dy / length) * speed * deltaTime;
    }

    // Candidate new positions
    float newX = x + dx;
    float newY = y + dy;

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
