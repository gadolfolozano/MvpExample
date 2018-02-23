package gadolfolozano.pe.mvpexample.data.entity;

/**
 * Created by gustavo.lozano on 2/23/2018.
 */

public class UserEntity {

    private String authToken;
    private String emailAddress;

    public UserEntity() {
        //Do Nothing
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
