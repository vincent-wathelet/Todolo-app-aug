package be.ehb.Todolo;

import android.os.AsyncTask;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.io.IOException;

import be.ehb.Todolo.room.DAO.TaskDAO;
import be.ehb.Todolo.room.DAO.TodoListDAO;
import be.ehb.Todolo.room.Entity.Task;
import be.ehb.Todolo.room.Entity.TodoList;
import be.ehb.Todolo.room.database.Database;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(RobolectricTestRunner.class)
public class DatabaseTest {

   
    @Test
    public void checkTodoListEntity() throws Exception {

        TodoList list = new TodoList("het is een test",1);
        list.setId(5);
        TodoList list1 = new TodoList("het is een test",1);
        list1.setId(5);
        assertThat(list1.getTitle() , equalTo(list.getTitle()));
    }

}


