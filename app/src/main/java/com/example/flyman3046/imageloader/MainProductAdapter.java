package com.example.flyman3046.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.flyman3046.imageloader.model.ProductItem;

import java.util.List;

public class MainProductAdapter extends RecyclerView.Adapter<MainProductAdapter.SolventViewHolders> {

    private List<ProductItem> itemList;
    private final static String TAG = MainProductAdapter.class.getSimpleName();

    public MainProductAdapter(List<ProductItem> itemList) {
        this.itemList = itemList;
    }

    @Override
    public SolventViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card, null);
        return new SolventViewHolders(layoutView);
    }

    @Override
    public void onBindViewHolder(SolventViewHolders holder, int position) {
        final ProductItem obj = itemList.get(position);
        Log.wtf(TAG, obj.getImg() == null ? "'obj is null" : "obj is not null");
        holder.card.setTag(obj);

        List<Byte> data = obj.getImg().getData().getData();
        byte[] array = new byte[data.size()];
        for(int i = 0; i < array.length; i++) {
            array[i] = data.get(i);
        }

        Bitmap decodedByte = BitmapFactory.decodeByteArray(array, 0, array.length);

        holder.imageView.setImageBitmap(decodedByte);
        holder.textView.setText("this is a test");
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public class SolventViewHolders extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imageView;
        public View card;

        public SolventViewHolders(View itemView) {
            super(itemView);
            card = itemView;
            textView = (TextView) itemView.findViewById(R.id.country_name);
            imageView = (ImageView) itemView.findViewById(R.id.country_photo);
        }
    }
}
