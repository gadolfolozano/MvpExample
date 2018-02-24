package gadolfolozano.pe.mvpexample.data.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;
import javax.inject.Singleton;

import gadolfolozano.pe.mvpexample.data.entity.UserEntity;
import gadolfolozano.pe.mvpexample.view.model.ModelResponse;

/**
 * Created by gustavo.lozano on 2/23/2018.
 */
@Singleton
public class UserRepository {

    FirebaseAuth mAuth;

    @Inject
    public UserRepository() {
        mAuth = FirebaseAuth.getInstance();
    }

    public LiveData<ModelResponse<UserEntity>> signIn(String email, String password) {
        final MutableLiveData<ModelResponse<UserEntity>> data = new MutableLiveData<>();
        final ModelResponse<UserEntity> modelResponse = new ModelResponse<>();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (task.isSuccessful() && user != null) {
                            UserEntity userEntity = new UserEntity();
                            userEntity.setAuthToken(user.getUid());
                            userEntity.setEmailAddress(user.getEmail());

                            data.setValue(modelResponse.createSucces(userEntity));
                        } else {
                            data.setValue(modelResponse.createError(null));
                        }
                    }
                });
        return data;
    }

    public LiveData<ModelResponse<UserEntity>> registerUser(String email, String password) {
        final MutableLiveData<ModelResponse<UserEntity>> data = new MutableLiveData<>();
        final ModelResponse<UserEntity> modelResponse = new ModelResponse<>();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (task.isSuccessful() && user != null) {
                            UserEntity userEntity = new UserEntity();
                            userEntity.setAuthToken(user.getUid());
                            userEntity.setEmailAddress(user.getEmail());

                            data.setValue(modelResponse.createSucces(userEntity));
                        } else {
                            data.setValue(modelResponse.createError(null));
                        }
                    }
                });
        return data;
    }

}
