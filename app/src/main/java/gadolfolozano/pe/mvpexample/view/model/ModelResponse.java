package gadolfolozano.pe.mvpexample.view.model;

/**
 * Created by adolfo on 24/02/18.
 */

public class ModelResponse<T> {
    public static final int SUCCESS = 1;
    public static final int ERROR = 0;

    public ModelResponse(){
        this.status = ERROR;
    }

    private ModelResponse(int status, T body){
        this.status = status;
        this.body = body;
    }

    private int status;
    private T body;

    public T getBody() {
        return body;
    }

    public int getStatus() {
        return status;
    }

    public ModelResponse<T>  createSucces(T body){
        return new ModelResponse<>(SUCCESS, body);
    }

    public ModelResponse<T> createError(T body){
        return new ModelResponse<>(ERROR, body);
    }
}
