package gadolfolozano.pe.mvpexample.di.component;

import javax.inject.Singleton;

import dagger.Component;
import gadolfolozano.pe.mvpexample.di.module.AlbumModule;
import gadolfolozano.pe.mvpexample.view.activity.MainActivity;

/**
 * Created by gustavo.lozano on 2/20/2018.
 */
@Singleton
@Component(modules = { AlbumModule.class })
public interface AlbumComponent {
    void inject(MainActivity mainActivity);
}
