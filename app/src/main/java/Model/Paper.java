package Model;

public class Paper {

    private String paperId;
    private String paperName;
    private String paperYear;
    private String PaperQuestionCount;
    private String paperAttempts;
    private String paperAllocatedTime;

    public Paper(String paperId, String paperName, String paperYear, String paperQuestionCount, String paperAttempts, String paperAllocatedTime) {
        this.paperId = paperId;
        this.paperName = paperName;
        this.paperYear = paperYear;
        PaperQuestionCount = paperQuestionCount;
        this.paperAttempts = paperAttempts;
        this.paperAllocatedTime = paperAllocatedTime;
    }

    public String getPaperQuestionCount() {
        return PaperQuestionCount;
    }

    public void setPaperQuestionCount(String paperQuestionCount) {
        PaperQuestionCount = paperQuestionCount;
    }

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    public String getPaperAllocatedTime() {
        return paperAllocatedTime;
    }

    public void setPaperAllocatedTime(String paperAllocatedTime) {
        this.paperAllocatedTime = paperAllocatedTime;
    }

    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    public String getPaperYear() {
        return paperYear;
    }

    public void setPaperYear(String paperYear) {
        this.paperYear = paperYear;
    }

    public String getPaperAttempts() {
        return paperAttempts;
    }

    public void setPaperAttempts(String paperAttempts) {
        this.paperAttempts = paperAttempts;
    }


}
