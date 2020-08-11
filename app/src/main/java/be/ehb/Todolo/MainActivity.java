package be.ehb.Todolo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import be.ehb.Todolo.Apapters.TodoAdapter;
import be.ehb.Todolo.ViewModel.TodoViewModel;
import be.ehb.Todolo.fragmentinterfaces.CreateTodoInterface;
import be.ehb.Todolo.interfaces.TodoOnItemClickListener;
import be.ehb.Todolo.room.Entity.TodoList;
import be.ehb.Todolo.room.Entity.TodoListWithTasks;

public class MainActivity extends AppCompatActivity implements CreateTodoInterface {

    private DrawerLayout drawerLayout;
    private TodoAdapter adapter;
    private TodoViewModel todoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // recycler view
        RecyclerView recyclerView = findViewById(R.id.todo_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter = new TodoAdapter(this);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new TodoOnItemClickListener() {
            @Override
            public void onEditClick(int position) {
                    TodoListWithTasks listWithTasks = adapter.getItemOnPosition(position);
                    createFragment(listWithTasks.getTodoList(),position);
            }
        });

        //data and setting to recycler view
        todoViewModel = ViewModelProviders.of(this).get(TodoViewModel.class);
        todoViewModel.getAllTodo().observe(this, new Observer<List<TodoListWithTasks>>() {
            @Override
            public void onChanged(@Nullable List<TodoListWithTasks> todoListWithTasks) {

                adapter.setTodo(todoListWithTasks);


            }
        });

        //swipe function for edit and delete
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {


            }
        });


        //floating button
        FloatingActionButton floatingActionButton = findViewById(R.id.floating_addList);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createFragment(null,-1);
            }
        });

        //nav balk
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_mainactivity_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.nav_drawer_open,R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


    }


    @Override
    public void createFragment(TodoList list,int position) {

        CreateTodoFragment createTodoFragment = CreateTodoFragment.newInstance(list,position);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_right,R.anim.enter_from_right,R.anim.exit_to_right);
        transaction.addToBackStack(null);
        transaction.add(R.id.home_layout,createTodoFragment,"CREATETODO_FRAGMENT").commit();

    }

    @Override
    public void onFragmentInteraction(TodoList list,int position) {

        FloatingActionButton fab = this.findViewById(R.id.floating_addList);
        fab.setVisibility(View.VISIBLE);
        if(list != null)
        {
          if (position == -1) {
              todoViewModel.insert(list);
          }
          else
              {
                  todoViewModel.update(list);
                  adapter.notifyItemChanged(position);
              }
        }
        onBackPressed();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_upper_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_list_delete_all)
        {
            //TODO: 2 string nog aan maken zo dat  ze vertaald kunnen worden
            new AlertDialog.Builder(this)
                    .setTitle("Title")
                    .setMessage("Do you really want to whatever?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            todoViewModel.deleteall();
                            Toast.makeText(MainActivity.this, R.string.toast_message_all_List_deleted, Toast.LENGTH_SHORT).show();
                        }})
                    .setNegativeButton(android.R.string.no, null).show();
                

        }
        return super.onOptionsItemSelected(item);
    }
}