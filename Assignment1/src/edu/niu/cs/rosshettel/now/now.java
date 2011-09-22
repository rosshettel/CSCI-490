/*************************************
 * Name:			Ross Hettel
 * Z-ID:			Z1624450
 * Date Modified:	September 19, 2011
 * Description:		A big-ass button filling up the whole
 * 					screen that when clicked updates with
 * 					the correct time.
 *************************************/
package edu.niu.cs.rosshettel.now;

//Import Statements
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.util.Date;
import android.util.Log;

public class now extends Activity implements View.OnClickListener {
	Button btn; //declaring a button widget
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);	//calls the Android activity intilization 
        
        btn=new Button(this); //create new button instance
        btn.setOnClickListener(this); //send all button click to this instance
        updateTime(); //run this when app is first run, so button has current time
        setContentView(btn); //fill the screen with the button
    }
    
    //this will update the time when the button is clicked
    public void onClick(View view) {
    	updateTime();
    }
    
    //this sets the text of the button to the string value of the current date
    public void updateTime() {
    	btn.setText(new Date().toString());
    	Log.v("rosshettel", "btn value= " + btn.getText());
    }
}