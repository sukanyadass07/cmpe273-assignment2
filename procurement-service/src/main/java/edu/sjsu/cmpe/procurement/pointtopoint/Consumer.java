package edu.sjsu.cmpe.procurement.pointtopoint;

import java.util.ArrayList;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.fusesource.stomp.jms.StompJmsConnectionFactory;
import org.fusesource.stomp.jms.StompJmsDestination;

import edu.sjsu.cmpe.procurement.config.ProcurementServiceConfiguration;

public class Consumer {

	@SuppressWarnings("rawtypes")
	private ArrayList arrayOfIsbn = new ArrayList();
	
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public ArrayList readMessageFromQueue() throws JMSException {
    	
    	   	
	String user = env("APOLLO_USER", "admin");
	String password = env("APOLLO_PASSWORD", "password");
	String host = env("APOLLO_HOST", "54.215.210.214");
	int port = Integer.parseInt(env("APOLLO_PORT", "61613"));
	String queue = "/queue/66309.book.orders";
	String destination = "/queue/66309.book.orders";

	StompJmsConnectionFactory factory = new StompJmsConnectionFactory();
	factory.setBrokerURI("tcp://" + host + ":" + port);

	Connection connection = factory.createConnection(user, password);
	connection.start();
	Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	Destination dest = new StompJmsDestination(destination);

	MessageConsumer consumer = session.createConsumer(dest);
	System.out.println("Waiting for messages from " + queue + "...");
	long waitUntil = 5000; //for 5 secs
	try{
	while(true) {
	    Message msg = consumer.receive(waitUntil);
	    if( msg instanceof  TextMessage ) {
		String body = ((TextMessage) msg).getText();
		System.out.println("Received message = " + body);
		String isbn = body.substring(10, 11);
		
		arrayOfIsbn.add(isbn);
		} else if(msg == null){
			
			System.out.println("No new messages. Existing due to timeout - " + waitUntil / 1000 + " sec");
			break;

	    } else {
		System.out.println("Unexpected message type: "+msg.getClass());
	    }
	}
	}catch(NullPointerException e){
		e.printStackTrace();
	}
	
	connection.close();
	System.out.println("The connection closed");
	return(arrayOfIsbn);
    }
    

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

