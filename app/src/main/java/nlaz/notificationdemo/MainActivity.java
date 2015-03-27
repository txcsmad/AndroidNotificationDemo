package nlaz.notificationdemo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends ActionBarActivity {

    private ListView listView;
    private ArrayList<Task> objects;
    private TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listview);
        objects = new ArrayList<Task>();

        // Generate Fake Data
        getFakeData();

        // Set adapter
        adapter = new TaskAdapter(this, objects);
        listView.setAdapter(adapter);

    }

    private void getFakeData(){
        Calendar half_minute = Calendar.getInstance();
        half_minute.set(Calendar.SECOND, half_minute.get(Calendar.SECOND) + 10);
        Task task1 = new Task("Get MAD", "Feeling so MAD right now", half_minute);
        objects.add(task1);

        Calendar one_minute = Calendar.getInstance();
        one_minute.set(Calendar.MINUTE, one_minute.get(Calendar.MINUTE) + 1);
        Task task2 = new Task("Eat Dinner", "Heat up some Ramen.", one_minute);
        objects.add(task2);

        Calendar one_hour = Calendar.getInstance();
        one_hour.set(Calendar.HOUR_OF_DAY, one_hour.get(Calendar.HOUR_OF_DAY) + 1);
        Task task3 = new Task("Watch Netflix", "Catch up on Bloodline", one_hour);
        objects.add(task3);

    }

    private class TaskAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        private ArrayList<Task> mData;

        public TaskAdapter(Context context, ArrayList<Task> objects){
            this.mData = objects;
            this.mInflater = (LayoutInflater)LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }
            Task task = mData.get(position);
            holder.title.setText(task.getTitle());
            holder.description.setText(task.getDescription());
            holder.time.setText(task.getTimeString());
            holder.button.setChecked(task.isActive());
            holder.button.setOnCheckedChangeListener(getCheckedChangeListener(task));
            return convertView;
        }

        private CompoundButton.OnCheckedChangeListener getCheckedChangeListener(final Task task){
            return new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    task.setActive(buttonView.isChecked());

                    if (buttonView.isChecked()){
                        Intent myIntent = new Intent(MainActivity.this, MyReceiver.class);
                        myIntent.putExtra("TASK", Parcels.wrap(task));
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent,0);

                        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
                        alarmManager.set(AlarmManager.RTC, task.getTime().getTimeInMillis(), pendingIntent);

                    } else {
                        //TODO: Cancel Alarm Service
                    }
                }
            };
        }

    }

    public static class ViewHolder {
        public TextView title;
        public TextView time;
        public TextView description;
        public ToggleButton button;

        public ViewHolder(View view){
            title       = (TextView) view.findViewById(R.id.item_title);
            time        = (TextView) view.findViewById(R.id.item_time);
            description = (TextView) view.findViewById(R.id.item_description);
            button      = (ToggleButton) view.findViewById(R.id.toggle_button);
        }
    }
}
