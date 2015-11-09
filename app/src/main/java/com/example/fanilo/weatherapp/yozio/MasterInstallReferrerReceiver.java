package com.example.fanilo.weatherapp.yozio;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.yozio.android.YozioReferrerReceiver;

/**
 * This is the sample code with the best practice for registering multiple 
 * receivers for Google Play's Referrer String via broadcast.
 * 
 */
public class MasterInstallReferrerReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		
		/** 
		 * YozioReferrerReceiver will process the referrer string, 
		 * fetch out the meta data, and call YozioMetaDataCallback accordingly.
		 * 
		 * Make sure you call YozioReferrerReceiver first. It is an asynchronous method,  
		 * and will return immediately within a few milliseconds.
		 * 
		 * If you don't put it as the first receiver, the receiver(s) run before it might 
		 * take too long, and you will run the following risks   
		 * 
		 * 1) Yozio receiver never get called before Android kills the process.  
		 *    In this case, Yozio will fail to track the install or pass the meta data to 
		 *    your app accordingly. 
		 *    
		 * 2) Yozio receiver is called after a long period of time. From several hundred 
		 *    milliseconds to a few seconds later.
		 *    In this case, Yozio will still be able to track the install and pass the meta data
		 *    to your app, but you might have missed the opportunity to personalize the 1st time
		 *    user experience using the meta data. Like site load speed on the web, every 
		 *    millisecond counts.
		 * 
		 */
    	YozioReferrerReceiver yozioReferrerReceiver = new YozioReferrerReceiver();
    	yozioReferrerReceiver.onReceive(context, intent);
    	
    	/**
    	 * You can uncomment the following lines to see that YozioReferrerReceiver returns 
    	 * immediately, and will not block any other receivers.
    	 * 
    	 * Also, make sure that you test all of the referrer receivers to ensure that they don't 
    	 * take a long time (>500ms) to run. Long running referrer receivers will block the main 
    	 * thread, YozioMetaDataCallback, or any other async methods until it is finished. 
    	 * 
    	 * As a result, the end users will see a delayed personalized experience, which would 
    	 * hurt your conversion rates.   
    	 * 
    	 */
    	// LongBlockingReferrerReceiver longBlockingReferrerReceiver = new LongBlockingReferrerReceiver();
    	// longBlockingReferrerReceiver.onReceive(context, intent);
	}

}
