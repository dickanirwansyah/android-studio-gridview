package dicka.com.sqlitegridview.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import dicka.com.sqlitegridview.R;
import dicka.com.sqlitegridview.entity.Produk;

public class ProdukAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Produk> arrayProduks;

    public ProdukAdapter(Context context, int layout, ArrayList<Produk> arrayProduks){
        this.context = context;
        this.layout = layout;
        this.arrayProduks = arrayProduks;
    }

    private class ViewHolder{
        ImageView imageViewProduk;
        TextView txtNamaProduk;
        TextView txtHargaProduk;
    }

    @Override
    public int getCount() {
        return arrayProduks.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayProduks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ViewHolder viewHolder = new ViewHolder();

        if (row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            viewHolder.txtNamaProduk = (TextView) row.findViewById(R.id.txtNamaProduk);
            viewHolder.txtHargaProduk = (TextView) row.findViewById(R.id.txtHargaProduk);
            viewHolder.imageViewProduk = (ImageView) row.findViewById(R.id.imageViewProduk);
            row.setTag(viewHolder);
        }else{

            viewHolder = (ViewHolder) row.getTag();
        }

        Produk produk = arrayProduks.get(position);
        viewHolder.txtNamaProduk.setText(produk.getNama());
        viewHolder.txtHargaProduk.setText(produk.getHarga());
        byte[] produkGambar = produk.getGambar();
        Bitmap bitmap = BitmapFactory.decodeByteArray(produkGambar, 0, produk.getGambar().length);
        viewHolder.imageViewProduk.setImageBitmap(bitmap);

        return row;
    }
}
