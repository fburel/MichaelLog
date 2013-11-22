package fr.florianburel.mickaellog;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by fl0 on 22/11/2013.
 */
public class DatabaseManager extends SQLiteOpenHelper
{


    private static final String DATABASE_NAME = "myDatabase";
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_DID_UPDATE = "fr.florianburel.mickaellog.databaseManager.update";

    public DatabaseManager(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL("create table call\n" +
                "(\n" +
                "  _id    INTEGER primary key autoincrement,\n" +
                "  name   \"VARCHAR()\",\n" +
                "  number \"VARCHAR()\",\n" +
                "  date   \"VARCHAR()\"\n" +
                ");");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int installedVersion, int newVersion)
    {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS call");
        onCreate(sqLiteDatabase);
        /*
        switch (installedVersion)
        {
            case 1:
                // code sql pour passer de v1 -> v2
                // pas de break!!!
            case 2:
                // code sql pour passer de v2 -> v3
                // pas de break!!!
            case 3:
                // code sql pour passer de v3 -> v4
                // pas de break!!!

        }
        */
    }

    public void insertCall(Call receivedCall, Context context)
    {
        long i = getWritableDatabase().insert("call", null, contentValuesFromCall(receivedCall));
        Intent broadcast = new Intent(DATABASE_DID_UPDATE);
        broadcast.putExtra("id", i);
        context.sendBroadcast(broadcast);
    }

    private ContentValues contentValuesFromCall(Call call)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", call.getContactName());
        contentValues.put("number", call.getPhoneNumber());
        contentValues.put("date", "" + call.getCallDate().getTime());
        return contentValues;
    }

    public ArrayList<Call> getAllCalls()
    {
        ArrayList<Call> calls = new ArrayList<Call>();

        Cursor c = this.getReadableDatabase().rawQuery("SELECT name, number, date FROM call", null);

        String[] names = c.getColumnNames();
        ArrayList<String> namesArray = new ArrayList<String>();
        for(String name : names)
        {
            namesArray.add(name);
        }

        c.moveToFirst();
        while (!c.isAfterLast())
        {
            String name = c.getString(namesArray.indexOf("name"));
            String number = c.getString(namesArray.indexOf("number"));
            Long millis = Long.parseLong(c.getString(namesArray.indexOf("date")));
            Date date = new Date(millis);

            calls.add(new Call(name, number, date));

            c.moveToNext();
        }

        c.close();

        return calls;

    }
}
