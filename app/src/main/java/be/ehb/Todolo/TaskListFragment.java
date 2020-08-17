package be.ehb.Todolo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import be.ehb.Todolo.Apapters.TaskAdapter;
import be.ehb.Todolo.Apapters.TodoAdapter;
import be.ehb.Todolo.ViewModel.TaskViewModel;
import be.ehb.Todolo.parceble.ListParcel;
import be.ehb.Todolo.room.Entity.Task;

public class TaskListFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String LIST = "list";

    private ListParcel list;
    private TaskViewModel taskviewmodel;
    private TaskAdapter adapter;

    public TaskListFragment() {
        // Required empty public constructor
    }

    public static TaskListFragment newInstance(ListParcel listParcel) {
        TaskListFragment fragment = new TaskListFragment();
        Bundle args = new Bundle();
        args.putParcelable(LIST,listParcel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            if (getArguments().getParcelable(LIST) != null) {
                list = getArguments().getParcelable(LIST);
                if (getActivity() != null && list != null) {
                    taskviewmodel = new ViewModelProvider(getActivity()).get(TaskViewModel.class);
                    taskviewmodel.getAllListTodos(list.id);
                }
            }
            else
            {
                taskviewmodel = new ViewModelProvider(getActivity()).get(TaskViewModel.class);
                taskviewmodel.getAllStaredTodos(true);
            }
        }

        adapter  = new TaskAdapter(this.getContext());





        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rView =  inflater.inflate(R.layout.fragment_todo_recyler, container, false);

        RecyclerView recyclerView = rView.findViewById(R.id.recycler_view_todo_taks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        taskviewmodel.getTaskLivedata().observe(this.getViewLifecycleOwner(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                adapter.submitList(tasks);
            }
        });

        return rView;

    }

    private void getBack()
    {
        getActivity().finish();
    }
}