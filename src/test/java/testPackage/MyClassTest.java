package testPackage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import testPackage.TestFile;

public class MyClassTest {

    @Test
    @DisplayName("Add Test")
    public void addition() {
        int _num = TestFile.getNum();

        assertEquals(2, _num + 2);
    }

}