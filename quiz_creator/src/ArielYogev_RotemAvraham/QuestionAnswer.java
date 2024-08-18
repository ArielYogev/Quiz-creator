package ArielYogev_RotemAvraham;

import java.io.Serializable;

public class QuestionAnswer extends Answer implements Serializable {
	private static final long serialVersionUID = -3732920764479198394L;

	private boolean IsCorrect;

	public QuestionAnswer(String answer, boolean IsCorrect) {
		super(answer);
		setCorrect(IsCorrect);
	}

	public boolean setCorrect(boolean IsCorrect) {
		if (IsCorrect) {
			this.IsCorrect = true;
			return true;
		} else if (!IsCorrect) {
			this.IsCorrect = false;
			return true;
		} else {
			return false;
		}
	}

	public boolean getCorrect() {
		return IsCorrect;
	}

	public String toString() {
		return super.toString() + "--->" + IsCorrect;
	}

}
