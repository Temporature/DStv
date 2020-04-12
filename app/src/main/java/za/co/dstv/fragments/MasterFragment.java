package za.co.dstv.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import za.co.dstv.R;
import za.co.dstv.adapter.TodoListRecyclerViewAdapter;
import za.co.dstv.model.TodoModel;
import za.co.dstv.utils.AppConstants;
import za.co.dstv.utils.ProgressDialogHelper;
import za.co.dstv.viewmodel.DstvDatabaseViewModel;
import za.co.dstv.viewmodel.TodoListViewModel;

import java.util.ArrayList;
import java.util.List;

public class MasterFragment extends Fragment {


    private TodoListViewModel personViewModel;
    private RecyclerView rvRecycler;
    private TodoModel person;
    private boolean orientation;
    private ProgressDialogHelper mProgressDialogHelper;
    private DstvDatabaseViewModel personDBViewModel;


    public static MasterFragment getInstance(boolean orientation) {
        MasterFragment masterFragment = new MasterFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(AppConstants.TAG_orientation, orientation);
        masterFragment.setArguments(bundle);
        return masterFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        person = new TodoModel();
        personViewModel = new TodoListViewModel(getActivity().getApplication(), person);
        personDBViewModel = new DstvDatabaseViewModel(getActivity().getApplication());
        Bundle bundle = getArguments();
        if (bundle != null) {
            orientation = bundle.getBoolean(AppConstants.TAG_orientation);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_master, container, false);
        rvRecycler = rootView.findViewById(R.id.rvRecycler);
        rvRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mProgressDialogHelper = new ProgressDialogHelper(getContext());
        mProgressDialogHelper.show();
        getDataFromDataBase();
        return rootView;
    }

    /*This method is responsible to observe data change in database
    If Database is empty get the data from API */
    private void getDataFromDataBase() {
        personDBViewModel.getAllData().observe(getViewLifecycleOwner(), new Observer<List<TodoModel>>() {
            @Override
            public void onChanged(List<TodoModel> personViewModels) {
                if (personViewModels != null && personViewModels.size() > 0) {
                    ArrayList<TodoListViewModel> arrayList = new ArrayList<>();
                    for (TodoModel mPerson : personViewModels) {
                        arrayList.add(new TodoListViewModel(getActivity().getApplication(), mPerson));

                    }
                   /* TodoListRecyclerViewAdapter customAdapter = new TodoListRecyclerViewAdapter(getActivity(), arrayList);
                    rvRecycler.setAdapter(customAdapter);*/
                    mProgressDialogHelper.hide();
                }
            }
        });
    }




}
