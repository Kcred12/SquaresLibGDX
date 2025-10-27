package com.squares;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Player {

    public float x, y;
    public float dx = 0, dy = 0;
    private final float speed;

    public final int SIZE = 30;
    public final float radius;
    public Texture texture;

    public boolean movingLeft, movingRight, movingUp, movingDown;

    public Player(float startX, float startY, float speed) {
        this.x = startX;
        this.y = startY;
        this.speed = speed;
        this.texture = createTexture(SIZE, SIZE, new Color(0f, 1f, 1f, 1f));
        this.radius = SIZE / 2f + 3;
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
        dx = 0;
        dy = 0;

        if (movingLeft) dx -= 1;
        if (movingRight) dx += 1;
        if (movingUp) dy += 1;
        if (movingDown) dy -= 1;

        float length = (float) Math.sqrt(dx*dx + dy*dy);
        if (length > 0) {
            dx = dx / length * speed * deltaTime;
            dy = dy / length * speed * deltaTime;
        }

        x += dx;
        y += dy;

        // Clamp
        x = Math.max(0, Math.min(x, Gdx.graphics.getWidth() - SIZE));
        y = Math.max(0, Math.min(y, Gdx.graphics.getHeight() - SIZE));
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    public void drawOutline(ShapeRenderer renderer) {
        renderer.rect(x, y, SIZE, SIZE);
    }

    public void dispose() {
        texture.dispose();
    }
}
