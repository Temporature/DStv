package za.co.dstv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;

import za.co.dstv.fragments.MasterFragment;
import za.co.dstv.fragments.TodoListFragment;
import za.co.dstv.utils.AppHelper;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppHelper.getInstance().addFragment(this, TodoListFragment.getInstance(), false);

    }


}
