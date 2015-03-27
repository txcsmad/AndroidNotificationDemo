package nlaz.notificationdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.parceler.Parcels;


public class NotificationReceiver extends ActionBarActivity {

    private Task mTask;
    private TextView titleView;
    private TextView descriptionView;
    private TextView timeView;
    private ToggleButton button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        Intent intent = getIntent();
        mTask = Parcels.unwrap(intent.getParcelableExtra("TASK"));

        titleView       = (TextView) findViewById(R.id.result_title);
        descriptionView = (TextView) findViewById(R.id.result_description);
        timeView        = (TextView) findViewById(R.id.result_time);
        button          = (ToggleButton) findViewById(R.id.result_button);

        titleView.setText(mTask.getTitle());
        descriptionView.setText(mTask.getDescription());
        timeView.setText(mTask.getTimeString());
        button.setChecked(mTask.isActive());
    }

}
