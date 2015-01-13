package sendsms;


import java.util.Properties;


public class SendTextMessageVBB{

	private String apiKey = null;
	private String apiSecret = null;
	private String smsFrom = null;

	/*
	constructor
	*/
	public SendTextMessageVBB(){
		try{
			Properties configFile = new Properties();
			//load config.properties. that stored data for database.
			configFile.load(this.getClass().getClassLoader().getResourceAsStream("configNexmoSms.properties"));

			apiKey = configFile.getProperty("key");
			apiSecret = configFile.getProperty("secret");

			smsFrom = "VBB";
		}catch(Exception e){
			System.out.println(e);
		}
	}

	/*
	return true for successful send
	*/
	public boolean send(String smsTo, String userName, String bloodGroup, String city, String mobileNumber, String emailId){
		String smsText = "Urgent "+bloodGroup+" blood need for "+userName+" who lives in your city "+city+".\n\n"+
						 "Mobile No. "+mobileNumber+"\n"+
						 "Email id : "+emailId+"\n\n"+
						 " - VBB";
		SendTextMessage sendTextMessage = new SendTextMessage(apiKey, apiSecret);
		return sendTextMessage.send(smsFrom, smsTo, smsText);
	}

}