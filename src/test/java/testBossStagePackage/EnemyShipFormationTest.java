package testBossStagePackage;

import engine.Core;
import engine.GameSettings;
import entity.Bullet;
import entity.EnemyShip;
import entity.Ship;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


//1. 각 레벨마다 아래 내용 검증
//1) 각 enemy, boss들이 총알 잘 나가는지, 맞으면 죽는지, 동선 겹치지 않는지 검증,
//2) 다음 레벨로 넘어 갈 때 전에 게임흔적 하나도 남지 않는지
//3) 내 함선에서 총알이 제대로 나오는지, 맞으면 내 함선 죽는지
//4) 적이 앞에 있으면 총알을 안쏘는지 확인

public class EnemyShipFormationTest {
    private List<EnemyShip> shooters;
    EnemyShip shooter;
    Set<Bullet> bullets;
    final int SHOOTING_INTERVAL = 740;


    @Before
    public void setup() {
        //given
        shooters = new ArrayList<EnemyShip>();
        shooter = new EnemyShip();
        bullets = new HashSet<Bullet>();


        System.out.println("before");
    }

    @DisplayName("제대로 파괴되었는지 테스트")
    @Test
    public void testDestroy() {

        setup();
        // when
        this.shooter.destroy();
        // then
        assertTrue(shooter.isDestroyed(), "fail");
        teardown();

    }

    @DisplayName("보스 스테이지일 때 총알 쿨다운 제대로 작동하는지 테스트")
    @Test
    public void testBossStageCooldown() {

        setup();
        // when
        GameSettings SETTINGS_LEVEL_1 =
                new GameSettings(5, 4, 60, 2000, 0);
        GameSettings Boss_LEVEL_1 =
                new GameSettings(5, 5, 50, 2500,1);
        final int SHOOTING_INTERVAL = 740;
        try {
            Ship ship = new Ship(440 / 2, 520 - 30, SETTINGS_LEVEL_1);
            Field field = ship.getClass().getDeclaredField("shootingCooldown");
            field.setAccessible(true);

            System.out.println(field.get(ship));
            System.out.println(Core.getCooldown(SHOOTING_INTERVAL));

            Field field_c = field.get(ship).getClass().getDeclaredField("milliseconds");
            field_c .setAccessible(true);

            System.out.println(field_c);

            int value = (int) field_c.get(field.get(ship));
            System.out.println("val: " + value);
            System.out.println(SHOOTING_INTERVAL);

            assertEquals(value, SHOOTING_INTERVAL);



            Ship ship2 = new Ship(440 / 2, 520 - 30, Boss_LEVEL_1);
            Field field2 = ship2.getClass().getDeclaredField("shootingCooldown");
            field2.setAccessible(true);

            int value1 = (int) field_c.get(field2.get(ship2));

            assertEquals(value1, 50);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        // then
        teardown();

    }

    @After
    public void teardown() {
        System.out.println("teardown");
    }

}