package testPausePackage;
import engine.InputManager;
import engine.Sound;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import screen.Screen;
import screen.SettingScreen;
import screen.TitleScreen;

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

    @DisplayName("키보드 입력시마다 starttime 갱신 잘 되는지 테스트")
    @Test
    public void testReturnCoed() throws InterruptedException {
        InputManager inputManager = getInputManager();

        //assertFalse(inputManager.isKeyDown(), "false");
    }

    @DisplayName("pause모드시 계속 pause모드 작동하고 r누를 때에만 풀리는지 테스트")
    @Test
    public void testColors() {


    }

    @DisplayName("pause모드시 계속 pause모드 작동하고 r누를 때에만 풀리는지 테스트")
    @Test
    public void testSpaceBar() {


    }

    @After
    public void teardown() {
        System.out.println("teardown");
    }
}
