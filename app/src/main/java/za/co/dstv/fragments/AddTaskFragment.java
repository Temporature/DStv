package za.co.dstv.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import za.co.dstv.R;
import za.co.dstv.model.TodoModel;
import za.co.dstv.utils.AppConstants;
import za.co.dstv.utils.AppHelper;
import za.co.dstv.viewmodel.DstvDatabaseViewModel;

public class AddTaskFragment extends Fragment {

    private RadioGroup rgPriority;
    private AppCompatButton btnSave;
    private TextInputLayout tlTask, tlDescription;
    private DstvDatabaseViewModel dstvDBViewModel;
    private EditText etTask, etDescription;
    private TodoModel todoModel;
    private final int INPUT_TEXT_LENGTH = 20;
    private final int INPUT_TEXT_LENGTH_MAX = 100;
    private final static String EDIT = "edit";

    public static AddTaskFragment getInstance(TodoModel model) {
        AddTaskFragment mAddTaskFragment = new AddTaskFragment();
        if (model != null) {
            Bundle bundle = new Bundle();
            bundle.putString(EDIT, AppHelper.getInstance().convertObjectToString(model));
            mAddTaskFragment.setArguments(bundle);
        }
        return mAddTaskFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null && !TextUtils.isEmpty(bundle.getString(EDIT)))
            todoModel = AppHelper.getInstance().convertStringToObject(bundle.getString(EDIT), TodoModel.class);

        dstvDBViewModel = new DstvDatabaseViewModel(getActivity().getApplication());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_task, container, false);
        rgPriority = view.findViewById(R.id.rgPriority);
        btnSave = view.findViewById(R.id.btnSave);
        tlTask = view.findViewById(R.id.tlTask);
        tlDescription = view.findViewById(R.id.tlDescription);
        etTask = view.findViewById(R.id.etTask);
        etDescription = view.findViewById(R.id.etDescription);
        validateAndSave(view);
        validateTextInput();
        editDetails(todoModel);
        return view;
    }

    private void validateTextInput() {
        etTask.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() >= INPUT_TEXT_LENGTH)
                    tlTask.setError(getString(R.string.task_name_error_watcher));
                else
                    tlTask.setError(null);

            }
        });

        etDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() >= INPUT_TEXT_LENGTH_MAX)
                    tlDescription.setError(getString(R.string.task_description_error_watcher));
                else
                    tlDescription.setError(null);

            }
        });
    }

    private void validateAndSave(View view) {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int radioButtonId = rgPriority.getCheckedRadioButtonId();
                int priority = 0;
                String task = null;
                String description = null;

                switch (radioButtonId) {
                    case R.id.rbHigh:
                        priority = AppConstants.PRIORITY_HIGH;
                        break;
                    case R.id.rbMedium:
                        priority = AppConstants.PRIORITY_MEDIUM;
                        break;
                    case R.id.rbLow:
                        priority = AppConstants.PRIORITY_LOW;
                        break;

                }

                if (!TextUtils.isEmpty(etTask.getText().toString())) {
                    task = etTask.getText().toString();
                } else {
                    Toast.makeText(getContext(), R.string.task_not_empty, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!TextUtils.isEmpty(etDescription.getText().toString())) {
                    description = etDescription.getText().toString();
                } else {
                    Toast.makeText(getContext(), R.string.description_not_empty, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (priority > 0 && !TextUtils.isEmpty(task) && !TextUtils.isEmpty(description)) {
                    TodoModel model = new TodoModel();
                    model.setTitle(task);
                    model.setSubTitle(description);
                    model.setPriority(priority);
                    model.setStatus(AppConstants.INCOMPLETE);
                    if(todoModel != null){
                        model.setId(todoModel.getId());
                        dstvDBViewModel.updateRecord(model);
                    }else {
                        dstvDBViewModel.insert(model);
                    }

                    Objects.requireNonNull(getActivity()).onBackPressed();
                } else {
                    Toast.makeText(getContext(), R.string.manadatory_fields, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void editDetails(TodoModel model) {
        try {
            if(model != null){
                int priority = model.getPriority();
                switch (priority) {
                    case AppConstants.PRIORITY_HIGH:
                        rgPriority.check(R.id.rbHigh);
                        break;
                    case AppConstants.PRIORITY_MEDIUM:
                        rgPriority.check(R.id.rbMedium);
                        break;
                    case AppConstants.PRIORITY_LOW:
                        rgPriority.check(R.id.rbLow);
                        break;

                }

                etTask.setText(model.getTitle());
                etDescription.setText(model.getSubTitle());
                btnSave.setText(R.string.update);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
