package be.ehb.Todolo;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import be.ehb.Todolo.ViewModel.TaskViewModel;
import be.ehb.Todolo.parceble.ListParcel;

public class ActivityTodoListWithDetail extends AppCompatActivity {

    TaskViewModel listWithTasks;
    TaskListFragment taskListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_with_detal);
        listWithTasks = ViewModelProviders.of(this).get(TaskViewModel.class);




        //nav balk
        Toolbar toolbar = findViewById(R.id.activitytodolistwithdetail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        if (intent.hasExtra("listwithtask")) {
            ListParcel listParcel = intent.getParcelableExtra("listwithtask");
            if (listParcel.id != -1 ) {
                getSupportActionBar().setTitle(listParcel.title);
                TaskListFragment taskListFragment = TaskListFragment.newInstance(listParcel);
                if (findViewById(R.id.Single_pane) != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.Single_pane, taskListFragment).commit();
                }
                else if(findViewById(R.id.Recycler_pane) != null)
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.Recycler_pane, taskListFragment).commit();
                }


            }
        }
        else if (intent.hasExtra("Starlisted"))
        {
            getSupportActionBar().setTitle(R.string.btn_stared_text);
            taskListFragment = TaskListFragment.newInstance(null);
            if (findViewById(R.id.Single_pane) != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.Single_pane, taskListFragment).commit();
            }
            else if(findViewById(R.id.Recycler_pane) != null)
            {
                getSupportFragmentManager().beginTransaction().replace(R.id.Recycler_pane, taskListFragment).commit();
            }
        }
        else
        {
            finish();
        }


    }
}