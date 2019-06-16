package dicka.com.sqlitegridview;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;

import dicka.com.sqlitegridview.entity.Produk;
import dicka.com.sqlitegridview.helper.SQLiteHelper;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private EditText edtNama;
    private EditText edtHarga;
    private Button btnChoose;
    private ImageView imageView;
    private Button btnAdd;
    private Button btnShowData;

    public static SQLiteHelper sqliteHelper;
    final int REQUEST_CODE_GALERY = 999;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sqliteHelper = new SQLiteHelper(this, "ProdukDB.sqlite", null, 1);
        sqliteHelper.queryData("CREATE TABLE if not exists PRODUK (id VARCHAR PRIMARY KEY, " +
                "nama VARCHAR, harga VARCHAR, gambar BLOB)");

        initial();

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE
                        }, REQUEST_CODE_GALERY);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validasiInput()){

                    try{

                        Produk produk = new Produk();
                        produk.setId(UUID.randomUUID().toString());
                        produk.setNama(edtNama.getText().toString());
                        produk.setHarga(edtHarga.getText().toString());
                        produk.setGambar(imageViewToByte(imageView));
                        sqliteHelper.simpanProduk(produk);
                        Toast.makeText(MainActivity.this, "data produk berhasil disimpan", Toast.LENGTH_SHORT).show();
                        clearData();

                        Log.d(TAG, "onClick: simpanDataProduk : "+produk.toString());

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }
        });

        btnShowData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProdukListActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CODE_GALERY){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALERY);
            }else{
                Toast.makeText(getApplicationContext(), "maaf anda tidak dapat mengakses galery", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == REQUEST_CODE_GALERY && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);

                Log.d(TAG, "onActivityResult: bitmap : "+bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initial(){
        edtNama = (EditText) findViewById(R.id.edtNama);
        edtHarga = (EditText) findViewById(R.id.edtHarga);
        imageView = (ImageView) findViewById(R.id.imageView);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnChoose = (Button) findViewById(R.id.btnChoose);
        btnShowData = (Button) findViewById(R.id.btnShowData);
    }

    private byte[] imageViewToByte(ImageView image){
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    private void clearData(){
        edtNama.setText("");
        edtHarga.setText("");
        imageView.setImageResource(R.mipmap.ic_launcher);
    }

    private boolean validasiInput(){
        boolean valid = false;
        if (edtNama.getText().toString().equals("")){
            Toast.makeText(MainActivity.this, "nama masih kosong", Toast.LENGTH_SHORT).show();
        }else if (edtHarga.getText().toString().equals("")){
            Toast.makeText(MainActivity.this, "harga masih kosong", Toast.LENGTH_SHORT).show();
        }else{
            valid = true;
        }
        return valid;
    }

}
