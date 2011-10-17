/****************************************************************
   PROGRAM:   Assignment 3 part 2
   AUTHOR:    Ross Hettel, John Miller, Alex Wohead
   LOGON ID:  Z1549355, Z159807, Z1624450
   DUE DATE:  10/12 at class time

   FUNCTION:  This is a mortgage and investment calculator.  It will calculate
    		  your monthly payment and the total amount to be repaid for a 
    		  mortgage, and the final value for an investment.

   INPUT:     principal, interest, term from user

   OUTPUT:    monthly payment, total amount to be repaid, and any errors
              also, final value and any errors

   NOTES:     None.
****************************************************************/
package edu.niu.cs.rosshettel.Assignment3;

import edu.niu.cs.rosshettel.Assignment3.R;

import android.app.TabActivity;
import android.content.res.Resources;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;

public class A3TabbedCalculatorActivity extends TabActivity {
    /** Called when the activity is first created. */
	EditText mortPrinciple, mortInterest, mortTerm, 
			investPrinciple, investInterest, investTerm;
	Button mortCalculate, investCalculate, clear;
	TextView output_mortgage, output_repayment, outputFinalValue, mortOutputError, investOutputError;
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
        TabHost pgmTabs = getTabHost();
        TabHost.TabSpec spec = pgmTabs.newTabSpec("tag1");
        
        /*  */
        spec.setContent(R.id.tab1);
        spec.setIndicator(res.getText(R.string.borrow_tab),	res.getDrawable(R.drawable.tab_borrow));
        pgmTabs.addTab(spec);
        
        spec = pgmTabs.newTabSpec("tag2");
        
        spec.setContent(R.id.tab2);
        spec.setIndicator(res.getText(R.string.invest_tab), res.getDrawable(R.drawable.tab_invest));
        pgmTabs.addTab(spec);

        mortPrinciple = (EditText) findViewById(R.id.amount_borrow_input);
        mortInterest = (EditText) findViewById(R.id.rate_borrow_input);
        mortTerm = (EditText) findViewById(R.id.time_borrow_input);
        
        mortCalculate = (Button) findViewById(R.id.calculate_button_borrow);
        
        investPrinciple = (EditText) findViewById(R.id.amount_invest_input);
        investInterest = (EditText) findViewById(R.id.rate_invest_input);
        investTerm = (EditText) findViewById(R.id.time_invest_input);
        
        investCalculate = (Button) findViewById(R.id.calculate_button_invest);
        
        outputFinalValue = (TextView) findViewById(R.id.final_value_ouput);
        output_mortgage = (TextView) findViewById(R.id.monthly_payment_output);
        output_repayment = (TextView) findViewById(R.id.total_payment_output);
        mortOutputError = (TextView) findViewById(R.id.borrow_error);
        investOutputError = (TextView) findViewById(R.id.interest_error);        
    }
}