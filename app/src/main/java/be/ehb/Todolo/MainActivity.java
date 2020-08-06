package be.ehb.Todolo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

import be.ehb.Todolo.Apapters.TodoAdapter;
import be.ehb.Todolo.ViewModel.TodoViewModel;
import be.ehb.Todolo.room.Entity.TodoListWithTasks;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    private TodoViewModel todoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RecyclerView recyclerView = findViewById(R.id.todo_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final TodoAdapter adapter = new TodoAdapter(this);
        recyclerView.setAdapter(adapter);


        //data

        todoViewModel = ViewModelProviders.of(this).get(TodoViewModel.class);
        todoViewModel.getAllTodo().observe(this, new Observer<List<TodoListWithTasks>>() {
            @Override
            public void onChanged(@Nullable List<TodoListWithTasks> todoListWithTasks) {

                adapter.setTodo(todoListWithTasks);


            }
        });

        //nav balk
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.ic_logo);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_mainactivity_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.nav_drawer_open,R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


    }
}