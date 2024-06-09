package Draz.afinal.data.MyMessage;

import static android.Manifest.permission.CALL_PHONE;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import static androidx.core.app.ActivityCompat.requestPermissions;
import static androidx.core.content.PermissionChecker.checkSelfPermission;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.PermissionChecker;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import Draz.afinal.R;

/**
 * hkjlhjkhk
 */

public class MyMessageAdabter extends ArrayAdapter<MyMessages> {
    //המזהה של קובץ עיצוב הפריט
    private final int itemLayout;
MyMessages messages=new MyMessages();
    /**
     * פעולה בונה מתאם
     *
     * @param context  קישור להקשר (מסך- אקטיביטי)
     * @param resource עיצוב של פריט שיציג הנתונים של העצם
     */
    public MyMessageAdabter(@NonNull Context context, int resource) {
        super(context, resource);
        this.itemLayout = resource;
    }

    /**
     * בונה פריט גרפי אחד בהתאם לעיצוב והצגת נתוני העצם עליו
     *
     * @param position    מיקום הפריט החל מ 0
     * @param convertView
     * @param parent      רכיב האוסף שיכיל את הפריטים כמו listview
     * @return . פריט גרפי שמציג נתוני עצם אחד
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //בניית הפריט הגרפי מתו קובץ העיצוב
        View vitem = convertView;
        if (vitem == null)
            vitem = LayoutInflater.from(getContext()).inflate(itemLayout, parent, false);
        //קבלת הפניות לרכיבים בקובץ העיצוב
        ImageView imageView = vitem.findViewById(R.id.imgVitm);
        TextView tvTitle = vitem.findViewById(R.id.tvitm_Title);
        TextView tvText = vitem.findViewById(R.id.tvitm_Text);
        TextView tvImportance = vitem.findViewById(R.id.tvitmimportance);
        ImageButton btnSendSMS = vitem.findViewById(R.id.imgBtnSendSmsitm);
        ImageView btnwhtapp = vitem.findViewById(R.id.btnWhats);
        ImageButton btnCall = vitem.findViewById(R.id.imgBtnCallitm);
        ImageButton btnDel = vitem.findViewById(R.id.imgBtnDeleteitm);
        //קבלת הנתון (עצם) הנוכחי
        MyMessages current = getItem(position);
        //הצגת הנתונים על שדות הרכיב הגרפי
        tvTitle.setText(current.getTitle());
        tvText.setText(current.getText());
        tvImportance.setText("Importance:" + current.getImportance());

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delMyMessagesFromDB_FB(messages);
            }
        });

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callAPhoneNumber(current.phone);
            }
        });

        btnSendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSendSmsApp(current.getText(), current.phone);
            }
        });


        btnwhtapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSendWhatsAppV2("msg to : ", current.getPhone());
            }
        });
        return vitem;



    }

    /**
     * מחיקת פריט כולל התמונה מבסיס הנתונים
     * @param myMessages הפריט שמוחקים
     */
    private void delMyMessagesFromDB_FB(MyMessages myMessages)
    {
        //הפנייה/כתובת  הפריט שרוצים למחוק
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("MyUsers").document(myMessages.getUid()).

                delete().//מאזין אם המחיקה בוצעה
                addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    remove(myMessages);// מוחקים מהמתאם
                    Toast.makeText(getContext(), "deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    /**
     * @param phone מספר טלפון שרוצים להתקשר אליו
     */
    private void callAPhoneNumber(String phone) {
        //בדיקה אם יש הרשאה לביצוע שיחה
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//בדיקת גרסאות
            //בדיקה אם ההרשאה לא אושרה בעבר
            if (checkSelfPermission(getContext(), CALL_PHONE) == PermissionChecker.PERMISSION_DENIED) {
                //רשימת ההרשאות שרוצים לבקש אישור
                String[] permissions = {CALL_PHONE};
                //בקשת אישור הרשאות (שולחים קוד הבקשה)
                //התשובה תתקבל בפעולה onRequestPermissionsResult
                requestPermissions((Activity) getContext(), permissions, 100);
            } else {
                //אינטנט מרומז לפתיחת אפליקצית ההודות סמס
                Intent phone_intent = new Intent(Intent.ACTION_CALL);
                phone_intent.setData(Uri.parse("tel:" + phone));
                getContext().startActivity(phone_intent);


            }
        }

    }
    public void openSendSmsApp(String msg, String phone) {
        //אינטנט מרומז לפתיחת אפליקצית ההודות סמס
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
        //מעבירים מספר הטלפון
        smsIntent.setData(Uri.parse("smsto:" + phone));
        //ההודעה שנרצה שתופיע באפליקצית ה סמס
        smsIntent.putExtra("sms_body", msg);
        smsIntent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        smsIntent.addCategory(Intent.CATEGORY_DEFAULT);
        //פתיחת אפליקציית ה סמס
        getContext().startActivity(smsIntent);
    }
    /**
     * פתיחת אפליקצית שליחת whatsapp
     *
     * @param msg   .. ההודעה שרוצים לשלוח
     * @param phone
     */
    public void openSendWhatsAppV2(String msg, String phone) {
        //אינטנט מרומז לפתיחת אפליקצית ההודות סמס
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        ;
        String url = null;
        try {
            url = "https://api.whatsapp.com/send?phone=" + phone + "&text=" + URLEncoder.encode(msg, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            //throw new RuntimeException(e);
            e.printStackTrace();
            Toast.makeText(getContext(), "there is no whatsapp!!", Toast.LENGTH_SHORT).show();
        }
        sendIntent.setData(Uri.parse(url));
        sendIntent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        sendIntent.addCategory(Intent.CATEGORY_DEFAULT);
        //פתיחת אפליקציית ה סמס
        getContext().startActivity(sendIntent);
    }
}

