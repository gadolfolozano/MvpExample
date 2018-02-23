package gadolfolozano.pe.mvpexample.util;

import android.text.TextUtils;
import android.util.Patterns;

/**
 * Created by gustavo.lozano on 2/23/2018.
 */

public class StringValidation {
    private StringValidation() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean isValidEmail(String email){
        return email!= null && !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidPassword(String password){
        return password != null && password.length() > 6;
    }
}
