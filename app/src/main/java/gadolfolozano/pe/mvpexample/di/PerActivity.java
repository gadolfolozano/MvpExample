package gadolfolozano.pe.mvpexample.di;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by gustavo.lozano on 2/20/2018.
 */

@Scope
@Retention(RUNTIME)
public @interface PerActivity {}
