public class GenerateUser {

    public static JUser randomUser(){
        return new JUser()
                .withEmail(Utils.randomEmail(6))
                .withPassword(Utils.randomString(8))
                .withName(Utils.randomString(10));
    }

    public static JUser randomUserWOEmail(){
        return new JUser()

                .withPassword(Utils.randomString(8))
                .withName(Utils.randomString(10));
    }

    public static JUser randomUserWOPassword(){
        return new JUser()
                .withEmail(Utils.randomEmail(6))

                .withName(Utils.randomString(10));
    }

    public static JUser randomUserWOName(){
        return new JUser()
                .withEmail(Utils.randomEmail(6))
                .withPassword(Utils.randomString(8))
                ;
    }
}
