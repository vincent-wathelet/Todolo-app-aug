package be.ehb.Todolo;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewParentCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;
import java.util.zip.Inflater;

import be.ehb.Todolo.fragmentinterfaces.CreateTodoInterface;
import be.ehb.Todolo.room.Entity.TodoList;
public class CreateTodoFragment extends Fragment {

    private static final String FRAGMENTTITLE = "FRAGMENTTITLE";
    private static final String TITLE = "TITLE";
    private static final String PRIORITY = "PRIORITY";
    private static final String ID = "ID";

    private TodoList todoList = null;
    private CreateTodoInterface mListener;
    private String title = null;

    public CreateTodoFragment() {
    }

    public static CreateTodoFragment newInstance(TodoList list) {
        CreateTodoFragment todofragment = new CreateTodoFragment();
        Bundle args = new Bundle();
        if (list != null){
            args.putInt(FRAGMENTTITLE, R.string.create_todo_titel_update);
            args.putString(TITLE,list.getTitle());
            args.putInt(PRIORITY,list.getPriority());
            args.putInt(ID,list.getId());
        }

        return todofragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getResources().getString(getArguments().getInt(FRAGMENTTITLE));
            todoList = new TodoList(getArguments().getString(TITLE),getArguments().getInt(PRIORITY));
            todoList.setId(getArguments().getInt(ID));
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.createtodofragment,container,false);
        FloatingActionButton fab = requireActivity().findViewById(R.id.floating_addList);
        fab.setVisibility(View.GONE);
        ImageButton button = view.findViewById(R.id.btn_todo_list_save);
        ImageButton close = view.findViewById(R.id.btn_create_list_close);
        final TextView idtextfield = view.findViewById(R.id.create_list_id_variable);
        final TextView titleText = view.findViewById(R.id.txt_todolist_title);
        final RadioGroup radioGroup = view.findViewById(R.id.priority_btn_group);
        if(title != null )
        {
            idtextfield.setText(String.valueOf(todoList.getId()));
            TextView fragmentTitle =   view.findViewById(R.id.create_list_titeltxt);
            fragmentTitle.setText(title);
            titleText.setText(this.todoList.getTitle());
            switch (todoList.getPriority()){
                case 0:
                default:
                    radioGroup.check(R.id.create_list_smallrbtn);
                    break;
                case 1:
                    radioGroup.check(R.id.create_list_meduimrbtn);
                    break;
                case 2:
                    radioGroup.check(R.id.create_list_highrbtn);
                    break;
                case 3:
                    radioGroup.check(R.id.create_list_urgentrbtn);
                    break;
            }


        }
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                requireActivity().getSupportFragmentManager().popBackStack();
                FloatingActionButton fab = requireActivity().findViewById(R.id.floating_addList);
                fab.setVisibility(View.VISIBLE);

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                int priority = 0;
                int checkebtnid = radioGroup.getCheckedRadioButtonId();
                switch (checkebtnid)
                {
                    case R.id.create_list_smallrbtn:
                    default:
                        priority = 0;
                        break;
                    case R.id.create_list_meduimrbtn:
                        priority = 1;
                        break;
                    case R.id.create_list_highrbtn:
                        priority = 2;
                        break;
                    case R.id.create_list_urgentrbtn:
                        priority = 3;
                        break;

                }
                if (idtextfield.getText() == null ||idtextfield.getText().equals(""))
                {


                    TodoList list = new TodoList(titleText.getText().toString(),priority);
                    sendBack(list);
                }
                else
                    {
                        TodoList list = new TodoList(titleText.getText().toString(),priority);
                        list.setId(Integer.parseInt(idtextfield.getText().toString()));
                        sendBack(list);
                    }

            }
        });
        return view;
    }

    public TodoList getTodoList()
    {
        return this.todoList;
    }

    public void sendBack(TodoList todoList)
    {
        if (mListener != null) {
            mListener.onFragmentInteraction(todoList);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CreateTodoInterface) {
            mListener = (CreateTodoInterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CreateTodoInterface");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


}
