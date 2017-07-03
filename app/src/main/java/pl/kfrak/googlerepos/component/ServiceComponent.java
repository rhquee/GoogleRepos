package pl.kfrak.googlerepos.component;

import javax.inject.Singleton;

import dagger.Component;
import pl.kfrak.googlerepos.MainActivity;
import pl.kfrak.googlerepos.modules.ServiceModule;

/**
 * Created by RENT on 2017-07-03.
 */

@Singleton
@Component(modules = {ServiceModule.class})
public interface ServiceComponent {

    void inject(MainActivity activity);

}
