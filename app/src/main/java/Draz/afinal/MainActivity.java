package Draz.afinal;

import static android.Manifest.permission.SCHEDULE_EXACT_ALARM;
import static android.Manifest.permission.SEND_SMS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.widget.TimePicker;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

//import Draz.afinal.data.AppDatabase;
import java.util.ArrayList;

import Draz.afinal.data.MyMessage.MyMessageAdabter;
import Draz.afinal.data.MyMessage.MyMessages;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton fabAdd;
    private SearchView srchV;
    private Spinner spnrSubject;
    private ListView lstvMsg;
    private MyMessageAdabter messageAdabter;
    private static final int PERMISSION_REQUEST_CODE = 1;


    TimePicker alarmTimePicker;
    PendingIntent pendingIntent;
    AlarmManager alarmManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fabAdd = findViewById(R.id.fabAdd);
        srchV = findViewById(R.id.srchV);//הפניה לרכיב הגרפי שמציג אוסף
        messageAdabter = new MyMessageAdabter(this,R.layout.mymessage_item_layout);//בניית המתאם
        lstvMsg = findViewById(R.id.lstvMsg);
        lstvMsg.setAdapter(messageAdabter);//קישור המתאם אם המציג הגרפי לאוסף
        spnrSubject = findViewById(R.id.spnrSubject);
      //  alarmTimePicker =  findViewById(R.id.alarmTimePicker);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Add_Message_Activity.class);
                startActivity(i);

            }
        });
        // Check for SMS permission
        if (ContextCompat.checkSelfPermission(this,SEND_SMS) != PackageManager.PERMISSION_GRANTED ) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this, new String[]{SEND_SMS}, PERMISSION_REQUEST_CODE);
        }
        if (ContextCompat.checkSelfPermission(this,SCHEDULE_EXACT_ALARM) != PackageManager.PERMISSION_GRANTED ) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this, new String[]{SCHEDULE_EXACT_ALARM}, PERMISSION_REQUEST_CODE);
        }
        spnrSubject = findViewById(R.id.spnrSubject);
        srchV = findViewById(R.id.srchV);
        lstvMsg = findViewById(R.id.lstvMsg);
        Log.d("draz", "onCreate");
        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();
    }

    /**
     *  קריאת נתונים ממסד הנתונים firestore
     * @return .... רשימת הנתונים שנקראה ממסד הנתונים
     */
    /**
     *  קריאת נתונים ממסד הנתונים firestore
     * @return .... רשימת הנתונים שנקראה ממסד הנתונים
     */
    /**
     *  קריאת נתונים ממסד הנתונים firestore
     * @return .... רשימת הנתונים שנקראה ממסד הנתונים
     */
    public void readMessagesFrom_FB()
    {
        //استخراج الرقم المميز للمستعمل الذي سجل الدخول لاستعماله كاسم لل دوكيومينت
        String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        //בניית רשימה ריקה
        ArrayList<MyMessages> arrayList =new ArrayList<>();
        //קבלת הפנייה למסד הנתונים
        FirebaseFirestore ffRef = FirebaseFirestore.getInstance();
        //קישור לקבוצה collection שרוצים לקרוא
        ffRef.collection("MyUsers").document(uid).collection("messages").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    /**
                     * תגובה לאירוע השלמת קריאת הנתונים
                     * @param task הנתונים שהתקבלו מענן מסד הנתונים
                     */
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {// אם בקשת הנתונים התקבלה בהצלחה
                            //מעבר על כל ה״מסמכים״= עצמים והוספתם למבנה הנתונים
                            for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                //המרת העצם לטיפוס שלו// הוספת העצם למבנה הנתונים
                                arrayList.add(document.toObject(MyMessages.class));
                            }
                            messageAdabter.clear();//ניקוי המתאם מכל הנתונים
                            messageAdabter.addAll(arrayList);//הוספת כל הנתונים למתאם
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Error Reading data"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }




    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.edit_message) {
            Toast.makeText(this, "Edit_message", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(MainActivity.this, Edit_Message_Activity.class);
            startActivity(i);
        }
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
                if (item.getItemId() == R.id.edit_message) {
                    Toast.makeText(MainActivity.this, "Add", Toast.LENGTH_SHORT).show();
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
        readMessagesFrom_FB();

    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d("EZ", "onpause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("EZ", "onstop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("EZ", "ondestroy");
    }
    // Handle permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
            } else {
                // Permission denied
                Toast.makeText(getApplicationContext(), "Permission denied. Can't send SMS.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}