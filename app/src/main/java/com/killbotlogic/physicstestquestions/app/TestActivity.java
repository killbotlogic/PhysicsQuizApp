package com.killbotlogic.physicstestquestions.app;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.killbotlogic.physicstestquestions.app.question.Test;
import com.killbotlogic.physicstestquestions.app.question.TestQuestion;

import java.util.List;
import java.util.Objects;

public class TestActivity extends Activity implements ActionBar.TabListener,
        TestResultsFragment.OnFragmentInteractionListener,
        TestQuestionFragment.OnFragmentInteractionListener {

    private TabsPagerAdapter tabsAdapter;
    private ActionBar actionBar;


    private Test test;
    private ViewPager viewPager;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_test, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        final TestFragment frag = (TestFragment) getFragmentManager()
//                .findFragmentByTag(TestFragment.FRAGMENT_TAG);

        //final Test test = frag.getTest();
        //final TestFragment.TestQuestionAdapter adapter = ((TestFragment.TestQuestionAdapter)frag.getListAdapter());
//        final ListView view = frag.getListView();

        switch (item.getItemId()) {
            case R.id.menu_mark:


                runOnUiThread(new Runnable() {
                    public void run() {
                        viewPager.setCurrentItem(test.getTestQuestions().size());
//                        Fragment frag = tabsAdapter.getItem(test.getTestQuestions().size());
//                        FragmentTransaction trans = getFragmentManager().beginTransaction();
//                        trans.detach(frag);
//                        trans.attach(frag);
//                        trans.commit();
                    }
                });

//                frag.setListAdapter(adapter); // Fucking hack because refresh will not work
//                ViewGroup vg = (ViewGroup) findViewById (R.id.test_container);
//                vg.invalidate();
//
//                adapter.notifyDataSetChanged();
//                adapter.notifyDataSetInvalidated();
//                view.invalidate();
//                view.requestLayout();
//                view.invalidateViews();
//                view.refreshDrawableState();

                return true;
            case R.id.menu_clear:
                runOnUiThread(new Runnable() {
                    public void run() {
                        AlertDialog.Builder builder = new AlertDialog.Builder(TestActivity.this);
                        builder.setMessage("Clear test answers?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        test.clear();

                                        ActionBar.Tab tab = getActionBar().getSelectedTab();
                                        update(tab.getPosition());
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).show();
                    }
                });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        if (savedInstanceState == null) {

            String testId = getIntent().getStringExtra(TestFragment.ARG_ITEM_ID);
            test = App.getDatabase().getTestMap().get(testId);

            setTitle(test.toString());


            tabsAdapter = new TabsPagerAdapter(getFragmentManager());

            viewPager = (ViewPager) findViewById(R.id.pager);
            viewPager.setAdapter(tabsAdapter);
            viewPager.setOnPageChangeListener(
                    new ViewPager.SimpleOnPageChangeListener() {
                        @Override
                        public void onPageSelected(int position) {


                            // When swiping between pages, select the
                            // corresponding tab.
                            goToQuestion(position);
                        }
                    }
            );
//            viewPager.setOffscreenPageLimit(0);

            actionBar = getActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

            for (TestQuestion q : test.getTestQuestions()) {
                ActionBar.Tab tab = actionBar.newTab().setText("Question " + q.index).setTabListener(this);
                actionBar.addTab(tab);

            }
            ActionBar.Tab tab = actionBar.newTab().setText("Results").setTabListener(this);
            actionBar.addTab(tab);

        }
    }

    @Override
    public void onTabSelected(final ActionBar.Tab tab, FragmentTransaction ft) {
        goToQuestion(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
//            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }




    public void goToQuestion(int index) {
        if (viewPager.getCurrentItem() != index) {
            viewPager.setCurrentItem(index);
        }

        if (actionBar.getSelectedNavigationIndex() != index) {
            actionBar.setSelectedNavigationItem(index);
        }
        update(index);
    }

    private void update(int index) {
        Log.i("UI", "UPDATING: " + index);

        viewPager.invalidate();

        OnVisibleInterface fragment = (OnVisibleInterface) tabsAdapter.instantiateItem(viewPager, index);
        if (fragment != null) {
            fragment.onVisible();
        }
    }

    public class TabsPagerAdapter extends FragmentPagerAdapter {

        public TabsPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int index) {

            List<TestQuestion> questions = test.getTestQuestions();

            if (index == questions.size()) {
                Bundle args = new Bundle();
                args.putString(TestResultsFragment.ARG_ITEM_ID, test.uuid.toString());
                return Fragment.instantiate(TestActivity.this, TestResultsFragment.class.getName(), args);
            } else {
                TestQuestion question = questions.get(index);
                //return Fragment.instantiate(TestActivity.this, TestQuestionFragment.class.getName(), args);
                Log.i("WTF", "TestQuestionFragment.newInstance(" + index + ");");
                return TestQuestionFragment.newInstance(question.uuid.toString());
            }
        }

        @Override
        public int getCount() {

            return test.getTestQuestions().size() + 1;
        }

        @Override
        public int getItemPosition(Object object) {
//            if (object instanceof TestQuestionFragment) {
//                TestQuestion question = ((TestQuestionFragment) object).getTestQuestion();
//                return test.getTestQuestions().indexOf(question);
//            } else if (object instanceof TestResultsFragment) {
//                return test.getTestQuestions().size();
//            } else
//                return super.getItemPosition(object);
            return POSITION_NONE;
        }

    }


}
