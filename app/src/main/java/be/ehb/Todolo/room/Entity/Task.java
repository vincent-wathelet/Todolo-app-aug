package be.ehb.Todolo.room.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "task_table")
public class Task {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int todolistid;

    private String title;

    private String description;

    private String tododate;

    private int priority;

    private boolean stared;

    private boolean archived;

    private boolean completed;


    public Task(int todolistid, String title, String description, String tododate, int priority, boolean stared, boolean archived, boolean completed) {
        this.todolistid = todolistid;
        this.title = title;
        this.description = description;
        this.tododate = tododate;
        this.priority = priority;
        this.stared = stared;
        this.archived = archived;
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTodolistid() {
        return todolistid;
    }

    public void setTodolistid(int todolistid) {
        this.todolistid = todolistid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTododate() {
        return tododate;
    }

    public void setTododate(String tododate) {
        this.tododate = tododate;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isStared() {
        return stared;
    }

    public void setStared(boolean stared) {
        this.stared = stared;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
