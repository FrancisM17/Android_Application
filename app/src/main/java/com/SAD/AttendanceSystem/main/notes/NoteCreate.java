package com.SAD.AttendanceSystem.main.notes;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.SAD.AttendanceSystem.R;
import com.SAD.AttendanceSystem.main.AppBase;

public class NoteCreate extends AppCompatActivity {
    EditText title, body;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_create);
        Button btn = findViewById(R.id.noteSaveButton);
        assert btn != null;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNotification();
                saveData();
            }
        });

        spinner = (Spinner) findViewById(R.id.pinSpinner);
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, AppBase.divisions);
        assert spinner != null;
        spinner.setAdapter(adapterSpinner);
    }

    private void addNotification() {
        NotificationCompat.Builder build = new NotificationCompat.Builder(this)
                .setSmallIcon((R.mipmap.ic_launcher_round))
                .setContentTitle("Notes on Standby")
                .setContentText("Notes");
        Intent notifIntent = new Intent(this, NoteCreate.class);
        PendingIntent content = PendingIntent.getActivity(this, 0, notifIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        build.setContentIntent(content);
        NotificationManager manage = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manage.notify(0, build.build());
    }

    private void saveData() {
        title = (EditText) findViewById(R.id.noteTitle);
        body = (EditText) findViewById(R.id.noteBody);
        EditText sub = (EditText) findViewById(R.id.subjectNote);
        String qu = " INSERT INTO NOTES(title,body,cls,sub) VALUES('" + title.getText().toString() + "','" + body.getText().toString() + "'," +
                "'" + spinner.getSelectedItem().toString() + "','" + sub.getText().toString().toUpperCase() + "')";
        if (AppBase.handler.execAction(qu)) {
            Toast.makeText(getBaseContext(), "Note Saved", Toast.LENGTH_LONG).show();
            this.finish();
        }
    }
}

