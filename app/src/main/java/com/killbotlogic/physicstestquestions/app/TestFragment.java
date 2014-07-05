package com.killbotlogic.physicstestquestions.app;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;


import com.killbotlogic.physicstestquestions.app.question.Test;
import com.killbotlogic.physicstestquestions.app.question.TestQuestion;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * A list fragment representing a list of Items. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link TestQuestionFragment}.
 * <p>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class TestFragment extends ListFragment {

    public static final String ARG_ITEM_ID = "test_uuid";
    public static final String FRAGMENT_TAG = "fragment_test";
    private Test _test;
    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    private Callbacks mCallbacks;

    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(String id);
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TestFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            if (getArguments().containsKey(ARG_ITEM_ID) && getArguments().getString(ARG_ITEM_ID) != null) {
                // TODO: use a Loader to load content from a content provider. This was the suggestion ???

                String testId = getArguments().getString(ARG_ITEM_ID);
                _test = App.getDatabase().getTestMap().get(testId);

                getActivity().setTitle(_test.toString());

                setListAdapter(new TestQuestionAdapter(getActivity(), _test.getTestQuestions()));
            }
            //Log.wtf("Dead end", "WTF are you doing here, TestFragment arguments not specified");
        }

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
        //View rootView = inflater.inflate(R.layout.fragment_test_question, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);

        // Notify the active callbacks interface (the activity, if the
        // fragment is attached to one) that an item has been selected.

        mCallbacks.onItemSelected(_test.getTestQuestions().get(position).uuid.toString());


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }

    public Test getTest() {
        return  _test;
    }

    public class TestQuestionAdapter extends ArrayAdapter<TestQuestion> {

        private List<TestQuestion> _questions;
        private LayoutInflater _inflater;
        private Context _context;
        private int _resource;

        private DataSetObserver _observer;

        public TestQuestionAdapter(Context context, List<TestQuestion> questions) {
            super(context, R.layout.item_test_question, questions);
            _context = context;
            _resource = R.layout.item_test_question;
            _questions = questions;
            _inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            _observer = new DataSetObserver() {
                @Override
                public void onChanged() {
                    super.onChanged();
                    Log.wtf("DataSetObserver", "onChanged()");
                }

                @Override
                public void onInvalidated() {
                    super.onInvalidated();
                    Log.wtf("DataSetObserver", "onInvalidated()");
                }
            };

            registerDataSetObserver(_observer);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {


            // 1.
            if (view == null) {
                view = _inflater.inflate(_resource, null);
            }

            // 2.
            TextView title = (TextView) view.findViewById(R.id.title);
            TextView category = (TextView) view.findViewById(R.id.category);
            TextView marks = (TextView) view.findViewById(R.id.marks);
            RatingBar score = (RatingBar) view.findViewById(R.id.ratingBar);
            TextView attempted = (TextView) view.findViewById(R.id.attempted);
            TextView marked = (TextView) view.findViewById(R.id.marked);
            TextView solution = (TextView) view.findViewById(R.id.solution);

            TestQuestion q = getItem(position);

            score.setEnabled(false);
            score.setFocusable(false);
            score.setFocusableInTouchMode(false);
            DecimalFormat df = new DecimalFormat("#.#");
            if (q.isMarked()) {
                marked.setText("Score: " + df.format(q.getEarnedPoints()) + " / " + df.format(q.getTotalPoints()));

                score.setRating((float) (q.getEarnedPoints() / q.getTotalPoints() * score.getNumStars()));
                score.setNumStars((int)q.getTotalPoints());

                if (q.getEarnedPoints() == q.getTotalPoints()) {
                    marked.setTextColor(Color.GREEN);
                } else if (q.getEarnedPoints() == 0) {
                    marked.setTextColor(Color.RED);
                } else {
                    marked.setTextColor(Color.YELLOW);
                }
            } else {
                score.setVisibility(View.GONE);
                marked.setVisibility(View.GONE);
            }
            if (q.isAttempted()) {
                attempted.setText("Answered: " + q.getUserAnswer());
                attempted.setTextColor(Color.GREEN);
            } else {
                if (q.isMarked())
                    attempted.setText("Not attempted");
                else
                    attempted.setText("Not attempted yet");
                if (_test.isStarted())
                    attempted.setTextColor(Color.RED);
            }
            if (q.isAnswerShown()) {
                solution.setText("Solution: " + q.getQuestion().getSolution().name());
            } else {
                solution.setVisibility(View.GONE);
            }


            title.setText("Question " + (position + 1));
            category.setText(q.getQuestion().category.toString());
            marks.setText("Marks: " + df.format(q.getTotalPoints()));
            return view;
        }

        @Override
        public void notifyDataSetChanged(){
            Log.wtf("UI Thread", "notifyDataSetChanged();");
            super.notifyDataSetChanged();
        }
    }
}
