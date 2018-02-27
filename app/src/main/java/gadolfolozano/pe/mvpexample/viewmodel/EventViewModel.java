package gadolfolozano.pe.mvpexample.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import java.util.List;

import gadolfolozano.pe.mvpexample.data.repository.EventRepository;
import gadolfolozano.pe.mvpexample.view.model.EventModel;
import gadolfolozano.pe.mvpexample.view.model.ModelResponse;
import gadolfolozano.pe.mvpexample.view.model.UserModel;

/**
 * Created by adolfo on 25/02/18.
 */

public class EventViewModel extends ViewModel {
    private EventRepository eventRepository;

    public EventViewModel(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void init() {
        //Do Nothing
    }

    public LiveData<ModelResponse<EventModel>> saveEvent(EventModel eventModel, String userId) {
        return eventRepository.saveEvent(eventModel, userId);
    }

    public LiveData<ModelResponse<List<EventModel>>> getEvents(){
        return eventRepository.getEvents();
    }

    public LiveData<ModelResponse<List<EventModel>>> getEvents(String userId){
        return eventRepository.getEvents(userId);
    }

    public LiveData<ModelResponse<EventModel>> enrollToEvent(String eventId, String userId) {
        return eventRepository.enrollToEvent(eventId, userId);
    }

    public LiveData<ModelResponse<List<UserModel>>> getEnrolleds(String eventId){
        return eventRepository.getEnrolleds(eventId);
    }

    public static class Factory implements ViewModelProvider.Factory {
        private final EventRepository dependency;

        public Factory(EventRepository dependency) {
            this.dependency = dependency;
        }

        @SuppressWarnings("unchecked")
        @Override
        public EventViewModel create(Class modelClass) {
            return new EventViewModel(dependency);
        }
    }
}
