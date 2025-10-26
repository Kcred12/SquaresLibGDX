package com.squares;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SquaresGame extends ApplicationAdapter {

    private Player player;
    private SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        player = new Player(100, 100, 200);

        // Set InputProcessor for reliable keyboard input
        Gdx.input.setInputProcessor(new PlayerInput(player));
    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        // Clear screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update player
        player.update(deltaTime);

        // Draw player
        batch.begin();
        batch.draw(player.texture, player.x, player.y);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        player.dispose();
    }
}
