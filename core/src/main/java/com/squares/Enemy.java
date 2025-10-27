package com.squares;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Enemy {

    public final int SIZE = 15;
    private final float maxSpeed;

    public float x, y;
    public float dx, dy;
    public final float radius;
    private static final Random random = new Random();

    public Color color = new Color(1f, 0f, 0.9f, 1f);
    public Texture texture;

    public Array<Vector2> trail = new Array<>();
    private final int MAX_TRAIL = 5;

    public Enemy(float maxSpeed) {
        this.maxSpeed = maxSpeed;

        x = random.nextFloat() * (Gdx.graphics.getWidth() - SIZE);
        y = random.nextFloat() * (Gdx.graphics.getHeight() - SIZE);

        float angle = (float) (random.nextFloat() * Math.PI * 2);
        dx = (float) Math.cos(angle) * maxSpeed;
        dy = (float) Math.sin(angle) * maxSpeed;

        texture = createTexture(SIZE, SIZE, color);
        radius = SIZE / 2f + 2;
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
        float newX = x + dx * deltaTime;
        float newY = y + dy * deltaTime;

        if (newX < 0 || newX + SIZE > Gdx.graphics.getWidth()) dx = -dx;
        if (newY < 0 || newY + SIZE > Gdx.graphics.getHeight()) dy = -dy;

        x = Math.max(0, Math.min(newX, Gdx.graphics.getWidth() - SIZE));
        y = Math.max(0, Math.min(newY, Gdx.graphics.getHeight() - SIZE));

        // Trail
        trail.add(new Vector2(x, y));
        if (trail.size > MAX_TRAIL) trail.removeIndex(0);
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
