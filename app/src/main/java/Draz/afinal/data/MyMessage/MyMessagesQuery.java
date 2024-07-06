package Draz.afinal.data.MyMessage;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao//واجهة استعلامات على جدول المعطيات
public interface MyMessagesQuery {
    public interface MyTasksQuery {
        /**
         * اعادة جميع معطيات جدول الرسائل
         *
         * @return **قائمة من الرسائل
         */
        @Query ( "SELECT * FROM MYMESSAGES" )
        List<MyMessages> getAllMessages();

        /**
         * ادخال مهمات
         * @param t مجموعة الرسائل
         */
        @Insert
        void insertTask(MyMessages...t);//استطيع ادخال كائن او مجموعة

        /**
         * تعديل المهمات
         * @param messages مجموعة الرسائل للتعديل (التعديل حسب المفتاح الرئيسي)
         */
        @Update
        void updateTask(MyMessages ...messages);

        /**
         * حذف رسالة او الرسائل
         * @param messages حذف االرسائل (حسب المفتاح الرئيسي )
         */
        @Delete
        void delereTasks(MyMessages...messages);

        @Query("DELETE FROM MyMessages WHERE keyid=:id")
        void delTaskById(long id);





    }
}