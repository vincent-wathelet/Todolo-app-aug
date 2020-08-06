package be.ehb.Todolo.room.DAO;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import be.ehb.Todolo.room.Entity.TodoList;
import be.ehb.Todolo.room.Entity.TodoListWithTasks;

@Dao
public interface TodoListDAO {


    @Insert
    void insert(TodoList list);


    @Update
    void update(TodoList list);

    @Delete
    void delete(TodoList list);

    @Query("SELECT * FROM list_table ORDER BY priority DESC ")
    LiveData<List<TodoListWithTasks>> getAllTodoList();

    @Query("DELETE from list_table")
    void deleteAllTask();

}
