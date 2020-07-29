package be.ehb.Todolo.room.Entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class TodoListWithTasks {

    @Embedded
    private TodoList todoList;

    @Relation(parentColumn = "id" , entityColumn = "todolistid" , entity = Task.class)
    private List<Task> tasks;

}
