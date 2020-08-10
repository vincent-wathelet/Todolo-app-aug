package be.ehb.Todolo.fragmentinterfaces;

import be.ehb.Todolo.room.Entity.TodoList;

public interface CreateTodoInterface {

    void createFragment(TodoList list);

    void onFragmentInteraction(TodoList list);
}
