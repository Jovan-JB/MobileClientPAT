package id.ac.petra.mobileclient2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class BookingActivity extends AppCompatActivity {

    private EditText usernameField;
    private EditText teleponField;
    private EditText waktuField;
    private EditText jenisField;
    private Button bookButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        usernameField = findViewById(R.id.usernameField);
        teleponField = findViewById(R.id.teleponField);
        waktuField = findViewById(R.id.waktuField);
        jenisField = findViewById(R.id.jenisField);
        bookButton = findViewById(R.id.bookButton);

        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameField.getText().toString().trim();
                String telepon = teleponField.getText().toString().trim();
                String waktu = waktuField.getText().toString().trim();
                String jenis = jenisField.getText().toString().trim();

                if (username.isEmpty() || telepon.isEmpty() || waktu.isEmpty() || jenis.isEmpty()) {
                    Toast.makeText(BookingActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    JSONObject bookingData = new JSONObject();
                    try {
                        bookingData.put("username", username);
                        bookingData.put("telepon", telepon);
                        bookingData.put("waktu", waktu);
                        bookingData.put("jenis", jenis);

                        new BookAsyncTask().execute(bookingData.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private class BookAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String result = "";
            try {
                String bookingData = params[0];
                URL url = new URL("http://172.22.36.163:7000/booking");

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                OutputStream os = connection.getOutputStream();
                os.write(bookingData.getBytes());
                os.flush();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder responseBuilder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        responseBuilder.append(line);
                    }
                    result = responseBuilder.toString();
                } else {
                    result = "Error: " + responseCode;
                }

                connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
                result = "Error occurred while connecting to the server.";
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(BookingActivity.this, result, Toast.LENGTH_SHORT).show();
        }
    }
}
