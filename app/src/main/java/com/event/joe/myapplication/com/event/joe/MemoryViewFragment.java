package com.event.joe.myapplication.com.event.joe;

import android.app.Fragment;
import android.app.VoiceInteractor;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.event.joe.myapplication.R;

import java.io.File;
import java.io.FileNotFoundException;

public class MemoryViewFragment extends Fragment {
//TODO: Social Media Integration
    private TextView memoryLocation;
    private TextView memoryDate;
    private TextView memoryTitle;
    private TextView memoryDescription;
    private ImageView memoryImage;
    private File f;
    private String imageURL;

    private static final String MEMORY_DATE = "memoryDate";
    private static final String MEMORY_LOCATION = "memoryLocation";
    private static final String MEMORY_DESCRIPTION = "memoryDescription";
    private static final String MEMORY_IMAGE = "memoryImage";
    private static final String MEMORY_TITLE = "memoryTitle";

    private String bigMemory = "Big Memory";
    private String quickMemory = "Quick Memory";
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_memory_view, container, false);

        memoryLocation = (TextView) view.findViewById(R.id.tvMemoryLocation);
        memoryDate = (TextView) view.findViewById(R.id.tvMemoryDate);
        memoryTitle = (TextView) view.findViewById(R.id.tvMemoryTitle);
        memoryDescription = (TextView) view.findViewById(R.id.tvMemoryDescription);
        memoryImage = (ImageView) view.findViewById(R.id.ivMemoryImage);

        memoryLocation.setText(getArguments().getString(MEMORY_LOCATION));
        memoryDate.setText(getArguments().getString(MEMORY_DATE));
        memoryTitle.setText(getArguments().getString(MEMORY_TITLE));
        memoryDescription.setText(getArguments().getString(MEMORY_DESCRIPTION));

        final String imageResource = getArguments().getString(MEMORY_IMAGE);
        if (imageResource.equals("none")) {
            memoryImage.setImageResource(R.drawable.ic_quick_memory);
        } else if(imageResource.equals("Big Memory")){
            memoryImage.setImageResource(R.drawable.description);
        }else if (imageResource.length() > bigMemory.length() || imageResource.length() > quickMemory.length()){
            f = new File(imageResource);
            Bitmap bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());
            memoryImage.setImageBitmap(bitmap);
            memoryImage.setRotation(memoryImage.getRotation() + -270);
            imageURL = f.getAbsolutePath().substring(19, f.getAbsolutePath().length());

            memoryImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String imageSDCard = "sdcard" + imageURL;
                    Toast.makeText(getActivity(), imageSDCard, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), ImageViewActivity.class);
                    intent.putExtra("imageResource", f.getAbsolutePath());
                    intent.putExtra(KeyID.TITLE.toString(), getArguments().getString(MEMORY_TITLE));
                    startActivity(intent);
                }
            });
        }else {
            memoryImage.setImageResource(R.drawable.icon);
        }


        ImageView buttonShare = (ImageView)view.findViewById(R.id.btnShareButton);
        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shareText = "Remember This... " + getArguments().getString(MEMORY_TITLE) + " it was on " + getArguments().getString(MEMORY_DATE);
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, shareText);
                startActivity(intent);
            }
        });
        return view;
    }


}
