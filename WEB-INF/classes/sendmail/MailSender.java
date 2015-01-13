package sendmail;


import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class MailSender {

    private String from = null;		//sender mail id = "vbb.ratul@gmail.com"
    private String password = null;	//sender mail password = ""
    private String host = null;		//sending mail host name = "smtp.gmail.com"
    private String port = null;   	//port = 587 for gmail and for yahoo 465 or 25(common mail port)

	private final String SMTP = "smtp";		//protocol

    /*
    CONSTRUCTOR
    */
    public MailSender(String fromMailId, String mailPassword, String hostName, String portNumber) {
		from = fromMailId;
		password = mailPassword;
		host = hostName;
		port = portNumber;
    }

	/*
	send mail with file attachment if need
	*/
    public boolean send(String[] toList, String messageSubject, String messageBody, String attachFilePath, String attacheFileName)
    {
        try
        {
            //initialization
            Properties props = System.getProperties();
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.user", from);
            props.put("mail.smtp.password", password);
            props.put("mail.smtp.port", port);
            props.put("mail.smtp.auth", "true");

            Session session = Session.getDefaultInstance(props, null);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));

            //add recipient
            InternetAddress[] toAddress = new InternetAddress[toList.length];
            for (int i = 0; i < toList.length; i++) {
                toAddress[i] = new InternetAddress(toList[i]);
            }
            for (int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            //subject of mail
            message.setSubject(messageSubject);


            //body of mail
            MimeBodyPart mimeBodyPartText = new MimeBodyPart();
            mimeBodyPartText.setText(messageBody);

			//attachment of mail
			MimeBodyPart mimeBodyPartFile = null;
			if( attachFilePath!=null ){
				FileDataSource fds = new FileDataSource(attachFilePath);
				mimeBodyPartFile = new MimeBodyPart();
				mimeBodyPartFile.setDataHandler(new DataHandler(fds));
				mimeBodyPartFile.setFileName(attacheFileName);
			}

			//adding file attachment and message to mail
            Multipart mp = new MimeMultipart();
            mp.addBodyPart(mimeBodyPartText);
            if( mimeBodyPartFile!=null )
            	mp.addBodyPart(mimeBodyPartFile);
            message.setContent(mp);
            message.setSentDate(new Date());

			//send mail
            Transport transport = session.getTransport(SMTP);
            transport.connect(host, from, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

        	//successful send
        	return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }


	/*public static void main(String args[]){
		String[] mail={"ratul840@yahoo.c"};
		new MailSender("vbb.ratul@gmail.com", "vbb.ratul", "smtp.gmail.com", "587").send(mail, "check mail subject", "check mail body", null, null);

	}*/

}
