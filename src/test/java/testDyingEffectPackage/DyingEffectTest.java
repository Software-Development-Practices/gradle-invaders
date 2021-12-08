package testDyingEffectPackage;

import engine.*;
import entity.EnemyShip;
import entity.EnemyShipFormation;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import screen.GameScreen;
import static org.junit.jupiter.api.Assertions.*;


public class DyingEffectTest {

    @Before
    public void setup() {
        //given

        System.out.println("before");
    }


    @DisplayName("함선 파괴 후 vib 실행  하는지")
    @Test
    public void testReturnCoed() {
        GameState gameState = new GameState(1, 0, 3, 0, 0);
        GameSettings SETTINGS_LEVEL_1 =
                new GameSettings(5, 4, 60, 2000, 0);
        GameScreen gameClass = new GameScreen(gameState,
                SETTINGS_LEVEL_1,
                true, 448, 520, 60);
        GameSettings Boss_LEVEL_1 =
                new GameSettings(5, 5, 50, 2500,1);
        EnemyShipFormation formation2 = new EnemyShipFormation(Boss_LEVEL_1);

        EnemyShipFormation formation = new EnemyShipFormation(SETTINGS_LEVEL_1);
        EnemyShip shooter = formation.getEnemyShips().get(1).get(1);
        shooter.setHP(1);
        formation.destroy(shooter);
        assertTrue(shooter.isDestroyed(), "false");

    }

    @After
    public void teardown() {
        System.out.println("teardown");
    }
}
