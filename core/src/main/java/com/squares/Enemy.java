package com.squares;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import java.util.Random;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Enemy {

    public final int SIZE = 15;
    public Vector2 position;
    public Vector2 velocity;
    public final float radius;
    public Color color = new Color(1f, 0f, 0.9f, 1f);
    public Texture texture;

    private final float maxSpeed;
    private static final Random random = new Random();

    public Enemy(float maxSpeed) {
        this.maxSpeed = maxSpeed;

        // Random starting position
        position = new Vector2(
            random.nextFloat() * (Gdx.graphics.getWidth() - SIZE),
            random.nextFloat() * (Gdx.graphics.getHeight() - SIZE)
        );

        // Random velocity
        float angle = random.nextFloat() * (float)Math.PI * 2f;
        this.velocity = new Vector2((float)Math.cos(angle), (float)Math.sin(angle)).scl(maxSpeed);

        this.texture = createTexture(SIZE, SIZE, color);
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
        // Move enemy
        position.add(this.velocity.cpy().scl(deltaTime));

        // Bounce off walls
        if (position.x < 0 || position.x + SIZE > Gdx.graphics.getWidth()) this.velocity.x = -this.velocity.x;
        if (position.y < 0 || position.y + SIZE > Gdx.graphics.getHeight()) this.velocity.y = -this.velocity.y;

        // Clamp after bounce
        this.position.x = Math.max(0, Math.min(this.position.x, Gdx.graphics.getWidth() - SIZE));
        this.position.y = Math.max(0, Math.min(this.position.y, Gdx.graphics.getHeight() - SIZE));
    }

    public void draw(SpriteBatch batch) {
        batch.draw(this.texture, this.position.x, this.position.y);
    }  

    public void drawOutline(ShapeRenderer shapeRenderer) {
        shapeRenderer.rect(this.position.x, this.position.y, SIZE, SIZE);
    }

    public void dispose() {
        texture.dispose();
    }
}
