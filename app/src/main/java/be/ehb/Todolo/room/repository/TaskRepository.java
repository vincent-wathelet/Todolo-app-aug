package be.ehb.Todolo.room.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

import be.ehb.Todolo.room.DAO.*;
import be.ehb.Todolo.room.Entity.Task;
import be.ehb.Todolo.room.database.Database;

public class TaskRepository {

    private TaskDAO taskDAO;

    private LiveData<List<Task>> allTask;

    public TaskRepository(Application application)
    {
        Database database = Database.getInstance(application);
        taskDAO = database.taskDAO();
        allTask = taskDAO.getAllTask();
    }

    public void insert(Task task)
    {
        new insertTaskAsyncTask(taskDAO).execute(task);
    }


    public void update(Task task)
    {
        new updateTaskAsyncTask(taskDAO).execute(task);
    }

    public void delete(Task task)
    {
        new deleteTaskAsyncTask(taskDAO).execute(task);
    }

    public void deleteAll()
    {
        new deleteTaskAsyncTask(taskDAO).execute();
    }

    public LiveData<List<Task>> getAllStaredTask(boolean status)  {
        LiveData<List<Task>>  liveData = null;
        AsyncTask<Boolean, Void, LiveData<List<Task>>> task =  new  getAllStarredTaskAsyncTask(taskDAO).execute(status);
        if (task.isCancelled())
        {
            return liveData;
        }
        else
            {
                try {
                    return task.get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    return liveData;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return liveData;
                }
            }
    }

    public LiveData<List<Task>> getAllTaskFromList(int id)  {
        LiveData<List<Task>>  liveData = null;
        AsyncTask<Integer, Void, LiveData<List<Task>>> task =  new  getAllTaskFromListTaskAsyncTask(this.taskDAO).execute(id);
        if (task.isCancelled())
        {
            return liveData;
        }
        else
        {
            try {
                return task.get();
            } catch (ExecutionException e) {
                e.printStackTrace();
                return liveData;
            } catch (InterruptedException e) {
                e.printStackTrace();
                return liveData;
            }
        }
    }

    public LiveData<List<Task>> getAllTask()
    {
        return allTask;
    }

    private static class insertTaskAsyncTask extends AsyncTask<Task, Void, Void>
    {
        private TaskDAO taskDAO;

        private insertTaskAsyncTask(TaskDAO taskDAO)
        {
            this.taskDAO = taskDAO;
        }

        @Override
        protected Void doInBackground(Task... tasks) {

            taskDAO.insert(tasks[0]);
            return null;
        }
    }

    private static class updateTaskAsyncTask extends AsyncTask<Task, Void, Void>
    {
        private TaskDAO taskDAO;

        private updateTaskAsyncTask(TaskDAO taskDAO)
        {
            this.taskDAO = taskDAO;
        }

        @Override
        protected Void doInBackground(Task... tasks) {

            taskDAO.update(tasks[0]);
            return null;
        }
    }
    private static class deleteTaskAsyncTask extends AsyncTask<Task, Void, Void>
    {
        private TaskDAO taskDAO;

        private deleteTaskAsyncTask(TaskDAO taskDAO)
        {
            this.taskDAO = taskDAO;
        }

        @Override
        protected Void doInBackground(Task... tasks) {

            taskDAO.delete(tasks[0]);
            return null;
        }
    }

    private static class deleteAllTaskAsyncTask extends AsyncTask<Void, Void, Void>
    {
        private TaskDAO taskDAO;

        private deleteAllTaskAsyncTask(TaskDAO taskDAO)
        {
            this.taskDAO = taskDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            taskDAO.deleteAllTask();
            return null;
        }
    }
    private static class getAllStarredTaskAsyncTask extends AsyncTask<Boolean, Void, LiveData<List<Task>>>
    {
        private TaskDAO taskDAO;

        public getAllStarredTaskAsyncTask(TaskDAO taskDAO) {
            this.taskDAO = taskDAO;
        }


        @Override
        protected LiveData<List<Task>> doInBackground(Boolean... booleans) {

            return taskDAO.getAllStaredTask(booleans[0]);
        }
    }

    private static  class getAllTaskFromListTaskAsyncTask extends AsyncTask<Integer,Void,LiveData<List<Task>>>
    {
        private TaskDAO taskDAO;

        public getAllTaskFromListTaskAsyncTask(TaskDAO taskDAO) {
            this.taskDAO = taskDAO;
        }

        @Override
        protected LiveData<List<Task>> doInBackground(Integer... ints) {

            return this.taskDAO.getAllTaskFromList(ints[0]);
        }
    }

}

