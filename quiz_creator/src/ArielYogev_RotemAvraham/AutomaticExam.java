package ArielYogev_RotemAvraham;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

public class AutomaticExam  extends Exam implements Examable, Serializable{
	private static final long serialVersionUID = -3732920764479198394L;
	final int MAX_ANSWERS=4;

	Random random=new Random();
	private int indexQ;
	private int indexAns;
	private Question currentQ;

	public AutomaticExam(int numOfQuestiones,String examName) throws ExceptionMaxQuestiones {
		super(numOfQuestiones,examName);
	}

	public boolean checkIfQuesMatchForTest(CloseQuestion currentQ, Stock s) {
		int counter=0;
		for(int i=1;i<currentQ.getNumOfAnswers();i++) {
			if(currentQ.getNumOfAnswers()<=3)
				return false;
			if(currentQ.getallAnswers()[i].getCorrect()==false) {
				counter++;
			}	
		}
		if(counter>=3)
			return true;
		else
			return false;
	}
	public int randInt(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}
	@Override
	public void createExam(Stock newS) throws  Exception   {
		CloseQuestion newQuestionToTest = null;
		CloseQuestion temp = null;
		int conterTrue = 0;
		int counterFalse=0;

		for(int i=0;i<examQ.length;i++) {//question
			indexQ=randInt(0, newS.getAllQuestiones().length-1);//choose index of question from the big stock
			currentQ=newS.getAllQuestiones()[indexQ];	
			newS.removeQuestion(indexQ);//remove question from copy array 
			if(currentQ instanceof CloseQuestion) {
				temp=(CloseQuestion)currentQ;
				try {
					newQuestionToTest=new CloseQuestion(temp.getQuestion(), temp.getLevel());
					newQuestionToTest.setId(temp);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				for(int j=0;j<MAX_ANSWERS-1;j++) {//answers
					indexAns=random.nextInt(2,temp.getNumOfAnswers());
					if(j!=0) {
						while (!(newQuestionToTest.checkIfAnswerExist(temp.getallAnswers()[indexAns]))) {
							indexAns=random.nextInt(2,temp.getNumOfAnswers()-1);
						}
					}
					if(temp.getallAnswers()[indexAns].getCorrect()==true) {
						conterTrue++;
					}
					else
						counterFalse++;
					if(conterTrue <= 1) {
						try {
							newQuestionToTest.addAnswer(temp.getallAnswers()[indexAns]);
						} catch (Exception e) {
							e.printStackTrace();
						}	
					}
					else if(temp.getallAnswers()[indexAns].getCorrect()==false){
						try {
							newQuestionToTest.addAnswer(temp.getallAnswers()[indexAns]);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					else
						j--;
				}
				if(counterFalse==MAX_ANSWERS-1) {
					newQuestionToTest.getallAnswers()[1].setCorrect(true);
				}
				conterTrue=0;
				counterFalse=0;
				newQuestionToTest.setArrayForTest(newQuestionToTest.getallAnswers(), MAX_ANSWERS);
				examQ[i]=(Question)newQuestionToTest;
			}
			else {//add open question
				examQ[i]=(OpenQuestion)currentQ;
			}
		}
		printExam(examQ);
	}	
}
