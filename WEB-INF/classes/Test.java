import java.util.*;


import sendsms.*;
import database.*;
import sendmail.*;

public class Test{
	public static void main(String[] args) {
		//SendTextMessageVBB sv = new SendTextMessageVBB();
		//System.out.println(sv.send("+8801673725106", "ratul", "B+", "Dhaka", "01916144845", "ratul13@live.com"));
		//RegUserInfoTable ruit = new RegUserInfoTable();
		//Vector v = (Vector)ruit.getModifiedUserInfoUserName("ratul").get(0);
		//System.out.println(v.get(3));
		new MailSenderVBB();
	}

}