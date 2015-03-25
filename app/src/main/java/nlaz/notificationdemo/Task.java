package nlaz.notificationdemo;

import java.util.Calendar;

/**
 * Created by nlazaris on 3/25/15.
 */
public class Task {


    private String title;
    private String description;
    private Calendar time;
    private boolean isActive = false;

    public Task(String title, String description, Calendar time){
        this.title = title;
        this.description = description;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Calendar getTime() {
        return time;
    }

    public String getTimeString(){
        return String.format("%tr", time);
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
}
