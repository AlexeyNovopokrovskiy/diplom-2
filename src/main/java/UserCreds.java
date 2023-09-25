public class UserCreds {

    private String email;
    private String password;
    private String name;

    public UserCreds(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public UserCreds(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static UserCreds credsFrom(JUser user){
        return new UserCreds(user.getEmail(), user.getPassword());
    }

    public static UserCreds credsChangedEmail(JUser user){
        String newEmail = "1" + user.getEmail();
        return new UserCreds(newEmail, user.getPassword());
    }

    public static UserCreds credsChangedPassword(JUser user){
        String newPassword = user.getPassword() + "1";
        return new UserCreds(user.getEmail(), newPassword);
    }

    public static UserCreds changeUserCredsEmail(JUser user){
        String newEmail = "1" + user.getEmail();
        return new UserCreds(newEmail, user.getPassword(), user.getName());
    }

    public static UserCreds changeUserCredsPassword(JUser user){
        String newPassword = user.getPassword() + "1";
        return new UserCreds(user.getEmail(), newPassword, user.getName());
    }

    public static UserCreds changeUserCredsName(JUser user){
        String newName = user.getName() + "1";
        return new UserCreds(user.getEmail(), user.getPassword(), newName);
    }



    public String getName() {
        return name;
    }
}
