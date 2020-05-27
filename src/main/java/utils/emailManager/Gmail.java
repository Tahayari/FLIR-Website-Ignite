package utils.emailManager;

import com.sun.mail.imap.protocol.FLAGS;
import org.testng.Assert;
import testData.TestData;

import javax.mail.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class Gmail {
    Properties props;
    Session session;
    Store store;
    Folder inbox;

    public Gmail(String email, String pass) {
        props = new Properties();
        try {
            props.load(new FileInputStream(new File(System.getProperty("user.dir") + "/src/main/java/config/smtp.properties")));
            session = Session.getDefaultInstance(props, null);

            store = session.getStore("imaps");
            store.connect("smtp.gmail.com", email, pass);

            inbox = store.getFolder("inbox");
            inbox.open(Folder.READ_WRITE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void waitForNewMessages() {
        try {
            int messageCount = inbox.getMessageCount();
            long startTime = System.currentTimeMillis();
            long endTime = System.currentTimeMillis();
            System.out.println("+++++[GmailDebug] : Waiting for incoming email");
            while (messageCount <= 0 && ((endTime - startTime) / 1000) < TestData.WAIT_FOR_ELEMENT_TIMEOUT) {
                messageCount = inbox.getMessageCount();
                endTime = System.currentTimeMillis();
            }
            if(messageCount <= 0){
                throw new MessagingException();
            }
            System.out.println("+++++[GmailDebug] : "+inbox.getMessageCount()+" new email received");

        } catch (MessagingException e) {
            Assert.assertEquals("No new emails arrived in the last " + TestData.WAIT_FOR_ELEMENT_TIMEOUT + " seconds",
                    "New email in inbox");
            e.printStackTrace();
        }
    }

    public void deleteAllMessages() {
        try {
            int messageCount = inbox.getMessageCount();
            while (messageCount > 0) {
                Message[] messages = inbox.getMessages();
                for (int i = 0; i < messageCount; i++) {
                    messages[i].setFlag(FLAGS.Flag.DELETED, true);
                }
                inbox = store.getFolder("inbox");
                messageCount = inbox.getMessageCount();
            }
            System.out.println("+++++[GmailDebug] : Previous messages are deleted");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public String read() {

        try {
            inbox = store.getFolder("inbox");
            inbox.open(Folder.READ_WRITE);
            Message[] messages = inbox.getMessages();
            System.out.println("------------------------------");
            if (messages.length > 0)
                return messages[0].getContent().toString();
            else throw new Exception("+++++[GmailDebug] : Error reading the email");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error while retrieving the token";
    }
}
