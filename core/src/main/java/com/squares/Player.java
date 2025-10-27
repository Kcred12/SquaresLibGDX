package com.squares;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {

    public Vector2 position;     // Player position
    public Vector2 velocity;     // Movement velocity
    private final float speed;
    public final int SIZE = 30;
    public final float radius;
    public Texture texture;

    // Movement flags
    public boolean movingLeft, movingRight, movingUp, movingDown;

    public Player(float startX, float startY, float speed) {
        this.position = new Vector2(startX, startY);
        this.velocity = new Vector2(0, 0);
        this.speed = speed;
        this.texture = createTexture(SIZE, SIZE, new Color(0f, 1f, 1f, 1f)); // neon cyan
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
        // Reset velocity
        this.velocity.set(0, 0);

        if (movingLeft)  this.velocity.x -= 1;
        if (movingRight) this.velocity.x += 1;
        if (movingUp)    this.velocity.y += 1;
        if (movingDown)  this.velocity.y -= 1;

        // Normalize diagonal movement
        if (this.velocity.len() > 0) {
            this.velocity.nor().scl(speed * deltaTime);
        }

        // Move player
        position.add(this.velocity);

        // Clamp to screen
        position.x = Math.max(0, Math.min(position.x, Gdx.graphics.getWidth() - SIZE));
        position.y = Math.max(0, Math.min(position.y, Gdx.graphics.getHeight() - SIZE));
    }

    public void draw(SpriteBatch batch) {
        batch.draw(this.texture, this.position.x, this.position.y);
    }

    public void blink() {
        float dashDistance = 100f;
        Vector2 dashVector = new Vector2(0, 0);

        if (movingLeft)  dashVector.x -= 1;
        if (movingRight) dashVector.x += 1;
        if (movingUp)    dashVector.y += 1;
        if (movingDown)  dashVector.y -= 1;

        if (dashVector.len() > 0) {
            dashVector.nor().scl(dashDistance);
            this.position.add(dashVector);

            // Clamp to screen after dash
            this.position.x = Math.max(0, Math.min(this.position.x, Gdx.graphics.getWidth() - SIZE));
            this.position.y = Math.max(0, Math.min(this.position.y, Gdx.graphics.getHeight() - SIZE));
        }
    }

    public void drawOutline(ShapeRenderer shapeRenderer) {
        shapeRenderer.rect(this.position.x, this.position.y, SIZE, SIZE);
    }

    public void dispose() {
        this.texture.dispose();
    }
}
