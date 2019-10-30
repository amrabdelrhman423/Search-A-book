package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

public class Book extends AppCompatActivity {
    TextView titel;
    TextView description;
    TextView auther;
    Button Download;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        titel=findViewById(R.id.book_titel);
        auther=findViewById(R.id.book_auther);
        description=findViewById(R.id.description);
        imageView=findViewById(R.id.book);
        Download=findViewById(R.id.download);
        titel.setText( getIntent().getStringExtra("TITEL"));
        auther.setText(getIntent().getStringExtra("AUTHER"));
        description.setText(getIntent().getStringExtra("DESCRIPTION"));
        String imageURL = getIntent().getStringExtra("IMAGE");

        Glide.with(getApplicationContext())
                .load(imageURL)
                .into(imageView);

        Download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urldownload = getIntent().getStringExtra("DOWNLOAD");
                Intent i =new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(urldownload));
                startActivity(i);
            }
        });

    }
}
