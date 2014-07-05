package com.killbotlogic.physicstestquestions.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.killbotlogic.physicstestquestions.app.question.Database;
import com.killbotlogic.physicstestquestions.app.question.Test;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;


public class HomeActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_home);

        final ListView listview = (ListView) findViewById(R.id.home_list);

        final ArrayAdapter<Test> adapt = new ArrayAdapter<Test>(this,
                android.R.layout.simple_list_item_1, App.getDatabase().getTests());
        listview.setAdapter(adapt);


        final Intent viewTestIntent = new Intent(this, TestActivity.class);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                final Test item = (Test) parent.getItemAtPosition(position);

                viewTestIntent.putExtra(TestFragment.ARG_ITEM_ID, item.uuid.toString());
                startActivity(viewTestIntent);

            }

        });


    }
}
