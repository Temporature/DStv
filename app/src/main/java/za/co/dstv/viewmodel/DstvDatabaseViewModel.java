package za.co.dstv.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import za.co.dstv.model.TodoModel;
import za.co.dstv.repository.TodoDatabaseRepository;

import java.util.List;

public class DstvDatabaseViewModel extends AndroidViewModel {

    private TodoDatabaseRepository repository;
    private LiveData<List<TodoModel>> listMutableLiveData;

    public DstvDatabaseViewModel(@NonNull Application application) {
        super(application);
        repository = new TodoDatabaseRepository(application);
        listMutableLiveData = repository.getAllData();
    }


    public void insert(TodoModel todoModel) {
        repository.insert(todoModel);
    }

     public LiveData<Integer> getTotalCount() {
        return repository.getTotalCount();
    }

    public LiveData<Integer> getTaskDoneCount() {
         return repository.getTaskDoneCount();
    }

    public void updateRecord(TodoModel todoModel){
        repository.updateRecord(todoModel);
    }
    public void delete(TodoModel todoModel) {
        repository.delete(todoModel);
    }
    public LiveData<List<TodoModel>> getAllData() {
        return listMutableLiveData;
    }
}