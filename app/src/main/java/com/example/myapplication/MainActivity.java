package com.example.myapplication;



import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText titel;
    Button search;
    RecyclerView listView;
    ImageView imageView;
    ProgressBar bar;
    Adapter adapter;
    String book_title;
    String GOOGLE_BOOKS_API;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titel=findViewById(R.id.field_titel);
        search =findViewById(R.id.search_btn);
        listView=findViewById(R.id.list_view);
        imageView=findViewById(R.id.imageView);
        bar=findViewById(R.id.progress_horizontal);
        bar.setVisibility(View.INVISIBLE);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                book_title =titel.getText().toString();
                if (book_title.length()==0){
                    Toast.makeText(getApplicationContext(), "Please Enter Book Titel", Toast.LENGTH_SHORT).show();
                }else {
                    imageView.setVisibility(View.GONE);
                    GOOGLE_BOOKS_API=
                            "https://www.googleapis.com/books/v1/volumes?q="+book_title;
                    books books=new books();
                    books.execute(GOOGLE_BOOKS_API);

                    InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,0);

                    titel.setText("");

                }
            }
        });

    }
    public class books extends AsyncTask<String,Void,ArrayList<DataClass>>{

        @Override
        protected ArrayList<DataClass> doInBackground(String... strings) {
            if(strings.length<1||strings[0]==null){
                return null;
            }
            ArrayList<DataClass>books=Utils.FetchBooksData(strings[0]);
            return books;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            bar.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPostExecute(final ArrayList<DataClass> dataClasses) {
            if (dataClasses !=null&& !dataClasses.isEmpty()){
                bar.setVisibility(View.INVISIBLE);
                adapter=new Adapter(getApplicationContext(),dataClasses);
                listView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                listView.setAdapter(adapter);
                adapter.setItemListener(new Adapter.OnClickItemListener() {
                    @Override
                    public void OnItemClick(int position) {
                        Intent intent =new Intent(getApplicationContext(),Book.class);
                        DataClass dataClass = dataClasses.get(position);
                        intent.putExtra("DESCRIPTION",dataClass.description);
                        intent.putExtra("TITEL",dataClass.titel);
                        intent.putExtra("AUTHER",dataClass.auther);
                        intent.putExtra("IMAGE",dataClass.imageUrl);
                        intent.putExtra("DOWNLOAD",dataClass.download);
                        startActivity(intent);
                    }
                });

            }
        }
    }

}
