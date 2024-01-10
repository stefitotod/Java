public abstract class User {

    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUserName() {
        return username;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);   
    }

    public abstract UserType getUserType();
    
}
