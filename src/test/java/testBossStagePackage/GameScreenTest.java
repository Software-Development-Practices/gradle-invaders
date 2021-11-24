package testBossStagePackage;

import engine.GameSettings;
import engine.GameState;
import entity.Bullet;
import entity.EnemyShip;
import entity.Ship;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import screen.GameScreen;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameScreenTest {
    private int lives;
    List<EnemyShip> shooters;
    EnemyShip shooter;
    Set<Bullet> bullets;
    Ship ship;

    GameState gameState = new GameState(1, 0, 3, 0, 0);
    GameSettings SETTINGS_LEVEL_1 =
            new GameSettings(5, 4, 60, 2000, 1);

    boolean bonusLife = true;


    //@Before // 각 테스트마다 인스턴스가 매번 다시 생성되어 독립적인 테스트 가능
    public void setup() {
        //given
        shooters = new ArrayList<EnemyShip>();
        shooter = new EnemyShip();
        bullets = new HashSet<Bullet>();
        ship = new Ship(440 / 2, 520 - 30, SETTINGS_LEVEL_1);
        lives = 3;

        System.out.println("before");
    }

    @DisplayName("충돌 시 생명 깎이는지")
    @Test
    public void testManageCollision() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        try {
        GameScreen gameClass = new GameScreen(gameState,
                SETTINGS_LEVEL_1,
                bonusLife, 448, 520, 60);

        Method method = gameClass.getClass().getDeclaredMethod("ManageCollision");
        method.setAccessible(true);
        method.invoke(gameClass);
        // then
        assertEquals(this.lives, 2);

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @DisplayName("총알이 위나 아래 화면 밖으로 나갔을 때 테스트")
    @Test
    public void testCleanBullets() {
        try {
            //int beforeY = bullet.getPositionY();
            GameScreen gameClass = new GameScreen(gameState,
                    SETTINGS_LEVEL_1,
                    bonusLife, 448, 520, 60);

            Method method = gameClass.getClass().getDeclaredMethod("cleanBullets");
            method.setAccessible(true);
            method.invoke(gameClass);
            // then
            //assertNotEquals(bullet.getPositionY(), beforeY);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }



    //@After // 후처리 작업
    public void teardown() {
        System.out.println("teardown");
    }

}