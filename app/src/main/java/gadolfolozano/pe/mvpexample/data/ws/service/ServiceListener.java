package gadolfolozano.pe.mvpexample.data.ws.service;

/**
 * Created by gustavo.lozano on 2/16/2018.
 */

public interface ServiceListener<T> {
    void onSucess(T response);

    void onError(Throwable t);
}
