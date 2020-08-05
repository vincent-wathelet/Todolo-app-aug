package be.ehb.Todolo.room.Entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class TodoListWithTasks {

    @Embedded
    private TodoList todoList;

    @Relation(parentColumn = "id" , entityColumn = "todolistid" , entity = Task.class)
    private List<Task> tasks;

    public TodoList getTodoList() {
        return todoList;
    }

    public void setTodoList(TodoList todoList) {
        this.todoList = todoList;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
