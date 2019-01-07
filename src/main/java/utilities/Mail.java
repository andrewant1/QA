package utilities;



import seleniumDriver.CreateDriver;

import javax.mail.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Mail {

    public static Message getGmailMessage(String username, String password, String email, String subject) throws Exception{

        String toField = null , subjectField = null;
        int iterations =1;
        Message getMessage = null;
        Session session = null;
        Store store = null;
        Properties props = System.getProperties();

        props.setProperty("mail.store.protocol","imap");
        props.setProperty("mail.imap.ssl.enable", "true");
        props.setProperty("mail.imap.port", "993");

        session = Session.getInstance(props, null);
        store = session.getStore("imaps");
        store.connect("imap.gmail.com",username,password);
        Folder folder =  store.getFolder("INBOX");
        folder.open(Folder.READ_WRITE);

        while(iterations <= 10){
            Message[] messages = null;
            messages = folder.getMessages();

            for (Message message :messages){
                toField = message.getHeader("TO")[0];
                subjectField = message.getSubject();

                if (toField.equalsIgnoreCase(email) || subjectField.equalsIgnoreCase(subject)){
                    getMessage = message;
                    break;
                }
            }

            if (getMessage == null){
                CreateDriver.getInstance().waitDriver(Global_VARS.TIMEOUT_SECOND);
                iterations++;
            }
            else {
                break;
            }
        }

        if (getMessage != null){
            return  getMessage;
        }
        else {
            throw new Exception("The Email Message was Not found!");
        }

    }


    public  static  String getMsgContent(String username, String password, String subject, String to) throws Exception{
        Message message = getGmailMessage(username, password, subject, to);
        String line = null;
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(message.getInputStream()));

        while ((line = reader.readLine()) != null){
            buffer.append(line);
        }
        return buffer.toString();
    }

    public  static String getMsgLink(String username, String password, String subject, String to) throws Exception{

        String content = getMsgContent(username, password, subject,  to);
        Pattern pattern = Pattern.compile("href=\\\"(.*?)\\\"",Pattern.DOTALL);
        Matcher matcher = pattern.matcher(content);
        String regUrl = null;
        while (matcher.find()){
            regUrl = matcher.group(1);
        }
        return  regUrl;
    }

    public static void deleteEmails(String username, String password) throws Exception{
        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imaps");
        Session session = Session.getInstance(props, null);
        Store store = session.getStore("imaps");
        store.connect("imap.gmail.com",username,password);
        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_WRITE);
        Message[] messages;
        messages = folder.getMessages();
        
        for ( int i = 0; i < messages.length; i++) {
            messages[i].setFlag(Flags.Flag.DELETED, true);
        }

        folder.close(true);

    }
}
