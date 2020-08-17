package be.ehb.Todolo.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import be.ehb.Todolo.room.Entity.Task;
import be.ehb.Todolo.room.Entity.TodoListWithTasks;
import be.ehb.Todolo.room.repository.TaskRepository;

public class TaskViewModel extends AndroidViewModel {

    private TaskRepository repository;
    private LiveData<List<Task>> taskLivedata;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        repository = new TaskRepository(application);
    }

    public void getAllTask()
    {
        taskLivedata = repository.getAllTask();
    }

    public LiveData<List<Task>> getTaskLivedata()
    {
        return taskLivedata;
    }
    public void insert(Task task)
    {
        repository.insert(task);
    }
    public void update(Task task)
    {
        repository.update(task);
    }
    public void delete(Task task)
    {
        repository.delete(task);
    }
    public void deleteAll()
    {
        repository.deleteAll();
    }
    public  void getAllListTodos(int id)
    {
        taskLivedata =  repository.getAllTaskFromList(id);
    }
    public  void getAllStaredTodos(boolean status)
    {
        taskLivedata = repository.getAllStaredTask(status);
    }
}
