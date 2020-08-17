package be.ehb.Todolo.Apapters;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import be.ehb.Todolo.R;
import be.ehb.Todolo.fragmentinterfaces.RecylcerTaskInterface;
import be.ehb.Todolo.interfaces.TodoOnItemClickListener;
import be.ehb.Todolo.room.Entity.Task;
import be.ehb.Todolo.room.Entity.TodoListWithTasks;

public class TaskAdapter extends ListAdapter<Task, TaskAdapter.TaskHolder> {

    private Context context;
    private RecylcerTaskInterface listener;

    public TaskAdapter(Context context)
    {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    public void setOnItemClickListener(RecylcerTaskInterface listener)
    {
        this.listener = listener;
    }
    private static final DiffUtil.ItemCallback<Task> DIFF_CALLBACK = new DiffUtil.ItemCallback<Task>()
    {


        @Override
        public boolean areItemsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
            return oldItem.isStared() == newItem.isStared() &&
                    oldItem.isCompleted() == newItem.isCompleted() &&
                    oldItem.isArchived() == newItem.isArchived() &&
                    oldItem.getDescription().equals( newItem.getDescription())&&
                    oldItem.getTitle().equals(  newItem.getTitle() )&&
                    oldItem.getTododate().equals( newItem.getTododate());
        }
    };

    @NonNull
    @Override
    public TaskAdapter.TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_todo_view_item,parent,false);
        return new TaskAdapter.TaskHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.TaskHolder holder, int position) {
        Task task = getItem(position);
        holder.recycler_todo_Titel.setText(task.getTitle());
        holder.recycler_todo_TodoDate.setText(task.getTododate());

        if (task.isStared())
        {

            holder.btnStar.setImageResource(R.drawable.ic_baseline_star_24);
        }
        else holder.btnStar.setImageResource(R.drawable.ic_baseline_star_border_24);
        int priority = task.getPriority();
        switch (priority)
        {
            case 0:
            default:
                holder.priorityView.setBackgroundColor(context.getResources().getColor(R.color.smallTask));
                holder.recycler_todo_txt_Priority.setText(R.string.priority_Small);
                break;
            case 1:
                holder.priorityView.setBackgroundColor(context.getResources().getColor(R.color.meduimPriority));
                holder.recycler_todo_txt_Priority.setText(R.string.priority_Meduim);
                break;
            case 2:
                holder.priorityView.setBackgroundColor(context.getResources().getColor(R.color.highPriority));
                holder.recycler_todo_txt_Priority.setText(R.string.priority_High);
                break;
            case 3:
                holder.priorityView.setBackgroundColor(context.getResources().getColor(R.color.urgent));
                holder.recycler_todo_txt_Priority.setText(R.string.priority_Urgent);
                break;
        }

        if (task.isCompleted())
        {
            holder.task_item_click.setBackgroundColor(context.getResources().getColor(R.color.design_default_color_error));
            holder.btnCompleted.setImageResource(R.drawable.ic_baseline_close_24);
        }
        else
        {
            holder.task_item_click.setBackgroundColor(context.getResources().getColor(R.color.cardcolor));
            holder.btnCompleted.setImageResource(R.drawable.ic_baseline_check_24);
        }

    }


    public Task getItemOnPosition(int position)
    {
        return getItem(position);
    }

    class TaskHolder extends RecyclerView.ViewHolder
    {
        private ImageButton btnStar;
        private ImageButton btnCompleted;
        private View priorityView;
        private TextView recycler_todo_Titel;
        private TextView recycler_todo_txt_Priority;
        private TextView recycler_todo_TodoDate;
        private LinearLayout task_item_click;


        public TaskHolder(@NonNull View itemView) {
            super(itemView);
            btnStar = itemView.findViewById(R.id.btn_todo_starred);

            btnCompleted = itemView.findViewById(R.id.btn_todo_completed);
            priorityView = itemView.findViewById(R.id.recycler_todo_priority_view);
            recycler_todo_Titel = itemView.findViewById(R.id.recycler_todo_Titel);
            recycler_todo_txt_Priority = itemView.findViewById(R.id.recycler_todo_txt_Priority);
            recycler_todo_TodoDate = itemView.findViewById(R.id.recycler_todo_TodoDate);
            task_item_click = itemView.findViewById(R.id.task_item_click);
            btnStar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null)
                    {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                        {
                            listener.onStarClick(position);
                        }
                    }
                }
            });
            btnCompleted.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null)
                    {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                        {
                            listener.onCompletedClick(position);
                        }
                    }
                }
            });
            task_item_click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null)
                    {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                        {
                            listener.sendItemClick(position);
                        }
                    }
                }
            });

        }
    }
}

