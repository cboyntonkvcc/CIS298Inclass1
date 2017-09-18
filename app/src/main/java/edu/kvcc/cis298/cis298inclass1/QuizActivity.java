package edu.kvcc.cis298.cis298inclass1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private TextView mQuestionTextView;

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




    private void checkAnswer(boolean userPressedTrue) {
        //Pull the answer from the current question
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerIsTrue();
        //declare a int to hold the string resource id of the answer
        int messageResId = 0;
        //if the questions answer and userpressingTrue are equal
        //they got it right correct answers will be when both
        //variables are the same. If they are different it is wrong
        //set the messageResId once we determine what to set it to
        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
        } else {
            messageResId = R.string.incorrect_toast;
        }


        //make the toast just like before only now, we only need one of them
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        //get a reference to the textview that displays the question
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
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
}
