/**
 * 
 */
package com.edu.niu.cs.rosshettel.A6Threads;

import java.io.IOException;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

/**
 * This is the flipCardCollection class. Contains an array filled of card objects.
 * Card objects contain 2 strings, frontText and backText.
 * TODO: fix crash when we remove on a nearly empty list
 * TODO: perhaps implement a shuffle feature here for restart
 */
public class flipCardCollection {
	private static String logtag = "A6";
	
	//the card class
	private class card {
		public String frontText;
		public String backText;
	}
	
	private LinkedList<card> cards;
	private ListIterator<card> cardItr;
	private card currentCard;
	
	public flipCardCollection(XmlPullParser xpp)
	{
		card newCard = null; //temp card when reading xml
				
		try {
			while(xpp.getEventType() != XmlPullParser.END_DOCUMENT)
			{
				if(xpp.getEventType() == XmlPullParser.START_TAG)
				{
					//initialize the card array to the right size
					if(xpp.getName().equals("flipcards"))
						cards = new LinkedList<card>();
					
					//now grab the cards
					if(xpp.getName().equals("card"))
						newCard = new card();
					
					//add the front side
					if(xpp.getName().equals("frontside"))
						newCard.frontText = (String)xpp.getAttributeValue(0);
					
					//add the back side
					if(xpp.getName().equals("backside"))
						newCard.backText = (String)xpp.getAttributeValue(0);
				}
				
				//add the card to the queue if we reached the endtag of card
				if(xpp.getEventType() == XmlPullParser.END_TAG)
				{
					if(xpp.getName().equals("card"))
						cards.add(newCard);
				}
				
				//move to the next XMl tag
				try {
					xpp.next();
				} catch (IOException e) {
					Log.e(logtag, "IOException: " + e.getLocalizedMessage());
				}
			}
			
			cardItr = cards.listIterator(0); //set up the iterator on the new list
			currentCard = cardItr.next(); //set the current card to the first card
		} catch(XmlPullParserException e) {
			Log.e(logtag, "XmlPullParserException: " + e.getMessage());
			return;
		}
	}
		
	//TODO: this is a bit redundant now we switched to a linked list
	public String getFlipText(String currentSide)
	{	
		//compare the current side's text to the front, if it matches, return the flip side
		if(currentSide == currentCard.frontText)
			return currentCard.backText;
		//otherwise check the back side, if it matches, return the flip side
		else if(currentSide == currentCard.backText)
			return currentCard.frontText;
		//otherwise we haven't found the right card, return the same string			
		else
			return currentSide;
	}
	
	public String getFrontText()
	{
		return currentCard.frontText;
	}
	
	public String nextCard()
	{
		if(cardItr.hasNext())
		{ //there is a next card, return that
			currentCard = cardItr.next();
			return currentCard.frontText;
		}
		else
		{ //reached the end of the list, try restarting at the beginning
			cardItr = cards.listIterator(0);
			try { 
				currentCard = cardItr.next();
				return currentCard.frontText;
			} //otherwise the list is empty, return a string explaning that
			catch(NoSuchElementException e) {
				Log.d(logtag, "caught a NoSuchElementException: "+ e.getLocalizedMessage());
				return "No more flip cards left";
			}
		}
	}
	
	public void removeCard()
	{
		if(cards.size() > 0) //only call remove if theres something in the list
		{
			cardItr.remove();
			currentCard = cardItr.next();
		}
	}
	
	public void moveToBeginning()
	{
		//reset the iterator at index 0
		cardItr = cards.listIterator(0);
	}
}
