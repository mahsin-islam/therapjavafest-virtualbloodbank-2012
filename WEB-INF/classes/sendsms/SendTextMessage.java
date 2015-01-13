package sendsms;


import com.nexmo.messaging.sdk.NexmoSmsClient;
import com.nexmo.messaging.sdk.SmsSubmissionResult;
import com.nexmo.messaging.sdk.messages.TextMessage;


public class SendTextMessage {

    private String apiKey = null;	//"2417980c"
    private String apiSecret = null;//"ebce3a36"

    private String smsFrom = null;	//"VBB"
    private String smsTo = null;	//"+8801673725106"
    private String smsText = null;	//"Hello World!"

	/*
	CONSTRUCTOR
	*/
	public SendTextMessage(String key, String secret){
		apiKey = key;
    	apiSecret = secret;
	}

	/*
	return true for successful sms send
	*/
	public boolean send(String from, String to, String text){

		smsFrom = from;
		smsTo = to;
		smsText = text;

		// Create a client for submitting to Nexmo
		NexmoSmsClient client = null;
		try {
			client = new NexmoSmsClient(apiKey, apiSecret);
		}
		//Failed to instanciate a Nexmo Client
		catch (Exception e) {
			return false;
		}

		// Create a Text SMS Message request object ...
		TextMessage message = new TextMessage(smsFrom, smsTo, smsText);

		// Use the Nexmo client to submit the Text Message ...
		SmsSubmissionResult[] results = null;
		try {
			results = client.submitMessage(message);
		}
		//Failed to communicate with the Nexmo Client
		catch (Exception e) {
			return false;
		}

		// Evaluate the results of the submission attempt ...
		for (int I=0; I<results.length; I++) {
			//if full sms does not successfully sent
			if (results[I].getStatus() != SmsSubmissionResult.STATUS_OK){
				return false;
			}
        }

        //successful sent
        return true;
	}


}
