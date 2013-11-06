package edu.sjsu.cmpe.library.pubsub;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.fusesource.stomp.jms.StompJmsConnectionFactory;
import org.fusesource.stomp.jms.StompJmsDestination;
import org.fusesource.stomp.jms.message.StompJmsMessage;

import edu.sjsu.cmpe.library.config.LibraryServiceConfiguration;
import edu.sjsu.cmpe.library.repository.SplitMessage;

public class LibraryListenerB {
	
	public void listenToTopicComputer() throws JMSException {

		
		LibraryServiceConfiguration libraryServiceConfiguration = new LibraryServiceConfiguration();
    	String user = libraryServiceConfiguration.getApolloUser();
    	String password = libraryServiceConfiguration.getApolloPassword();
    	String host = libraryServiceConfiguration.getApolloHost();
    	int port = Integer.parseInt(libraryServiceConfiguration.getApolloPort());
    	
    	String destination = "/topic/66309.book.computer";
		

		StompJmsConnectionFactory factory = new StompJmsConnectionFactory();
		factory.setBrokerURI("tcp://" + host + ":" + port);

		Connection connection = factory.createConnection(user, password);
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination dest = new StompJmsDestination(destination);
		System.out.println("LibraryB Topic Queue = " + dest);

		MessageConsumer consumer = session.createConsumer(dest);
		System.currentTimeMillis();
		System.out.println("LibraryB Waiting for messages...");
		long waitUntil = 50*1000; //for 50 seconds
		while(true) {
			Message msg = consumer.receive(waitUntil);
			 if( msg instanceof  TextMessage ) {
		           String body = ((TextMessage) msg).getText();
		           System.out.println("LibraryB Received TextMessage for ComputerTextMsg = " + body);
		           if(body != null){
				    	SplitMessage splitMessage = new SplitMessage();
				    	splitMessage.splitMessage(body);
				    	}
		    }else if (msg instanceof StompJmsMessage) {
				StompJmsMessage smsg = ((StompJmsMessage) msg);
				String body = smsg.getFrame().contentAsString();
				System.out.println("LibraryB Received StompJmsMessage for ComputerStompMsg = " + body);
				if(body != null){
			    	SplitMessage splitMessage = new SplitMessage();
			    	splitMessage.splitMessage(body);
			    	}
		    } else if (msg == null) {
		          System.out.println("LibraryB No new messages. Existing due to timeout - " + waitUntil / 1000 + " sec");
		          break;
		    } else {
		         System.out.println("LibraryB Unexpected message type: " + msg.getClass());
		    }
		}
		connection.close();
	    }

	    @SuppressWarnings("unused")
		private static String env(String key, String defaultValue) {
		String rc = System.getenv(key);
		if( rc== null ) {
		    return defaultValue;
		}
		return rc;
	    }

	    @SuppressWarnings("unused")
		private static String arg(String []args, int index, String defaultValue) {
		if( index < args.length ) {
		    return args[index];
		} else {
		    return defaultValue;
		}
	    }
	}



