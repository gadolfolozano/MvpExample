package gadolfolozano.pe.mvpexample.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import gadolfolozano.pe.mvpexample.data.entity.UserEntity;
import gadolfolozano.pe.mvpexample.data.repository.UserRepository;
import gadolfolozano.pe.mvpexample.view.model.ModelResponse;

/**
 * Created by gustavo.lozano on 2/23/2018.
 */

public class UserViewModel extends ViewModel {
    private UserRepository userRepository;

    public UserViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void init() {
        //Do Nothing
    }

    public LiveData<ModelResponse<UserEntity>> signIn(String email, String password) {
        return userRepository.signIn(email, password);
    }

    public LiveData<ModelResponse<UserEntity>> registerUser(String email, String password) {
        return userRepository.registerUser(email, password);
    }

    public static class Factory implements ViewModelProvider.Factory {
        private final UserRepository dependency;

        public Factory(UserRepository dependency) {
            this.dependency = dependency;
        }

        @SuppressWarnings("unchecked")
        @Override
        public UserViewModel create(Class modelClass) {
            return new UserViewModel(dependency);
        }
    }
}
