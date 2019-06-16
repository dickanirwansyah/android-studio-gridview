package dicka.com.sqlitegridview.entity;

import java.io.Serializable;
import java.util.Arrays;

public class Produk implements Serializable {

    private String id;
    private String nama;
    private String harga;
    private byte[] gambar;

    public Produk(){}

    public Produk(String id, String nama, String harga, byte[] gambar){
        this.id=id;
        this.nama = nama;
        this.harga = harga;
        this.gambar = gambar;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getNama(){
        return nama;
    }

    public void setNama(String nama){
        this.nama = nama;
    }

    public String getHarga(){
        return harga;
    }

    public void setHarga(String harga){
        this.harga = harga;
    }

    public byte[] getGambar() {
        return gambar;
    }

    public void setGambar(byte[] gambar) {
        this.gambar = gambar;
    }

    @Override
    public String toString() {
        return "Produk{" +
                "id='" + id + '\'' +
                ", nama='" + nama + '\'' +
                ", harga='" + harga + '\'' +
                ", gambar=" + Arrays.toString(gambar) +
                '}';
    }
}
