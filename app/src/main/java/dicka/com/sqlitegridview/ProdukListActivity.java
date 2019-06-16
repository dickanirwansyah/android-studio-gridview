package dicka.com.sqlitegridview;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import java.util.ArrayList;

import dicka.com.sqlitegridview.adapter.ProdukAdapter;
import dicka.com.sqlitegridview.entity.Produk;


public class ProdukListActivity extends AppCompatActivity {


    private GridView gridView;
    private ArrayList<Produk> produks;
    private ProdukAdapter produkAdapter = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk_list);

        init();

    }


    private void init(){

        gridView = (GridView) findViewById(R.id.gridView);
        produks = new ArrayList<>();
        produkAdapter = new ProdukAdapter(this, R.layout.produk_items, produks);
        gridView.setAdapter(produkAdapter);

        //get data from local database
        Cursor cursor = MainActivity.sqliteHelper.tampilkanData("SELECT * FROM PRODUK");
        produks.clear();

        while (cursor.moveToNext()){
            String id = cursor.getString(0);
            String nama = cursor.getString(1);
            String harga = cursor.getString(2);
            byte[] gambar = cursor.getBlob(3);

            produks.add(new Produk(id, nama, harga, gambar));
        }

        produkAdapter.notifyDataSetChanged();
    }

}
