import com.qwerty55558.javagame.consts.PropertyName;
import org.junit.jupiter.api.Test;


public class testName {
    @Test
    public void test() {
        String name = PropertyName.Coin.name();
        System.out.println(name);
    }
}