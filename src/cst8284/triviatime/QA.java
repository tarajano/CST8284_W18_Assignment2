/**
 * 
 */
package cst8284.triviatime;

/**
 * @author Manuel Alonso Tarajano (tarajano@gmail.com)
 * Mar 28, 2018  
 */
public class QA extends QARequirements {

  private String question, category, explanation;
  private int correctAnswer, points, difficulty;
  private String[] answers;    
  private boolean result;
  
  public QA(  String question, String[] answers,
              String category, String explanation, 
              int difficulty, int points, int correctAnswer){
    this.setQuestion(question);
    this.setAnswers(answers);
    this.setCategory(category);
    this.setExplanation(explanation);
    this.setDifficulty(difficulty);
    this.setPoints(points);
    this.setCorrectAnswerNumber(correctAnswer);
  }

  @Override
  public String getQuestion() {
    return this.question;
  }

  @Override
  public void setQuestion(String question) {
    this.question = question;
  }

  @Override
  public String[] getAnswers() {
    return this.answers;
  }

  @Override
  public void setAnswers(String[] answers) {
    this.answers = answers.clone();
  }

  @Override
  public String getExplanation() {
    return this.explanation;
  }

  @Override
  public void setExplanation(String explanation) {
    this.explanation = explanation;
  }

  @Override
  public String getCategory() {
    return this.category;
  }

  @Override
  public void setCategory(String category) {
    this.category = category;
  }

  @Override
  public int getDifficulty() {
    return this.difficulty;
  }

  @Override
  public void setDifficulty(int difficulty) {
    this.difficulty = difficulty; 
  }

  @Override
  public int getPoints() {
    return this.points;
  }

  @Override
  public void setPoints(int points) {
    this.points = points;
  }

  @Override
  public int getCorrectAnswerNumber() {
    return this.correctAnswer;
  }

  @Override
  public void setCorrectAnswerNumber(int correctAnswer) {
    this.correctAnswer = correctAnswer;
  }

  @Override
  public boolean isCorrect() {
    return this.result;
  }

  @Override
  public void setResult(boolean b) {
    this.result = b;
  }

}
