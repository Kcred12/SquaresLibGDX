package com.squares;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

public class SquaresGame extends ApplicationAdapter {

    private static final float PLAYER_SPEED = 400f;
    private static final float ENEMY_SPEED = 300f;

    private Player player;
    private Array<Enemy> enemies;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private float animationTime = 0;

    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        player = new Player(35, 100, PLAYER_SPEED);
        Gdx.input.setInputProcessor(new PlayerInput(player));

        enemies = new Array<>();
        for (int i = 0; i < 20; i++) {
            enemies.add(new Enemy(ENEMY_SPEED));
        }
    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        animationTime += deltaTime;

        clearScreen();

        drawBackgroundGradient();

        updateGameObjects(deltaTime);

        drawSprites();

        drawOutlines();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void drawBackgroundGradient() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        Color top = new Color(0.168f, 0f, 0.235f, 1f);
        Color bottom = new Color(0.1f, 0f, 0.2f, 1f);

        shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
                bottom, bottom, top, top);

        shapeRenderer.end();
    }

    private void updateGameObjects(float deltaTime) {
        player.update(deltaTime);

        for (Enemy enemy : enemies) {
            enemy.update(deltaTime);
            CollisionManager.checkPlayerEnemyCollision(player, enemy);
        }
    }

    private void drawSprites() {
        batch.begin();
        player.draw(batch);
        for (Enemy enemy : enemies) {
            enemy.draw(batch);
        }
        batch.end();
    }

    private void drawOutlines() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);

        player.drawOutline(shapeRenderer);

        for (Enemy enemy : enemies) {
            enemy.drawOutline(shapeRenderer);
        }

        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
        player.dispose();
        for (Enemy enemy : enemies) enemy.dispose();
    }
}
