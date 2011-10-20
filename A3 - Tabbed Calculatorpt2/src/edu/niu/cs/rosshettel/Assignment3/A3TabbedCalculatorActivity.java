/****************************************************************
   PROGRAM:   Assignment 3 part 2
   AUTHOR:    Ross Hettel, John Miller, Alex Wohead
   LOGON ID:  Z1549355, Z159807, Z1624450
   DUE DATE:  10/17 at class time

   FUNCTION:  This is a mortgage and investment calculator.  It will calculate
    		  your monthly payment and the total amount to be repaid for a 
    		  mortgage, and the final value for an investment.

   INPUT:     principal, interest, term from user

   OUTPUT:    monthly payment, total amount to be repaid, and any errors
              also, final value and any errors

   NOTES:     None.
****************************************************************/
package edu.niu.cs.rosshettel.Assignment3;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class A3TabbedCalculatorActivity extends TabActivity {
    //Here are our initializations for buttons and text fields.
	EditText mortPrinciple, mortInterest, mortTerm, 
			investPrinciple, investInterest, investTerm;
	Button mortCalculate, investCalculate, clear;
	TextView output_mortgage, output_repayment, outputFinalValue, mortOutputError, investOutputError;

	static String debug_tag = "A3pt2_Debug" ;

	//Here we were trying to see if declaring a member of each class would help.
	//Mortgage mortgage = new Mortgage();
	//Investment testInvestment = new Investment();
    /****************************************************************
		FUNCTION:   void onCreate(Bundle)
		ARGUMENTS:  saved instance state (Bundle)
		RETURNS:    nothing
		NOTES:      This is equivalent to main() in other programming languages.
		            It sets up the EditTexts, Buttons, and TextViews.
     ****************************************************************/
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Resources res = getResources();
        
        //Set up tabhost.
        TabHost pgmTabs = (TabHost)findViewById(android.R.id.tabhost);
        
        TabSpec spec1 = pgmTabs.newTabSpec("tag1");
        spec1.setIndicator(res.getText(R.string.borrow_tab),res.getDrawable(R.drawable.tab_borrow));
        Intent in1=new Intent(this, MortgageActivity.class);
        spec1.setContent(in1);

        TabSpec spec2 = pgmTabs.newTabSpec("tag2");
        spec2.setIndicator(res.getText(R.string.invest_tab), res.getDrawable(R.drawable.tab_invest));
        Intent in2=new Intent(this, Investment.class);
        spec2.setContent(in2);
        
        pgmTabs.addTab(spec1);
        pgmTabs.addTab(spec2);
        
    }
}