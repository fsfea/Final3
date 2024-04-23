package Draz.afinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

//import Draz.afinal.data.AppDatabase;


public class Sign_IN extends AppCompatActivity {
    private TextInputEditText etShortTitle;
    private TextInputEditText etPassword;
    private Button btnSignUP;
    private Button btnsignin;

    @SuppressLint ( "MissingInflatedId" )
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        if(FirebaseAuth.getInstance().getCurrentUser()!=null)
        {
            Intent i =new Intent(Sign_IN.this,MainActivity.class);
            startActivity(i);
        }
        etShortTitle = findViewById(R.id.etContact_phone);
        etPassword = findViewById(R.id.etText);
        btnSignUP = findViewById(R.id.btnSignUP);
        btnsignin = findViewById(R.id.btnsignin);

    }

    public void onClickSignUp(View v) {
        Intent i = new Intent(Sign_IN.this, Sign_Up.class);
        startActivity(i);
        finish();
    }

    private void checkEmailPassw_FB() {
        boolean isAllOK = true;//يحوي نتيجة فحص الحقول ان كانت سليمة
        //استخراج النص من الحقل ايميلة
        String email = etShortTitle.getText().toString();
        //استخراج نص من كلمة المرور
        String password = etPassword.getText().toString();
        //فحص الايميل ان كان طوله اقل من 6 او لا يحوي @ فهو خطأ
        if (email.length() < 6 | email.contains("@") == false) {
            //تعديل المتغير ليدل على ان الفحص يعطي حقل بريد
            isAllOK = false;
//عرض ملاحظة خطأ على الشاشاة داخل حقل البريد
            etShortTitle.setError("Wrong Email");
        }
        if (password.length() < 8 || password.contains(" ") == true) {

            isAllOK = false;
            etPassword.setError("Wrong Password");
        }

        if (isAllOK) {
            //עצם לביצוע רישום كائن لعملية التسجيل
            FirebaseAuth auth = FirebaseAuth.getInstance();
            //כניסה לחשבון בעזרת מיל ן סיסמא
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override//התגובה שמתקבל הניסיון הרישום בענן
                public void onComplete(@NonNull Task<AuthResult> task) {//הפרמטר מכיל מידע מהשרת על תוצאת הבקשה לרישום
                    if (task.isSuccessful()) {//אם הפעולה הצליחה
                        Toast.makeText(Sign_IN.this, "Signing in Succeeded", Toast.LENGTH_SHORT).show();
                        //מעבד למסך הראשי
                        Intent i = new Intent(Sign_IN.this, MainActivity.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(Sign_IN.this, "Signing in Faild", Toast.LENGTH_SHORT).show();
                        etShortTitle.setError(task.getException().getMessage());//  הצגת הודעת השגיאה שהקבלה מהענן
                    }

                }
            });
        }
    }
        public void onClickSign_In (View v)
        {
            checkEmailPassw_FB();
        }
    }
