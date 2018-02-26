package gadolfolozano.pe.mvpexample.di.component;

import javax.inject.Singleton;

import dagger.Component;
import gadolfolozano.pe.mvpexample.di.module.EventModule;
import gadolfolozano.pe.mvpexample.view.activity.CreateEventActivity;
import gadolfolozano.pe.mvpexample.view.activity.DetailEventActivity;
import gadolfolozano.pe.mvpexample.view.fragment.EventsFragment;

/**
 * Created by adolfo on 25/02/18.
 */
@Singleton
@Component(modules = {EventModule.class})
public interface EventComponent {
    void inject(CreateEventActivity createEventActivity);

    void inject(EventsFragment eventsFragment);

    void inject(DetailEventActivity detailEventActivity);
}
