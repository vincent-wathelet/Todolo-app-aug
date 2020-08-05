package be.ehb.Todolo.room.repository;


import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import be.ehb.Todolo.room.DAO.TodoListDAO;
import be.ehb.Todolo.room.Entity.TodoList;
import be.ehb.Todolo.room.Entity.TodoListWithTasks;
import be.ehb.Todolo.room.database.Database;

public class TodoListRepository {


    private TodoListDAO listDAO;

    private LiveData<List<TodoListWithTasks>> allList;

    public TodoListRepository(Application application)
    {
        Database database = Database.getInstance(application);
        listDAO = database.listDAO();
        allList = listDAO.getAllTodoList();
    }

    public void insert(TodoList list)
    {
        new insertTaskAsyncTask(listDAO).execute(list);
    }

    public void update(TodoList list)
    {
        new updateTaskAsyncTask(listDAO).execute(list);
    }

    public void delete(TodoList list)
    {
        new deleteTaskAsyncTask(listDAO).execute(list);
    }

    public void deleteAll()
    {
        new deleteAllTaskAsyncTask(listDAO).execute();
    }

    public LiveData<List<TodoListWithTasks>>getAllTask()
    {
        return allList;
    }

    private static class insertTaskAsyncTask extends AsyncTask<TodoList, Void, Void>
    {
        private TodoListDAO listDAO;

        private insertTaskAsyncTask(TodoListDAO listDAO)
        {
            this.listDAO = listDAO;
        }

        @Override
        protected Void doInBackground(TodoList... lists) {

            listDAO.insert(lists[0]);
            return null;
        }
    }
    private static class updateTaskAsyncTask extends AsyncTask<TodoList, Void, Void>
    {
        private TodoListDAO listDAO;

        private updateTaskAsyncTask(TodoListDAO listDAO)
        {
            this.listDAO = listDAO;
        }

        @Override
        protected Void doInBackground(TodoList... lists) {

            listDAO.update(lists[0]);
            return null;
        }
    }
    private static class deleteTaskAsyncTask extends AsyncTask<TodoList, Void, Void>
    {
        private TodoListDAO listDAO;

        private deleteTaskAsyncTask(TodoListDAO listDAO)
        {
            this.listDAO = listDAO;
        }

        @Override
        protected Void doInBackground(TodoList... lists) {

            listDAO.delete(lists[0]);
            return null;
        }
    }
    private static class deleteAllTaskAsyncTask extends AsyncTask<Void, Void, Void>
    {
        private TodoListDAO listDAO;

        private deleteAllTaskAsyncTask(TodoListDAO listDAO)
        {
            this.listDAO = listDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            listDAO.deleteAllTask();
            return null;
        }
    }



}
