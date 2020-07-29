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

    private String Description;

    private Date tododate;

    private int priority;

    private boolean stared;

    private boolean archived;

    private boolean completed;





}
