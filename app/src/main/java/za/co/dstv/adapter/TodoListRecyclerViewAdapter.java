package za.co.dstv.adapter;


import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import za.co.dstv.R;
import za.co.dstv.component.RoundedCornerCardView;
import za.co.dstv.database.UpdateDatabase;
import za.co.dstv.fragments.AddTaskFragment;
import za.co.dstv.model.TodoModel;
import za.co.dstv.presenter.TodoPresenter;
import za.co.dstv.utils.AppConstants;
import za.co.dstv.utils.AppHelper;
import za.co.dstv.viewmodel.DstvDatabaseViewModel;
import za.co.dstv.viewmodel.TodoListViewModel;

public class TodoListRecyclerViewAdapter extends RecyclerView.Adapter<TodoListRecyclerViewAdapter.ViewHolder> implements TodoPresenter {

    private FragmentActivity mContext;
    private final List<TodoModel> mValues;
    private LayoutInflater mLayoutInflater;
    private DstvDatabaseViewModel dstvDatabaseViewModel;
    private UpdateDatabase mUpdateDatabase;
    private TodoModel editModel;


    public TodoListRecyclerViewAdapter(FragmentActivity parent, List<TodoModel> items, DstvDatabaseViewModel dstvDatabaseViewModel, UpdateDatabase mUpdateDatabase) {
        this.mValues = items;
        this.mContext = parent;
        this.dstvDatabaseViewModel = dstvDatabaseViewModel;
        this.mUpdateDatabase = mUpdateDatabase;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = mLayoutInflater.inflate(R.layout.todo_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        TodoModel todoModel = mValues.get(position);
        holder.cvRoundedCornerCardView.setTitleText(todoModel.getTitle());
        holder.cvRoundedCornerCardView.setSubTitleText(todoModel.getSubTitle());
        if (todoModel.getStatus() == AppConstants.COMPLETE) {
            holder.cvRoundedCornerCardView.setImpactImage(mContext.getResources().getDrawable(R.drawable.ic_check_circle));
        } else {
            holder.cvRoundedCornerCardView.setImpactImage(mContext.getResources().getDrawable(R.drawable.ic_uncheck_circle));
        }
        if (todoModel.getPriority() == AppConstants.PRIORITY_HIGH) {
            holder.cvRoundedCornerCardView.setTitleTextColor(mContext.getResources().getColor(R.color.high_impact));
        } else if (todoModel.getPriority() == AppConstants.PRIORITY_MEDIUM) {
            holder.cvRoundedCornerCardView.setTitleTextColor(mContext.getResources().getColor(R.color.medium_impact));
        } else if (todoModel.getPriority() == AppConstants.PRIORITY_LOW) {
            holder.cvRoundedCornerCardView.setTitleTextColor(mContext.getResources().getColor(R.color.low_impact));
        } else {
            holder.cvRoundedCornerCardView.setTitleTextColor(mContext.getResources().getColor(R.color.task_completed));
        }

        holder.cvRoundedCornerCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(todoModel.getStatus() == AppConstants.COMPLETE){
                    Toast.makeText(mContext, R.string.task_done, Toast.LENGTH_SHORT).show();
                }else{editModel = mValues.get(position);
                    showPopupMenu(holder);}

            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


    @Override
    public void clickCard(TodoListViewModel todoListViewModel) {
        Toast.makeText(mContext, "" + todoListViewModel.subTitleText, Toast.LENGTH_SHORT).show();

    }


    class ViewHolder extends RecyclerView.ViewHolder {
        RoundedCornerCardView cvRoundedCornerCardView;

        ViewHolder(View view) {
            super(view);
            cvRoundedCornerCardView = view.findViewById(R.id.cvRoundedCornerCardView);
        }


    }

    private void taskComplete(TodoModel editModel) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        TodoModel mTodoModel = new TodoModel();
                        mTodoModel.setPriority(AppConstants.PRIORITY_NO);
                        mTodoModel.setStatus(AppConstants.COMPLETE);
                        mTodoModel.setId(editModel.getId());
                        mTodoModel.setTitle(editModel.getTitle());
                        mTodoModel.setSubTitle(editModel.getSubTitle());
                        dstvDatabaseViewModel.updateRecord(mTodoModel);
                        mUpdateDatabase.onUpdateRecord(mTodoModel);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("Did you completed the Task?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    private void showPopupMenu(ViewHolder holder) {
        //creating a popup menu
        PopupMenu popup = new PopupMenu(mContext, holder.cvRoundedCornerCardView);
        //inflating menu from xml resource
        popup.inflate(R.menu.main_menu);
        //adding click listener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_edit) {
                    AppHelper.getInstance().replaceFragment(mContext, AddTaskFragment.getInstance(editModel), true);
                } else if (item.getItemId() == R.id.menu_delete) {
                    dstvDatabaseViewModel.delete(editModel);
                } else {
                    taskComplete(editModel);
                }
                return true;
            }
        });
        //displaying the popup
        popup.show();
    }
}