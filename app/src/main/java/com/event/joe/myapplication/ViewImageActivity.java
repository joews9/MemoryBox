package com.event.joe.myapplication;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RotateDrawable;
import android.media.Image;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ViewImageActivity extends AppCompatActivity {
    String imageResource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = this.findViewById(android.R.id.content).getRootView();

        imageResource = getIntent().getExtras().getString("imagePath");
        File f = new File(imageResource);
        Toast.makeText(ViewImageActivity.this, f.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        ImageView imageView =  (ImageView)findViewById(R.id.full_image);
        imageView.setImageBitmap(BitmapFactory.decodeFile(f.getAbsolutePath()));
        imageView.setRotation(imageView.getRotation() + 90);
        setContentView(R.layout.activity_view_image);
    }

}
