package za.co.dstv.utils;

/**
 * Interface for all tags constants
 * Created by Sharadw.
 */
public interface AppConstants {

    String EMPTY = "";
    String SUCCESS = "success",
            TAG_appid = "appid",
            FRAGMENT_TAG = "myFragment";

    String TAG_personName = "personName";
    String TAG_personId = "personId";
    String TAG_orientation = "orientation";

    //database
    String TABLE_TODO = "table_todo";
    int COMPLETE = 1 ;
    int INCOMPLETE = 0 ;

    int PRIORITY_HIGH = 3;
    int PRIORITY_MEDIUM = 2;
    int PRIORITY_LOW = 1;
    int PRIORITY_NO = 0;

}
