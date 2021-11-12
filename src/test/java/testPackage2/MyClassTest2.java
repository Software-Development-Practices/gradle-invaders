package testPackage2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MyClassTest2 {
    int num = 5;

    @Test
    @DisplayName("Multi Test")
    public void multiple() {
        assertEquals(30, num * 6);
    }

}