package com.event.joe.myapplication;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;

public class MemoryViewActivity extends AppCompatActivity {
//TODO: Social Media Integration
    private TextView memoryLocation;
    private TextView memoryDate;
    private TextView memoryTitle;
    private TextView memoryDescription;
    private ImageView memoryImage;

    private static final String MEMORY_DATE = "memoryDate";
    private static final String MEMORY_LOCATION = "memoryLocation";
    private static final String MEMORY_DESCRIPTION = "memoryDescription";
    private static final String MEMORY_IMAGE = "memoryImage";
    private static final String MEMORY_TITLE = "memoryTitle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_view);

        memoryLocation = (TextView)findViewById(R.id.tvMemoryLocation);
        memoryDate = (TextView)findViewById(R.id.tvMemoryDate);
        memoryTitle = (TextView)findViewById(R.id.tvMemoryTitle);
        memoryDescription = (TextView)findViewById(R.id.tvMemoryDescription);
        memoryImage = (ImageView)findViewById(R.id.ivMemoryImage);

        memoryLocation.setText(getIntent().getExtras().getString(MEMORY_LOCATION));
        memoryDate.setText(getIntent().getExtras().getString(MEMORY_DATE));
        memoryTitle.setText(getIntent().getExtras().getString(MEMORY_TITLE));
        memoryDescription.setText(getIntent().getExtras().getString(MEMORY_DESCRIPTION));

        final String imageResource = getIntent().getExtras().getString(MEMORY_IMAGE);
        if(imageResource.equals("none")){
            memoryImage.setImageResource(R.drawable.ic_quick_memory);
        }else{
            File f = new File(imageResource);
            Bitmap bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());
            memoryImage.setImageBitmap(BitmapFactory.decodeFile(f.getAbsolutePath()));
            memoryImage.setRotation(memoryImage.getRotation() + 90);

            memoryImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(MemoryViewActivity.this, ImageViewActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("imagePath", imageResource);
//                    intent.putExtras(bundle);
//                    startActivity(intent);
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(imageResource), "image/*");
                    startActivity(intent);
                }
            });
        }

    }
}
