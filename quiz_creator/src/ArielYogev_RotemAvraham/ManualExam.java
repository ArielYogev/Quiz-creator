package ArielYogev_RotemAvraham;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ManualExam extends Exam implements Examable{
	private static final long serialVersionUID = -3732920764479198394L;
	final int MAX_QUESTIONES=10;

	private Question[]examQ;
	private int userIndexQuestion;

	public ManualExam(int numOfQuestionsTest,String examName) throws ExceptionMaxQuestiones {
		super(numOfQuestionsTest,examName);
	}
	public Scanner sc=new Scanner (System.in); 

	//Without default answers.check if number of answer out of range
	public int checkAnswerIndexToQuestion(int IndexAnswer ,int indexQuestion ,Stock s) throws Exception {
		while (IndexAnswer>((CloseQuestion)s.getAllQuestiones()[indexQuestion-1]).getNumOfAnswers()-2||IndexAnswer<0||indexQuestion<0) {
			IndexAnswer = InputException.inputMisMatchExpc("the answer number you enter is out of range , please enter a diffrent number");
		}
		return IndexAnswer ;
	}
	public CloseQuestion QuestionAndAnswersToFile(int indexQ,Stock s) throws Exception {
		int numOfAnswers;
		int indexAnswer = 0;
		CloseQuestion currentQ=(CloseQuestion) s.getAllQuestiones()[indexQ];
		CloseQuestion newQuestion=new CloseQuestion(currentQ.getQuestion(), currentQ.getLevel());
		numOfAnswers=InputException.inputMisMatchExpc("how many answers do you want to add from the question you choose?");
		while(numOfAnswers>newQuestion.getNumOfAnswers()) {
			numOfAnswers=InputException.inputMisMatchExpc("index out of range,try again");
		}
		//QuestionAnswer[]allAnswers=new QuestionAnswer[numOfAnswers+2];
		for(int i=2;i<numOfAnswers+2;i++) {
			indexAnswer=InputException.inputMisMatchExpc("enter number of answer");
			indexAnswer=checkAnswerIndexToQuestion(indexAnswer,userIndexQuestion,s);
			while(!newQuestion.checkIfAnswerExist(currentQ.getallAnswers()[indexAnswer+1])) {
				System.out.println("answer exist.try again");
				indexAnswer=sc.nextInt();
				indexAnswer=checkAnswerIndexToQuestion(indexAnswer,userIndexQuestion,s);
			}
			newQuestion.addAnswer(currentQ.getallAnswers()[indexAnswer+1]);
		}
		newQuestion.countToFAnswers(newQuestion.getallAnswers());
		return newQuestion;
	}
	public boolean checkValidQuestionForTest(int userIndexQuestion,Stock s) {
		CloseQuestion newq;
		int numOfAnswers;
		if(s.getAllQuestiones()[userIndexQuestion-1] instanceof CloseQuestion) {
			try {
				newq=(CloseQuestion)s.getAllQuestiones()[userIndexQuestion-1];
				numOfAnswers=newq.getNumOfAnswers();
				if(numOfAnswers-2<MAX_ANSWERS) {
					throw new ExceptionMaxAnswers("you can only choose question with minimum 4 answers,try again");
				}
			}
			catch (ExceptionMaxAnswers e) {
				System.out.println(e.getMessage());
				return false;	
			}

			return true;		
		}
		else
			return true;
	}
	
	@Override
	public void createExam(Stock s) throws Exception ,FileNotFoundException{
		LocalDateTime testDate = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
		String formattedDateTime = testDate.format(formatter);
		String fileNameExam = "exam_" + formattedDateTime + ".txt";
		String fileNameSolution = "solution_" + formattedDateTime + ".txt";
		File exam= new File(fileNameExam);
		File solution= new File(fileNameSolution);
		PrintWriter ex = new PrintWriter(exam);
		PrintWriter sl = new PrintWriter(solution);
		int counterOfQ=1;
		System.out.println("those are all the questiones with their answers");
		System.out.println(s.toString());
		int numOfQuestionsTest=numOfQuestiones;
		Question[]examQ=new Question[numOfQuestionsTest];
		CloseQuestion newQuestion;
		for(int i=0;i<numOfQuestiones;i++) {
			userIndexQuestion=InputException.arrayBoundsExcp(s.getAllQuestiones(),"please choose one question");
			while(!checkIfQuestionExistInTest(s,userIndexQuestion-1,examQ))
			{
				userIndexQuestion=InputException.arrayBoundsExcp(s.getAllQuestiones(),"question already in test or not exist,please choose again");
			}
			if(s.getAllQuestiones()[userIndexQuestion-1] instanceof CloseQuestion) {
				while(!checkValidQuestionForTest(userIndexQuestion, s)||!checkIfQuestionExistInTest(s,userIndexQuestion-1,examQ)) {
					userIndexQuestion=InputException.arrayBoundsExcp(s.getAllQuestiones(),"please choose again");
				}
				newQuestion=QuestionAndAnswersToFile(userIndexQuestion-1,s);
				examQ[i]=newQuestion;
				ex.print(i+1+")"+newQuestion.getQuestion()+"\n");
				sl.print(i+1+")"+newQuestion.getQuestion()+"#"+s.getAllQuestiones()[userIndexQuestion-1].getLevel()+"\n");

				for(int j=0;j<newQuestion.getNumOfAnswers();j++) {
					ex.println("("+(j+1)+")"+newQuestion.getallAnswers()[j].getAnswer());
					sl.println("("+(j+1)+")"+newQuestion.getallAnswers()[j].toString());
				}
			}
			else {
				examQ[i]=s.getAllQuestiones()[userIndexQuestion-1];
				ex.println(counterOfQ+")"+s.getAllQuestiones()[userIndexQuestion-1].getQuestion());
				ex.println("write your answer: ___________________________________");
				OpenQuestion op=(OpenQuestion) s.getAllQuestiones()[userIndexQuestion-1];
				sl.println(counterOfQ+")"+s.getAllQuestiones()[userIndexQuestion-1].getQuestion());
				sl.println(op.getAnswer().toString());
			}
			ex.println();
			sl.println();
			counterOfQ++;
		}
		ex.close();
		sl.close();

		System.out.println("your test is ready! thank you");				
	}
}
