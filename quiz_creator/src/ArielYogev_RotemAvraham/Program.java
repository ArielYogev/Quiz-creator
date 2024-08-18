package ArielYogev_RotemAvraham;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
import ArielYogev_RotemAvraham.Question.eDifficulty;

public class Program implements Serializable {
	private static final long serialVersionUID = -3732920764479198394L;
	public static Scanner sc=new Scanner (System.in); 
	//replace answer in open question
	public static void replaceAnswerForOpenQ(Stock s, int userIndexQuestion, int index) throws Exception {
		if (index==1) {
			addAnswerToOpenQuestion( s,(OpenQuestion)s.getAllQuestiones()[userIndexQuestion-1]);
		}
		else 
			System.out.println("back to menu :)");	
	}
	//user check Difficulty
	public static eDifficulty checkDiffculty() throws Exception {

		int index;
		Question.eDifficulty[]allLevels=Question.eDifficulty.values();
		for(int i=0;i<allLevels.length;i++) {
			System.out.println((allLevels[i].ordinal()+1)+")"+ allLevels[i].name());
		}
		index=InputException.inputMisMatchExpc("please pick difficulty level:");
		while (index !=1 && index !=2 && index!=3) {
			index=InputException.inputMisMatchExpc("please pick number from 1 to 3");
		}
		return allLevels[index-1];	
	}
	//check If Question Exist In Test
	public static boolean checkIfQuestionExistInTest (int []arrTest,int indexOfQuestion) {
		for ( int i = 0 ; i < arrTest.length; i++) {
			if(arrTest[i] != 0) {
				if ( arrTest[i] == indexOfQuestion) {
					System.out.println("question is already exist.try again");
					return false ;
				}	
			}
		}
		return true ;
	}
	//add answer and her boolean value.check that number of answers for questioners not out of bounds
	public static void addNewToFQuestion(Stock s,int userIndexQuestion) throws Exception {

		QuestionAnswer questionAnswer ;
		int userIndexAnswer;
		boolean isCorrect;
		CloseQuestion c=(CloseQuestion)s.getAllQuestiones()[userIndexQuestion-1];
		System.out.println(s.toStringAnswersOnly());		
		userIndexAnswer=InputException.arrayBoundsExcp(s.getAllAnswers(), "those are the all answers,please choose one to add:");
		while(!((CloseQuestion) s.getAllQuestiones()[userIndexQuestion-1]).checkIfAnswerExist(s.getAllAnswers()[userIndexAnswer-1])) {
			System.out.println("answer already exist");
			userIndexAnswer =InputException.arrayBoundsExcp(s.getAllAnswers(),"please enter another option");
		}	
		isCorrect=InputException.booleanExcp("is this answer true or false?");
		questionAnswer=new QuestionAnswer(s.getAllAnswers()[userIndexAnswer-1].getAnswer(), isCorrect);	
		System.out.println("answer added succesfully");
		((CloseQuestion) s.getAllQuestiones()[userIndexQuestion-1]).addAnswer(questionAnswer);	
	}
	//check if answer exist for specific question in test
	public static boolean checkIfAnswerExistInTest(QuestionAnswer[] allAnswers ,QuestionAnswer answer) {
		for ( int i = 0 ;i<allAnswers.length;i++) {
			while(allAnswers[i]==answer) {
				return false;
			}
		}
		return true ;
	}
    //add answer To Open Question
	public static void addAnswerToOpenQuestion( Stock s, OpenQuestion openQuestion) throws Exception {
		int index;
		String answerOpenQuestion ;
		boolean isOk =false;
		int choice=InputException.inputMisMatchExpc("please choose one option:\n (1) for choose answer from stock \n (2) for type your own answer"	);
		while(!isOk) {

			switch (choice) {
			case 1:
				System.out.println(s.toStringAnswersOnly());
				index = InputException.arrayBoundsExcp(s.getAllAnswers(),"please choose answer from stock");
				System.out.println("question added succefuly");
				Answer ans= new Answer(s.getAllAnswers()[index-1].getAnswer());
				openQuestion.setAnswer(ans);
				s.addAnswerToStock(ans);
				s.addQuestionToStock(openQuestion);
				isOk =true;
				break;
			case 2:
				System.out.println("type your answer:");
				answerOpenQuestion=sc.next();
				ans= new Answer(answerOpenQuestion);
				openQuestion.setAnswer(ans);
				System.out.println("question added succefuly");
				s.addAnswerToStock(ans);
				s.addQuestionToStock(openQuestion);
				isOk =true;
				break;
			default:
				System.out.println("Invalid option");
				choice=InputException.inputMisMatchExpc("press 1 or 2"	);

				break;
			}
		}
	}
    // counting the match questioners for auto test, at least 3 answers
	public static Question[] counterOfMatchQForAutoTest(Stock s,int numOfQuestionesForTest) throws Exception {
		int counterflase=0;
		int counterOfMatchQ=0;
		CloseQuestion temp;
		Question[]newArr = new Question[s.getNumOfQuestions()];
		System.arraycopy(s.getAllQuestiones(), 0, newArr, 0, s.getNumOfQuestions());
		for(int i=0;i<s.getNumOfQuestions();i++) {
			if(s.getAllQuestiones()[i] instanceof CloseQuestion) {
				temp=(CloseQuestion)s.getAllQuestiones()[i];
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
				else {
					newArr[i]=newArr[newArr.length-1];
					newArr[newArr.length-1]=null;
				}
				counterflase=0;
			}
			else {
				counterOfMatchQ++;

			}
		}
		Question[]afterRemove =new Question[counterOfMatchQ];
		System.arraycopy(newArr, 0, afterRemove,0,counterOfMatchQ);

		if(counterOfMatchQ<numOfQuestionesForTest) {
			throw new Exception("you can choose maximum " +counterOfMatchQ +" try again");
		}
		return afterRemove;
	}
	// add new stock to all stocks array
	public static Stock[] addNewStock(Stock []allStocks,Stock s) {
		int numOfStocks=allStocks.length;
		numOfStocks++;
		Stock[]allStocksNew=new Stock[numOfStocks];
		System.arraycopy(allStocks, 0, allStocksNew, 0, numOfStocks-1);
		allStocksNew[numOfStocks-1]=s;
		allStocks=allStocksNew;
		return allStocksNew;
	}
	//remove stock from array- hard coded use
	public static Stock[] removeStock(Stock []allStocks,int index) {
		allStocks[index-1]=null;
		allStocks[index-1]=allStocks[allStocks.length-1];
		allStocks[allStocks.length-1] = null;
		Stock[]allStocksNew=new Stock[allStocks.length-1];
		System.arraycopy(allStocks, 0, allStocksNew,0,allStocks.length-1);
		allStocks=allStocksNew;
		return allStocks;
	}


	public static void main(String[] args) throws Exception { 
		//hard coded//
		Stock general=new Stock("general");//for default stock for start
		
		//-----------------read from file----------------------
		Stock[]allStocks;
		ObjectInputStream inFile=new ObjectInputStream(new FileInputStream("Stocks.dat"));
		allStocks=(Stock[])inFile.readObject();
		inFile.close();
		//----------------------------------------------------
		
		//---------------------menus----------------------------
		final  int MAX_QUESTIONES=10;//for test
		final int EXIT=0;
		int choose;
		int numOfAnswers;
		Answer answer;
		CloseQuestion closeQuestion;
		OpenQuestion openQuestion;
		int userIndexQuestion = 0;
		int userIndexAnswer = 0;
	//	QuestionAnswer[]allAnswers;
		int numOfQuestionsTest = 0 ;
		String question ;
		eDifficulty theLevel ;
		int index ;
		//int choice;
		//String answerOpenQuestion;
		String answerUser;
		Stock s;
		Stock tempStock;
		Boolean createStock=false;
		int indexStock = 0;
		//Scanner str=new Scanner (System.in); 
        //--------------first menu---------------------------		
		System.out.println("Choose one of the following options:");
		System.out.println("1) add new stock");
		System.out.println("2) work with existing stock");
		System.out.println(EXIT + ") Exit");
		choose = InputException.inputMisMatchExpc("Enter your choice --> ");

		switch (choose) {
		case 1: 
			createStock=true;
			System.out.println("enter name for your new stock");
			answerUser = sc.next();
			tempStock=new Stock(answerUser);
			s=tempStock;
			indexStock=choose;
			choose=0;
			break;
		case 2:
			for(int i=0;i<allStocks.length;i++) {
				System.out.println(i+1+")"+allStocks[i].getName());
			}
			choose = InputException.inputMisMatchExpc("please choose one stock");
			s=allStocks[choose-1];
			indexStock=choose;
			choose=0;
			break;
		case EXIT:
			System.out.println("continue to the main menu---->");
			s=general;

			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + choose);
		}
        //--------------second menu---------------------------		

		do {
			System.out.println();
			System.out.println("Choose one of the following options:");
			System.out.println("1) show all the questiones and their optional answers");
			System.out.println("2) Add new answer to stock");
			System.out.println("3) add new answer to existing question ");
			System.out.println("4) add new question to stock");
			System.out.println("5) remove/replace answer from question");
			System.out.println("6) remove question from the stock");
			System.out.println("7) create new test");
			System.out.println(EXIT + ") Exit");
			choose = InputException.inputMisMatchExpc("Enter your choice --> ");
			switch (choose) {
			case 1:
				System.out.println(s.toString());	
				break;
			case 2:
				System.out.println("type your new answer");
				sc.nextLine();
				answerUser=sc.nextLine();
				answer=new Answer(answerUser);
				if (!s.addAnswerToStock(answer)) {
					System.out.println("answer already exist");
				}
				else {
					System.out.println("answer added succsefuly");
				}
				System.out.println(s.toStringAnswersOnly());
				break;
			case 3:
				System.out.print(s.toString());	
				if(s.getAllQuestiones().length!=0) {
					userIndexQuestion =InputException.arrayBoundsExcp(s.getAllQuestiones(),"please enter number of question to add your new answer");
					if(s.getAllQuestiones()[userIndexQuestion-1] instanceof OpenQuestion) {
						index=InputException.inputMisMatchExpc("not possible to add answer.if you want to replace answer press(1)");
						replaceAnswerForOpenQ(s,userIndexQuestion,index);	
					}
					else
						addNewToFQuestion(s,userIndexQuestion);	
				}
				else
					System.out.println("there is no questiones in stock please add first");

				break;
			case 4:
				Scanner scan=new Scanner (System.in); 
				index = InputException.inputMisMatchExpc("which question do you want to add? \n press (1) for multiple choice \n press (2) for open question");
				while (index !=1 && index !=2) {
					index = InputException.inputMisMatchExpc("this is not one of the optiones, please choose again");
				}
				System.out.println("please enter your new question");
				question=sc.nextLine();
				while(!s.checkIfQuestionExistInStock(question)) {
					System.out.println("question already exist,please try again");
					question=sc.nextLine();
				}
				theLevel=checkDiffculty();
				if (index==1) {
					closeQuestion=new CloseQuestion(question,theLevel);
					s.addQuestionToStock(closeQuestion);
					numOfAnswers=InputException.inputMisMatchExpc("how many answers do you want to add?(maximum 8 answers)");
					try {
						for(int i=0;i<numOfAnswers;i++) {
							addNewToFQuestion(s,s.getNumOfQuestions());
						}
					}
					catch (Exception e) {
						System.out.println(e.getMessage());
					}
				}
				else {
					openQuestion = new OpenQuestion(question, theLevel);
					addAnswerToOpenQuestion( s, openQuestion);
				}
				break;  
			case 5:
				System.out.print(s.toString());	
				if(s.getAllQuestiones().length!=0) {
					userIndexQuestion=InputException.arrayBoundsExcp(s.getAllQuestiones(),"please enter the number of the question you want to remove answer from");
					if (s.getAllQuestiones()[userIndexQuestion-1]instanceof OpenQuestion) {
						index=InputException.inputMisMatchExpc("not possible to add answer.if you want to replace answer press(1)");
						replaceAnswerForOpenQ(s,userIndexQuestion,index);
					}
					else {
						CloseQuestion cq=(CloseQuestion) s.getAllQuestiones()[userIndexQuestion-1];
						userIndexAnswer=InputException.inputMisMatchExpc("please enter the answer number you want to remove");
						try{
							((CloseQuestion) s.getAllQuestiones()[userIndexQuestion-1]).removeAnswer(userIndexAnswer);	

							System.out.println("answer removed succsefuly");
						}catch(Exception e) {
							System.out.println(e.getMessage());
						}
					}
				}
				else
					System.out.println("there is no questiones in stock please add first");
				break;
			case 6:
				System.out.print(s.toString());	
				boolean isOk=false;
				if(s.getAllQuestiones().length!=0) {
					while(!isOk) {
						try{
							userIndexQuestion=sc.nextInt();

							if(userIndexQuestion <0)
								throw new Exception("negative number,try again");

							s.removeQuestion(userIndexQuestion-1);
							System.out.println("question removed succsefuly");
							isOk=true;
						}
						catch(InputMismatchException e) {
							System.out.println( "enter only a number,try again");
							sc.nextLine();

						}
						catch (Exception e) {
							System.out.println("index " +userIndexQuestion + " is out of range, try again");
							sc.nextLine();
						}
					}
				}
				else
					System.out.println("there is no questiones in stock please add first");
				break;
			case 7:
				index=InputException.inputMisMatchExpc("choose one of the following options: \n (1) for automtaic exam \n (2)for manual exam");
				while(index!=1&&index!=2) {
					index=InputException.inputMisMatchExpc("try again 1 or 2");
				}
				isOk=false;

				if(index==1){//automatic exam
					Stock newStock = new Stock ("forTest");
					while(!isOk) {
						try {
							numOfQuestionsTest=InputException.inputMisMatchExpc("how many quetiones do you want in your test?");
							if (numOfQuestionsTest>MAX_QUESTIONES)
								throw new ExceptionMaxQuestiones("you can add only 10 questiones");
							Question newQuestiones[]=counterOfMatchQForAutoTest(s,numOfQuestionsTest);
							newStock.setAllQuestion(newQuestiones);
							if(numOfQuestionsTest<0)
								throw new Exception("negative number please try again");

							if (numOfQuestionsTest>s.getAllQuestiones().length)
								throw new Exception("there is not enough questiones in stock");
							isOk=true;
						}
						catch (InputMismatchException e) {
							System.out.println("enter only a number,try again");
						}
						catch (ExceptionMaxQuestiones e) {
							System.out.println(e.getMessage());
						}
						catch (Exception e) {
							System.out.println(e.getMessage());
						}
					}
					AutomaticExam a = new AutomaticExam(numOfQuestionsTest, "exam");
					a.createExam(newStock);
				}

				if(index==2) {	
					while(!isOk) {
						try {
							numOfQuestionsTest=InputException.inputMisMatchExpc("how many quetiones do you want in your test?");
							if (numOfQuestionsTest>MAX_QUESTIONES)
								throw new ExceptionMaxQuestiones("you can add only 10 questiones");
							if(numOfQuestionsTest<0)
								throw new Exception("negative number please try again");
							if (numOfQuestionsTest>s.getAllQuestiones().length)
								throw new Exception("there is not enough questiones in stock");
							isOk=true;
						}
						catch (InputMismatchException e) {
							System.out.println("enter only a number,try again");
						}
						catch (ExceptionMaxQuestiones e) {
							System.out.println(e.getMessage());
						}
						catch (Exception e) {
							System.out.println(e.getMessage());
						}

					}
					ManualExam m=new ManualExam(numOfQuestionsTest,"exam");
					m.createExam(s);
				}
							
			case EXIT:
				System.out.println("Goodbye!");
				break;
			default:
				System.out.println("Invalid option");
				break;
			}
		}	
		while (choose != EXIT);
		if(createStock) {
			allStocks=addNewStock(allStocks, s);//add new stock to the all stocks array
		}
		else {
			allStocks[indexStock-1]=s;//in case of changing the stock,update stock in array
		}
		//--------------------write stock-----------------------------------
		ObjectOutputStream outFileNew=new ObjectOutputStream(new FileOutputStream(s.getName()+".dat"));
		outFileNew.writeObject(s);
		outFileNew.close();
		ObjectOutputStream outFileNew2=new ObjectOutputStream(new FileOutputStream("Stocks.dat"));
		outFileNew2.writeObject(allStocks);
		outFileNew2.close();
		System.out.println("thank you!,bye bye");	
	}
}

