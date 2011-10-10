package edu.niu.cs.rosshettel.Assignment3;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;

public class A3TabbedCalculatorActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        TabHost pgmTabs = (TabHost)findViewById(R.id.tabhost);
        pgmTabs.setup();
        TabHost.TabSpec spec = pgmTabs.newTabSpec("tag1");
        
        /*  */
        spec.setContent(R.id.tab1);
        spec.setIndicator("Borrow");
        pgmTabs.addTab(spec);
        
        spec = pgmTabs.newTabSpec("tag2");
        
        spec.setContent(R.id.tab2);
        spec.setIndicator("Investment");
        pgmTabs.addTab(spec);


        
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

}