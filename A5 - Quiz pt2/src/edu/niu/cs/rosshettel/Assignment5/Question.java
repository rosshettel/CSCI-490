/****************************************************************
   PROGRAM:   Assignment 4
   AUTHOR:    Ross Hettel, John Miller, Alex Wohead
   LOGON ID:  Z1549355, Z159807, Z1624450
   DUE DATE:  11/02 at class time

   FUNCTION:  The Question class is an object that holds the question text,
   			  possible answers, and correct answer. Also includes a function to check
   			  if the submitted answer is the right one.

   INPUT:     none

   OUTPUT:    none

   NOTES:     The get and set methods are self-explantory and therefore do not
   			  contain much commenting.
****************************************************************/
package edu.niu.cs.rosshettel.Assignment5;

public class Question {
	String questionText;
	String[] candidates;
	int rightAnswer;
	
	Question() 
	{
		candidates = new String[4];
	}
	
	public void setQuestion(String question)
	{
		questionText = question;
	}
	
	public String getQuestion()
	{
		return questionText;
	}
	
	public void setCandidates(int index, String candidate)
	{
		candidates[index] = candidate;
	}
	
	public String getCandidate(int index)
	{
		return candidates[index];
	}
	
	public void setAnswer(int answer)
	{
		rightAnswer = answer;
	}
	
	/* check answer if it's right and return the appropriate bool */
	public boolean checkAnswer(int answer)
	{
		if(answer == rightAnswer)
			return true;
		else
			return false;
	}
}
