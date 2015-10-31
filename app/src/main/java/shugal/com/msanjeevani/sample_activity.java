package shugal.com.msanjeevani;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class sample_activity extends AppCompatActivity {

    TextInputLayout nameLayout, ageLayout, dateLayout, timeLayout;
    EditText nameTxt, ageTxt, dateTxt, timeTxt;
    Spinner sampleSpinner, labSpinner;
    RadioButton male, female;

    final int TIME_DIALOG = 0, DATE_DIALOG = 1;

    SampleData data = new SampleData();
    Calendar calender = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Collect Samples");


        nameLayout = (TextInputLayout) findViewById(R.id.nameLayout);
        ageLayout = (TextInputLayout) findViewById(R.id.ageLayout);
        dateLayout = (TextInputLayout) findViewById(R.id.dateLayout);
        timeLayout = (TextInputLayout) findViewById(R.id.timeLayout);

        nameTxt = (EditText) findViewById(R.id.nameTxt);
        ageTxt = (EditText) findViewById(R.id.ageTxt);
        dateTxt = (EditText) findViewById(R.id.dateTxt);
        timeTxt = (EditText) findViewById(R.id.timeTxt);

        sampleSpinner = (Spinner) findViewById(R.id.hospitalSpinner);
        labSpinner = (Spinner) findViewById(R.id.labSpinner);

        nameTxt.addTextChangedListener(new MyTextWatcher(nameTxt));
        ageTxt.addTextChangedListener(new MyTextWatcher(ageTxt));
        dateTxt.addTextChangedListener(new MyTextWatcher(dateTxt));
        timeTxt.addTextChangedListener(new MyTextWatcher(timeTxt));

        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);

        fillSpinners();
    }

    private void fillSpinners() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.samples_array, android.R.layout.simple_spinner_dropdown_item);
        sampleSpinner.setAdapter(adapter);
        sampleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                data.setSample(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        adapter = ArrayAdapter.createFromResource(this,
                R.array.labs_array, android.R.layout.simple_spinner_dropdown_item);
        labSpinner.setAdapter(adapter);
        labSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                data.setLab(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void launchDatePicker(View view) {
        showDialog(DATE_DIALOG);
    }

    public void launchTimePicker(View view) {
        showDialog(TIME_DIALOG);
    }

    public void addSample(View view) {
        if (!validateName()) {
            return;
        }

        if (!validateAge()) {
            return;
        }

        if (!validateDate()) {
            return;
        }

        if (!validateTime()) {
            return;
        }

        data.setPatient_name(nameTxt.getText().toString());
        int age = Integer.parseInt(ageTxt.getText().toString());
        data.setAge(age);
        data.setDate(dateTxt.getText().toString());
        data.setTime(timeTxt.getText().toString());

        if (male.isChecked()) {
            data.setGender("Male");
        } else if (female.isChecked()) {
            data.setGender("Female");
        }
        DatabaseHelper db = new DatabaseHelper(this);
        db.addSample(data);
        db.close();
        startActivity(new Intent(this, MainActivity.class));
        Toast.makeText(getApplicationContext(), "Request Sent!!", Toast.LENGTH_SHORT).show();
        finish();
        //data.setLecture_number(Integer.parseInt(lectureNumber.getText().toString()));
        //db.addTimetableSlot(data);
    }

    @Override
    public Dialog onCreateDialog(int id) {

        if (id == DATE_DIALOG) {
            return new DatePickerDialog(this,
                    dPickerListener,
                    calender.get(Calendar.YEAR),
                    calender.get(Calendar.MONTH),
                    calender.get(Calendar.DAY_OF_MONTH));

        } else if (id == TIME_DIALOG) {
            return new TimePickerDialog(this,
                    mTimeSetListener, calender.get(Calendar.HOUR), calender.get(Calendar.MINUTE), false);
        }

        return null;
    }

    private String makeDate(int day_of_month, int month, int year) {
        String date = day_of_month + " " + makeMonth(month) + " " + year;
        return date;
    }

    private String makeMonth(int month) {
        String mMonth = "";
        if (month == 1) {
            mMonth = "January";
        } else if (month == 2) {
            mMonth = "February";
        } else if (month == 3) {
            mMonth = "March";
        } else if (month == 4){
            mMonth = "April";
        } else if (month == 5) {
            mMonth = "May";
        } else if (month == 6) {
            mMonth = "June";
        } else if (month == 7) {
            mMonth = "July";
        } else if (month == 8) {
            mMonth = "August";
        } else if (month == 9) {
            mMonth = "September";
        } else if (month == 10) {
            mMonth = "October";
        } else if (month == 11) {
            mMonth = "November";
        } else if (month == 12) {
            mMonth = "December";
        }

        return mMonth;
    }

    private boolean validateTime() {
        if (timeTxt.getText().toString().trim().isEmpty()) {
            timeLayout.setError("Please Enter a time");
            requestFocus(timeTxt);
            return false;
        } else {
            timeLayout.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateDate() {
        if (dateTxt.getText().toString().trim().isEmpty()) {
            dateLayout.setError("Please Enter Date");
            requestFocus(dateTxt);
            return false;
        } else {
            dateLayout.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateAge() {
        if (ageTxt.getText().toString().trim().isEmpty()) {
            ageLayout.setError("Please Enter your Age");
            requestFocus(ageTxt);
            return false;
        } else {
            ageLayout.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateName() {
        if (nameTxt.getText().toString().trim().isEmpty()) {
            nameLayout.setError("Please Enter your Name");
            requestFocus(nameTxt);
            return false;
        } else {
            nameLayout.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.nameTxt:
                    validateName();
                    break;
                case R.id.ageTxt:
                    validateAge();
                    break;

                case R.id.dateTxt:
                    validateDate();
                    break;
                case R.id.timeTxt:
                    validateTime();
                    break;
            }
        }
    }

    private DatePickerDialog.OnDateSetListener dPickerListener = new DatePickerDialog.OnDateSetListener() {


        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            String message = makeDate(dayOfMonth, (monthOfYear+1), year);
            dateTxt.setText(message);

        }
    };

    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    String message = pad(hourOfDay) + ":" + pad(minute);
                    timeTxt.setText(message);
                }
            };

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }
}
