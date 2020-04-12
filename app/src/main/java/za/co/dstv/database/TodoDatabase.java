package za.co.dstv.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import za.co.dstv.model.TodoModel;
import za.co.dstv.utils.AppConstants;

@Database(entities = {TodoModel.class}, version = 1)
public abstract class TodoDatabase extends RoomDatabase {

    private static TodoDatabase instance;

    public abstract TodoDao todoDao();

    public static synchronized TodoDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                     TodoDatabase.class, AppConstants.TABLE_TODO)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
