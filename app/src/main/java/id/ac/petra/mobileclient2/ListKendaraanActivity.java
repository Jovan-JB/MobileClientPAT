package id.ac.petra.mobileclient2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ListKendaraanActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private KendaraanAdapter kendaraanAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_kendaraan);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        kendaraanAdapter = new KendaraanAdapter();
        recyclerView.setAdapter(kendaraanAdapter);

        // Make the HTTP request
        new FetchKendaraanTask().execute();
    }

    private class FetchKendaraanTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            String response = null;
            HttpURLConnection connection = null;

            try {
                URL url = new URL("http://172.22.36.163:7000/listkendaraan"); // Replace with your API endpoint
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                // Read the response
                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                response = stringBuilder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }

            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            if (response != null) {
                try {
                    JSONObject responseObject = new JSONObject(response);
                    JSONArray jsonArray = responseObject.getJSONArray("response");
                    List<Kendaraan> kendaraanList = parseResponse(jsonArray);
                    kendaraanAdapter.setKendaraanList(kendaraanList);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ListKendaraanActivity.this, "Failed to parse response", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(ListKendaraanActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private List<Kendaraan> parseResponse(JSONArray jsonArray) throws JSONException {
        List<Kendaraan> kendaraanList = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            int idMobil = jsonObject.getInt("idmobil");
            String jenisMobil = jsonObject.getString("jenis_mobil");
            int jumlahMobil = jsonObject.getInt("jumlah_mobil");
            int hargaMobil = jsonObject.getInt("harga_mobil");

            Kendaraan kendaraan = new Kendaraan(idMobil, jenisMobil, jumlahMobil, hargaMobil);
            kendaraanList.add(kendaraan);
        }

        return kendaraanList;
    }

}

