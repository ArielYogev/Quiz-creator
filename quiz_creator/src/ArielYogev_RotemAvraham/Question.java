package ArielYogev_RotemAvraham;

import java.io.Serializable;

public abstract class Question implements Serializable {//abstract because no such a question object
	private static final long serialVersionUID = -3732920764479198394L;

	protected String question ;
	protected static int counter=0;
	protected int id;
	protected enum eDifficulty {Hard ,Medium,Easy};
	protected eDifficulty theLevel ;

	public Question(String question,eDifficulty theLevel) {
		this.question=question;
		id=++counter;
		this.theLevel=theLevel;
	}

	public int getId () {
		return id ;
	}
	public String getQuestion() {
		return question;
	}
	public eDifficulty getLevel() {
		return theLevel;
	}
	public void setId (Question qOld) {
		id=qOld.id;
		counter--;	
	}
	public abstract String toString () ;	
}
