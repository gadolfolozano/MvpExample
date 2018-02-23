package gadolfolozano.pe.mvpexample.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import gadolfolozano.pe.mvpexample.data.entity.UserEntity;
import gadolfolozano.pe.mvpexample.data.repository.UserRepository;

/**
 * Created by gustavo.lozano on 2/23/2018.
 */

public class UserViewModel extends ViewModel {
    private UserRepository userRepository;

    /*@Inject // UserRepository parameter is provided by Dagger 2
    public UserViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }*/

    public void init() {
        this.userRepository = new UserRepository();
    }

    public LiveData<UserEntity> signIn(String email, String password) {
        return userRepository.signIn(email, password);
    }
}
