package pbru.it.sukuma.itpbru;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;

public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        //bind widget
        calendarView = (CalendarView) findViewById(R.id.calendarView);


        //active when click calendar
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
                confirmDialog(day, month, year);

            }
        });

    }   // main method

    private void confirmDialog(int day, int month, int year) {

        final String strDate = Integer.toString(day) +
                "/" + Integer.toString(month) +
                "/" + Integer.toString(year);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.doremon48);
        builder.setTitle(strDate);
        builder.setMessage("You would like to record Income or Payment ?");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("Income", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                Intent intent = new Intent(CalendarActivity.this, UploadAccount.class);
                intent.putExtra("Login", getIntent().getStringArrayExtra("Login"));
                intent.putExtra("InOut", 0);
                intent.putExtra("Date", strDate);
                startActivity(intent);


                dialog.dismiss();


            }
        });
        builder.setNegativeButton("Payment", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(CalendarActivity.this, UploadAccount.class);
                intent.putExtra("Login", getIntent().getStringArrayExtra("Login"));
                intent.putExtra("InOut", 1);
                intent.putExtra("Date", strDate);
                startActivity(intent);
                dialog.dismiss();

            }
        });
        builder.show();


    }   //confirmdialog

    //Explicit
    private CalendarView calendarView;

}   //main class
