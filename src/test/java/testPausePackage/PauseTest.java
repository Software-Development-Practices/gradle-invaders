package testPausePackage;
import engine.GameSettings;
import engine.GameState;
import engine.InputManager;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import screen.GameScreen;
import screen.Screen;
import screen.SettingScreen;
import screen.TitleScreen;

import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static engine.Core.getInputManager;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

// pause - 10s간 키보드 입력 없으면 자동 pause 모드
public class PauseTest {

    @Before
    public void setup() {
        //given
        System.out.println("before");
    }

    @DisplayName("키보드 입력시마다 Input managing 잘 되는지 테스트")
    @Test
    public void testInputManager() throws InterruptedException {
        InputManager inputManager = getInputManager();

        assertFalse(inputManager.isKeyDown(1), "false");
        assertFalse(inputManager.isActive(), "false");
        inputManager.isPressed = true;
        assertTrue(inputManager.isActive(), "false");

        assertFalse(inputManager.isKeyDown(KeyEvent.VK_RIGHT), "false");
    }

    @DisplayName("게임스크린에서 update() 잘 되는지 테스트")
    @Test
    public void testPause() throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        boolean bonusLife = true;

        GameState gameState = new GameState(1, 0, 3, 0, 0);

        GameSettings SETTINGS_LEVEL_1 =
                new GameSettings(5, 4, 60, 2000, 1);

        GameScreen gameClass = new GameScreen(gameState,
                SETTINGS_LEVEL_1,
                bonusLife, 448, 520, 60);

        Method method = gameClass.getClass().getDeclaredMethod("update");
        method.setAccessible(true);
        method.invoke(gameClass);

        InputManager inputManager = getInputManager();
        inputManager.isPressed = true;
        assertTrue(inputManager.isActive(), "false");

        assertFalse(inputManager.isKeyDown(KeyEvent.VK_RIGHT), "false");
    }


    @After
    public void teardown() {
        System.out.println("teardown");
    }
}