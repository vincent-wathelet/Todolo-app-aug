package be.ehb.Todolo.Apapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import java.util.ArrayList;
import java.util.List;

import be.ehb.Todolo.R;
import be.ehb.Todolo.interfaces.TodoOnItemClickListener;
import be.ehb.Todolo.room.Entity.TodoListWithTasks;

public class TodoAdapter extends Adapter<TodoAdapter.Todoholder> {

    private List<TodoListWithTasks> todo = new ArrayList<>();
    private TodoOnItemClickListener listener;
    private Context context;

    public TodoAdapter(Context context)
    {
        this.context = context;
    }

    @NonNull
    @Override
    public Todoholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item,parent,false);
        return new Todoholder(itemView,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull Todoholder holder, int position) {
        TodoListWithTasks listWithTasks = todo.get(position);
        holder.txtTitel.setText(listWithTasks.getTodoList().getTitle());
        int priority = listWithTasks.getTodoList().getPriority();
        switch (priority)
        {
            case 0:
            default:
                holder.priorityView.setBackgroundColor(context.getResources().getColor(R.color.smallTask));
              holder.txtVPriority.setText(R.string.priority_Small);
              break;
            case 1:
                holder.priorityView.setBackgroundColor(context.getResources().getColor(R.color.meduimPriority));
                holder.txtVPriority.setText(R.string.priority_Meduim);
                break;
            case 2:
                holder.priorityView.setBackgroundColor(context.getResources().getColor(R.color.highPriority));
                holder.txtVPriority.setText(R.string.priority_High);
                break;
            case 3:
                holder.priorityView.setBackgroundColor(context.getResources().getColor(R.color.Urgent));
                holder.txtVPriority.setText(R.string.priority_Urgent);
                break;
        }


    }

    @Override
    public int getItemCount() {
        return todo.size();
    }

    public void setTodo(List<TodoListWithTasks> todo) {
        this.todo = todo;
        //TODO : nog verbeteren
        notifyDataSetChanged();

    }

    public void notifyDataChanged(int position)
    {
        notifyItemChanged(position);
    }
    public void notifyDataDeleted(int posistion)
    {
        notifyItemRemoved(posistion);
    }

    public void setOnItemClickListener(TodoOnItemClickListener listener)
    {
        this.listener = listener;
    }

    public TodoListWithTasks getItemOnPosition(int position)
    {
        return todo.get(position);
    }

    class Todoholder extends ViewHolder
    {
        private View priorityView;
        private TextView txtVPriority;
        private  TextView txtTitel;
        private ImageButton editbtn;

        public Todoholder(@NonNull final View itemView, final TodoOnItemClickListener listener) {
            super(itemView);
            this.priorityView = itemView.findViewById(R.id.recycler_list_priority_view);
            this.txtTitel = itemView.findViewById(R.id.recycler_list_Titel);
            this.txtVPriority = itemView.findViewById(R.id.recycler_list_txt_Priority);
            this.editbtn = itemView.findViewById(R.id.recycler_list_edit);

            editbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null)
                    {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                        {
                            listener.onEditClick(position);
                        }
                    }
                }
            });
        }
    }
}
