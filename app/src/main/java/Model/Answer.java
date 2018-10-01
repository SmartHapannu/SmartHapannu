package Model;

import java.util.Comparator;

public class Answer {
    private String answerId;
    private String answerName;
    private String answerContent;
    private byte[] answerImage;
    private int isCorrect;
    private String questionId;

    public Answer(String answerId, String answerName, String answerContent, byte[] answerImage, int isCorrect, String questionId) {
        this.answerId = answerId;
        this.answerName = answerName;
        this.answerContent = answerContent;
        this.answerImage = answerImage;
        this.isCorrect = isCorrect;
        this.questionId = questionId;
    }

    /*public static Comparator<Answer> AnswerNameComparator = new Comparator<Answer>() {

        @Override
        public int compare(Answer o1, Answer o2) {
            String answerName1 = o1.getAnswerName();
            String answerName2 = o2.getAnswerName();

            return answerName1.compareTo(answerName2);
        }

    };*/

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getAnswerName() {
        return answerName;
    }

    public void setAnswerName(String answerName) {
        this.answerName = answerName;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    public byte[] getAnswerImage() {
        return answerImage;
    }

    public void setAnswerImage(byte[] answerImage) {
        this.answerImage = answerImage;
    }

    public int getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(int isCorrect) {
        this.isCorrect = isCorrect;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }
}
