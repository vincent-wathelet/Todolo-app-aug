package be.ehb.Todolo.room.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import be.ehb.Todolo.room.Entity.Task;

@Dao
public interface TaskDAO {

    @Insert
    void insert(Task task);


    @Update
    void update(Task task);

    @Delete
    void delete(Task task);

    @Query("SELECT * FROM task_table ORDER BY priority DESC")
    LiveData<List<Task>> getAllTask();

    @Query("DELETE from task_table")
    void deleteAllTask();

    @Query("SELECT * FROM task_table WHERE todolistid = :id ORDER BY priority DESC")
    LiveData<List<Task>> getAllTaskFromList(int id);
    @Query("SELECT * FROM task_table WHERE stared = :status ORDER BY priority DESC")
    LiveData<List<Task>> getAllStaredTask(boolean status);
}
