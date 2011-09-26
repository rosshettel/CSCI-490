package edu.niu.cs.rosshettel.mortgage;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MortgageCalculatorActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
    }
    
    /*
     * Grab the data from the user inputs, and calculate the mortgage rate
     * John Miller makes an edit here to test github.
     */
    
    /****************************************************************
    	FUNCTION:   double calculateMonthlyPayment(double, double, int)
    	ARGUMENTS:  principle (double), annual interest rate (double), term in months (int)
    	RETURNS:    monthly payment (double)
    	NOTES:      This calculates the monthly payment for a mortgage.
     ****************************************************************/
    public double calculateMonthlyPayment(double principle, double annualInterestRate, int numMonths) 
    	{
   			double monthlyInterestRate = annualInterestRate/1200;
    		double monthlyPmt = principle*monthlyInterestRate/(1-Math.pow((1+monthlyInterestRate),(-numMonths)));
    		return monthlyPmt;
   		}
    /****************************************************************
    	FUNCTION:   double calculateTotalRepayment(double, double, int)
    	ARGUMENTS:  principle (double), annual interest rate (double), term in months (int)
    	RETURNS:    total amount of repayment (double)
    	NOTES:      This calculates the total repayment on a mortgage.
     ****************************************************************/
    public double calculateTotalRepayment(double principle, double annualInterestRate, int numMonths)
    	{
    		double finalValue = principle*Math.pow((1+annualInterestRate/100), (numMonths/12));
    		return finalValue;
    	}
    /*
     * Clear all the input data
     */
    public void clearInputs(View theButton) {
    	//wipe it all clean
    }
}
