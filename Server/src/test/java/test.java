import org.example.controller.LoginController;
import org.example.view.Menu;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class test extends Menu {
    @Test
    public void LoginControllerlogout() {
        Assertions.assertEquals(LoginController.logout(), "user logged out successfully!");
    }
    @Test
    public void testCheckController(){
        TestCheckController testCheckController=new TestCheckController();
        testCheckController.test();
    }
    @Test
    public void testSignupController(){
        SignupControllerTest signupControllerTest=new SignupControllerTest();
        signupControllerTest.test();
    }
    @Test
    public void SignupMenuTest(){
        SignupMenuTest signupMenuTest=new SignupMenuTest();
        signupMenuTest.Test();
    }
    @Test
    public void ProfileControllerTest(){
        ProfileControllerTest profileControllerTest=new ProfileControllerTest();
        profileControllerTest.test();
    }
}
