public class Quizz {

    final private String Question;
    final private String Answer;

    public Quizz(String Question, String Answer) {
            this.Question = Question;
            this.Answer = Answer;
        }

    public String getQuestion() {
        return Question;
    }

    public String getAnswer() {
        return Answer;
    }

    public boolean _isCorrectAnswer(String Answer) {
        return Answer.equals(Answer);
    }
}
