package com.mk.databasedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText taskNameEt, detailsEt;
    Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        taskNameEt = (EditText) findViewById(R.id.task_name);
        detailsEt = (EditText) findViewById(R.id.details);
        addBtn = (Button) findViewById(R.id.addButton);
        addBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String taskName = taskNameEt.getText().toString();
        String details = detailsEt.getText().toString();
        Database database = new Database(this);
        database.openWritableDb();
        String message;
        if(database.insertTask(taskName, details))
            message = "Record Added Successfully!!";
        else
            message = "Error!!";
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        database.closeDatabase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.show_tasks:
                Intent intent = new Intent(this, ShowTasks.class);
                startActivity(intent);
        }
        return true;
    }
}
