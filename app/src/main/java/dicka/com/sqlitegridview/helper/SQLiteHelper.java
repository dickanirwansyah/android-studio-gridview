package dicka.com.sqlitegridview.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.sql.Blob;
import java.util.UUID;

import dicka.com.sqlitegridview.entity.Produk;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHelper.class.getSimpleName();

    private static final String TABEL_PRODUK = "PRODUK";


    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void queryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public void simpanProduk(Produk produk){
        SQLiteDatabase database = getWritableDatabase();
        String query = "INSERT INTO "+TABEL_PRODUK+" VALUES (?, ?, ?, ?)";
        SQLiteStatement statement = database.compileStatement(query);
        statement.clearBindings();
        statement.bindString(1, produk.getId());
        statement.bindString(2, produk.getNama());
        statement.bindString(3, produk.getHarga());
        statement.bindBlob(4, produk.getGambar());
        statement.executeInsert();
    }

    public Cursor tampilkanData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
