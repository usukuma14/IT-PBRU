package pbru.it.sukuma.itpbru;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.concurrent.Callable;

/**
 * Created by lap324-12 on 6/7/2016 AD.
 */
public class MyMange {

    //Explicit
    private MyOpenHelper myOpenHelper;
    private SQLiteDatabase sqLiteDatabase;

    public static final String user_table = "userTABLE";
    public static final String colume_id = "_id";
    public static final String colume_name = "Name";
    public static final String colume_surname = "Surname";
    public static final String colume_user = "User";
    public static final String colume_password = "Password";



    public MyMange(Context context) {

        myOpenHelper = new MyOpenHelper(context);
        sqLiteDatabase = myOpenHelper.getWritableDatabase();




    }   //constructor


    public long addNewUser(String strID,
                           String strName,
                           String strSurname,
                           String strUser,
                           String strPassword) {


        ContentValues contentValues = new ContentValues();
        contentValues.put(colume_id, strID);
        contentValues.put(colume_name, strName);
        contentValues.put(colume_surname, strSurname);
        contentValues.put(colume_user, strUser);
        contentValues.put(colume_password, strPassword);


        return sqLiteDatabase.insert(user_table, null, contentValues);

    }

}   //main class
