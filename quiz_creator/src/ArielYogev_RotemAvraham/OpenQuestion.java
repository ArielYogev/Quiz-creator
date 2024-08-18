package ArielYogev_RotemAvraham;

public class OpenQuestion extends Question{
	private Answer answer;

	public OpenQuestion(String question, eDifficulty theLevel) throws Exception {
		super(question, theLevel);
	}
	public void setAnswer(Answer answer) {//add ans replace in the same function
		this.answer=answer;
	}
	public Answer getAnswer () {
		return answer;
	}
	@Override
	public String toString() {
		return "id is:#"+id +" "+ question + " #difficulty level:" + theLevel  + "\n answer is :" + answer +"\n" ;
	}
}
