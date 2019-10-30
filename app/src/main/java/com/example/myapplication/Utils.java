package com.example.myapplication;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class Utils {
    public static String titel ,auther,publicher,date,description,thumbnail,download;
    public static final String LOG_TAG=Utils.class.getSimpleName();



    public static ArrayList<DataClass>FetchBooksData(String requestUrl){
        URL url =createUrl(requestUrl);
        String jsonResponse = new String();
        try {
            jsonResponse=makeHttpRequest(url);

        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<DataClass>book=extractFeauterFromJson(jsonResponse);
        return book;

    }
    //create url
    public static URL createUrl(String strUrl){
     URL url = null;
     try {

         url = new URL(strUrl);

     } catch (MalformedURLException e) {
         e.printStackTrace();
     }
        return url;
    }

    public static String makeHttpRequest(URL url) throws IOException{

        String Jsonresponse = "";
        if (url==null){
            return Jsonresponse;
        }
        HttpURLConnection urlConnection =null;
        InputStream inputStream=null;
        try {
            urlConnection =(HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

                if (urlConnection.getResponseCode() == 200) {
                    inputStream = urlConnection.getInputStream();
                    Jsonresponse = ReadFromStream(inputStream);
                } else {
                    Log.e(LOG_TAG, "response code not 200");
                }

        } catch (IOException e) {
            e.printStackTrace();
            Log.e(LOG_TAG,"problem in json",e);
        }
        finally {
            if (urlConnection!=null){
                urlConnection.disconnect();
            }
            if (inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return Jsonresponse;
    }

    private static String ReadFromStream(InputStream inputStream)throws IOException {
        StringBuilder stringBuilder =new StringBuilder();
        if (inputStream!=null){

            InputStreamReader inputStreamReader=new InputStreamReader(inputStream, Charset.forName("UTF-8"));

            BufferedReader reader=new BufferedReader(inputStreamReader);
            String line =reader.readLine();
            while ( line!=null){
                stringBuilder.append(line);
                line=reader.readLine();
            }
        }

        return stringBuilder.toString();
    }

    private static ArrayList<DataClass> extractFeauterFromJson(String booksJson){
        if (TextUtils.isEmpty(booksJson)){
            return null;
        }
        ArrayList<DataClass> books=new ArrayList<>();
            try {
                JSONObject baseJsonResponse=new JSONObject(booksJson);
                JSONArray itemsArray =baseJsonResponse.getJSONArray("items");
                for (int i=0;i<itemsArray.length();i++){
                    JSONObject firstitem=itemsArray.getJSONObject(i);
                    JSONObject info =firstitem.getJSONObject("volumeInfo");
                    if (info.has("title")){
                        titel=info.getString("title");
                    }else {
                        titel="titel not found";
                    }
                    if (info.has("authors")){
                        auther=info.getJSONArray("authors").getString(0);
                    }else {
                        auther="auther not found";
                    }
                    if (info.has("publisher")){
                        publicher=info.getString("publisher");
                    }
                    else {
                        publicher="not found publisher";
                    }
                    if (info.has("publishedDate")){
                        date=info.getString("publishedDate");
                    }
                    else {
                        date="publicher data not found";
                    }
                    if (info.has("description")){
                        description=info.getString("description");
                    }
                    else {
                        description="description not found";
                    }
                    if (info.has("imageLinks")){
                        JSONObject image=info.getJSONObject("imageLinks");
                        thumbnail=image.getString("thumbnail");
                    }else {
                        thumbnail="";
                    }
                    if (info.has("canonicalVolumeLink")){
                        download=info.getString("canonicalVolumeLink");
                    }else {
                        download="https://www.google.com";
                    }
                    books.add(new DataClass(titel,auther,publicher,description,date,thumbnail,download));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        return books;
    }
}
