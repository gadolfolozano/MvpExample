package gadolfolozano.pe.mvpexample.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import gadolfolozano.pe.mvpexample.ws.service.GetAlbumsService;

/**
 * Created by gustavo.lozano on 2/20/2018.
 */

@Module
public class RestServiceModule {
    @Provides
    @Singleton
    GetAlbumsService providesGetAlbumsService() {
        return new GetAlbumsService("");
    }
}
