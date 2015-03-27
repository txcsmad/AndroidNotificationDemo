package nlaz.notificationdemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import org.parceler.Parcels;

/**
 * Created by nlazaris on 3/25/15.
 */
public class MyAlarmService extends Service
{

    private Task task;
    private NotificationManager mManager;

    @Override
    public IBinder onBind(Intent arg0)
    {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        task = Parcels.unwrap(intent.getParcelableExtra("TASK"));
        mManager = (NotificationManager) this.getApplicationContext().getSystemService(this.getApplicationContext().NOTIFICATION_SERVICE);
        createNotification();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void createNotification() {
        // Prepare intent which is triggered if the
        // notification is selected
        Intent intent = new Intent(this, NotificationReceiver.class);
        intent.putExtra("TASK",Parcels.wrap(task));
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

        // Build notification
        // Actions are just fake
        Notification notification = new Notification.Builder(this)
                .setContentTitle(task.getTitle())
                .setContentText(task.getDescription())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // hide the notification after its selected
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, notification);

    }

    @Override
    public void onDestroy()
    {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

}
