package shugal.com.msanjeevani;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Deepinder on 10/24/2015.
 */
public class SampleSchedulingService extends IntentService {
    NotificationCompat.Builder m1Hour;
    private NotificationManager mNotificationManager;
    private int l, k;
    public SampleSchedulingService() {
        super("SchedulingService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

            sendNotification();
    }

    private void sendNotification() {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        DatabaseHelper db = new DatabaseHelper(getApplicationContext());

        String weekDay;
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);

        Calendar calendar = Calendar.getInstance();
        weekDay = dayFormat.format(calendar.getTime());


        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, AppointmentList.class), 0);


        Calendar c = Calendar.getInstance();
        int d = c.get(Calendar.HOUR_OF_DAY);
        int e = c.get(Calendar.MINUTE);
        int f = c.get(Calendar.SECOND);


        if (!db.isAppointmentListEmpty()) {
            ArrayList<AppointmentData> contacts = db.showAppointments();

            for (int i = 0; i < contacts.size(); i++) {
                AppointmentData data = contacts.get(i);
                String time = data.getTime();
                String doctor = data.getDoctor();
                String hospital = data.getHospital();
                String stHour = time.substring(0, 2);
                String stMin = time.substring(3, 5);
                int sHour = Integer.parseInt(stHour);
                int sMin = Integer.parseInt(stMin);

                l = sHour-1;
                k = sMin;
                if (d == l && e == k && f < 4) {
                    m1Hour = new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.icll)
                            .setContentTitle("mSanjeevani")
                            .setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText( "Upcoming Appointment with "+doctor +" at "+ hospital
                                    + " in 1 hour"))
                            .setContentText("Upcoming Appointment with "+doctor +" at "+ hospital
                                    + " in 1 hour");

                    m1Hour.setAutoCancel(true);
                    m1Hour.setContentIntent(contentIntent);

                    mNotificationManager.notify(1, m1Hour.build());
                    Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
                    // Vibrate for 500 milliseconds
                    v.vibrate(1000);
                }
            }
        }

    }
}
