import org.example.controller.SignupController;
import org.example.model.DataBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.Socket;

public class SignupControllerTest extends SignupController {
    private Socket socket;

    @Test
    public void test(){
        randomSloganTest();
    }
    @Test
    public void randomSloganTest(){
        Assertions.assertNotNull(DataBase.getSlogans().contains(makeRandomSlogan(socket)));
    }
}
