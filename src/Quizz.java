public class Quizz {

    final private String Question;
    final private String Answer;
    final private int Value;

    public Quizz(String Question, String Answer, int value) {
            this.Question = Question;
            this.Answer = Answer;
            this.Value = value;
    }

    public String getQuestion() {
        return Question;
    }

    public String getAnswer() {
        return Answer;
    }

    public int getValue(){
        return Value;
    }

    public boolean _isCorrectAnswer(String Answer) {
        return Answer.equalsIgnoreCase(Answer);
    }
}
