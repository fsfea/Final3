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
         * اعادة جميع معطيات جدول المهمات
         *
         * @return **قائمة من المهمات
         */
        @Query ( "SELECT * FROM MYMESSAGES" )
        List<MyMessages> getAllMessages();

        /**
         * ادخال مهمات
         * @param t مجموعة مهمات
         */
        @Insert
        void insertTask(MyMessages...t);//استطيع ادخال كائن او مجموعة

        /**
         * تعديل المهمات
         * @param tasks مجموعة مهمات للتعديل (التعديل حسب المفتاح الرئيسي)
         */
        @Update
        void updateTask(MyMessages ...tasks);

        /**
         * حذف مهمة او مهمات
         * @param tasks حذف المهمات (حسب المفتاح الرئيسي )
         */
        @Delete
        void delereTasks(MyMessages...tasks);

        @Query("DELETE FROM MyMessages WHERE keyid=:id")
        void delTaskById(long id);





    }
}