package Draz.afinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
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
        lstvMsg.setAdapter(messageAdabter);//קישור המתאם אם המציג הגרפי לאוסף
        spnrSubject = findViewById(R.id.spnrSubject);
        lstvMsg = findViewById(R.id.lstvMsg);
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
    public ArrayList<MyMessages> readMessageFrom_FB()
    {
        //בניית רשימה ריקה
        ArrayList<MyMessages> arrayList =new ArrayList<>();
        //קבלת הפנייה למסד הנתונים
        FirebaseFirestore ffRef = FirebaseFirestore.getInstance();
        //קישור לקבוצה לקבוצה שרוצים לקרוא
        ffRef.collection("MyUsers").
                document(FirebaseAuth.getInstance().getUid()).
                collection("subjects").
                document(spnrSubject.getSelectedItem().toString()).
                //הוספת מאזין לקריאת הנתונים
                        collection("Tasks").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Message<QuerySnapshot> message) {

                    }

                    /**
                     * תגובה לאירוע השלמת קריאת הנתונים
                     * @param Message הנתונים שהתקבלו מענן מסד הנתונים
                     */
                    @Override
                    public void onComplete(@NonNull Message<QuerySnapshot> Message) {
                        if(Message.isSuccessful())// אם בקשת הנתונים התקבלה בהצלחה
                            //מעבר על כל ה״מסמכים״= עצמים והוספתם למבנה הנתונים
                            for (DocumentSnapshot document : Message.getResult().getDocuments())
                            {
                                //המרת העצם לטיפוס שלו// הוספת העצם למבנה הנתונים
                                arrayList.add(document.toObject(MyMessages.class));
                            }
                        else{
                            Toast.makeText(MainActivity.this, "Error Reading data"+Message.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        return arrayList;
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