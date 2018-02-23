package gadolfolozano.pe.mvpexample.data.ws.service;

import gadolfolozano.pe.mvpexample.data.ws.APIClient;
import gadolfolozano.pe.mvpexample.data.ws.APIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gustavo.lozano on 2/16/2018.
 */

public abstract class ServiceBase<T> {

    private ServiceListener<T> serviceListener;

    public ServiceListener<T> getServiceListener() {
        return serviceListener;
    }

    public void setServiceListener(ServiceListener<T> serviceListener) {
        this.serviceListener = serviceListener;
    }

    protected ServiceBase() {
        //Do Nothing
    }

    public void execute() {
        Call<T> call = createCall();
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (serviceListener != null) {
                    if (response.body() != null) {
                        serviceListener.onSucess(response.body());
                    } else {
                        serviceListener.onError(new Exception());
                    }
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                call.cancel();
                if (serviceListener != null) {
                    serviceListener.onError(t);
                }
            }
        });
    }

    protected abstract Call<T> createCall();

    protected APIInterface getAPIInterface() {
        return APIClient.getClient().create(APIInterface.class);
    }

}
