package com.example.todo.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.todo.Model.ToDoModel;
import com.example.todo.R;
import com.example.todo.Utils.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

public class TodoWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new TodoWidgetItemFactory(getApplicationContext());
    }

    class TodoWidgetItemFactory implements RemoteViewsFactory {
        private Context context;
        private List<ToDoModel> tasks;
        private DatabaseHandler db;

        TodoWidgetItemFactory(Context context) {
            this.context = context;
            this.tasks = new ArrayList<>();
        }

        @Override
        public void onCreate() {
            db = new DatabaseHandler(context);
            db.openDatabase();
            updateTasks();
        }

        @Override
        public void onDataSetChanged() {
            updateTasks();
        }

        private void updateTasks() {
            tasks.clear();
            List<ToDoModel> allTasks = db.getAllTasks();
            for (ToDoModel task : allTasks) {
                if (task.getStatus() == 0) {
                    tasks.add(task);
                }
            }
        }

        @Override
        public void onDestroy() {
            tasks.clear();
        }

        @Override
        public int getCount() {
            return tasks.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            if (position == RemoteViews.MARGIN_BOTTOM || tasks.isEmpty()) {
                return null;
            }

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.todo_widget_item);
            ToDoModel task = tasks.get(position);
            views.setTextViewText(R.id.widget_item_text, task.getTask());

            Intent fillIntent = new Intent();
            views.setOnClickFillInIntent(R.id.widget_item_text, fillIntent);

            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
