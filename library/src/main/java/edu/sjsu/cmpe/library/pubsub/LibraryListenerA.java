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

public class LibraryListenerA {
	
	public void listenToAllTopics() throws JMSException {
		
		
		LibraryServiceConfiguration libraryServiceConfiguration = new LibraryServiceConfiguration();
    	String user = libraryServiceConfiguration.getApolloUser();
    	String password = libraryServiceConfiguration.getApolloPassword();
    	String host = libraryServiceConfiguration.getApolloHost();
    	int port = Integer.parseInt(libraryServiceConfiguration.getApolloPort());

		
		StompJmsConnectionFactory factory = new StompJmsConnectionFactory();
		factory.setBrokerURI("tcp://" + host + ":" + port);

		Connection connection = factory.createConnection(user, password);
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		String strDestinationComputer = "/topic/66309.book.computer";
		Destination destinationComputer = new StompJmsDestination(strDestinationComputer);
		
		String strDestinationManagement = "/topic/66309.book.management";
		Destination destinationManagement = new StompJmsDestination(strDestinationManagement);
		
		String strDestinationComics = "/topic/66309.book.comics";
		Destination destinationComics = new StompJmsDestination(strDestinationComics);
		
		String strDestinationSelfImprovement = "/topic/66309.book.selfimprovement";
		Destination destinationSelfImprovement = new StompJmsDestination(strDestinationSelfImprovement);
		
		MessageConsumer consumerComputer = session.createConsumer(destinationComputer);
		MessageConsumer consumerManagement = session.createConsumer(destinationManagement);
		MessageConsumer consumerComics = session.createConsumer(destinationComics);
		MessageConsumer consumerSelfImprovement = session.createConsumer(destinationSelfImprovement);
		
		System.currentTimeMillis();
		System.out.println("Waiting for messages...");
		long waitUntil = 50*1000; //for 50 seconds

		while(true) {

		    Message messageComputer = consumerComputer.receive(waitUntil);

		    if( messageComputer instanceof  TextMessage ) {
		    	String body = ((TextMessage) messageComputer).getText();
		    	System.out.println("Received message from ComputerTextMsg = " + body);
		    	if(body != null){
		    	SplitMessage splitMessage = new SplitMessage();
		    	splitMessage.splitMessage(body);
		    	}
		    } else if (messageComputer instanceof StompJmsMessage) {
			StompJmsMessage smsg = ((StompJmsMessage) messageComputer);
			String body = smsg.getFrame().contentAsString();
			System.out.println("Received message from ComputerStompMsg = " + body);
			if(body != null){
		    	SplitMessage splitMessage = new SplitMessage();
		    	splitMessage.splitMessage(body);
		    	}
			} else if(messageComputer == null){
				
				System.out.println("No new messages in Computer. Existing due to timeout - " + waitUntil / 1000 + " sec");
			} else {
			System.out.println("Unexpected message type: "+messageComputer.getClass());
		    }
		    
			Message messageManagement = consumerManagement.receive(waitUntil);
		    
		    if( messageManagement instanceof  TextMessage ) {
		    	String body = ((TextMessage) messageManagement).getText();
		    	System.out.println("Received message from ManagementTextMsg = " + body);
		    	if(body != null){
			    	SplitMessage splitMessage = new SplitMessage();
			    	splitMessage.splitMessage(body);
			    	}
			} else if (messageManagement instanceof StompJmsMessage) {
			StompJmsMessage smsg = ((StompJmsMessage) messageManagement);
			String body = smsg.getFrame().contentAsString();
			System.out.println("Received message from ManagementStompMsg = " + body);
			if(body != null){
		    	SplitMessage splitMessage = new SplitMessage();
		    	splitMessage.splitMessage(body);
		    	}
			} else if(messageManagement == null){
				
				System.out.println("No new messages in Management. Existing due to timeout - " + waitUntil / 1000 + " sec");
			} else {
			System.out.println("Unexpected message type: "+messageManagement.getClass());
		    }

		    Message messageComics = consumerComics.receive(waitUntil);

		    if( messageComics instanceof  TextMessage ) {
		    	String body = ((TextMessage) messageComics).getText();
			System.out.println("Received message from ComputerTextMsg = " + body);
			if(body != null){
		    	SplitMessage splitMessage = new SplitMessage();
		    	splitMessage.splitMessage(body);
		    	}
			} else if (messageComics instanceof StompJmsMessage) {
			StompJmsMessage smsg = ((StompJmsMessage) messageComics);
			String body = smsg.getFrame().contentAsString();
			System.out.println("Received message from ComicsStompMsg = " + body);
			if(body != null){
		    	SplitMessage splitMessage = new SplitMessage();
		    	splitMessage.splitMessage(body);
		    	}
			} else if(messageComics == null){
				
				System.out.println("No new messages in Comics. Existing due to timeout - " + waitUntil / 1000 + " sec");
			} else {
			System.out.println("Unexpected message type: "+messageComics.getClass());
		    }
		    
			Message messageSelfImprovement = consumerSelfImprovement.receive(waitUntil);
		    
		    if( messageSelfImprovement instanceof  TextMessage ) {
		    	String body = ((TextMessage) messageSelfImprovement).getText();
		    	System.out.println("Received message from SelfImprovementTextMsg = " + body);
		    	if(body != null){
			    	SplitMessage splitMessage = new SplitMessage();
			    	splitMessage.splitMessage(body);
			    	}
			} else if (messageSelfImprovement instanceof StompJmsMessage) {
			StompJmsMessage smsg = ((StompJmsMessage) messageSelfImprovement);
			String body = smsg.getFrame().contentAsString();
			System.out.println("Received message from SelfImprovementStompMsg = " + body);
			if(body != null){
		    	SplitMessage splitMessage = new SplitMessage();
		    	splitMessage.splitMessage(body);
		    	}
			} else if(messageSelfImprovement == null){
				
				System.out.println("No new messages in Self Improvement. Existing due to timeout - " + waitUntil / 1000 + " sec");
				break;
			} else {
			System.out.println("Unexpected message type: "+messageSelfImprovement.getClass());
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
