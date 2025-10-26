package com.squares;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class SquaresGame extends ApplicationAdapter {

    private static float player_speed = 400f;
    private static float enemy_speed = 200f;

    private Player player;
    private Array<Enemy> enemies;
    private SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        player = new Player(35, 100, player_speed);

        // Set InputProcessor for reliable keyboard input
        Gdx.input.setInputProcessor(new PlayerInput(player));
        
        enemies = new Array<Enemy>();
        for (int i = 0; i < 20; i++) {

            Enemy enemy = new Enemy(enemy_speed);
            enemies.add(enemy);
        }
    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        // Clear screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update player and enemies
        player.update(deltaTime);
        for (Enemy enemy : enemies) {
            enemy.update(deltaTime);
        }  

        for (int i = 0; i < enemies.size; i++) {
            for (int j = i + 1; j < enemies.size; j++) {

                Enemy a = enemies.get(i);
                Enemy b = enemies.get(j);

                if (a.collidesWith(b)) {
                    // Swap velocities (simple elastic collision)
                    float tempX = a.speedX;
                    float tempY = a.speedY;
                    a.speedX = b.speedX;
                    a.speedY = b.speedY;
                    b.speedX = tempX;
                    b.speedY = tempY;
                }
            }
        }

        // Draw player and enemies
        batch.begin();
        batch.draw(player.texture, player.x, player.y);
        for (Enemy enemy : enemies) {
            batch.draw(enemy.texture, enemy.x, enemy.y);
        }  
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        player.dispose();
        for (Enemy enemy : enemies) {
            enemy.dispose();
        }
    }
}
