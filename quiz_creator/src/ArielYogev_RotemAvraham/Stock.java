package ArielYogev_RotemAvraham;

import java.io.Serializable;

public class Stock implements Serializable {
	private static final long serialVersionUID = -3732920764479198394L;
	final int MAX_QUESTIONES=10;

	public int numOfQuestions;//hard coded
	private int numOfAnswers;
	private String name;
	private Question[]allQuestions;
	private Answer[]allAnswers;

	public Stock (String name) {
		setStockName(name);
		allAnswers=new Answer[numOfAnswers];
		allQuestions=new Question[numOfQuestions];
	}
	
	public void setAllQuestion (Question[]allQuestionesNwe) {
		this.allQuestions=allQuestionesNwe;
		this.numOfQuestions=allQuestionesNwe.length;
	}
	
	public void setStockName(String name) {
			this.name=name;
	}
	
	public boolean checkIfQuestionExistInStock (String question) {
		for (int i=0;i<allQuestions.length;i++ ) {
			if (allQuestions[i].getQuestion().equals(question) ) {
				return false;
			}
		}
		return true;
	}
	
	public void addQuestionToStock(Question q) {
		numOfQuestions=numOfQuestions+1;
		Question[]allQuestionesNew=new Question[numOfQuestions];
		System.arraycopy(allQuestions, 0, allQuestionesNew, 0, numOfQuestions-1);
		allQuestionesNew[numOfQuestions-1]=q;
		allQuestions=allQuestionesNew;
	}
	
	public boolean addAnswerToStock(Answer a) {
		for(int i=0;i<allAnswers.length;i++) {
			if(allAnswers[i]!=null) {
				if(allAnswers[i].getAnswer().equals(a.getAnswer())) {//check that the answer is not exist in the stock
					return false;
				}
			}
		}
		numOfAnswers=numOfAnswers+1;
		Answer[]allAnswersNew=new Answer[numOfAnswers];
		System.arraycopy(allAnswers, 0, allAnswersNew, 0, numOfAnswers-1);//create new array+1 with the new answer
		allAnswersNew[numOfAnswers-1]=a;
		allAnswers=allAnswersNew;
		return true;
	}

	public void removeQuestion(int index) throws Exception {
		if (index > allQuestions.length)
			throw new Exception("index out of bounds");
		allQuestions[index]=null;
		allQuestions[index]=allQuestions[numOfQuestions-1];
		allQuestions[numOfQuestions-1] = null;
		numOfQuestions--;
		Question[]allQuestionesNew=new Question[numOfQuestions];
		System.arraycopy(allQuestions, 0, allQuestionesNew,0,numOfQuestions);
		allQuestions=allQuestionesNew;
	}
	public void setNumOfQuestionesForManual(int numOfQuestiones) throws ExceptionMaxQuestiones {
		if(numOfQuestiones>MAX_QUESTIONES)
			throw new ExceptionMaxQuestiones("you can only choose "+MAX_QUESTIONES+" try again");
		else
			this.numOfQuestions=numOfQuestiones;
	}
	public void counterOfMatchQForAutoTest(int numOfQuestionesForTest) throws Exception {
		int counterflase=0;
		int counterOfMatchQ=0;
		CloseQuestion temp;
		for(int i=0;i<allQuestions.length;i++) {
			if(allQuestions[i] instanceof CloseQuestion) {
				temp=(CloseQuestion)allQuestions[i];
				if(temp.getNumOfAnswers()>=3) {
					for(int j=2;j<temp.getNumOfAnswers();j++) {
						if(temp.getallAnswers()[j].getCorrect()==false) {
							counterflase++;
						}	
					}
				}
				if(counterflase>=3) {
					counterOfMatchQ++;
				}
				counterflase=0;
			}
			else
				counterOfMatchQ++;
		}
		if(counterOfMatchQ<numOfQuestionesForTest) {
			throw new Exception("you can choose maximum " +counterOfMatchQ +" try again");
		}
	}
	public int getNumOfQuestions() {
		return numOfQuestions;
	}
	public Answer[] getAllAnswers() {
		return allAnswers;
	}
	public Question[] getAllQuestiones() {
		return allQuestions;
	}
    public String getName() {
    	return name;
    }
	public String toString() {//show all the questiones with their answers
		StringBuffer str=new StringBuffer();
		for(int i=1;i<=numOfQuestions;i++) {
			if(allQuestions[i-1]!=null && !allQuestions[i-1].equals(""))
				str.append(i+")"+allQuestions[i-1].toString()+"\n");
		}
		return str.toString();
	}
	public String toStringAnswersOnly() {
		StringBuffer str=new StringBuffer();
		for(int i=0;i<allAnswers.length;i++) {
			str.append("["+(i+1)+"]"+getAllAnswers()[i]+"\n");
		}
		return str.toString() ;
	}
}
