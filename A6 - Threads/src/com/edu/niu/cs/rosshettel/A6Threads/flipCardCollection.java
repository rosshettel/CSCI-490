/**
 * 
 */
package com.edu.niu.cs.rosshettel.A6Threads;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

/**
 * @author NIU-STUDENT
 *
 */
public class flipCardCollection {
	private static String logtag = "A6";
	
	//the card class
	private class card {
		public String frontText;
		public String backText;
	}
	
	private card[] cards;
	private int cardIndex;
	
	public flipCardCollection(XmlPullParser xpp)
	{
		int currentCard = 0;
				
		try {
			while(xpp.getEventType() != XmlPullParser.END_DOCUMENT)
			{
				if(xpp.getEventType() == XmlPullParser.START_TAG)
				{
					if(xpp.getName().equals("flipcards"))
					{
						//initialize the card array to the right size
						int size = Integer.parseInt(xpp.getAttributeValue(1));
						cards = new card[size];
					}
					
					//now grab the cards
					if(xpp.getName().equals("card"))
					{
						cards[currentCard] = new card();
						Log.d(logtag, "created new card | #" + currentCard);
					}
					
					//add the front side
					if(xpp.getName().equals("frontside"))
						cards[currentCard].frontText = (String)xpp.getAttributeValue(0);
					
					//add the back side
					if(xpp.getName().equals("backside"))
						cards[currentCard].backText = (String)xpp.getAttributeValue(0);
				}
				
				//increment the currentCard if we reached the end tag of card
				if(xpp.getEventType() == XmlPullParser.END_TAG)
				{
					if(xpp.getName().equals("card"))
						currentCard++;
				}
				
				//move to the next XMl tag
				try {
					xpp.next();
				} catch (IOException e) {
					Log.e(logtag, "IOException: " + e.getLocalizedMessage());
				}
			}
		} catch(XmlPullParserException e)
		{
			Log.e(logtag, "XmlPullParserException: " + e.getMessage());
			return;
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			Log.d(logtag, "out of bounds) currentCard=" + currentCard + ", length=" + cards.length);
		}
	}
	
	public flipCardCollection(int size)
	{
		//initialize the new card array
		cards = new card[size];
	}
	
	public void setCardFrontText(String text)
	{
		cards[cardIndex].frontText = text;
	}
	
	public void setCardFrontText(String text, int cardIndex)
	{
		cards[cardIndex].frontText = text;
	}
	
	public void setCardBackText(String text)
	{
		cards[cardIndex].backText = text;
	}
	
	public void setCardBackText(String text, int cardIndex)
	{
		cards[cardIndex].backText = text;
	}
		
	public String getFlipText(String currentSide)
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
	
	public String nextCard(int cardIndex)
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
