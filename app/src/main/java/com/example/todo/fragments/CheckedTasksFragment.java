package com.example.todo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.Adapter.ToDoAdapter;
import com.example.todo.MainActivity;
import com.example.todo.Model.ToDoModel;
import com.example.todo.R;
import com.example.todo.Utils.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

public class CheckedTasksFragment extends Fragment {
    private RecyclerView tasksRecyclerView;
    private ToDoAdapter tasksAdapter;
    private DatabaseHandler db;
    private List<ToDoModel> taskList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checked_tasks, container, false);
        
        db = new DatabaseHandler(getActivity());
        db.openDatabase();
        
        tasksRecyclerView = view.findViewById(R.id.checkedTasksRecyclerView);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        
        tasksAdapter = new ToDoAdapter(db, (MainActivity) getActivity());
        tasksRecyclerView.setAdapter(tasksAdapter);
        
        taskList = new ArrayList<>();
        updateTaskList();
        
        return view;
    }

    public void updateTaskList() {
        taskList = db.getAllTasks();
        List<ToDoModel> checkedTasks = new ArrayList<>();
        for(ToDoModel task : taskList) {
            if(task.getStatus()==1) {
                checkedTasks.add(task);
            }
        }
        tasksAdapter.setTasks(checkedTasks);
        tasksAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateTaskList();
    }
}
