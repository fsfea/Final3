package Draz.afinal.data.MyMessage;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import Draz.afinal.R;


public class MyMessageAdabter extends ArrayAdapter<MyMessages> {
    //המזהה של קובץ עיצוב הפריט
    private final int itemLayout;

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
        View vitem= convertView;
        if(vitem==null)
            vitem= LayoutInflater.from(getContext()).inflate(itemLayout,parent,false);
        //קבלת הפניות לרכיבים בקובץ העיצוב
        ImageView imageView=vitem.findViewById(R.id.imgVitm);
        TextView tvTitle=vitem.findViewById(R.id.tvitm_Title);
        TextView tvText=vitem.findViewById(R.id.tvitm_Text);
        TextView tvImportance=vitem.findViewById(R.id.tvitmimportance);
        ImageButton btnSendSMS=vitem.findViewById(R.id.imgBtnSendSmsitm);
        ImageButton btnEdit=vitem.findViewById(R.id.imgBtnEdititm);
        ImageButton btnCall=vitem.findViewById(R.id.imgBtnCallitm);
        ImageButton btnDel=vitem.findViewById(R.id.imgBtnDeleteitm);
        //קבלת הנתון (עצם) הנוכחי
        MyMessages current=getItem(position);
        //הצגת הנתונים על שדות הרכיב הגרפי
        tvTitle.setText(current.getTitle());
        tvText.setText(current.getText());
        tvImportance.setText("Importance:"+current.getImportance());



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
        db.collection("MyUsers").
                document(myMessages.getUid()).
                collection("Messages").document(myMessages.mesjId).
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



}

