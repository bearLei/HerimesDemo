package unit;


import impl.IUserManager;

/**
 * create by lei on 2019/1/28/028
 * desc:
 */
public class UserManager implements IUserManager {


    private static final UserManager ourInstance = new UserManager();

    public static UserManager getInstance() {
        return ourInstance;
    }

    public UserManager() {
    }

    private Person person;

    public Person getPerson() {
        return person;
    }

    @Override
    public void setPerson(Person person) {
        this.person = person;
    }
}
