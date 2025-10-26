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
            checkPlayerEnemyCollision(player, enemy);
        }  
        

        // Draw player and enemies
        batch.begin();
        batch.draw(player.texture, player.x, player.y);
        for (Enemy enemy : enemies) {
            batch.draw(enemy.texture, enemy.x, enemy.y);
        }  
        batch.end();
    }

    private void checkPlayerEnemyCollision(Player player, Enemy enemy) {

        // Calculate the distance between player and enemy centers
        float dx = enemy.x - player.x; 
        float dy = enemy.y - player.y;

        // Compute the squared distance (faster than sqrt)
        float distanceSquared = dx * dx + dy * dy;

        // Compute sum of radii
        float radiusSum = player.radius + enemy.radius;

        // Compare squared values to avoid using sqrt
        if (distanceSquared < radiusSum * radiusSum) {

            // ---- They Are Colliding ----

            // Step 1: Normalize the collision direction
            float distance = (float) Math.sqrt(distanceSquared);
            float nx = dx / distance;  // normalized X direction
            float ny = dy / distance;  // normalized Y direction

            // Step 2: Push the enemy away from the player
            float overlap = radiusSum - distance;
            enemy.x += nx * overlap * 0.5f;
            enemy.y += ny * overlap * 0.5f;

            // Step 3: Push the player away from the enemy
            player.x -= nx * overlap * 0.5f;
            player.y -= ny * overlap * 0.5f;

            // Step 4: Bounce (swap velocities along collision normal)
            enemy.dx = -enemy.dx;
            enemy.dy = -enemy.dy;
    }
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
