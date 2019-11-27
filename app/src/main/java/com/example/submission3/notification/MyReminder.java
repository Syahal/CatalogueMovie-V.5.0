package com.example.submission3.notification;


import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.submission3.BuildConfig;
import com.example.submission3.MainActivity;
import com.example.submission3.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class MyReminder extends BroadcastReceiver {

    public static final String DAILY_REMINDER = "daily_reminder";
    public static final String NEW_RELEASE_REMINDER = "new_release_reminder";
    public static final String EXTRA_DATA = "extra_data";

    private final int DAILY_ID = 1;
    private final int NEWRELEASE_ID = 2;

    private Context context;

    String DATE_FORMAT = "yyyy-MM-dd";

    public MyReminder() {
    }

    public MyReminder(Context context) {
        this.context = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String data = intent.getStringExtra(EXTRA_DATA);

        if (data.equals(DAILY_REMINDER)) {
            showDailyReminder(context);
        } else if (data.equals(NEW_RELEASE_REMINDER)) {
            getNewReleaseReminder(context);
        }
    }

    private Calendar calendar(String type) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, type.equals(DAILY_REMINDER) ? 16 : 16);
        calendar.set(Calendar.MINUTE, 31);
        calendar.set(Calendar.SECOND, 0);

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }

        return calendar;
    }

    private Intent reminderHasSet(String type) {
        Intent intent = new Intent(context, MyReminder.class);
        intent.putExtra(EXTRA_DATA, type);
        return intent;
    }

    public void setDailyReminder() {
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, DAILY_ID, reminderHasSet(DAILY_REMINDER), 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar(DAILY_REMINDER).getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    private void showDailyReminder(Context context) {
        int NOTIFICATION_ID = 1;
        String ID = "channel1";
        String NAME = "Daily Reminder";

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, ID)
                .setSmallIcon(R.drawable.ic_notif_movie)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_notif_movie))
                .setContentTitle(context.getResources().getString(R.string.app_name))
                .setContentText(context.getResources().getString(R.string.daily_reminder))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setContentIntent(pendingIntent)
                .setSound(alarmSound)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Notification notification = builder.build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(ID, NAME, NotificationManager.IMPORTANCE_DEFAULT);
            builder.setChannelId(ID);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
        if (notificationManager != null) {
            notificationManager.notify(NOTIFICATION_ID, notification);
        }
    }

    public void setNewRelaseReminder() {
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NEWRELEASE_ID, reminderHasSet(NEW_RELEASE_REMINDER), 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar(NEW_RELEASE_REMINDER).getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    private void getNewReleaseReminder(final Context context) {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        String currentDate = dateFormat.format(date);
        Log.d("Today : ", currentDate);

        String url = "https://api.themoviedb.org/3/discover/movie?api_key=" +
                BuildConfig.API_KEY + "&primary_release_date.qte=" +
                currentDate + "&primary_release_date.lte="+ currentDate;

        AsyncHttpClient httpClient = new AsyncHttpClient();
        httpClient.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    int id = 2;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String title = object.getString("title");
                        String overview = object.getString("overview");
                        showNewReleaseReminder(context, title, overview, id);
                        id++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void showNewReleaseReminder(Context context, String title, String overview, int id) {
        String ID = "channel2";
        String NAME = "New Release Today";

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, ID)
                .setSmallIcon(R.drawable.ic_notif_movie)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_notif_movie))
                .setContentTitle(title)
                .setContentText(overview)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setContentIntent(pendingIntent)
                .setSound(alarmSound)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Notification notification = builder.build();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(ID, NAME, NotificationManager.IMPORTANCE_DEFAULT);
            builder.setChannelId(ID);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        if (notificationManager != null) {
            notificationManager.notify(id, notification);
        }
    }

    public void cancelReminder(Context context, String data) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, MyReminder.class);
        int requestCode = data.equalsIgnoreCase(DAILY_REMINDER) ? DAILY_ID : NEWRELEASE_ID;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        pendingIntent.cancel();
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
        Toast.makeText(context, R.string.cancel_reminder, Toast.LENGTH_SHORT).show();
    }

    public void cancelDailyReminder(Context context) {
        cancelReminder(context, DAILY_REMINDER);
    }

    public void cancelNewReleaseReminder(Context context) {
        cancelReminder(context, NEW_RELEASE_REMINDER);
    }
}
