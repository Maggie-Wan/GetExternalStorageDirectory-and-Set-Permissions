package com.example.student.ExternalWriteandRead;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {
    final int REQUEST_EXTERNAL_STORAGE=123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    public void outWrite(View view) {
        String path = getExternalFilesDir("file").getAbsolutePath();
        Log.d("path","read:"+path);
        String fname=path + File.separator + "test1.txt";
        try {
            FileWriter fw = new FileWriter(fname);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("happy to find you");
            bw.newLine();
            bw.write("it is sad");
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void outRead(View view) {
        String path = getExternalFilesDir("file").getAbsolutePath();
        Log.d("path","read:"+path);
        String fname=path + File.separator + "test1.txt";
        try {
            FileReader fr = new FileReader(fname);
            BufferedReader br = new BufferedReader(fr);
            String str;
            while ((str=br.readLine()) != null)
            {
                Log.d("FNAME", "Read:" + str);
            }
            br.close();
            fr.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void nolimitwrite(View view) {
        int permission = ActivityCompat.checkSelfPermission(this,
                WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            //未取得權限，向使用者要求允許權
            ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE},
                    REQUEST_EXTERNAL_STORAGE); //因為要辨別使用者允許的是寫入還是讀取所以這邊的string只放write
        }else{
            //已有權限，可進行檔案存取
            sdwrite();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==REQUEST_EXTERNAL_STORAGE){
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //取得權限，進行檔案存取
                String str=permissions[0];
                if(str.equals("android.permission.WRITE_EXTERNAL_STORAGE") ){  //這邊用if判斷時要用equals()，不能用==
                    sdwrite();
                }else {
                    sdread();
                }
                //http://sweeteason.pixnet.net/blog/post/33710656-java-%E8%B6%85%E7%B4%9A%E6%96%B0%E6%89%8B%E5%AD%B8%E7%BF%92%E7%AD%86%E8%A8%98---%3D%3D-%26-equals
                //Log.d("permission","read:"+permissions[0]);
                //Log.d("permission","read:"+permissions[1]);

            } else {
                //使用者拒絕權限，停用檔案存取功能
            }
        }
    }

    private void sdwrite(){
        File f = Environment.getExternalStorageDirectory();
        Log.d("FNAME","read"+f.getAbsolutePath());
        File f2 = new File(f.getAbsolutePath() + File.separator + "mydata");
        f2.mkdir();
        File txtFile = new File(f2.getAbsolutePath() + File.separator + "data5.txt");
        try {
            FileWriter fw = new FileWriter(txtFile);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("this is test");
            bw.newLine();
            bw.write("This is test2");
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void nolimitread(View view) {
        int permission = ActivityCompat.checkSelfPermission(this,
                READ_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            //未取得權限，向使用者要求允許權
            ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE},
                    REQUEST_EXTERNAL_STORAGE);
        }else{
            //已有權限，可進行檔案存取
            sdread();
        }
    }

    private void sdread(){
        File f = Environment.getExternalStorageDirectory();
        Log.d("FNAME", f.getAbsolutePath());
        File f2 = new File(f.getAbsolutePath() + File.separator + "mydata");
        File txtFile = new File(f2.getAbsolutePath() + File.separator + "data5.txt");
        try {
            FileReader fr = new FileReader(txtFile);
            BufferedReader br = new BufferedReader(fr);
            String str;
            while ((str=br.readLine()) != null)
            {
                Log.d("FNAME", "Read:" + str);
            }
            br.close();
            fr.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

