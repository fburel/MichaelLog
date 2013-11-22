package fr.florianburel.mickaellog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.sql.DatabaseMetaData;
import java.util.Calendar;

/**
 * Created by fl0 on 22/11/2013.
 */
public class CallBroadcastReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent)
    {

        // Log the content of the data received
        Log.d("toto", intent.getExtras().keySet().toString());
        for(String key : intent.getExtras().keySet())
        {
            Log.d("toto", intent.getExtras().get(key).toString());
        }

        // test if the phone is ringing
        if(intent.getExtras().getString("state").equals("RINGING"))
        {
            String incomingNumber = intent.getExtras().getString("incoming_number");

            // Stockage du numéro en base de données
            Call receivedCall = new Call(incomingNumber, context);
            DatabaseManager dbManager = new DatabaseManager(context);
            dbManager.insertCall(receivedCall, context);
        }

    }
}
