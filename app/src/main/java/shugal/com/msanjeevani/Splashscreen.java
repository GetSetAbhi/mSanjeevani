package shugal.com.msanjeevani;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.facebook.shimmer.ShimmerFrameLayout;

/**
 * Created by abhishek on 30/10/15.
 */
public class Splashscreen extends AppCompatActivity {


    int SCREEN_TIMEOUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        startSplashscreen();

        ShimmerFrameLayout container =
                (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container);
        container.setDuration(3000);
        container.startShimmerAnimation();
    }

    private boolean isFirstTime() {
        boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isfirstrun", true);
        if (isFirstRun) {
            getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().
                    putBoolean("isfirstrun", false).commit();

            return true;

        } else {
            return false;
        }
    }

    private void startSplashscreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (isFirstTime()) {
                    createDatabases();
                    Intent firstInputs = new Intent(Splashscreen.this, Greetings.class);
                    startActivity(firstInputs);
                } else {
                    Intent proceedToMain = new Intent(Splashscreen.this, MainActivity.class);
                    startActivity(proceedToMain);
                }

                finish();
            }
        }, SCREEN_TIMEOUT);
    }

    private void createDatabases() {
        DatabaseHelper db = new DatabaseHelper(this);


        DoctorData datas0 = new DoctorData("Dr Aman", "Fortis");
        db.addDoctor(datas0);
        DoctorData datas1 = new DoctorData("Dr Agarwal", "Fortis");
        db.addDoctor(datas1);
        DoctorData datas2 = new DoctorData("Dr Kumar", "Max Hospital");
        db.addDoctor(datas2);
        DoctorData datas3 = new DoctorData("Dr Agarwal", "Max Hospital");
        db.addDoctor(datas3);
        DoctorData datas4 = new DoctorData("Dr Geeta", "PGI");
        db.addDoctor(datas4);
        DoctorData datas5 = new DoctorData("Dr Vishwas", "PGI");
        db.addDoctor(datas5);
        DoctorData datas6 = new DoctorData("Dr Verma", "GMCH Sector 32");
        db.addDoctor(datas6);
        DoctorData datas7 = new DoctorData("Dr Vaishali", "GMCH Sector 32");
        db.addDoctor(datas7);
        DoctorData datas8 = new DoctorData("Dr Gupta", "Mukat Hospital");
        db.addDoctor(datas8);
        DoctorData datas9 = new DoctorData("Dr Vishal", "Mukat Hospital");
        db.addDoctor(datas9);
        DoctorData datas10 = new DoctorData("Dr Rohit", "Grewal Eye Institute");
        db.addDoctor(datas10);
        DoctorData datas11 = new DoctorData("Dr Shekhar", "Grewal Eye Institute");
        db.addDoctor(datas11);

        AppointmentData data = new AppointmentData("kk", 9, "k", "k", "k", "k", "k");
        db.addAppointment(data);
        SampleData data1 = new SampleData("kk", 9, "k", "k", "k", "k");
        db.addSample(data1);
        db.deleteFirstValues();


        db.close();
    }

}
