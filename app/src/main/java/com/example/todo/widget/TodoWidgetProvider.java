package com.example.todo.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.example.todo.MainActivity;
import com.example.todo.R;

public class TodoWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            // Set up the intent for the ListView service
            Intent serviceIntent = new Intent(context, TodoWidgetService.class);
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));

            // Set up the intent for when a list item is clicked
            Intent clickIntent = new Intent(context, MainActivity.class);
            PendingIntent clickPendingIntent = PendingIntent.getActivity(context, 0, clickIntent, PendingIntent.FLAG_IMMUTABLE);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.todo_widget);
            views.setRemoteAdapter(R.id.widget_listview, serviceIntent);
            views.setPendingIntentTemplate(R.id.widget_listview, clickPendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, views);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_listview);
        }
        
        // Start the update service
        Intent serviceIntent = new Intent(context, TodoWidgetUpdateService.class);
        context.startService(serviceIntent);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Intent serviceIntent = new Intent(context, TodoWidgetUpdateService.class);
        context.startService(serviceIntent);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        Intent serviceIntent = new Intent(context, TodoWidgetUpdateService.class);
        context.stopService(serviceIntent);
    }
}
