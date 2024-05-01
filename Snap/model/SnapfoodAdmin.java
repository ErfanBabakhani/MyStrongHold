package model;
import java.util.ArrayList;
public class SnapfoodAdmin extends User {
    
    private static SnapfoodAdmin single_instance = null;
    
    protected SnapfoodAdmin(){
        super();
    }

    public static synchronized SnapfoodAdmin getInstance() {
        if (single_instance == null)
            single_instance = new SnapfoodAdmin();

        return single_instance;
    }


}