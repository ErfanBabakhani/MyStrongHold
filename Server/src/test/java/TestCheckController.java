import org.example.controller.CheckController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestCheckController extends CheckController{
    @Test
    public void test(){
        checkPasswordTest();
        checkUsernameTest();
        checkEmailTest();
    }
    @Test
    public void checkPasswordTest(){
        Assertions.assertEquals(CheckController.checkPassword("dsa@o"),"Password length should be greater than 5.");
        Assertions.assertEquals(CheckController.checkPassword("Sobhan@2"),"accepted");
    }
    @Test
    public void checkUsernameTest(){
        Assertions.assertTrue(CheckController.checkUsername("Erfan_166"));
        Assertions.assertFalse(CheckController.checkUsername("Alireza@9_"));
    }
    @Test
    public void checkEmailTest(){
        Assertions.assertEquals("Email can't have more than one @",CheckController.checkEmail("iran@plestin@Hemas.com"));
        Assertions.assertEquals("accepted",CheckController.checkEmail("sobhanarshadi.officila@gmail.com"));
    }
}
