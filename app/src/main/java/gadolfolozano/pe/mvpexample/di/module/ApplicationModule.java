package gadolfolozano.pe.mvpexample.di.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import gadolfolozano.pe.mvpexample.AndroidApplication;

/**
 * Created by gustavo.lozano on 2/20/2018.
 */
@Module
public class ApplicationModule {
    private final AndroidApplication application;

    public ApplicationModule(AndroidApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }
}
