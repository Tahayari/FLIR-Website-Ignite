package utils.emailManager;

import com.sun.mail.imap.protocol.FLAGS;

import javax.mail.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class GmailInbox {
    Properties props;
    Session session;
    Store store;
    Folder inbox;

    public GmailInbox(String email,String pass) {
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
            while (messageCount <= 0) {
                messageCount = inbox.getMessageCount();
            }
            System.out.println("New email received");

        } catch (MessagingException e) {
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
            System.out.println("----Previous messages are deleted");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public String read() {

        try {
            int messageCount = inbox.getMessageCount();

            Message[] messages = inbox.getMessages();
            System.out.println("------------------------------");

            String token = messages[0].getContent().toString();
            return token;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error while retrieving the token";
    }
}
