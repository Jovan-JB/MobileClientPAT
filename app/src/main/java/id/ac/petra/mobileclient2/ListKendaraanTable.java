package id.ac.petra.mobileclient2;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ListKendaraanTable extends AppCompatActivity {

    private ListView listViewKendaraan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_kendaraan_table);
        listViewKendaraan = findViewById(R.id.listViewKendaraan);

        new datakendaraan().execute();
    }

    private class datakendaraan extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String result;
            try {
                URL url = new URL("http://192.168.0.105:7000/listkendaraan");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();
                    result = response.toString();
                } else {
                    result = "Error: " + responseCode;
                }
                connection.disconnect();
            } catch (Exception e) {
                result = "Error: " + e.getMessage();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            List<String> listmobil = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject kendaraan = jsonArray.getJSONObject(i);
                    String jenis = kendaraan.getString("jenis_mobil");
                    String jumlah = kendaraan.getString("jumlah_mobil");
                    String harga = kendaraan.getString("harga_mobil");

                    String detailkendaraan = "Jenis Mobil: " + jenis + "\n"
                                            + "Jumlah Tersedia: " + jumlah + "\n"
                                            + "Harga: " + harga;

                    listmobil.add(detailkendaraan);
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(ListKendaraanTable.this,
                        android.R.layout.simple_list_item_1, listmobil);
                listViewKendaraan.setAdapter(adapter);

            } catch (JSONException e) {
                Toast.makeText(ListKendaraanTable.this, "Error parsing JSON data", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }
}