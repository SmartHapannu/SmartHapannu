package Model;

public class AttemptsCategory {

    private String attemptsCategoryId;
    private String categoryId;
    private String subCategoryId;
    private String correctCount;
    private String unansweredCount;
    private String fullCount;
    private String attemptsMasterId;


    public AttemptsCategory(String attemptsCategoryId, String categoryId, String subCategoryId, String correctCount, String unansweredCount, String fullCount, String attemptsMasterId) {
        this.attemptsCategoryId = attemptsCategoryId;
        this.categoryId = categoryId;
        this.subCategoryId = subCategoryId;
        this.correctCount = correctCount;
        this.unansweredCount = unansweredCount;
        this.fullCount = fullCount;
        this.attemptsMasterId = attemptsMasterId;
    }


    public String getAttemptsCategoryId() {
        return attemptsCategoryId;
    }

    public void setAttemptsCategoryId(String attemptsCategoryId) {
        this.attemptsCategoryId = attemptsCategoryId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getCorrectCount() {
        return correctCount;
    }

    public void setCorrectCount(String correctCount) {
        this.correctCount = correctCount;
    }

    public String getUnansweredCount() {
        return unansweredCount;
    }

    public void setUnansweredCount(String unansweredCount) {
        this.unansweredCount = unansweredCount;
    }

    public String getFullCount() {
        return fullCount;
    }

    public void setFullCount(String fullCount) {
        this.fullCount = fullCount;
    }

    public String getAttemptsMasterId() {
        return attemptsMasterId;
    }

    public void setAttemptsMasterId(String attemptsMasterId) {
        this.attemptsMasterId = attemptsMasterId;
    }
}
