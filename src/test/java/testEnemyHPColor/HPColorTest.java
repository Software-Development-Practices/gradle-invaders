package testEnemyHPColor;

import engine.GameSettings;
import entity.EnemyShip;
import entity.EnemyShipFormation;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class HPColorTest {
    GameSettings SETTINGS_LEVEL_1 =
            new GameSettings(5, 4, 60, 2000, 0);
    EnemyShipFormation formation = new EnemyShipFormation(SETTINGS_LEVEL_1);



    @Before
    public void setup() {
        //given
        System.out.println("before");
    }
    //EnemyShipFormation
    @DisplayName("공격 받으면 HP 잘 깎이는지 테스트")
    @Test
    public void testHP() throws InterruptedException {
        EnemyShip shooter = formation.getEnemyShips().get(1).get(1);
        shooter.setHP(3);
        formation.destroy(shooter);
        // then
        assertEquals(2, shooter.getHP(), "false");
        assertFalse(shooter.isDestroyed(), "false");

        formation.destroy(shooter);
        assertEquals(1, shooter.getHP(), "false");
        assertFalse(shooter.isDestroyed(), "false");

        formation.destroy(shooter);
        assertEquals(0, shooter.getHP(), "false");
        assertTrue(shooter.isDestroyed(), "false");

    }

    @After
    public void teardown() {
        System.out.println("teardown");
    }
}
