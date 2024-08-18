package ArielYogev_RotemAvraham;

import java.io.Serializable;

public class CloseQuestion extends Question implements Serializable {
	private static final long serialVersionUID = -3732920764479198394L;

	public final int MAX_ANSWERS = 10;
	private int numOfAnswers;
	private QuestionAnswer[] allAnswers = new QuestionAnswer[MAX_ANSWERS];
	private int counter;
	int currentIndex =2;
	int minus=1;

	public CloseQuestion(String question,eDifficulty theLevel) throws Exception {																			
		super(question, theLevel);
		allAnswers=setDeafultAnswers();
	}
	public QuestionAnswer[] setDeafultAnswers() {//insert default answers to array-method for test only
		Answer a1=new Answer("there is more than one correct answer");
		allAnswers[0]=new QuestionAnswer(a1.getAnswer(), false);
		Answer a2=new Answer("none of the followings answers are true");
		allAnswers[1]=new QuestionAnswer(a2.getAnswer(), false);
		numOfAnswers=numOfAnswers+2;
		return allAnswers;
	}
	public void setAllAnswersArray(QuestionAnswer[]old) {
		allAnswers=old;
	}
	public boolean addAnswer(QuestionAnswer newAnswer) throws Exception {// gets new object type answer
		if (numOfAnswers+1 >= MAX_ANSWERS && numOfAnswers < 0) {
			throw new Exception("you can't fill more than 8 answers");	
		}

		for(int i=0;i<allAnswers.length;i++) {
			if(allAnswers[i]!=null) {
				if(allAnswers[i].getAnswer().equals(newAnswer.getAnswer())) 						
					return false;
			}
		}
		numOfAnswers++;
		allAnswers[numOfAnswers-1] = newAnswer;
		return true;

	} 
	public boolean checkIfAnswerExist(Answer answer) {
		for(int i=0;i<numOfAnswers;i++) {
			if(allAnswers[i].getAnswer().equals(answer.getAnswer()))
				return false;	  
		}

		return true;
	}

	public void countToFAnswers(QuestionAnswer []allAnswers) {//methode for test only.count how many true\false answers for deafult answers
		for(int i=2;i<allAnswers.length;i++) {
			if (allAnswers[i]!=null) 
			if(allAnswers[i].getCorrect()==true)
				counter++;
		}
		if(counter>1||counter==0) {
			for(int i=2;i<allAnswers.length;i++) {//i=2 because arr[0] and arr[1] are deafoltives answers 
				if (allAnswers[i]!=null)
				allAnswers[i].setCorrect(false);
			}
			if(counter>1)
				allAnswers[0].setCorrect(true);
			if(counter==0)
				allAnswers[1].setCorrect(true);
		}
	}
	public void removeAnswer(int indexOfAnswer) throws Exception {// remove by index from user array
		if (indexOfAnswer > allAnswers.length)
			throw new Exception("there is no enough answers in stock, try again");
		allAnswers[indexOfAnswer+1] = null;
		allAnswers[indexOfAnswer+1] = allAnswers[numOfAnswers-1];
		numOfAnswers--;
	}

	public QuestionAnswer[]getallAnswers() {
		return allAnswers;
	}

	public int getNumOfAnswers() {
		return numOfAnswers;
	}
	public int getMaxAnswers() {
		return MAX_ANSWERS;
	}
	public void setArrayForTest(QuestionAnswer[]oldArr, int maxAnswers) {//for automatic
		QuestionAnswer[]newArr=new QuestionAnswer[maxAnswers];
		System.arraycopy(oldArr, 1, newArr, 0, maxAnswers);
		this.allAnswers=newArr;
		this.numOfAnswers=maxAnswers;
		currentIndex=0;
		minus=-1;
	}
	@Override
	public String toString() {// show the question and all her answers
		StringBuffer str = new StringBuffer();
		str.append("id is:#"+id +" "+ question + " #difficulty level:" + theLevel + "\n");
		for (int i=currentIndex ; i <numOfAnswers ; i++) {//i=2 because arr[0] and arr[1] are deafoltives answers 
			if(allAnswers[i]!=null||!allAnswers[i].equals(""))
				str.append("[" + (i-minus) + "]" + allAnswers[i].toString() + "\n");
		}
		return str.toString();
	}
}
