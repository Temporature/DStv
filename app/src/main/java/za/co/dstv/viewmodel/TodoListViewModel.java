package za.co.dstv.viewmodel;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import za.co.dstv.model.TodoModel;
import za.co.dstv.repository.TodoListRepository;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class TodoListViewModel extends ViewModel {

    public String titleText;
    public String subTitleText;
    private Application mContext;
    public MutableLiveData<ArrayList<TodoListViewModel>> mutableLiveData = new MutableLiveData<>();
    public MutableLiveData<List<TodoModel>> listMutableLiveData = new MutableLiveData<>();
    private ArrayList<TodoListViewModel> arrayList;
    private TodoListRepository mPersonDBRepository;


    public TodoListViewModel(Application mContext, TodoModel todoModel) {
        this.mContext = mContext;
        this.titleText = todoModel.getTitle();
        this.subTitleText = todoModel.getSubTitle();
        mPersonDBRepository = new TodoListRepository(mContext);
    }

}
