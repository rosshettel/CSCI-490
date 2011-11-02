package edu.niu.cs.rosshettel.Assignment4;


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
	
	public boolean checkAnswer(int answer)
	{
		if(answer == rightAnswer)
			return true;
		else
			return false;
	}
}
