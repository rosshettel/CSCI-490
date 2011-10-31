package edu.niu.cs.rosshettel.Assignment4;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
//test
public class Internet extends Activity {
    WebView bowser;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.internet);
        bowser=(WebView)findViewById(R.id.webkit);
        
        bowser.loadUrl("http://en.wikipedia.org/wiki/IPod");
        
    }
}