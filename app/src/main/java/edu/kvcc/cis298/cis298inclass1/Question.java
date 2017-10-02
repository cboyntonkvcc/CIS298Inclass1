package edu.kvcc.cis298.cis298inclass1;

/**
 * Created by cisco on 9/18/2017.
 */

public class Question {

    //this is an int because it will reference the memory
    //adress in the R.java file that points to the string resource in strings.xml
    //for the text of this particular question
    private int mTextResId;

    //resource id of the correct answer radio button
    private int mCorrectAnswerResId;

    //Resource id of all of the possible answers for the question
    private int[] mChoiceResIds;

    public Question(int textResId, int correctAnswerResId, int[] choiceResIds)
    {
        mTextResId = textResId;
        mCorrectAnswerResId = correctAnswerResId;
        mChoiceResIds = choiceResIds;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public int getCorrectAnswerResId() {
        return mCorrectAnswerResId;
    }

    public void setCorrectAnswerResId(int correctAnswerResId) {
        mCorrectAnswerResId = correctAnswerResId;
    }

    public int[] getChoiceResIds() {
        return mChoiceResIds;
    }

    public void setChoiceResIds(int[] choiceResIds) {
        mChoiceResIds = choiceResIds;
    }
}
