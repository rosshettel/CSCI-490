/****************************************************************
PROGRAM:   Presentation
AUTHOR:    Ross Hettel, John Miller, Alex Wohead
LOGON ID:  Z1549355, Z159807, Z1624450
DUE DATE:  11/28 at class time

FUNCTION:  Provides examples of styles and themes
INPUT:     None
OUTPUT:    None
NOTES:     None
****************************************************************/
package edu.niu.cs.rosshettel.presentation;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class PresentationActivity extends TabActivity {

 /****************************************************************
        FUNCTION:   void onCreate(Bundle)
        ARGUMENTS:  saved instance state (Bundle)
        RETURNS:    nothing
        NOTES:      This sets up the tabs.
  ****************************************************************/
    @Override
 public void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
     setContentView(R.layout.main);
     Resources res = getResources();
     
     //Set up tabhost.
     TabHost pgmTabs = (TabHost)findViewById(android.R.id.tabhost);
     
     //create the first tab for styles example #1
     TabSpec spec1 = pgmTabs.newTabSpec("tag1");
     spec1.setIndicator(res.getText(R.string.styles_first_tab));
     Intent in1=new Intent(this, Styles1.class);
     spec1.setContent(in1);

     //create second tab for styles example #2
     TabSpec spec2 = pgmTabs.newTabSpec("tag2");
     spec2.setIndicator(res.getText(R.string.styles_second_tab));
     Intent in2=new Intent(this, Styles2.class);
     spec2.setContent(in2);
     
     //create the first tab for themes example #1
     TabSpec spec3 = pgmTabs.newTabSpec("tag3");
     spec3.setIndicator(res.getText(R.string.themes_first_tab));
     Intent in3=new Intent(this, Themes1.class);
     spec3.setContent(in3);

     //create second tab for themes example #2
     TabSpec spec4 = pgmTabs.newTabSpec("tag4");
     spec4.setIndicator(res.getText(R.string.themes_second_tab));
     Intent in4=new Intent(this, Themes2.class);
     spec4.setContent(in4);
     
     //add all of the tabs to the tabhost
     pgmTabs.addTab(spec1);
     pgmTabs.addTab(spec2);
     pgmTabs.addTab(spec3);
     pgmTabs.addTab(spec4);
 }
}