//package com.killbotlogic.physicstestquestions.app;
//
//import android.app.TaskStackBuilder;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.view.GestureDetectorCompat;
//import android.support.v4.view.MotionEventCompat;
//import android.util.Log;
//import android.view.GestureDetector;
//import android.view.MenuItem;
//import android.view.MotionEvent;
//import android.widget.Toast;
//
//import com.killbotlogic.physicstestquestions.app.question.TestQuestion;
//
//
//public class TestQuestionActivity extends FragmentActivity {
//
//
//    private static final String DEBUG_TAG = "TestQuestionActivity -> Gestures";
//    private TestQuestionSwipeListener swipeDetector;
//    private GestureDetectorCompat detector;
//    private TestQuestionFragment fragment;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_test_question);
//
//        // Show the Up button in the action bar.
//        getActionBar().setDisplayHomeAsUpEnabled(true);
//
//
//        swipeDetector = new TestQuestionSwipeListener();
//        detector = new GestureDetectorCompat(this, swipeDetector);
//
//
//
//        if (savedInstanceState == null) {
//
//            // Create the detail fragment and add it to the activity
//            Bundle arguments = new Bundle();
//            arguments.putString(TestQuestionFragment.ARG_ITEM_ID,
//                    getIntent().getStringExtra(TestQuestionFragment.ARG_ITEM_ID));
//
//            fragment = new TestQuestionFragment();
//
//            fragment.setArguments(arguments);
//
//            getFragmentManager().beginTransaction()
//                    .add(R.id.test_question_container, fragment, TestQuestionFragment.FRAGMENT_TAG)
//                    .commit();
//        }
//
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == android.R.id.home) {
//            // Respond to the action bar's Up/Home button
//            //http://developer.android.com/design/patterns/navigation.html#up-vs-back
//
//
//            Intent upIntent = getParentActivityIntent();
//
//            TestQuestionFragment frag = (TestQuestionFragment)getFragmentManager()
//                    .findFragmentByTag(TestQuestionFragment.FRAGMENT_TAG);
//
//            TestQuestion testQuestion = frag.getTestQuestion();
//            upIntent.putExtra(TestFragment.ARG_ITEM_ID, testQuestion.test.uuid.toString());
//            if (shouldUpRecreateTask(upIntent)) {
//                // This activity is NOT part of this app's task, so create a new task
//                // when navigating up, with a synthesized back stack.
//                TaskStackBuilder.create(this)
//                        // Add all of this activity's parents to the back stack
//                        .addNextIntentWithParentStack(upIntent)
//                                // Navigate up to the closest parent
//                        .startActivities();
//            } else {
//                // This activity is part of this app's task, so simply
//                // navigate up to the logical parent activity.
//                navigateUpTo(upIntent);
//            }
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        detector.onTouchEvent(ev);
//        return super.dispatchTouchEvent(ev);
//    }
//
//
//    public final class TestQuestionSwipeListener extends GestureDetector.SimpleOnGestureListener {
//
//        private static final int SWIPE_DISTANCE_THRESHOLD = 100;
//        private static final int SWIPE_VELOCITY_THRESHOLD = 100;
//
//        @Override
//        public boolean onDown(MotionEvent e) {
//            return true;
//        }
//
//        @Override
//        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//            float distanceX = e2.getX() - e1.getX();
//            float distanceY = e2.getY() - e1.getY();
//            if (Math.abs(distanceX) > Math.abs(distanceY) && Math.abs(distanceX) > SWIPE_DISTANCE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
//
//                final TestQuestion previous = fragment.getTestQuestion().getPreviousQuestion();
//                final TestQuestion next = fragment.getTestQuestion().getNextQuestion();
//
//                if (distanceX > 0)
//                    goToQuestion(previous);
//                else
//                    goToQuestion(next);
//                return true;
//            }
//            return false;
//        }
//    }
//
//    private void goToQuestion(TestQuestion question) {
//        if (question != null) {
//            Intent goTo = getIntent();
//            getIntent().putExtra(TestQuestionFragment.ARG_ITEM_ID, question.uuid.toString());
//            startActivity(goTo);
//        }
//    }
//}
