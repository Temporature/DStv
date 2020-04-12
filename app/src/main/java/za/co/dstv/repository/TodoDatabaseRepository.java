package za.co.dstv.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import za.co.dstv.database.TodoDao;
import za.co.dstv.database.TodoDatabase;
import za.co.dstv.model.TodoModel;

import java.util.List;

public class TodoDatabaseRepository {

    private TodoDao todoDao;
    private LiveData<List<TodoModel>> listMutableLiveData;

    public TodoDatabaseRepository(Application application){
        TodoDatabase database = TodoDatabase.getInstance(application);
        todoDao = database.todoDao();
        listMutableLiveData = todoDao.getAllData();
    }

    public void insert(TodoModel listMutableLiveData){

        new InsertDataAsyncTask(todoDao).execute(listMutableLiveData);

    }

     public LiveData<Integer> getTotalCount(){
        return  todoDao.getTotalCount();
    }
    public LiveData<Integer> getTaskDoneCount(){
         return todoDao.getTaskDoneCount(1);
    }

    public void updateRecord(TodoModel listMutableLiveData){

        new UpdateDataAsyncTask(todoDao).execute(listMutableLiveData);

    }

    public LiveData<List<TodoModel>> getAllData(){

        return listMutableLiveData;

    }

    public void delete(TodoModel model){
        new DeleteDataAsyncTask(todoDao).execute(model);
    }

    private static class InsertDataAsyncTask extends AsyncTask<TodoModel,Void,Void>{

        private TodoDao mTodoDao;

        private InsertDataAsyncTask(TodoDao mTodoDao){
            this.mTodoDao = mTodoDao;
        }

        @Override
        protected Void doInBackground(TodoModel... mutableLiveData) {
            mTodoDao.insert(mutableLiveData[0]);
            return null;
        }
    }

    private static class UpdateDataAsyncTask extends AsyncTask<TodoModel,Void,Void>{

        private TodoDao mTodoDao;

        private UpdateDataAsyncTask(TodoDao mTodoDao){
            this.mTodoDao = mTodoDao;
        }

        @Override
        protected Void doInBackground(TodoModel... mutableLiveData) {
            mTodoDao.updateRecord(mutableLiveData[0].getPriority(),mutableLiveData[0].getStatus(),mutableLiveData[0].getTitle(),mutableLiveData[0].getSubTitle(),mutableLiveData[0].getId());
            return null;
        }
    }

    private static class DeleteDataAsyncTask extends AsyncTask<TodoModel,Void,Void>{

        private TodoDao mTodoDao;

        private DeleteDataAsyncTask(TodoDao mTodoDao){
            this.mTodoDao = mTodoDao;
        }

        @Override
        protected Void doInBackground(TodoModel... mutableLiveData) {
            mTodoDao.delete(mutableLiveData[0]);
            return null;
        }
    }

}
