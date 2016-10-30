package com.example.flyman3046.imageloader;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.flyman3046.imageloader.model.Data;
import com.example.flyman3046.imageloader.model.Img;
import com.example.flyman3046.imageloader.model.ProductItem;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getSimpleName();
    private final static int REQUEST_IMAGE_CAPTURE = 1;

    private RecyclerView mRecyclerView;
    private MainProductAdapter rcAdapter;
    private List<ProductItem> mGaggeredList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        StaggeredGridLayoutManager glm = new StaggeredGridLayoutManager(2, 1);
        mRecyclerView.setLayoutManager(glm);

        mGaggeredList = new ArrayList<>();
        rcAdapter = new MainProductAdapter(mGaggeredList);
        mRecyclerView.setAdapter(rcAdapter);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemViewCacheSize(30);
        mRecyclerView.setDrawingCacheEnabled(true);
        mRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.take_camera);
        if(fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Click action
                    Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                }
            });
        }

        downloadImages();
    }

    private void uploadImage(Bitmap bp) {
        Log.d(TAG, "Start to post query");
        ApiClient.ApiStores apiStores = ApiClient.retrofit().create(ApiClient.ApiStores.class);
        bp = getResizedBitmap(bp, 300);

        ProductItem item = new ProductItem();
        Img img = new Img();
        Data data = new Data();
        List<Byte> list = new ArrayList<>();
        for(byte b : bitMapToString(bp)) {
            list.add(b);
        }
        data.setData(list);
        img.setData(data);
        item.setImg(img);

        Observable<Message> observable = apiStores.uploadProduct(item);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Message>() {
                    @Override
                    public void onCompleted() {
                        Log.wtf(TAG, "onCompleted");
                        downloadImages();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.wtf(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(Message message) {
                        Log.wtf(TAG, "Received a message: " + message.getMessage());
                    }
                });
    }

    private Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    private void downloadImages() {
        Log.d(TAG, "Start to get json");
        ApiClient.ApiStores apiStores = ApiClient.retrofit().create(ApiClient.ApiStores.class);

        Observable<List<ProductItem>> observable = apiStores.getProducts();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ProductItem>>() {
                    @Override
                    public void onCompleted() {
                        Log.wtf(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.wtf(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(List<ProductItem> result) {
                        Log.wtf(TAG, "number received: " + result.size());

                        mGaggeredList.clear();
                        mGaggeredList.addAll(result);
                        rcAdapter.notifyDataSetChanged();
                    }
                });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.wtf(TAG, "the request code is returned");

        if(data!= null && data.getExtras() != null) {
            Log.wtf(TAG, "upload the image");
            Bitmap bp = (Bitmap) data.getExtras().get("data");
            uploadImage(bp);
        }
        else {
            Toast.makeText(this, "no pictures was taken", Toast.LENGTH_SHORT).show();
        }
    }

    private byte[] bitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        return baos.toByteArray();
    }
}
