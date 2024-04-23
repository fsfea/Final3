package Draz.afinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

//import Draz.afinal.data.AppDatabase;
import Draz.afinal.data.MyMessage.MyMessages;
import Draz.afinal.data.MyMessage.MyMessagesQuery;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton fabAdd;
    private SearchView srchV;
    private Spinner spnrSubject;
    private ListView lstvTasks;
    TimePicker alarmTimePicker;
    PendingIntent pendingIntent;
    AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fabAdd = findViewById(R.id.fabAdd);
        srchV = findViewById(R.id.srchV);
        spnrSubject = findViewById(R.id.spnrSubject);
        lstvTasks = findViewById(R.id.lstvTasks);
//        alarmTimePicker =  findViewById(R.id.timePicker);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


        fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Add_Message_Activity.class);
                startActivity(i);

            }
        });
        spnrSubject = findViewById(R.id.spnrSubject);
        srchV = findViewById(R.id.srchV);
        lstvTasks = findViewById(R.id.lstvTasks);
        Log.d("draz", "onCreate");
        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.itmSettings) {
            Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
        }

        if (item.getItemId() == R.id.itmlogOut) {
            Toast.makeText(this, "LogOut", Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();
            finish();
        }
        return true;
    }

    public void showYesNoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Log out");
        builder.setMessage("Are you sure");
        AlertDialog dialog = builder.create();
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                Toast.makeText(MainActivity.this, "Signing out", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                Toast.makeText(MainActivity.this, "Signing out", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog1 = builder.create();
        dialog.show();
    }

    public void ShowPopUpMenu(View v, MyMessages item) {

        PopupMenu popup = new PopupMenu(this, v);
        popup.inflate(R.menu.popup_maim);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.itmAddTask) {
                    Toast.makeText(MainActivity.this, "Add", Toast.LENGTH_SHORT).show();
                    ;
                    Intent i = new Intent(MainActivity.this, Add_Message_Activity.class);
                    startActivity(i);
                }
                if (item.getItemId() == R.id.itmDelete) {
                    Toast.makeText(MainActivity.this, "Delete", Toast.LENGTH_SHORT).show();
                }
                if (item.getItemId() == R.id.itmEdit) {
                    Toast.makeText(MainActivity.this, "Edit", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        popup.show();
    }

    /**
     * عملية تجهيز السبنر بالمواضيع
     */

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("draz", "ez");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();

       // initAllListView();
    }

//    private void initAllListView() {
//        AppDatabase db = AppDatabase.getDB((getApplicationContext()));
//        MyMessagesQuery messagesQuery = db.getMyMessage();
//        List<MyMessages> allMesseages= messagesQuery.get();
//        ArrayAdapter<MyMessages> tsksAdapter = new ArrayAdapter<MyMessages>(this, android.R.layout.simple_dropdown_item_1line);
//        tsksAdapter.addAll(allMesseages);
//        lstvTasks.setAdapter((tsksAdapter));
//        lstvTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int i, long I) {
//                ShowPopUpMenu(view, tsksAdapter.getItem(i));
//            }
//        });
   // }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("EZ", "onpause");
        Toast.makeText(this, "onpause", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("EZ", "onstop");
        Toast.makeText(this, "onstop", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("EZ", "ondestroy");
        Toast.makeText(this, "ondestroy", Toast.LENGTH_SHORT).show();
    }
//    private void initListBySubjId(long key_id)
//    {
//        AppDatabase db =AppDatabase.getDB((getApplicationContext()));
//        MyMessagesQuery messagesQuery=db.getMyMessage();
//        List<MyMessages>allMessages=messagesQuery.get(key_id);
//        ArrayAdapter<MyMessages>messagesAdapter = new ArrayAdapter<MyMessages>(this, android.R.layout.simple_dropdown_item_1line);
//        messagesAdapter.addAll(allMessages);
//        lstvTasks.setAdapter((messagesAdapter));
//
//    }
}