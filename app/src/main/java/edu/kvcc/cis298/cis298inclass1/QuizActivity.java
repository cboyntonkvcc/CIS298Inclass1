package edu.kvcc.cis298.cis298inclass1;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    //used for logging to the logcat
    private static final String TAG = "QuizActivity";

    //this will be used at the key in a key => value
    //called the bundle to save information between screen rotations
    private static final String KEY_INDEX = "index";

    //declare a request code integer that can be sent with the
    //startActivityForResult method. This way when we return
    //to this activity we can check this request code to see if it is the
    //one that matches the one we sent when we started
    //the cheat activity
    private static final int REQUEST_CODE_CHEAT = 0;

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private TextView mQuestionTextView;
    private Button mCheatButton;



    //array of questions. we send over the resource id from R.java
    //as the first parameter of the constructor
    //we will use this stored ResourceId (which references a string in strings.xml later)\
    //if we were to have a string variable on the question model
    //and try to pass the string value, it would work, but it
    //goes against the convesntion of android development
    private Question[] mQuestionBank = new Question[]{
            new Question (R.string.question_oceans, true),
            new Question (R.string.question_mideast, false),
            new Question (R.string.question_africa, true),
            new Question (R.string.question_americas, true),
            new Question (R.string.question_asia, true),
    };

    //index of the current question we are on.
    private int mCurrentIndex = 0;


    //class level variable to know whether the person used
    //the cheat activity
    private boolean mIsCheater;




    private void checkAnswer(boolean userPressedTrue) {
        //Pull the answer from the current question
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerIsTrue();
        //declare a int to hold the string resource id of the answer
        int messageResId = 0;

        //first check to see if the user cheated
        if(mIsCheater){
            //set the answer to the do not cheat string
            messageResId = R.string.judgment_toast;

        }else {


            //if the questions answer and userpressingTrue are equal
            //they got it right correct answers will be when both
            //variables are the same. If they are different it is wrong
            //set the messageResId once we determine what to set it to
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }

        }
        //make the toast just like before only now, we only need one of them
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "OnCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        //get a reference to the textview that displays the question
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);


        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener()
        { @Override
            public void onClick(View v) {

            //create a new intent to get the activity started
            //intents are objects that hold all of the data needed
            //to start up a new activity. we then call startActivity
            //with the intent as a parameter. The intent is used by the OS
            //to determine what activity to start up
            //Activites are started vy the os. Not by the app
            boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerIsTrue();

            Intent i = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
            startActivityForResult(i, REQUEST_CODE_CHEAT);


        }

        });



        //check the saveInstanceState bundle and see if there is
        //an index that we need to fetch out so we display the correct
        //question
        //when the app first launches there is no bundle
        //that only happens when switching activities or on screen rotation
        //therefore we need to see if it is null before we try
        //to pull info out from it
        if(savedInstanceState != null){
            //get the value that is stored in it with the key of KEY_INDEX
            //if there is no entry with that key, use 0 as a defult
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        //update the question now that we have the index
        updateQuestion();


        //use R to reach into the view layout and pull out a
        //reference to the button we want to use in code
        //we will get access to the button in the view by using
        //the id property that we declared on the layout
        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });


        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               checkAnswer(false);
            }
        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //this will allow us to cycle the index back to zero if
                //it is about to become 5
                mCurrentIndex = (mCurrentIndex +1) % mQuestionBank.length;

                //reset cheater bool
                mIsCheater = false;

                //update the question
                updateQuestion();
            }
        });


    }

    private void updateQuestion() {
        //get the question from the array again this will be
        // an integer because we are fetching eh memory address stored
        //in the question that points to the string we want to show
        int question = mQuestionBank[mCurrentIndex].getTextResId();

        //set the text on the question text view to the string resource
        //located at the memory address stored in question
        mQuestionTextView.setText(question);
    }


    //handle returning from another actitivity

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //if something went wrong in the other activity  and the result
        //code is not ok we can just return  no need to do any work
        if(resultCode != Activity.RESULT_OK){
            return;
        }
        //check the request code and see which one it is
        //since we only have 1 other activity safe to say its gonna
        //be that one. but in the event we had more activities
        //we would want to know which one we are returning from
        if (requestCode == REQUEST_CODE_CHEAT){
            //Check to see if the return data (Intent) is null for some reason
            if (data == null){
                return;
            }
            //everything checks out we can safely pull out the data we need
            //we will use the static method on the cheat class to decode the
            //returned data and tell us if the person cheated or not
            mIsCheater = CheatActivity.wasAnserShown(data);
        }
    }



    //handles screen rotaion
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX,mCurrentIndex);

    }









    //Log overrides that just log stuff out to the logCat

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }
}

