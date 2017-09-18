package edu.kvcc.cis298.cis298inclass1;

/**
 * Created by cisco on 9/18/2017.
 */

public class Question {

    //this is an int because it will reference the memory
    //adress in the R.java file that points to the string resource in strings.xml
    //for the text of this particular question
    private int mTextResId;
    private boolean mAnswerIsTrue;

    public Question(int textResId, boolean answerIsTrue)
    {
        mTextResId = textResId;
        mAnswerIsTrue = answerIsTrue;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerIsTrue() {
        return mAnswerIsTrue;
    }

    public void setAnswerIsTrue(boolean answerIsTrue) {
        mAnswerIsTrue = answerIsTrue;
    }
}
