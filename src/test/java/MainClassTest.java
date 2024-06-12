import org.junit.Assert;
import org.junit.Test;

import java.util.Locale;

public class MainClassTest {
    private MainClass mainClass = new MainClass();
    String str = "hello";
    String str_upper = "Hello";

    @Test
    public void testGetLocalNumber(){
        Assert.assertTrue(String.format("%s is not equals %s", mainClass.getClassString(), str),
                mainClass.getClassString().contains(str));
        Assert.assertTrue(String.format("%s is not equals %s", mainClass.getClassString(), str_upper),
                mainClass.getClassString().contains(str_upper));
    }

}
