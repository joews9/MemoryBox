package com.event.joe.myapplication.com.event.joe;

import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.event.joe.myapplication.R;

import java.io.File;

public class ImageViewActivity extends AppCompatActivity {

    private String imageResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        imageResource = getIntent().getExtras().getString("imagePath");
        ImageView iv = (ImageView)findViewById(R.id.ivFullImage);
        File f = new File(imageResource);
        iv.setImageBitmap(BitmapFactory.decodeFile(f.getAbsolutePath()));
        iv.setRotation(iv.getRotation() + 90);

    }
}
