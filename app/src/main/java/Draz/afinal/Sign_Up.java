package Draz.afinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import Draz.afinal.data.usersTable.MyUser;

public class Sign_Up extends AppCompatActivity
{
    private Button btnUpdate;
    private Button BTNCancel;
    private TextInputEditText etname;
    private TextInputEditText etphone;
    private TextInputEditText etShortTitle;
    private TextInputEditText etPassword;
    private TextInputEditText etre_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        btnUpdate = findViewById(R.id.btnSave);
        BTNCancel = findViewById(R.id.BTNCancel);
        etname = findViewById(R.id.etname);
        etphone = findViewById(R.id.etphone);
        etShortTitle = findViewById(R.id.etContact_phone);
        etPassword = findViewById(R.id.etText);
        etre_password = findViewById(R.id.etre_password);

    }

    public void onClickCancel(View v) {

        finish();
    }

    private void checkEmailPassw_FB() {
        boolean isAllOK = true;
        String email = etShortTitle.getText().toString();
        String password = etPassword.getText().toString();
        String re_password = etre_password.getText().toString();
        String name = etname.getText().toString();
        String phone = etphone.getText().toString();

        if (email.length() < 6 | email.contains("@") == false) {
            isAllOK = false;

            etShortTitle.setError("Wrong Email");
        }
        if (password.length() < 8 || password.contains(" ") == true) {

            isAllOK = false;
            etPassword.setError("Wrong Password");
        }
        if (re_password.length() < 8 || re_password.contains(" ") == true) {

            isAllOK = false;
            etre_password.setError("Wrong re_Password");
        }
        if (name.length() < 3 || name.contains(" ") == true) {

            isAllOK = false;
            etname.setError("Wrong name");
        }
        if (phone.length() < 10 || phone.contains(" ") == true) {

            isAllOK = false;
            etphone.setError("Wrong phone");
        }


        if(isAllOK)
        {
            //עצם לביצוע רישום كائن لعملية التسجيل
            FirebaseAuth auth=FirebaseAuth.getInstance();
            //יצירת לחשבון בעזרת מיל ן סיסמא
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override//התגובה שמתקבל הניסיון הרישום בענן
                public void onComplete(@NonNull Task<AuthResult> task) {//הפרמטר מכיל מידע מהשרת על תוצאת הבקשה לרישום
                    if (task.isSuccessful()) {//אם הפעולה הצליחה
                        Toast.makeText(Sign_Up.this, "Signing up Succeeded", Toast.LENGTH_SHORT).show();

                        SaveUser_FB(email,name,password,phone);
                    } else {
                        Toast.makeText(Sign_Up.this, "Signing up Faild", Toast.LENGTH_SHORT).show();
                        etShortTitle.setError(task.getException().getMessage());//הצגת הודעת השגיאה שהקבלה מהענן
                    }
                }
            });
        }
    }

    public void onClickSignUp (View v)
    {
        checkEmailPassw_FB();


    }
    private void SaveUser_FB(String email, String name, String phone,String password ){
        //مؤشر لقاعدة البيانات
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        //استخراج الرقم المميز للمستعمل الذي سجل الدخول لاستعماله كاسم لل دوكيومينت
        String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        //بناء الكائن الذي سيتم حفظه
        MyUser user=new MyUser();
        user.setEmail(email);
        user.setFullName(name);
        user.setPhone(phone);
        user.setPassw(password);
        user.setKey(uid);
        ;
        //اضافة كائن "لمجموعة" المستعملين ومعالج حدث لفحص   نجاح المطلوب
        // معالج حدث لفحص هل تم المطلوب من قاعدة البيانات
        db.collection("MyUsers").document(uid).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            //داله معالجه الحدث
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                // هل تم تنفيذ المطلوب بنجاح
                if (task.isSuccessful()) {
                    Toast.makeText(Sign_Up.this, "Succeeded to Add profile", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Sign_Up.this, Sign_IN.class);
                    startActivity(i);

                }
                else {
                    Toast.makeText(Sign_Up.this,"Failed to Add Profile", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
