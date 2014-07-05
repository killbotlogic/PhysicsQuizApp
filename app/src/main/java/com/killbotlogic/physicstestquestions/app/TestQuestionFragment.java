package com.killbotlogic.physicstestquestions.app;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.killbotlogic.physicstestquestions.app.question.MultipleChoice;
import com.killbotlogic.physicstestquestions.app.question.TestQuestion;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


public class TestQuestionFragment extends Fragment implements OnVisibleInterface {

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "test_question_uuid";
    public static final String FRAGMENT_TAG = "fragment_test_question";

    private TestQuestion testQuestion;
    private View root;
    private Map<MultipleChoice, RadioButton> options;
    private LayoutInflater inflater;
    private OnFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TestQuestionFragment() {
    }

    public static TestQuestionFragment newInstance(String testQuestionId) {
        TestQuestionFragment fragment = new TestQuestionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ITEM_ID, testQuestionId);
        fragment.setArguments(args);

        return fragment;
    }

    public TestQuestion getTestQuestion() {
        return testQuestion;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (savedInstanceState == null) {
            if (getArguments().containsKey(ARG_ITEM_ID)) {
                testQuestion = App.getDatabase().getTestQuestionMap().get(getArguments().getString(ARG_ITEM_ID));
                Log.i("WTF", "TestQuestion is (" + testQuestion + ");");
            }
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (testQuestion == null)
            if (getArguments().containsKey(ARG_ITEM_ID)) {
                testQuestion = App.getDatabase().getTestQuestionMap().get(getArguments().getString(ARG_ITEM_ID));
                Log.i("WTF", "Loading question AGAIN (" + testQuestion + ");");
                Log.i("WTF", "Loading question AGAIN (" + testQuestion.getUserAnswer() + ");");
            }
        this.inflater = inflater;
        root = inflater.inflate(R.layout.fragment_test_question, container, false);

        TextView txtTitle = (TextView) root.findViewById(R.id.text_test_question_title);
        txtTitle.setText("Question " + testQuestion.index);

        TextView questionText = (TextView) root.findViewById(R.id.question_text);
        questionText.setText(Html.fromHtml(testQuestion.getQuestion().question));


        onVisible();


        ImageView img = (ImageView) root.findViewById(R.id.question_image);
        if (testQuestion.getQuestion().image != null && !testQuestion.getQuestion().image.isEmpty()) {
            img.setImageBitmap(getImage("pics/" + testQuestion.getQuestion().image));
        } else {
            img.setVisibility(View.GONE);
        }

        return root;
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

    private Bitmap getImage(String location) {
        AssetManager assetManager = getResources().getAssets();
        Bitmap img = null;
        try {
            InputStream stream = assetManager.open(location.toLowerCase());
            img = BitmapFactory.decodeStream(stream);
        } catch (IOException e) {
            e.printStackTrace();
            Log.wtf("File System", "Can't find image " + location + " associated with question");

        }

        return img;
    }

    @Override
    public void onVisible() {

//            if (inflater == null)
//                return;
        if (getActivity() == null)
            return;
//            Log.wtf("WTF", "UPDATING: " + this);

        if (true) {//options == null) {
            options = new HashMap<MultipleChoice, RadioButton>();

//            options.put(MultipleChoice.A, (RadioButton) root.findViewById(R.id.radio_a));
//            options.put(MultipleChoice.B, (RadioButton) root.findViewById(R.id.radio_b));
//            options.put(MultipleChoice.C, (RadioButton) root.findViewById(R.id.radio_c));
//            options.put(MultipleChoice.D, (RadioButton) root.findViewById(R.id.radio_d));
//            options.put(MultipleChoice.E, (RadioButton) root.findViewById(R.id.radio_e));

            options.put(MultipleChoice.A, new RadioButton(getActivity()));
            options.put(MultipleChoice.B, new RadioButton(getActivity()));
            options.put(MultipleChoice.C, new RadioButton(getActivity()));
            options.put(MultipleChoice.D, new RadioButton(getActivity()));
            options.put(MultipleChoice.E, new RadioButton(getActivity()));
        }


        RadioGroup radioGroup = (RadioGroup) root.findViewById(R.id.question_options);
            radioGroup.removeAllViews();
        //options.keySet()
        for (final MultipleChoice i : new MultipleChoice[]{MultipleChoice.A, MultipleChoice.B, MultipleChoice.C, MultipleChoice.D, MultipleChoice.E}) {
            final RadioButton choice = options.get(i);
            choice.setText(i.name() + ". " + Html.fromHtml(testQuestion.getQuestion().answers.get(i)));
            choice.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            testQuestion.setUserAnswer(i);
                        }
                    }
            );
//            if (testQuestion.getUserAnswer() == i) {
//                choice.setChecked(true);
//            }

//            getActivity().runOnUiThread(new Runnable() {
//                public void run() {

//            radioGroup.buildDrawingCache();
//            radioGroup.refreshDrawableState();
//            radioGroup.destroyDrawingCache();
//            radioGroup.buildDrawingCache();
//            radioGroup.forceLayout();
//            radioGroup.invalidate();
//            radioGroup.buildLayer();
//            radioGroup.recomputeViewAttributes(choice);
//
            radioGroup.addView(choice);
//                    choice.setChecked(false);

            if (testQuestion.getUserAnswer() == i) {
                choice.setChecked(true);
            } else {
                choice.setChecked(false);
            }

//                    choice.requestLayout();
//            choice.invalidate();
//                    choice.destroyDrawingCache();
//            choice.forceLayout();
//                    choice.refreshDrawableState();
//                    choice.buildDrawingCache();
            Log.i("WTF", "answer(" + testQuestion.getUserAnswer() + ");");
            Log.i("WTF", i + ".isChecked(" + choice.isChecked() + ");");

//                }
//            });


        }

//        radioGroup.invalidate();
//        radioGroup.forceLayout();

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }



}
