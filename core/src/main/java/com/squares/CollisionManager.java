package com.squares;

import com.badlogic.gdx.math.Vector2;

public class CollisionManager {

    public static boolean checkPlayerEnemyCollision(Player player, Enemy enemy) {
        Vector2 playerCenter = player.position.cpy().add(player.SIZE / 2f, player.SIZE / 2f);
        Vector2 enemyCenter = enemy.position.cpy().add(enemy.SIZE / 2f, enemy.SIZE / 2f);

        Vector2 diff = enemyCenter.cpy().sub(playerCenter);
        float distance = diff.len();
        float radiusSum = player.radius + enemy.radius;

        if (distance < radiusSum) {
            Vector2 normal = diff.nor();
            float overlap = radiusSum - distance;

            // Push player and enemy apart
            player.position.sub(normal.cpy().scl(overlap * 0.2f));
            enemy.position.add(normal.cpy().scl(overlap * 0.8f));

            // Bounce enemy
            float dot = enemy.velocity.dot(normal);
            enemy.velocity.sub(normal.cpy().scl(2 * dot));

            return true;
        }
        return false;
    }

}
