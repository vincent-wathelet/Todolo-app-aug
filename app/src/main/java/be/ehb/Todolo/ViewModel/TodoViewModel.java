package be.ehb.Todolo.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import be.ehb.Todolo.room.Entity.TodoList;
import be.ehb.Todolo.room.Entity.TodoListWithTasks;
import be.ehb.Todolo.room.repository.TodoListRepository;

public class TodoViewModel extends AndroidViewModel {

    private TodoListRepository repository;
    private LiveData<List<TodoListWithTasks>> liveTodoData;

    public TodoViewModel(@NonNull Application application) {
        super(application);
        repository = new TodoListRepository(application);
        liveTodoData = repository.getAllTask();
    }

    public void insert(TodoList list)
    {
        repository.insert(list);
    }
    public void update(TodoList list)
    {
        repository.update(list);
    }
    public void delete(TodoList list)
    {
        repository.delete(list);
    }
    public void insert()
    {
        repository.deleteAll();
    }
    public LiveData<List<TodoListWithTasks>>  getAllTodo()
    {
        return liveTodoData;
    }

}
