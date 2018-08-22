package com.mk.databasedemo;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;


/**
 * Created by manikaran on 20/7/18.
 */

public class ShowTasks extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_tasks);
        ListView listView = (ListView) findViewById(R.id.list_view);
        Database db = new Database(this);
        db.openWritableDb();
        Cursor cursor = db.getTasks();
        if(cursor.getCount() == 0) {
            Log.d("Msg", "No records Found");
            listView.setVisibility(View.GONE);
        }
        else {
            listView.setAdapter(new MyCursorAdapter(this, cursor, false));
            //cursor.close();
        }
        db.closeDatabase();

    }

    private class MyCursorAdapter extends CursorAdapter {

        MyCursorAdapter(Context context, Cursor c, boolean autoRequery) {
            super(context, c, autoRequery);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return getLayoutInflater().inflate(R.layout.db_item, parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView task_name_tv = view.findViewById(R.id.task_name);
            TextView task_details_tv = view.findViewById(R.id.task_details);
            String taskName = cursor.getString(cursor.getColumnIndex(Database.DatabaseHelper.COLUMN_TASK_NAME));
            String taskDetails = cursor.getString(cursor.getColumnIndex(Database.DatabaseHelper.COLUMN_DETAILS));
            task_name_tv.setText(taskName);
            task_details_tv.setText(taskDetails);
        }
    }
}
