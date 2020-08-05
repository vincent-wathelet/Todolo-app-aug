package be.ehb.Todolo.room.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import be.ehb.Todolo.room.DAO.*;
import be.ehb.Todolo.room.Entity.*;

@androidx.room.Database(entities = {Task.class, TodoList.class}, version = 1)
public abstract class Database extends RoomDatabase {

    private static Database database;

    public abstract TaskDAO taskDAO();

    public abstract TodoListDAO listDAO();

    // voor te zorgen dat bij multie treading geen dubble instance worden gemaakt
    public static  synchronized Database getInstance(Context context)
    {
        if (database == null)
        {
            database = Room.databaseBuilder(context.getApplicationContext(),Database.class,"TodoloDatabase").fallbackToDestructiveMigration().addCallback(callback).build();
        }
        return database;
    }

    private static Callback callback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new FillDBAsyncTask(database).execute();
        }
    };

    private static class FillDBAsyncTask extends AsyncTask<Void,Void,Void>
    {
        private TaskDAO taskDAO;
        private TodoListDAO listDAO;

        private FillDBAsyncTask(Database db)
        {
            taskDAO = db.taskDAO();
            listDAO = db.listDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            listDAO.insert(new TodoList("Taken lijst 1",1));
            listDAO.insert(new TodoList("Andriod",3));
            listDAO.insert(new TodoList("Taken lijst 2",1));
            listDAO.insert(new TodoList("Huistaken",2));
            taskDAO.insert(new Task(3,"strijken doen","","05/09/2020",3,false,false,false));
            taskDAO.insert(new Task(1,"ui maken","","05/09/2020",3,false,false,false));
            taskDAO.insert(new Task(1,"room implementerezn","","05/09/2020",2,false,false,false));
            taskDAO.insert(new Task(1,"database uitschrijven","","05/09/2020",2,true,false,true));
            return null;
        }
    }

}
