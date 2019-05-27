package com.example.nsyawalia.banany_v21;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Build.VERSION;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;


public class MainActivity extends AppCompatActivity {
    public static Toolbar myToolbar;
    public Button ConnectButton;
    public int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 0;
    AddServerActivity activityAddServer;
    public GridView gridView;
    FtpDbHelper helper = new FtpDbHelper(this);
    ArrayList<String> serverList = new ArrayList();

    //Class untuk si pemanggilan logo
    private static int SPLASH_TIME_OUT = 4000;

    @Override
    protected void onStart(Bundle savedInstanceState) {
        super.onStart(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(MainActivity.this, StartedActivity.class);
                startActivity(homeIntent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    // Buat si Class C03211
    class C03211 implements OnClickListener{
        C03211(){

        }

        public void onClick(View v){
            Intent intent = new Intent(MainActivity.this, ClientActivity.class);
            MainActivity.this.helper.InsertServer("BANANY", "192.168.4.1", 21, "pi", "banany", "/storage/emulated/0/", "/media/pi");
            MainActivity.this.setExtras(intent, "BANANY");
            MainActivity.this.startActivity(intent);

        }
    }


    public static void deleteCache(Context context){
        try {
            deleteDir(context.getCacheDir());
        } catch (Exception e){

        }
    }

    // Class Hapus si directory

    public static boolean deleteDir(File dir){
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for(String file : children){
                if(!deleteDir(new File(dir, file))){
                    return false;
                }
            }
            return dir.delete();
        } else if (dir == null || !dir.isFile()){
            return false;
        } else {
            return dir.delete()
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(C0322R.menu.main_action_bar, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() != C0322R.id.about) {
            return false;
        }

        Dialog dialog = new Dialog(this);
        dialog.setTitle("About...");
        dialog.setContentView(C0322R.layout.about_dialog);
        dialog.show();
        return true;
    }

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView((int) C0322R.layout.activity_main2);
        myToolbar = (Toolbar) findViewById(C0322R.id.mainToolbar);
        setSupportActionBar((myToolbar);
        getSupportActionBar().setIcon((int) C0322R.mipmap.ic_laucher);
        getPermissions();
        ((Button) findViewById(C0322R.id.ConnectButton)).setOnClickListener(new C03211());
    }

    protected void onResume(){
        super.onResume();
        this.serverList = this.helper.getAllServers();
        LinearLayout linearLayout1 = (LinearLayout) findViewById(C0322R.id.noitemsLinearLayout);
        LinearLayout linearLayout2 = (LinearLayout) findViewById(C0322R.id.placeholderLinearLayout);
        if (this.serverList.size() == 0) {
            linearLayout1.setVisibility(0);
            linearLayout2.setVisibility(8);
        } else {
            linearLayout1.setVisibility(8);
            linearLayout2.setVisibility(0);
        }
        Iterator it = this.serverList.iterator();
        while (it.hasNext()){
            String i = (String) it.next();
            this.gridView.setAdapter(new CustomGridAdapter(this, this.serverList));

        }
    }

    public void setExtras(Intent intent, String serverName){
        Cursor cursor = this.helper.getServerDetails(serverName);
        cursor.moveToFirst();
        intent.putExtra("SERVER_NAME", cursor.getString(cursor.getColumnIndex(FtpDbHelper.COLUMN_SERVER_NAME)));
        intent.putExtra("HOST_NAME", cursor.getString(cursor.getColumnIndex(FtpDbHelper.COLUMN_HOST_NAME)));
        intent.putExtra("PORT", cursor.getInt(cursor.getColumnIndex(FtpDbHelper.COLUMN_PORT)));
        intent.putExtra("USERNAME", cursor.getString(cursor.getColumnIndex(FtpDbHelper.COLUMN_USERNAME)));
        intent.putExtra("PASSWORD", cursor.getString(cursor.getColumnIndex(FtpDbHelper.COLUMN.PASSWORD)));
        intent.putExtra("LOCAL_DIRECTORY", cursor.getString(cursor.getColumnIndex(FtpDbHelper.COLUMN_LOCAL_DIR)));
        intent.putExtra("REMOTE_DIRECTORY", cursor.getString(cursor.getColumnIndex(FtpDbHelper.COLUMN_REMOTE_DIR)));
    }

    public void getPermissions(){
        if(ContextCompat.checkSelfPermission(this,"android.permission.READ_EXTERNAL_STORAGE") != 0 && VERSION.SDK_INT >= 16) {
            Log.e("w4llsk1", "Permission Request Initiated");
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, this.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        }
    }



}
