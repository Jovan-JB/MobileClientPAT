package id.ac.petra.mobileclient2;

public class Kendaraan {
    private int idMobil;
    private String jenisMobil;
    private int jumlahMobil;
    private double hargaMobil;

    public Kendaraan(int idMobil, String jenisMobil, int jumlahMobil, double hargaMobil) {
        this.idMobil = idMobil;
        this.jenisMobil = jenisMobil;
        this.jumlahMobil = jumlahMobil;
        this.hargaMobil = hargaMobil;
    }

    public int getIdMobil() {
        return idMobil;
    }

    public String getJenisMobil() {
        return jenisMobil;
    }

    public int getJumlahMobil() {
        return jumlahMobil;
    }

    public double getHargaMobil() {
        return hargaMobil;
    }
}
