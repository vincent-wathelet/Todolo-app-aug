package be.ehb.Todolo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import be.ehb.Todolo.Apapters.TodoAdapter;
import be.ehb.Todolo.ViewModel.TodoViewModel;
import be.ehb.Todolo.fragmentinterfaces.CreateTodoInterface;
import be.ehb.Todolo.interfaces.TodoOnItemClickListener;
import be.ehb.Todolo.parceble.ListParcel;
import be.ehb.Todolo.room.Entity.TodoList;
import be.ehb.Todolo.room.Entity.TodoListWithTasks;

public class MainActivity extends AppCompatActivity implements CreateTodoInterface , NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private TodoAdapter adapter;
    private TodoViewModel todoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //stared button
        LinearLayout btnstar = findViewById(R.id.btn_starred);
        btnstar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , ActivityTodoListWithDetail.class);
                intent.putExtra("Starlisted", true);
                startActivity(intent);
            }
        });



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

            @Override
            public void onClickListItem(int position) {
                    TodoListWithTasks listWithTasks = adapter.getItemOnPosition(position);
                    ListParcel listParcel = new ListParcel(listWithTasks.getTodoList());
                Intent intent = new Intent(MainActivity.this , ActivityTodoListWithDetail.class);
                intent.putExtra("listwithtask", listParcel);

                startActivity(intent);

            }
        });



        //data and setting to recycler view
        todoViewModel = ViewModelProviders.of(this).get(TodoViewModel.class);
        todoViewModel.getAllTodo().observe(this, new Observer<List<TodoListWithTasks>>() {
            @Override
            public void onChanged(@Nullable List<TodoListWithTasks> todoListWithTasks) {

                adapter.submitList(todoListWithTasks);


            }
        });

        //swipe function for edit and delete
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                View view = findViewById(R.id.drawer_mainactivity_layout);
                final int position = viewHolder.getAdapterPosition();
                final TodoListWithTasks deletedTodo = adapter.getItemOnPosition(position);
                todoViewModel.delete(deletedTodo.getTodoList());
                Snackbar snackbar = Snackbar
                        .make(view, R.string.todo_snacbar_delete_message, Snackbar.LENGTH_LONG)
                        .setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                todoViewModel.restoreitem(deletedTodo);
                            }
                        });

                snackbar.show();

            }

            // zal het delete icon en roder kader teken bij ieder keer een item naar links beweegt
            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
                    if(dX < 0){
                        Paint paint = new Paint();
                        View itemView = viewHolder.itemView;
                        float height = (float) itemView.getBottom() - (float) itemView.getTop();
                        float width = height / 3;
                        paint.setColor(getResources().getColor(R.color.urgent));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background,paint);
                        Drawable icon = AppCompatResources.getDrawable(getApplicationContext(), R.drawable.ic_baseline_delete_24);
                        Rect icon_dest = new Rect((int)(itemView.getRight() - 2*width) ,(int) (itemView.getTop() + width),(int) (itemView.getRight() - width),(int)(itemView.getBottom() - width));
                        icon.setBounds(icon_dest);
                        icon.draw(c);

                    }

                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }).attachToRecyclerView(recyclerView);


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
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


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
            new AlertDialog.Builder(this)
                    .setTitle(R.string.main_menu_deleteAll)
                    .setMessage(R.string.delete_all_message)
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.nav_Async_json:
                Intent intent = new Intent(MainActivity.this ,AsyncJson.class);
                intent.putExtra("URL","https://jsonplaceholder.typicode.com/posts");
                startActivity(intent);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}