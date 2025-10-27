package com.squares;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

public class SquaresGame extends ApplicationAdapter {

    private static float player_speed = 400f;
    private static float enemy_speed = 300f;

    private Player player;
    private Array<Enemy> enemies;
    private SpriteBatch batch;

    // A bunch of style things
    public float animation_time = 0;
    public ShapeRenderer shapeRenderer;

    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        player = new Player(35, 100, player_speed);

        // Set InputProcessor for reliable keyboard input
        Gdx.input.setInputProcessor(new PlayerInput(player));
        
        enemies = new Array<>();
        for (int i = 0; i < 20; i++) {

            Enemy enemy = new Enemy(enemy_speed);
            enemies.add(enemy);
        }
    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        animation_time += deltaTime;

        // Clear screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw background gradient
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Top color
        // Darker gradient background
        Color top = new Color(0.168f, 0f, 0.235f, 1f);   // deep purple (top)
        Color bottom = new Color(0.1f, 0f, 0.2f, 1f);    // darker purple (bottom)


        // Draw gradient as 2 triangles filling the screen
        shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
                bottom, bottom, top, top);

        shapeRenderer.end();


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

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);

        // Draw outline around player
        shapeRenderer.rect(player.x, player.y, player.SIZE, player.SIZE);

        // Draw outline around enemies
        for (Enemy enemy : enemies) {
            shapeRenderer.rect(enemy.x, enemy.y, enemy.SIZE, enemy.SIZE);
        }

        shapeRenderer.end();
    }

    private boolean checkPlayerEnemyCollision(Player player, Enemy enemy) {

        float playerCenterX = player.x + player.texture.getWidth() / 2f;
        float playerCenterY = player.y + player.texture.getHeight() / 2f;

        float enemyCenterX = enemy.x + enemy.texture.getWidth() / 2f;
        float enemyCenterY = enemy.y + enemy.texture.getHeight() / 2f;

        float dx = enemyCenterX - playerCenterX;
        float dy = enemyCenterY - playerCenterY;

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
            enemy.x += nx * overlap;
            enemy.y += ny * overlap;

            float dot = enemy.dx * nx + enemy.dy * ny;
            enemy.dx -= 2 * dot * nx;
            enemy.dy -= 2 * dot * ny;

            return true;
    }
    return false;
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
