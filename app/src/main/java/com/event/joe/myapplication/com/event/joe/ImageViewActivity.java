package com.event.joe.myapplication.com.event.joe;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.event.joe.myapplication.R;

import java.io.File;

public class ImageViewActivity extends AppCompatActivity {

    private String imageResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        imageResource = getIntent().getExtras().getString("imageResource");

        TextView tv = (TextView)findViewById(R.id.tvTitle);
        tv.setText(getIntent().getExtras().getString(KeyID.TITLE.toString()));
        ImageView iv = (ImageView)findViewById(R.id.ivFullImage);
        File f = new File(imageResource);
        try {
            //iv.setImageBitmap(BitmapFactory.decodeFile(f.getAbsolutePath()));
            iv.setImageBitmap(
                    decodeSampledBitmapFromResource(getResources(), f, 100, 100));
        } catch (Exception e) {
            iv.setImageResource(R.drawable.icon);
            e.printStackTrace();
        }
        iv.setRotation(iv.getRotation() + 90);

    }
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
    public static Bitmap decodeSampledBitmapFromResource(Resources res, File resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 50, 50);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(resId.getAbsolutePath(), options);
    }
}
