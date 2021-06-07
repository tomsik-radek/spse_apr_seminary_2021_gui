package tomsikr_seminarka;

public class Users {
	String name;
	String password;
	String permission;
	String phoneNumber;
	String age;
	int id;

    public Users(int id, String name, String password, String permission, String age, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.permission = permission;
        this.phoneNumber = phoneNumber;
        this.age = age;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
