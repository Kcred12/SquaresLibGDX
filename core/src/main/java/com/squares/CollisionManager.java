package com.squares;

public class CollisionManager {

    public static boolean checkPlayerEnemyCollision(Player player, Enemy enemy) {

        float px = player.x + player.SIZE / 2f;
        float py = player.y + player.SIZE / 2f;
        float ex = enemy.x + enemy.SIZE / 2f;
        float ey = enemy.y + enemy.SIZE / 2f;

        float dx = ex - px;
        float dy = ey - py;

        float distanceSquared = dx*dx + dy*dy;
        float radiusSum = player.radius + enemy.radius;

        if (distanceSquared < radiusSum * radiusSum) {
            float distance = (float) Math.sqrt(distanceSquared);
            float nx = dx / distance;
            float ny = dy / distance;

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
}
