package gadolfolozano.pe.mvpexample.data.ws;

import gadolfolozano.pe.mvpexample.util.Constanst;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by gustavo.lozano on 2/16/2018.
 */

public class APIClient {
    private APIClient() {
        throw new IllegalStateException("Utility class");
    }

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {

        if (retrofit != null) {
            return retrofit;
        }

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).cache(null).build();


        retrofit = new Retrofit.Builder()
                .baseUrl(Constanst.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }
}
