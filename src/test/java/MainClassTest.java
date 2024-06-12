import org.junit.Assert;
import org.junit.Test;

public class MainClassTest {
    private MainClass mainClass = new MainClass();
    int number = 14;

    @Test
    public void testGetLocalNumber(){
        Assert.assertTrue(String.format("%d != %d", number, mainClass.getLocalNumber()),
                number == mainClass.getLocalNumber());

    }
}
