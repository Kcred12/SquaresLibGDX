package com.squares;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SquaresGame extends ApplicationAdapter {

    private Player player;
    private Enemy enemy;
    private SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        player = new Player(35, 100, 400);

        // Set InputProcessor for reliable keyboard input
        Gdx.input.setInputProcessor(new PlayerInput(player));

        enemy = new Enemy(300, 300, 250);
    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        // Clear screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update player
        player.update(deltaTime);
        enemy.update(deltaTime);

        // Draw player
        batch.begin();
        batch.draw(player.texture, player.x, player.y);
        batch.draw(enemy.texture, enemy.x, enemy.y);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        player.dispose();
        enemy.dispose();
    }
}
