package Draz.afinal;

import static android.Manifest.permission.READ_CONTACTS;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.icu.text.CaseMap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import Draz.afinal.data.MyMessage.MyMessages;


public class Add_Message_Activity extends AppCompatActivity {
    private static final int REQUEST_CONTACT = 1;
    private Button btnSave;
    private Button BTNCancel,btnpickcontact;
    private SeekBar skbrImportance;
    private TextInputEditText etTitle;
    private TextInputEditText etText;
    private TextInputEditText etContact_name ;
    private TextInputEditText et_Contactphone ;
    private EditText etDate ;
    private EditText etTime ;
    private Button datePickerButton;
    private TextView selectedDateTextView;
    private static final int REQUEST_READ_CONTACTS_PERMISSION = 0;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_message);
        btnSave = findViewById(R.id.btnSave);
        BTNCancel = findViewById(R.id.BTNCancel);
        skbrImportance = findViewById(R.id.skbrImportance);
        etTitle = findViewById(R.id.etTitle);
        etContact_name = findViewById(R.id.etContact_name);
        etText = findViewById(R.id.etText);
        et_Contactphone= findViewById(R.id.etContact_phone);
        etDate = findViewById(R.id.etDate);
         etTime =findViewById(R.id.etTime);
        datePickerButton = findViewById(R.id.datePickerButton);
        selectedDateTextView = findViewById(R.id.selectedDateTextView);
        calendar = Calendar.getInstance();

// Intent to pick contacts

        btnpickcontact= findViewById(R.id.btnpickcontact);

        btnpickcontact.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                if (hasContactsPermission() == false)
                    requestContactsPermission();
                else {
                    final Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                    startActivityForResult(pickContact, REQUEST_CONTACT);
                }
            }
        });
        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

    }
    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Update the Calendar instance with the selected date
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        // Display the selected date in the TextView
                        updateDateTextView();
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        // Set the minimum date to today's date
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    private void updateDateTextView() {
        // Format the selected date and display it in the TextView
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String selectedDate = sdf.format(calendar.getTime());
        selectedDateTextView.setText("Selected Date: " + selectedDate);
    }


    private boolean hasContactsPermission()
    {
        return ContextCompat.checkSelfPermission(this, READ_CONTACTS) == PackageManager.PERMISSION_GRANTED;
    }

    // Request contact permission if it
    // has not been granted already
    private void requestContactsPermission()
    {
        if (!hasContactsPermission())
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS_PERMISSION);
        }
    }
    public void onClickSave(View v) {

        checkMyMessage();

    }
    private void checkMyMessage()
    {

        boolean isAllOk=true; // يحوي نتيجة فحص الحقول ان كانت سليمة


        String text=etText.getText().toString();
        String contact_name=etContact_name.getText().toString();
        String contact_phone=et_Contactphone.getText().toString();
        String date = etDate.getText().toString();
        String time =etTime.getText().toString();
        String title = etTitle.getText().toString();


        int importancee=skbrImportance.getProgress();


        if (title.length()<1)
        {
            isAllOk=false;
            etTitle.setError("short title is empty");
        }

        if (text.length()<1)
        {
            isAllOk=false;
            etText.setError("text is empty");
        }
      if (contact_name.length()<1)
        {
            isAllOk=false;
           etContact_name.setError("contact_name is empty") ;

        }
      if (contact_phone.length()<1)
      {
          isAllOk=false;
          et_Contactphone.setError("contact_phone is empty");
      }
      if (date.length()<1)
      {
          isAllOk=false;
          etDate.setError("date is empty");
      }
      if (time.length()<1)
        {
            isAllOk=false;
            etTime.setError("time is empty");
        }
        if(title.length()<1)
        {
            isAllOk=false;
            etTitle.setError("title is empty");
        }
        if(isAllOk)
        {
                savemessage(text,contact_name,contact_phone,date,time,title);

        }


    }

    private void savemessage(String text, String contact_name, String contact_phone, String date, String time, String title) {
        //مؤشر لقاعدة البيانات
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        //استخراج الرقم المميز للمستعمل الذي سجل الدخول لاستعماله كاسم لل دوكيومينت
        String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        //بناء الكائن الذي سيتم حفظه
       MyMessages messages=new MyMessages();
       messages.setText(text);
        messages.setContact_name(contact_name);
        messages.setContact_phone(contact_phone);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");


        Date d=null ,t= null;
        try {
            d = formatter.parse(date);
            messages.setDate(d);
             t = dateFormat.parse(time);
            Calendar calendar=Calendar.getInstance();
            calendar.set(d.getYear(),d.getMonth(),d.getDate(),t.getHours(),t.getMinutes());
            //todo alarm maneger

            messages.setTitle(title);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String id = db.collection("MyUsers").document(uid).collection("messages").document().getId();
        messages.setMesjId(id);
        //اضافة كائن "لمجموعة" المستعملين ومعالج حدث لفحص   نجاح المطلوب
        // معالج حدث لفحص هل تم المطلوب من قاعدة البيانات
        db.collection("MyUsers").document(uid).collection("messages").document(id).set(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
            //داله معالجه الحدث
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                // هل تم تنفيذ المطلوب بنجاح
                if (task.isSuccessful()) {
                    Toast.makeText(Add_Message_Activity.this, "Succeeded to Add profile", Toast.LENGTH_SHORT).show();
                    //SaveUser_FB(email ,name,phone,password);
                    Intent i = new Intent(Add_Message_Activity.this, MainActivity.class);
                    startActivity(i);

                }
                else {
                    Toast.makeText(Add_Message_Activity.this,"Failed to Add Profile", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onClickCancel2(View v) {

        finish();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_READ_CONTACTS_PERMISSION && grantResults.length > 0)
        {
            final Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(pickContact, REQUEST_CONTACT);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) return;

        if (requestCode == REQUEST_CONTACT && data != null)
        {
            Uri contactUri = data.getData();
//,ContactsContract.CommonDataKinds.Phone.NUMBER
            // Specify which fields you want your
            // query to return values for
            String[] queryFields = new String[]{ContactsContract.Contacts.DISPLAY_NAME};

            // Perform your query - the contactUri
            // is like a "where" clause here
            Cursor cursor = this.getContentResolver().query(contactUri, queryFields, null, null, null);



            try
            {
                // Double-check that you
                // actually got results
                if (cursor.getCount() == 0) return;

                // Pull out the first column
                // of the first row of data
                // that is your contact's name
                cursor.moveToFirst();
                String name = cursor.getString(0);
                etContact_name.setText(name);
//
//                Cursor c = getContentResolver().query(contactUri, null, null, null, null);
//                if (cursor.getCount() == 0) return;
//                if (c.moveToFirst()) {
//                    int phoneIndex = c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
//                    String num = c.getString(phoneIndex);
//                    et_Contactphone.setText(num);
//                }
            }
            finally
            {
                cursor.close();
            }
        }
    }
}

