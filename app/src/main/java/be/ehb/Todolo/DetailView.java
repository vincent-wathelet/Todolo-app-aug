package be.ehb.Todolo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import be.ehb.Todolo.ViewModel.TaskViewModel;
import be.ehb.Todolo.fragmentinterfaces.DetailTaskInterface;
import be.ehb.Todolo.room.Entity.Task;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailView extends Fragment {

    public static DetailTaskInterface listener;

    private static final String ARG_PARAM1 = "taskid";
    private Task task;
    private int id;
    private TaskViewModel taskviewmodel;

    public DetailView() {
        // Required empty public constructor
    }

    public static DetailView newInstance(int param1,DetailTaskInterface listenerInput) {
        DetailView fragment = new DetailView();
        listener = listenerInput;
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt(ARG_PARAM1);
        }
        if (getActivity() != null) {
            taskviewmodel = new ViewModelProvider(getActivity()).get(TaskViewModel.class);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_detail_view, container, false);
        if (id != -1)
        {
            task = taskviewmodel.getTaskLivedata().getValue().get(id);

            TextView t = v.findViewById(R.id.detail_task_titel);
            t.setText(task.getTitle());
            TextView date = v.findViewById(R.id.detail_task_date);
            date.setText(task.getTododate());
            TextView desc = v.findViewById(R.id.detail_task_details);
            desc.setText(task.getDescription());
            TextView textView = v.findViewById(R.id.detail_taskdesctitel);
            textView.setText(R.string.task_description);
            v.findViewById(R.id.detail_task_btn_edit).setVisibility(View.VISIBLE);
        }
        return v;
    }

    public void setTaskDetail(int posistion)
    {
        id = posistion;



    }
}