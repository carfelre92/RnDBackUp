package com.example.wlsgns.rnd_jin;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UploadPhoto extends AppCompatActivity implements View.OnClickListener {

    Button buttonSave,buttonLoad;
    TextView textView;
    ListView listView1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_photo);

        buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonLoad = (Button) findViewById(R.id.buttonLoad);
        textView = (TextView) findViewById(R.id.textView);
        listView1 = (ListView) findViewById(R.id.listView1);

        //Initializing listener to buttons
        buttonSave.setOnClickListener(this);
        buttonLoad.setOnClickListener(this);

        File mydir = new File("data/data/com.example.wlsgns.rnd_jin/saveFolder"); //Creating an internal dir;
        if (!mydir.exists())
        {
            mydir.mkdirs();
        } else {
            Toast.makeText(getApplicationContext(), "The folder "+mydir.toString()+" exist" , Toast.LENGTH_SHORT).show();
        }

    }



    public void fileSave(){
        String path = "data/data/com.example.wlsgns.rnd_jin/saveFolder";

        String time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String foodName = "apple";
        String string = "Hello world!";
        String filename = time + "_" +foodName;
        FileOutputStream fos;

        try {
            File file = new File(path,filename+".txt");

            fos = new FileOutputStream(file);
            fos.write(string.getBytes());
            fos.close();
            Toast.makeText(getApplicationContext(), "File name with "+filename+" has been saved", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fetchFiles(){
        //textView.setText("");

        String path = "data/data/com.example.wlsgns.rnd_jin/saveFolder";
        File directory = new File(path);
        File[] files = directory.listFiles();

        List<String> filesNameList = new ArrayList<>();

        for(int i=0; i<files.length; i++){
            filesNameList.add(files[i].getName());
            //textView.append(files[i].getName()+"\n");
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, filesNameList);

        listView1.setAdapter(arrayAdapter);


    }

    public void fetchFile(){
        try{
            String msg;
            FileInputStream fis = openFileInput("myfile");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuffer sb = new StringBuffer();

            while((msg=br.readLine())!=null){
                sb.append(msg+"\n");
            }

            Toast.makeText(getApplicationContext(), sb.toString(), Toast.LENGTH_SHORT).show();


        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void printFiles(){
        String path = "data/data/com.example.wlsgns.rnd_jin";
    }

    @Override
    public void onClick(View view) {
        if(view == buttonSave){
            fileSave();
        }
        if(view == buttonLoad){
            fetchFiles();
        }

    }
}
