package testBossStagePackage;

import engine.Cooldown;
import engine.GameSettings;
import engine.GameState;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

//  1. 구성 확인
//    3 Stage 마다 보너스 스테이지가 등장한다.
//    타이틀은 Bonus Stage {number} → number = 1, 2, 3
//    보너스 우주선은 다른 우주선보다 크다. (5배)
//  2. 기능 확인
//    보너스 스테이지의 우주선은 3초에 한 발씩 총알을 쏜다.
//    보너스 스테이지에는 비행기의 총알 발사 쿨타임이 없다.
//    보너스 우주선에 총알을 맞출 때마다 점수가 오른다.
//    스테이지마다 보너스 우주선의 체력은 오른다. (2배씩, 첫 체력 10방)

//  보스 스테이지에서도 쿨타임, 생명 잘 작동하는지 테스트하는 코드입니다.
public class CoreTest {

    @Before
    public void setup() {
        //given
        System.out.println("before");
    }

    @DisplayName("쿨타임 끝났는지 일반")
    @Test
    public void testcheckFinished1() throws InterruptedException {

        Cooldown animationCooldown = new Cooldown(2000);
        animationCooldown.reset();
        assertFalse(animationCooldown.checkFinished());
//        Thread.sleep(1000);
        TimeUnit.SECONDS.sleep((1));
        assertFalse(animationCooldown.checkFinished());
//        Thread.sleep(1000);
        TimeUnit.SECONDS.sleep((1));
        assertTrue(animationCooldown.checkFinished());
    }

    @DisplayName("쿨타임 끝났는지 보스")
    @Test
    public void testcheckFinished2() throws InterruptedException {
        ArrayList<GameSettings> Boss_LEVELs = new ArrayList<GameSettings>();

        GameSettings Boss_LEVEL_1 =
                new GameSettings(5, 5, 50, 2500,1);

        GameSettings Boss_LEVEL_2 =
                new GameSettings(5, 5, 50, 2500,2);

        GameSettings Boss_LEVEL_3 =
                new GameSettings(5, 5, 50, 2500,3);
        Boss_LEVELs.add(Boss_LEVEL_1);
        Boss_LEVELs.add(Boss_LEVEL_2);
        Boss_LEVELs.add(Boss_LEVEL_3);

        for (int i = 0; i < 3; i++){

            Cooldown BossCooldown = new Cooldown(Boss_LEVELs.get(i).getShootingFrecuency());
            BossCooldown.reset();
            assertFalse(BossCooldown.checkFinished());
            Thread.sleep(Boss_LEVELs.get(i).getShootingFrecuency());
            assertTrue(BossCooldown.checkFinished());
        }

    }

    @DisplayName("보스 스테이지 끝났을 때 생명이 max가 아니라면 보너스 라이프 true")
    @Test
    public void testBonusLife() {
        final int MAX_LIVES = 3;
        final int EXTRA_LIFE_FRECUENCY = 3;
        GameState gameState;
        gameState = new GameState(1, 0, MAX_LIVES, 0, 0);

        //레벨 2, 일반, 생명 3개
        gameState = new GameState(gameState.getLevel() + 1, gameState.getScore(),
                gameState.getLivesRemaining(), gameState.getBulletsShot(), gameState.getShipsDestroyed());

        boolean bonusLife = gameState.getLevel() % EXTRA_LIFE_FRECUENCY == 0
                && gameState.getLivesRemaining() < MAX_LIVES;

        assertFalse(bonusLife);

        // 두번째 보스스테이지, 생명 2개 남았을 때
        gameState = new GameState(gameState.getLevel() + 4, gameState.getScore(),
                gameState.getLivesRemaining() - 1, gameState.getBulletsShot(), gameState.getShipsDestroyed());

        bonusLife = gameState.getLevel() % EXTRA_LIFE_FRECUENCY == 0
                && gameState.getLivesRemaining() < MAX_LIVES;

        assertTrue(bonusLife);

        // 레벨 7, 일반, 생명 2개
        gameState = new GameState(gameState.getLevel() + 1, gameState.getScore(),
                gameState.getLivesRemaining(), gameState.getBulletsShot(), gameState.getShipsDestroyed());

        bonusLife = gameState.getLevel() % EXTRA_LIFE_FRECUENCY == 0
                && gameState.getLivesRemaining() < MAX_LIVES;

        assertFalse(bonusLife);
    }

    @After
    public void teardown() {
        System.out.println("teardown");
    }
}