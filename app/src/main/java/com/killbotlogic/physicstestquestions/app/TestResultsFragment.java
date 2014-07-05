package com.killbotlogic.physicstestquestions.app;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.killbotlogic.physicstestquestions.app.question.Question;
import com.killbotlogic.physicstestquestions.app.question.Test;
import com.killbotlogic.physicstestquestions.app.question.TestQuestion;

import java.text.DecimalFormat;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TestResultsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TestResultsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TestResultsFragment extends Fragment implements OnVisibleInterface {

    public static final String ARG_ITEM_ID = "test_uuid";
    public static final String FRAGMENT_TAG = "fragment_test_results";
    private Test test;
    private OnFragmentInteractionListener mListener;
    private View root;
    public TestResultsFragment() {
    }

    public static TestResultsFragment newInstance(String testId) {
        TestResultsFragment fragment = new TestResultsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ITEM_ID, testId);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            if (getArguments().containsKey(ARG_ITEM_ID)) {
                String testId = getArguments().getString(ARG_ITEM_ID);
                test = App.getDatabase().getTestMap().get(testId);

            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (test == null)
            if (getArguments().containsKey(ARG_ITEM_ID)) {
                String testId = getArguments().getString(ARG_ITEM_ID);
                test = App.getDatabase().getTestMap().get(testId);

            }

        root = inflater.inflate(R.layout.fragment_test_results, container, false);
        onVisible();
        return root;


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onVisible() {

        if (getActivity() == null)
            return;

        TableLayout table = (TableLayout) root.findViewById(R.id.table_test_results);
        table.removeAllViews();

        List<TestQuestion> questions = test.getTestQuestions();

        DecimalFormat format = new DecimalFormat("#.#");
        TableRow headerRow = new TableRow(getActivity());

        TextView txtQuestion = new TextView(getActivity());
        TextView txtAnswer = new TextView(getActivity());
        TextView txtSolution = new TextView(getActivity());
        TextView txtScore = new TextView(getActivity());

        txtQuestion.setGravity(Gravity.CENTER_HORIZONTAL);
        txtAnswer.setGravity(Gravity.CENTER_HORIZONTAL);
        txtSolution.setGravity(Gravity.CENTER_HORIZONTAL);
        txtScore.setGravity(Gravity.CENTER_HORIZONTAL);

        txtQuestion.setText("Question");
        txtAnswer.setText("Answer");
        txtSolution.setText("Solution");
        txtScore.setText("Score");

        txtQuestion.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        txtAnswer.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        txtSolution.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        txtScore.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);

        txtQuestion.setTypeface(txtQuestion.getTypeface(), Typeface.BOLD);
        txtAnswer.setTypeface(txtAnswer.getTypeface(), Typeface.BOLD);
        txtSolution.setTypeface(txtSolution.getTypeface(), Typeface.BOLD);
        txtScore.setTypeface(txtScore.getTypeface(), Typeface.BOLD);

        headerRow.addView(txtQuestion);
        headerRow.addView(txtAnswer);
        headerRow.addView(txtSolution);
        headerRow.addView(txtScore);

        table.addView(headerRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));


        for (final TestQuestion question : questions) {
            TableRow row = new TableRow(getActivity());

            TextView name = new TextView(getActivity());
            TextView answer = new TextView(getActivity());
            TextView solution = new TextView(getActivity());
            TextView score = new TextView(getActivity());

            name.setGravity(Gravity.CENTER_HORIZONTAL);
            answer.setGravity(Gravity.CENTER_HORIZONTAL);
            solution.setGravity(Gravity.CENTER_HORIZONTAL);
            score.setGravity(Gravity.CENTER_HORIZONTAL);

            name.setText("Question " + question.index);

            answer.setText(question.getUserAnswer().toString());
            solution.setText(question.getQuestion().answer.toString());
            score.setText(format.format(question.getEarnedPoints()) + " / " + format.format(question.getTotalPoints()));

            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TestActivity activity = (TestActivity) TestResultsFragment.this.getActivity();
                    activity.goToQuestion(question.index - 1);
                }
            });

            if (question.getEarnedPoints() == question.getTotalPoints()) {
                score.setTextColor(Color.GREEN);
            } else if (question.getEarnedPoints() == 0) {
                score.setTextColor(Color.RED);
            } else {
                score.setTextColor(Color.YELLOW);
            }

            row.addView(name);
            row.addView(answer);
            row.addView(solution);
            row.addView(score);

            table.addView(row, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }

        TextView totalScore = (TextView) root.findViewById(R.id.text_test_score);

        totalScore.setText("Total Score: " + format.format(test.getEarnedPoints()) + " / " + format.format(test.getTotalPoints()));
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
