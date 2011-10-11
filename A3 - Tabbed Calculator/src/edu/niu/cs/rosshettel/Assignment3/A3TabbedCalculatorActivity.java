package edu.niu.cs.rosshettel.Assignment3;

import java.text.DecimalFormat;

import edu.niu.cs.rosshettel.Assignment3.R;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;

public class A3TabbedCalculatorActivity extends Activity {
    /** Called when the activity is first created. */
	EditText mortPrinciple, mortInterest, mortTerm;
	Button mortCalculate, clear;
	TextView output_mortgage, output_repayment, mortOutputError;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Resources res = getResources();
        TabHost pgmTabs = (TabHost)findViewById(R.id.tabhost);
        pgmTabs.setup();
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
        
        output_mortgage = (TextView) findViewById(R.id.monthly_payment_output);
        output_repayment = (TextView) findViewById(R.id.total_payment_output);
        mortOutputError = (TextView) findViewById(R.id.borrow_error);

        
    }
    
    /****************************************************************
		FUNCTION:   void calculateMortgage(View theButton)
		ARGUMENTS:  view
		RETURNS:    none
		NOTES:      Get all the user inputs, convert to respective data types,
					then calculate the mortgage and output.
     ****************************************************************/
    public void calculateMortgage(View theButton) {
    	double principle_num = 0;
    	double interest_num = 0;
    	double monthlyResult, repaymentResult, interestPercent;
    	int term_num = 0;
        DecimalFormat df = new DecimalFormat("#.00");
        Log.d("A2_debug", "got to calcMortgage function");
        mortOutputError.setText(""); //reset the error messages
        
        //try to convert the user input to doubles/int, error if exception found
    	try {
    		principle_num = Double.parseDouble(mortPrinciple.getText().toString());
    		interest_num = Double.parseDouble(mortInterest.getText().toString());
    		interestPercent = interest_num / 100; //turn the whole into a decimal percentage
    		term_num = Integer.parseInt(mortTerm.getText().toString());
    	} catch (final NumberFormatException e) {
    		Log.d("A2_debug", "Encountered an error parsing user input.");
    		// let the user know the input is wrong
    		mortOutputError.setText("Warning - Input error, make sure you entered a valid value in each field above.");
        	//reset the output dialog as well
        	output_mortgage.setText("");
    		output_repayment.setText("");
    		return; //get outta here johnny!
    	}
    	
    	//calculate payments 
    	monthlyResult = calculateMonthlyPayment(principle_num, interestPercent, term_num*12);
    	repaymentResult = calculateTotalRepayment(monthlyResult, term_num*12);
  
    	//convert results to decimal format and output
    	output_mortgage.setText(df.format(monthlyResult)); 
    	output_repayment.setText(df.format(repaymentResult));
    }
    
    /****************************************************************
		FUNCTION:   double calculateMonthlyPayment(double, double, int)
		ARGUMENTS:  principle (double), annual interest rate (double), term in months (int)
		RETURNS:    monthly payment (double)
		NOTES:      This calculates the monthly payment for a mortgage.
     ****************************************************************/
    public double calculateMonthlyPayment(double principle, double annualInterestRate, int numMonths) 
		{
			double monthlyInterestRate = annualInterestRate/12;
			Log.d("A2_debug", "monthlyInterestRate: " + monthlyInterestRate);
			double monthlyPmt = principle*monthlyInterestRate/(1-Math.pow((1+monthlyInterestRate),(-numMonths)));
			return monthlyPmt;
		}
    /****************************************************************
		FUNCTION:   double calculateTotalRepayment(double, int)
		ARGUMENTS:  monthly payment (double), term in months (int)
		RETURNS:    total amount of repayment (double)
		NOTES:      This calculates the total repayment on a mortgage.
     ****************************************************************/
    public double calculateTotalRepayment(double monthlyPayment, int numMonths)
    	{
    		return monthlyPayment*numMonths;
    	}
    /****************************************************************
		FUNCTION:   double calculateFinalValue(double, double, int)
		ARGUMENTS:  principle (double), annual interest rate (double), term in years (int)
		RETURNS:    final value (double)
		NOTES:      This calculates the final value for an investment.
     ****************************************************************/
    public double calculateFinalValue(double principle, double annualInterestRate, int numYears)
    	{
    		return principle * Math.pow((1 + annualInterestRate), numYears);
    	}
    
    public void clearMortInputs(View theButton) {
    	//wipe it all clean
    	mortPrinciple.setText("");
    	mortInterest.setText("");
    	mortTerm.setText("");
    	
    	//reset all the output dialog as well
    	output_mortgage.setText("");
		output_repayment.setText("");
		
    	Log.d("A2_debug", "Cleared all the mortgage inputs.");
    }

}