package impl;


import annotion.ClassId;
import unit.Person;

/**
 * create by lei on 2019/1/28/028
 * desc:
 */
@ClassId("android.app.unit.Person.unit.UserManager")
public interface IUserManager {

    public Person getPerson() ;

    public void setPerson(Person person) ;
}
