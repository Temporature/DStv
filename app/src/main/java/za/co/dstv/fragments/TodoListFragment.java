package za.co.dstv.fragments;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;

import za.co.dstv.R;
import za.co.dstv.adapter.TodoListRecyclerViewAdapter;
import za.co.dstv.component.GoalProgress;
import za.co.dstv.database.UpdateDatabase;
import za.co.dstv.model.TodoModel;
import za.co.dstv.utils.AppHelper;
import za.co.dstv.utils.ProgressDialogHelper;
import za.co.dstv.viewmodel.DstvDatabaseViewModel;

public class TodoListFragment extends Fragment implements UpdateDatabase {

    private GoalProgress pbGoalProgress;
    private RecyclerView rvTodoList;
    private DstvDatabaseViewModel dstvDBViewModel;
    private ProgressDialogHelper mProgressDialogHelper;
    private FloatingActionButton mFloatingActionButton;
    private TextView tvMsg;

    public static TodoListFragment getInstance() {
        TodoListFragment todoListFragment = new TodoListFragment();
        return todoListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dstvDBViewModel = new DstvDatabaseViewModel(getActivity().getApplication());

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_todo_list, container, false);
        pbGoalProgress = rootView.findViewById(R.id.pbGoalProgress);
        tvMsg = rootView.findViewById(R.id.tvMsg);
        rvTodoList = rootView.findViewById(R.id.rvTodoList);
        rvTodoList.setLayoutManager(new LinearLayoutManager(getContext()));
        mProgressDialogHelper = new ProgressDialogHelper(getContext());
        mFloatingActionButton = rootView.findViewById(R.id.fabButton);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppHelper.getInstance().replaceFragment(getActivity(), AddTaskFragment.getInstance(null), true);
            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mProgressDialogHelper.show();
        getDataFromDataBase();
        calculateProgress();
    }

    /*This method is responsible to observe data change in database
         */
    private void getDataFromDataBase() {
        dstvDBViewModel.getAllData().observe(getViewLifecycleOwner(), new Observer<List<TodoModel>>() {
            @Override
            public void onChanged(List<TodoModel> todoModels) {
                if (todoModels != null && todoModels.size() > 0) {
                    tvMsg.setVisibility(View.GONE);
                    TodoListRecyclerViewAdapter customAdapter = new TodoListRecyclerViewAdapter(getActivity(), todoModels, dstvDBViewModel, TodoListFragment.this);
                    rvTodoList.setAdapter(customAdapter);
                }else{
                    tvMsg.setVisibility(View.VISIBLE);
                }
                mProgressDialogHelper.hide();
            }
        });
    }

    @Override
    public void onUpdateRecord(TodoModel model) {
        getDataFromDataBase();

    }
    private Double count ;
    private Double countTask;
    private void calculateProgress() {
        try {

            dstvDBViewModel.getTotalCount().observe(this, new Observer<Integer>() {
                @Override
                public void onChanged(Integer integer) {
                    count = Double.valueOf(integer);
                }
            });

            dstvDBViewModel.getTaskDoneCount().observe(this, new Observer<Integer>() {
                @Override
                public void onChanged(Integer integer) {
                    countTask = Double.valueOf(integer);
                    pbGoalProgress.setActiveText("Task to complete");
                    if(count != null && countTask != null) {
                        pbGoalProgress.setProgress(countTask.floatValue(), count.floatValue());
                        if (count > 0) {
                            double percent = (countTask / count) * 100;
                            pbGoalProgress.setInactiveActiveText(String.format("%.02f", percent) + "% Done");
                        }
                    }

                }
            });


        } catch (ArithmeticException e) {
            e.printStackTrace();
        }

    }

}
