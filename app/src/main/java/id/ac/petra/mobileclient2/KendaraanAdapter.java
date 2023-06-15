package id.ac.petra.mobileclient2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class KendaraanAdapter extends RecyclerView.Adapter<KendaraanAdapter.KendaraanViewHolder> {
    private List<Kendaraan> kendaraanList;

    public KendaraanAdapter() {
        this.kendaraanList = new ArrayList<>();
    }

    @NonNull
    @Override
    public KendaraanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kendaraan, parent, false);
        return new KendaraanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KendaraanViewHolder holder, int position) {
        Kendaraan kendaraan = kendaraanList.get(position);
        holder.bind(kendaraan);
    }

    @Override
    public int getItemCount() {
        return kendaraanList.size();
    }

    public void setKendaraanList(List<Kendaraan> kendaraanList) {
        this.kendaraanList = kendaraanList;
        notifyDataSetChanged();
    }

    public class KendaraanViewHolder extends RecyclerView.ViewHolder {
        private TextView txtJenisMobil;
        private TextView txtJumlahMobil;
        private TextView txtHargaMobil;

        public KendaraanViewHolder(@NonNull View itemView) {
            super(itemView);
            txtJenisMobil = itemView.findViewById(R.id.txtJenisMobil);
            txtJumlahMobil = itemView.findViewById(R.id.txtJumlahMobil);
            txtHargaMobil = itemView.findViewById(R.id.txtHargaMobil);
        }

        public void bind(Kendaraan kendaraan) {
            txtJenisMobil.setText(kendaraan.getJenisMobil());
            txtJumlahMobil.setText(String.valueOf(kendaraan.getJumlahMobil()));
            txtHargaMobil.setText(String.valueOf(kendaraan.getHargaMobil()));
        }
    }
}

