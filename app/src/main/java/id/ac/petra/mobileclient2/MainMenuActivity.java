package id.ac.petra.mobileclient2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainMenuActivity extends AppCompatActivity implements View.OnClickListener {

    private Button listKendaraanButton;
    private Button bookingButton;
    private Button cancelBookingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu2);

        listKendaraanButton = findViewById(R.id.listKendaraanButton);
        bookingButton = findViewById(R.id.bookingButton);
        cancelBookingButton = findViewById(R.id.cancelBookingButton);

        listKendaraanButton.setOnClickListener(this);
        bookingButton.setOnClickListener(this);
        cancelBookingButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.listKendaraanButton:
                // Handle List Kendaraan button click
                openListKendaraanActivity();
                break;
            case R.id.bookingButton:
                // Handle Booking button click
                openBookingActivity();
                break;
            case R.id.cancelBookingButton:
                // Handle Cancel Booking button click
                openCancelBookingActivity();
                break;
        }
    }

    private void openListKendaraanActivity() {
        Intent intent = new Intent(this, ListKendaraanActivity.class);
        startActivity(intent);
    }

    private void openBookingActivity() {
        Intent intent = new Intent(this, BookingActivity.class);
        startActivity(intent);
    }

    private void openCancelBookingActivity() {
        Intent intent = new Intent(this, CancelBookingActivity.class);
        startActivity(intent);
    }
}
