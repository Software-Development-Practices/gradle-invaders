package testBossStagePackage;

import engine.GameSettings;
import engine.GameState;
import engine.Score;
import entity.Bullet;
import entity.EnemyShip;
import entity.Ship;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScoreTest {
    private int score = 400;
    List<EnemyShip> shooters;
    EnemyShip shooter;
    Set<Bullet> bullets;
    Ship ship;

    GameState gameState = new GameState(1, 0, 3, 0, 0);
    GameSettings SETTINGS_LEVEL_3 =
            new GameSettings(5, 4, 60, 2000, 1);

    boolean bonusLife = true;


    @Before // 각 테스트마다 인스턴스가 매번 다시 생성되어 독립적인 테스트 가능
    public void setup() {
        //given
        Score score = new Score("gamescore", 400);
        System.out.println("before");
    }


    @DisplayName("스코어 비교 테스트")
    @Test
    public void testCleanBullets() {
        setup();
        Score score = new Score("gamescore", 400);

        assertEquals(score.compareTo(new Score("gamescore", 490)), 1);
        assertEquals(score.compareTo(new Score("gamescore", 400)), 0);
        assertEquals(score.compareTo(new Score("gamescore", 390)), -1);
        assertEquals(score.compareTo(new Score("gamescore", -1)), -1);

        teardown();

    }

    @After // 후처리 작업
    public void teardown() {
        System.out.println("teardown");
    }

}