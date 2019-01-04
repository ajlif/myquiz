package com.projectunimore.root.myquiz;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.view.View;
import java.util.ArrayList;
import java.util.Collections;
import android.content.SharedPreferences;

public class QuizActivity extends AppCompatActivity {

    // UI references.
    Button mTrueButton;
    Button mFalseButton;
    TextView mScoreTextView;
    TextView mQuestionTextView;
    ProgressBar mProgressBar;

    private String mDisplayName;

    int mIndex=0; // track which question is fetched
    int mScore=0;
    int mQuestion;
    Toast mToastMessage; // For keeping track if a Toast message is being shown.
    ArrayList<Integer> randomIndexes;
    int nextRandom = 0;



    // Create question bank using the TrueFalse class for each item in the array
    private TrueFalse[] mQuestionBank = new TrueFalse[] {
            new TrueFalse(R.string.question_1, true),
            new TrueFalse(R.string.question_2, true),
            new TrueFalse(R.string.question_3, true),
            new TrueFalse(R.string.question_4, true),
            new TrueFalse(R.string.question_5, true),
            new TrueFalse(R.string.question_6, false),
            new TrueFalse(R.string.question_7, true),
            new TrueFalse(R.string.question_8, false),
            new TrueFalse(R.string.question_9, true),
            new TrueFalse(R.string.question_10, true),
            new TrueFalse(R.string.question_11, false),
            new TrueFalse(R.string.question_12, false),
            new TrueFalse(R.string.question_13,true)
    };


    // Declaring constants here. Rather than a fixed number, choosing to make it a function
    // of the length of the question bank (the number of questions)
    final int PROGRESS_BAR_INCREMENT = (int) Math.ceil(100.0 / 10);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        setupDisplayName();
        mTrueButton = findViewById(R.id.true_button);
        mFalseButton = findViewById(R.id.false_button);
        mScoreTextView = findViewById(R.id.score);
        mQuestionTextView = findViewById(R.id.question_text_view);
        mProgressBar = findViewById(R.id.progress_bar);

        //verify the state of the bundle before going on
        if (savedInstanceState != null) {
            mScore = savedInstanceState.getInt("ScoreKey");
            mIndex = savedInstanceState.getInt("IndexKey");
            randomIndexes = savedInstanceState.getIntegerArrayList("randomIndexes");
            mScoreTextView.setText("Score " + mScore + "/10");
        } else {
            mScore = 0;
            mIndex = 0;
            randomIndexes = getUniqueRandomindexs();
        }

        mQuestion = mQuestionBank[randomIndexes.get(mIndex)].getmQuestionID();
        mQuestionTextView.setText(mQuestion);

        Log.d("QuizActivity", randomIndexes.toString());

    }

    // executed when true button is pressed
    public void onClickTrue(View v)
    {
        Log.d("QuizActivity", "onClickTrue");
        checkAnswer(true);
        updateQuestion();

    }

    // executed when false button is pressed
    public void onClickFalse(View v)
    {
        Log.d("QuizActivity", "onClickFalse");
        checkAnswer(false);
        updateQuestion();

    }

    public void onClickHistory(View v){

        Log.d("QuizActivity", "onClickHistory");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();


    }


    // Question update
    private void updateQuestion(){
        // This takes the modulus. Not a division.
        mIndex = (mIndex + 1) % randomIndexes.size();
        //nextRandom++;

        // Present an alert dialog if we reach the end.
        if(mIndex == 0) {
            mFalseButton.setEnabled(false);
            mTrueButton.setEnabled(false);
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Game Over");
            alert.setCancelable(false);
            alert.setMessage("You scored " + mScore + "/" + randomIndexes.size()+"points !");
            alert.setIcon(android.R.drawable.ic_dialog_info);
            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mIndex = 0;
                    mProgressBar.setProgress(0);
                    mScore=0;
                    mScoreTextView.setText("Score " + mScore + "/" + randomIndexes.size());
                    randomIndexes = getUniqueRandomindexs();
                    mQuestion = mQuestionBank[randomIndexes.get(mIndex)].getmQuestionID();
                    mQuestionTextView.setText(mQuestion);
                    mFalseButton.setEnabled(true);
                    mTrueButton.setEnabled(true);

                }
            });
            alert.setNegativeButton("Close Application", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alert.show();
        }

        mQuestion = mQuestionBank[randomIndexes.get(mIndex)].getmQuestionID();
        mQuestionTextView.setText(mQuestion);
        mProgressBar.incrementProgressBy(PROGRESS_BAR_INCREMENT);
        mScoreTextView.setText("Score " + mScore + "/" + randomIndexes.size());
    }

    // check answer
    private void checkAnswer(boolean userSelection) {

        boolean correctAnswer = mQuestionBank[mIndex].ismAnswer();
        // Can cancel the Toast message if there is one on screen and a new answer
        // has been submitted.
        if (mToastMessage != null) {
            mToastMessage.cancel();
        }
        if(userSelection == correctAnswer) {
            mToastMessage = Toast.makeText(getApplicationContext(), R.string.correct_toast, Toast.LENGTH_SHORT);
            mScore ++ ;

        } else {
            mToastMessage = Toast.makeText(getApplicationContext(), R.string.incorrect_toast, Toast.LENGTH_SHORT);
        }

        mToastMessage.show();
        }

    // get random question indexes
    public ArrayList<Integer> getUniqueRandomindexs(){
        ArrayList<Integer> list = new ArrayList<Integer>();
        ArrayList<Integer> result = new ArrayList<Integer>();
        for(int i=0;i<mQuestionBank.length;i++)
        {
            list.add(new Integer(i));
        }
        Collections.shuffle(list);
        for(int i=0;i<10;i++)
        {
            result.add(list.get(i));
        }
        return result;
    }

    // This callback is received when the screen is rotated so we can save the 'state'
    // of the app in a 'bundle'.
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("ScoreKey", mScore);
        outState.putInt("IndexKey", mIndex);
        outState.putIntegerArrayList("randomIndexes",randomIndexes);

    }

    // Retrieve the display name from the Shared Preferences
    private void setupDisplayName(){

        SharedPreferences prefs = getSharedPreferences(RegisterActivity.QUIZ_PREFS, MODE_PRIVATE);

        mDisplayName = prefs.getString(RegisterActivity.DISPLAY_NAME_KEY, null);

        if (mDisplayName == null) mDisplayName = "Anonymous";
        Log.d("QuizActivity", mDisplayName);
    }


}


