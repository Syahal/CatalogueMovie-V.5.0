package com.example.submission3.notification;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.submission3.R;

public class MyReminderActivity extends AppCompatActivity {

    private MyReminder myReminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reminder);
        setTitle(getString(R.string.reminder));

        Switch dailyReminder = findViewById(R.id.switch_daily);
        Switch newReleaseReminder = findViewById(R.id.switch_new_release);

        myReminder = new MyReminder(this);

        dailyReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    myReminder.setDailyReminder();
                    Toast.makeText(getApplicationContext(), getString(R.string.daily_reminder_on), Toast.LENGTH_SHORT).show();
                } else {
                    myReminder.cancelDailyReminder(getApplicationContext());
                    Toast.makeText(getApplicationContext(), getString(R.string.daily_reminder_off), Toast.LENGTH_SHORT).show();
                }
            }
        });

        newReleaseReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    myReminder.setNewRelaseReminder();
                    Toast.makeText(getApplicationContext(), getString(R.string.new_release_reminder_on), Toast.LENGTH_SHORT).show();
                } else {
                    myReminder.cancelNewReleaseReminder(getApplicationContext());
                    Toast.makeText(getApplicationContext(), getString(R.string.new_release_reminder_off), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
