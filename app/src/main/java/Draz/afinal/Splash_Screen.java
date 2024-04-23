package Draz.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.logging.LogRecord;

public class Splash_Screen extends AppCompatActivity
{
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //456456456465
        setContentView(R.layout.activity_splash_screen);

        //start next activity (screen) automatically after period of time
        android.os.Handler h=new Handler();
        Runnable r=new Runnable() {
            @Override
            public void run()
            {
                //to open new activity from current to next activity
                Intent i= new Intent(Splash_Screen.this,  Sign_IN.class);
                startActivity(i);
                //to close current activity
                finish();
            }
        };
        h.postDelayed(r,3000);
    }



    @Override
    protected void onRestart()
    {
        super.onRestart();
        Log.d("SD","onRestart" );
        Toast.makeText(this, "onRestart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Log.d("SD","onResume" );
        Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        Log.d("SD","onPause" );
        Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Log.d("SD","onStop" );
        Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Log.d("SD","onDestroy" );
        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();

    }
    
}







