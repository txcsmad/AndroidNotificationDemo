package nlaz.notificationdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by nlazaris on 3/25/15.
 */
public class MyReceiver extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Intent serviceIntent = new Intent(context, MyAlarmService.class);
        serviceIntent.putExtra("TASK", intent.getParcelableExtra("TASK"));
        context.startService(serviceIntent);

    }
}