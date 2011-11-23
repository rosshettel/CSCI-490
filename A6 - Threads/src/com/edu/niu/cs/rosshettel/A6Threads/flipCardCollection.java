/**
 * 
 */
package com.edu.niu.cs.rosshettel.A6Threads;

import java.util.Arrays;
import java.util.List;

import android.R.string;

/**
 * @author NIU-STUDENT
 *
 */
public class flipCardCollection {
	//the cards
	private class card {
		public string frontText;
		public string backText;
	}
	
	private card[] cards;
	private int cardIndex;
	
	public flipCardCollection(int size)
	{
		//initialize the new card array
		cards = new card[size];
	}
	
	public void setCardFrontText(string text)
	{
		cards[cardIndex].frontText = text;
	}
	
	public void setCardFrontText(string text, int cardIndex)
	{
		cards[cardIndex].frontText = text;
	}
	
	public void setCardBackText(string text)
	{
		cards[cardIndex].backText = text;
	}
	
	public void setCardBackText(string text, int cardIndex)
	{
		cards[cardIndex].backText = text;
	}
		
	public string getFlipText(string currentSide)
	{
		//compare the current side's text to the front, if it matches, return the flip side
		if(currentSide == cards[cardIndex].frontText)
			return cards[cardIndex].backText;
		//otherwise check the back side, if it matches, return the flip side
		else if(currentSide == cards[cardIndex].backText)
			return cards[cardIndex].frontText;
		//otherwise we haven't found the right card, return the same string			
		else
			return currentSide;
	}
	
	public string nextCard(int cardIndex)
	{
		if(cardIndex < cards.length)
			cardIndex = cardIndex+1;
		else
			cardIndex = 0;
		
		return cards[cardIndex].frontText;
	}
	
	public void removeCard(int cardToRemove)
	{		
		int i, j;
		j = 0;
		//create new array 1 less than current array
		card[] newCardArray = new card[cards.length - 1];
		
		for(i = 0; i < cards.length; i++)
		{
			//copy the card over to the new array only if its not the index to be removed
			if(i != cardToRemove)
			{
				newCardArray[j].frontText = cards[i].frontText;
				newCardArray[j].backText = cards[i].backText;
				j++;
			}	
		}
		//finally set the cards array to the new array
		cards = newCardArray;
	}
	
	
}
