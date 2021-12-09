package testSummaryPackage;

import engine.Sound;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import screen.Screen;
import screen.SettingScreen;
import screen.TitleScreen;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class SummaryTest {

    @Before
    public void setup() {
        //given
        System.out.println("before");
    }

    @DisplayName("리턴코드로 4번이 들어오는지 테스트")
    @Test
    public void testReturnCoed() throws InterruptedException {

    }

    @DisplayName("각 글자 색깔이 알맞게 들어오는지 테스트")
    @Test
    public void testColors() {


    }

    @DisplayName("스페이스 눌렀을 때 리턴코드 1번(메인화면)으로 돌아가는지 테스트")
    @Test
    public void testSpaceBar() {


    }

    @After
    public void teardown() {
        System.out.println("teardown");
    }
}
