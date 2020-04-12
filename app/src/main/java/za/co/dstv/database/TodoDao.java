package za.co.dstv.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import za.co.dstv.model.TodoModel;
import za.co.dstv.utils.AppConstants;

import java.util.List;

@Dao
public interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TodoModel listMutableLiveData);

    @Update
    void update(TodoModel todoModel);

    @Delete
    void delete(TodoModel todoModel);

    @Query("DELETE FROM "+ AppConstants.TABLE_TODO)
    void deleteAllNotes();

    @Query("SELECT * FROM "+AppConstants.TABLE_TODO)
    LiveData<List<TodoModel>> getAllData();


    @Query("UPDATE table_todo SET  priority = :priority,status = :status,title =:title,subTitle = :subTitle WHERE id =:id")
    void updateRecord(int priority,int status,String title,String subTitle,int id);

    @Query("SELECT COUNT(*) FROM "+AppConstants.TABLE_TODO)
    LiveData<Integer> getTotalCount();

    @Query("SELECT COUNT(*) FROM "+AppConstants.TABLE_TODO+" WHERE status = :status")
    LiveData<Integer> getTaskDoneCount(int status);
}
