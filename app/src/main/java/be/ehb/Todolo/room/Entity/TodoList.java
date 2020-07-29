package be.ehb.Todolo.room.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "list_table")
public class TodoList {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private int priority;


}
