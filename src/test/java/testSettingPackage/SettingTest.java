package testSettingPackage;

import engine.Sound;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import screen.GameScreen;
import screen.Screen;
import screen.SettingScreen;
import screen.TitleScreen;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;


public class SettingTest {

    @Before
    public void setup() {
        //given
        System.out.println("before");
    }

    @DisplayName("세팅 기능 테스트")
    @Test
    public void testSetting() throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException {
        SettingScreen screen = new SettingScreen(448, 520, 60);

        Field field = screen.getClass().getDeclaredField("activated");
        field.setAccessible(true);
        assertEquals(field.get(screen), 0);


        Field field2 = screen.getClass().getDeclaredField("resizeOption");
        field2.setAccessible(true);
        assertEquals(field2.get(screen), 0);

        Method method = screen.getClass().getDeclaredMethod("nextOptionItem");
        method.setAccessible(true);
        method.invoke(screen);
        assertEquals(field2.get(screen), 1);
    }

    @After
    public void teardown() {
        System.out.println("teardown");
    }
}
