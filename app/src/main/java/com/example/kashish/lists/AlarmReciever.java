package com.example.kashish.lists;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

public class AlarmReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String name=intent.getStringExtra(Contract.todo.NAME);

        NotificationManager manager=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel= new NotificationChannel(Contract.todo.CHANNEL_ID,Contract.todo.CHANNEL_NAME,NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder= new  NotificationCompat.Builder(context,Contract.todo.CHANNEL_ID);
        builder.setContentTitle(name);
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        builder.setContentText("ALARM");

        Intent intent1=new Intent(context,ListActivity.class);
        intent1.setAction("Alarm");

        Bundle data =new Bundle();
        data.putString(Contract.todo.NAME,intent.getStringExtra(Contract.todo.NAME));
        data.putInt(Contract.todo.AGE,intent.getIntExtra(Contract.todo.AGE,0));
        data.putString(Contract.todo.DATE ,intent.getStringExtra(Contract.todo.DATE));
        data.putString(Contract.todo.TIME ,intent.getStringExtra(Contract.todo.TIME));
        intent1.putExtras(data);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,(int)intent.getLongExtra("id",0),intent1,0);

        builder.setContentIntent(pendingIntent);

        Notification notification=builder.build();
        manager.notify((int)System.currentTimeMillis(),notification);


    }
}
