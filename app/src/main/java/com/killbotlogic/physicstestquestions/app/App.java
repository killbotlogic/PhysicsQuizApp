package com.killbotlogic.physicstestquestions.app;

import android.app.Application;
import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.killbotlogic.physicstestquestions.app.question.Database;

import org.json.JSONException;

import java.util.HashMap;

public class App extends Application {

    private static Context _context;
    private static Database _database;

    public static Database getDatabase() {
        return _database;
    }
    public static Context getAppContext() {
        return _context;
    }

    public void onCreate(){
        super.onCreate();
        _context = getApplicationContext();


        try {
            _database = new Database(getApplicationContext());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * Enum used to identify the tracker that needs to be used for tracking.
     *
     * A single tracker is usually enough for most purposes. In case you do need multiple trackers,
     * storing them all in Application object helps ensure that they are created only once per
     * application instance.
     */
    public enum TrackerName {
        APP_TRACKER, // Tracker used only in this app.
        GLOBAL_TRACKER, // Tracker used by all the apps from a company. eg: roll-up tracking.
        ECOMMERCE_TRACKER, // Tracker used by all ecommerce transactions from a company.
    }

    private HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();

    synchronized Tracker getTracker(TrackerName trackerId) {
        if (!mTrackers.containsKey(trackerId)) {

            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            Tracker t = (trackerId == TrackerName.APP_TRACKER) ? analytics.newTracker("UA-50156028-2")
                    : (trackerId == TrackerName.GLOBAL_TRACKER) ? analytics.newTracker(R.xml.global_tracker)
                    : null; //analytics.newTracker(R.xml.ecommerce_tracker);
            mTrackers.put(trackerId, t);

        }
        return mTrackers.get(trackerId);
    }

}
