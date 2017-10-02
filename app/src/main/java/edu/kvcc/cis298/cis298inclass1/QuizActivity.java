package edu.kvcc.cis298.cis298inclass1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private RadioGroup mQuestionGroup;
    private RadioButton mChoice1;
    private RadioButton mChoice2;
    private RadioButton mChoice3;
    private RadioButton mChoice4;


    private Button mSubmitButton;
    private Button mNextButton;
    private TextView mQuestionTextView;

    //array of questions. we send over the resource id from R.java
    //as the first parameter of the constructor
    //we will use this stored ResourceId (which references a string in strings.xml later)\
    //if we were to have a string variable on the question model
    //and try to pass the string value, it would work, but it
    //goes against the convesntion of android development
    private Question[] mQuestionBank = new Question[]{
            //First paramater is string that is the question text
            //second parameter is the id that is the correct answer for the question
            //this id id the id field assigned to the radio button widget that will
            //represent the correct answer
            //if radioButton2 is the correct answer I need to assign the id for
            //RadioButton2 it willnot have anything to do with the question itself
            //third parameter is an int array holding the possible answers to the question
            new Question (R.string.question_1_multiple,R.id.multiple_choice_3,
                    new int[] {R.string.question_1_choice_1,R.string.question_1_choice_2,R.string.question_1_choice_3,R.string.question_1_choice_4}),
            new Question (R.string.question_2_multiple,R.id.multiple_choice_2,
                    new int[] {R.string.question_2_choice_1,R.string.question_2_choice_2,R.string.question_2_choice_3,R.string.question_2_choice_4}),

    };

    //index of the current question we are on.
    private int mCurrentIndex = 0;




    private void checkAnswer(int selectedRadioButtonId) {
        //Pull the answer from the current question
        int correctAnswer = mQuestionBank[mCurrentIndex].getCorrectAnswerResId();
        //declare a int to hold the string resource id of the answer
        int messageResId = 0;
        //if the questions res id and correct answer res id are equal
        //they got it right correct answers will be when both
        //variables are the same. If they are different it is wrong
        //set the messageResId once we determine what to set it to
        if (selectedRadioButtonId == correctAnswer) {
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

        //get out the radio button information from the view
        mQuestionGroup = (RadioGroup) findViewById(R.id.multiple_group);

        mChoice1 = (RadioButton) findViewById(R.id.multiple_choice_1);
        mChoice2 = (RadioButton) findViewById(R.id.multiple_choice_2);
        mChoice3 = (RadioButton) findViewById(R.id.multiple_choice_3);
        mChoice4 = (RadioButton) findViewById(R.id.multiple_choice_4);

        updateQuestion();


        //use R to reach into the view layout and pull out a
        //reference to the button we want to use in code
        //we will get access to the button in the view by using
        //the id property that we declared on the layout



        mSubmitButton = (Button) findViewById(R.id.submit_button);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Query the radio button group to find out which radio
                //button  was selected. store the id of the selected one
                //in the variable selectedAnswerId
                //this will get the id of the radiobutton that was selected
                //it is operating on the Radiobutton widget, and thus returns the
                //id of the wiget control.
                int selectedAnswerId = mQuestionGroup.getCheckedRadioButtonId();
                //call checkAnser sending in the selectedAnswerId
                //see if the user selected a radio button
                if(selectedAnswerId == -1)
                {
                    //Toast not selected
                    Toast.makeText(QuizActivity.this, R.string.app_name, Toast.LENGTH_SHORT).show();
                }
                else {
                    checkAnswer(selectedAnswerId);
                }
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

        //fetch the question choice string from the question object
        int [] choices = mQuestionBank[mCurrentIndex].getChoiceResIds();

        //Assign each question choice text to the text property of the radio button
        mChoice1.setText(choices[0]);
        mChoice2.setText(choices[1]);
        mChoice3.setText(choices[2]);
        mChoice4.setText(choices[3]);

        //reset the radio group
        mQuestionGroup.clearCheck();
    }
}
