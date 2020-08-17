package be.ehb.Todolo;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import be.ehb.Todolo.ViewModel.TaskViewModel;
import be.ehb.Todolo.fragmentinterfaces.DetailTaskInterface;
import be.ehb.Todolo.fragmentinterfaces.RecyclerTaskFragmentInterface;
import be.ehb.Todolo.fragmentinterfaces.RecylcerTaskInterface;
import be.ehb.Todolo.parceble.ListParcel;
import be.ehb.Todolo.room.Entity.Task;

public class ActivityTodoListWithDetail extends AppCompatActivity implements DetailTaskInterface , RecyclerTaskFragmentInterface {

    TaskViewModel listWithTasks;
    TaskListFragment taskListFragment;
    DetailView detailViewFragment;

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
                TaskListFragment taskListFragment = TaskListFragment.newInstance(listParcel,this);
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
            taskListFragment = TaskListFragment.newInstance(null,this);
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

    @Override
    public void onEditSend(int id) {


    }

    @Override
    public void sendDetails(int posistion) {

        detailViewFragment = DetailView.newInstance(posistion,this);
        if (findViewById(R.id.Single_pane) != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.Single_pane, detailViewFragment).addToBackStack(null).commit();

        }
        else if(findViewById(R.id.Recycler_pane) != null)
        {

            getSupportFragmentManager().beginTransaction().replace(R.id.detail_pane,detailViewFragment).commit();
        }


    }
}