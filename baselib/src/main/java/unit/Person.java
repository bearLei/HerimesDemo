package unit;

/**
 * create by lei on 2019/1/28/028
 * desc:
 */
public class Person {

    private String name;
    private String passWord;

    public Person() {
    }

    public Person(String name, String passWord) {
        this.name = name;
        this.passWord = passWord;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

}
