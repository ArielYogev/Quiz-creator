package ArielYogev_RotemAvraham;

import java.io.Serializable;

public class Answer implements Serializable {
	 protected String answer;
	 
	 public Answer(String answer) {
		 setAnswer(answer);
	 }
	 public void setAnswer(String answer) {
			 this.answer=answer;
	 }
	 public String getAnswer() {
		 return answer;
	 }
	 public String toString() {
		return answer;
	 }
}
