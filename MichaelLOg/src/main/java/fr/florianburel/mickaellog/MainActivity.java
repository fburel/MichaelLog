package fr.florianburel.mickaellog;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by fl0 on 22/11/2013.
 */



public class MainActivity extends Activity
{
    private ListView listView;

    private ArrayList<Call> calls;
    private BroadcastReceiver br;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        // Récupération de la listView par son id
        this.listView = (ListView) findViewById(R.id.listView);

        this.br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                reloadData();
            }
        };

        registerReceiver(br, new IntentFilter(DatabaseManager.DATABASE_DID_UPDATE));

        reloadData();
    }

    private void reloadData() {

        DatabaseManager databaseManager = new DatabaseManager(this);
        this.calls = databaseManager.getAllCalls();

        this.listView.setAdapter(new ArrayAdapter<Call>(this, android.R.layout.simple_list_item_1, this.calls));

    }
}