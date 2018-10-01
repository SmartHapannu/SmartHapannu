package Model;

import java.util.List;

public class Question {
    private String questionId;
    private String questionContent;
    private byte[] questionImage;
    private String paperId;
    private String lessonId;
    private String subLessonId;
    private List<Answer> answers;

    public Question(String questionId, String questionContent, byte[] questionImage, String paperId, String lessonId, String subLessonId, List<Answer> answers) {
        this.questionId = questionId;
        this.questionContent = questionContent;
        this.questionImage = questionImage;
        this.paperId = paperId;
        this.lessonId = lessonId;
        this.subLessonId = subLessonId;
        this.answers = answers;
    }

    public String getSubLessonId() {
        return subLessonId;
    }

    public void setSubLessonId(String subLessonId) {
        this.subLessonId = subLessonId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public byte[] getQuestionImage() {
        return questionImage;
    }

    public void setQuestionImage(byte[] questionImage) {
        this.questionImage = questionImage;
    }

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    public String getLessonId() {
        return lessonId;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
