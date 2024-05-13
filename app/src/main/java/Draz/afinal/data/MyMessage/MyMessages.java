package Draz.afinal.data.MyMessage;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Time;
import java.util.Date;

@Entity
public class MyMessages {
    @PrimaryKey(autoGenerate = true)
    public long keyid;// رقم المهمة
    public String title;//عنوان الرسالة
    public int importance;//درجة الاهمية 1-5
    public String Text;//نص الرسالة
    public String contact_name;// اسماء جهات الاتصال
    public String  contact_phone;//رقم الهاتف
    public Date date;
    public int y;//السنة
    public int month;//الشهر
    public int d;//اليوم
    public Time time;
    public double h;//الساعات
    public double m;//الدقائق
    public boolean isCompleted;//هل تمت الرسالة
    public String mesjId;//رقم  الرسالة
    public long getKeyid() {
        return keyid;
    }

    @Override
    public String toString() {
        return "MyMessages{" +
                "keyid=" + keyid +
                ", title='" + title + '\'' +
                ", importance=" + importance +
                ", Text='" + Text + '\'' +
                ", contact_name='" + contact_name + '\'' +
                ", contact_phone='" + contact_phone + '\'' +
                ", date=" + date +
                ", y=" + y +
                ", month=" + month +
                ", d=" + d +
                ", time=" + time +
                ", h=" + h +
                ", m=" + m +
                ", isCompleted=" + isCompleted +
                ", mesjId='" + mesjId + '\'' +
                '}';
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
        return Text;
    }

    public void setText(String text) {
        Text = text;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getD() {
        return d;
    }

    public void setD(int d) {
        this.d = d;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public double getM() {
        return m;
    }

    public void setM(double m) {
        this.m = m;
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
}
