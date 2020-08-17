package be.ehb.Todolo.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import be.ehb.Todolo.room.Entity.Task;
import be.ehb.Todolo.room.Entity.TodoList;
import be.ehb.Todolo.room.Entity.TodoListWithTasks;
import be.ehb.Todolo.room.repository.TaskRepository;
import be.ehb.Todolo.room.repository.TodoListRepository;

public class TodoViewModel extends AndroidViewModel {

    private TodoListRepository repository;
    private TaskRepository taskRepository;
    private LiveData<List<TodoListWithTasks>> liveTodoData;

    public TodoViewModel(@NonNull Application application) {
        super(application);
        repository = new TodoListRepository(application);
        taskRepository =new TaskRepository(application);
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
    public void deleteall()
    {
        repository.deleteAll();
    }
    public void restoreitem(TodoListWithTasks listWithTasks)
    {
        List<Task> tasks = listWithTasks.getTasks();
        repository.insert(listWithTasks.getTodoList());
        for (Task task :tasks) {
            taskRepository.insert(task);
        }
    }
    public LiveData<List<TodoListWithTasks>>  getAllTodo()
    {
        return liveTodoData;
    }

}
