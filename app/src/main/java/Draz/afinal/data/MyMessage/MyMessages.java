package Draz.afinal.data.MyMessage;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

@Entity
public class MyMessages implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public long keyid;// رقم المهمة
    public String title;//عنوان الرسالة
    public int importance;//درجة الاهمية 1-5
    public String text;//نص الرسالة
    public String contact_name;// اسماء جهات الاتصال
    public String  contact_phone;//رقم الهاتف
    public long time ;//الوفت(الساعة/الدقائق)
    public boolean isCompleted;//هل تمت الرسالة
    public String mesjId;//رقم  الرسالة
    private String uid;
    public String phone;//رقم الهاتف
    public long getKeyid() {
        return keyid;
    }

    public void setKeyid(long keyid) {
        this.keyid = keyid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getContact_phone() {
        return contact_phone;
    }

    public void setContact_phone(String contact_phone) {
        this.contact_phone = contact_phone;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String getMesjId() {
        return mesjId;
    }

    public void setMesjId(String mesjId) {
        this.mesjId = mesjId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "MyMessages{" +
                "keyid=" + keyid +
                ", title='" + title + '\'' +
                ", importance=" + importance +
                ", text='" + text + '\'' +
                ", contact_name='" + contact_name + '\'' +
                ", contact_phone='" + contact_phone + '\'' +
                ", time=" + time +
                ", isCompleted=" + isCompleted +
                ", mesjId='" + mesjId + '\'' +
                ", uid='" + uid + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}