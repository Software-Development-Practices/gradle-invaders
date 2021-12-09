package testSummaryPackage;

import engine.Core;
import engine.Sound;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import screen.*;

import static org.junit.jupiter.api.Assertions.*;


public class SummaryTest {

    @Before
    public void setup() {
        //given
        System.out.println("before");
    }

    @DisplayName("SummaryScreen 오동작 테스트")
    @Test
    public void testReturnCoed() {
        SummaryScreen screen = new SummaryScreen( 448, 520, 60);
        System.out.println(screen.isRunning);
        assertFalse(screen.isRunning);
    }

    @After
    public void teardown() {
        System.out.println("teardown");
    }
}