package ua.student.archquiz.computerarchitecturequiz.model;

public class Question {
    private String text;
    private String[] options;
    private int correctOptionIndex;
    private int userAnswerIndex = -1; // -1 означає, що відповіді ще немає

    public Question(String text, String[] options, int correctOptionIndex) {
        this.text = text;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
    }

    public String getText() { return text; }
    public String[] getOptions() { return options; }
    public int getCorrectOptionIndex() { return correctOptionIndex; }

    // Методи для збереження відповіді студента
    public void setUserAnswer(int index) { this.userAnswerIndex = index; }
    public int getUserAnswer() { return userAnswerIndex; }
    public boolean isAnswered() { return userAnswerIndex != -1; }
}