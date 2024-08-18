package ArielYogev_RotemAvraham;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Exam implements Examable,Serializable  {
	private static final long serialVersionUID = -3732920764479198394L;

	final int MAX_ANSWERS=4;
	final int MAX_QUESTIONES=1;
	protected int numOfQuestiones;
	protected Question[]examQ;
	protected int counterQ=0;
	protected Question q;
	protected String examName;
	CloseQuestion closeQuestion ;
	OpenQuestion op;
	public Exam(int numOfQuestiones,String examName) throws ExceptionMaxQuestiones {
		this.numOfQuestiones=numOfQuestiones;
		examQ=new Question[numOfQuestiones];
		this.examName =examName;
	}
	public int getMaxQuestion() {
		return MAX_QUESTIONES;
	}
	public void printExam(Question[]examQ) throws FileNotFoundException {
		LocalDateTime testDate = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
		String formattedDateTime = testDate.format(formatter);
		String fileNameExam = "exam_" + formattedDateTime + ".txt";
		String fileNameSolution = "solution_" + formattedDateTime + ".txt";
		File exam= new File(fileNameExam);
		File solution= new File(fileNameSolution);
		PrintWriter ex = new PrintWriter(exam);
		PrintWriter sl = new PrintWriter(solution);
		for ( int i =0;i<examQ.length;i++) {
			if (examQ[i] instanceof CloseQuestion ) {
				closeQuestion= (CloseQuestion)examQ[i];
				ex.print(i+1+")"+closeQuestion.getQuestion()+"\n");
				sl.print(i+1+")"+closeQuestion.getQuestion()+"#"+closeQuestion.getLevel()+"\n");
				for ( int j=0;j<closeQuestion.getallAnswers().length;j++) {
					if(closeQuestion.getallAnswers()[j]!= null) {
					ex.println("("+(j+1)+")"+closeQuestion.getallAnswers()[j].getAnswer());
					sl.println("("+(j+1)+")"+closeQuestion.getallAnswers()[j].toString());
					}
				}
			}
			else {
				op=(OpenQuestion)examQ[i];
				ex.println(i+1+")"+op.getQuestion());
				ex.println("write your answer: ___________________________________");
				sl.println(i+1+")"+op.getQuestion());
				sl.println(op.getAnswer().toString());
			}
			ex.println();
			sl.println();
		}
		ex.close();
		sl.close();
	}

	public  boolean checkIfQuestionExistInTest(Stock s,int indexQ,Question[]examQ ) {
		for(int i=0;i<examQ.length;i++) {
			if(examQ[i]!=null) {
				if(examQ[i].getQuestion().equals(s.getAllQuestiones()[indexQ].getQuestion()))
					return false;	
			}
		}
		return true;
	}
}
