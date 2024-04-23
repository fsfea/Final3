package Draz.afinal.data.usersTable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
//Entity = Table =جدول
// عندما نريد ان نتعامل مع هذه الفئة كجدول معطيات

/**
 * *واجهة تحوي عمليات على قاعدة البيانات
 */
@Entity
public class MyUser {


        @PrimaryKey(autoGenerate = true)// تحديد الصفة كمفتاح رئيسي والذي ينتج بشكل تلقائي
        public long keyid;
    @ColumnInfo(name = "fullName")// اعطاء صفات جديد للعامود الصفة في الجدول
    public String fullName;
    public String email;// بحالة لم يتم اعطاء اسم للعامود يكون اسم الصفة اسم العامود
    public String phone;// رقم الهاتف
    public String passw;// الرقم السري
    public String key ;

    public long getKeyid() {
        return keyid;
    }

    public void setKeyid(long keyid) {
        this.keyid = keyid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassw() {
        return passw;
    }

    public void setPassw(String passw) {
        this.passw = passw;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "MyUser{" +
                "keyid=" + keyid +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", passw='" + passw + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}