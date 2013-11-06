package edu.sjsu.cmpe.library.pointtopoint;
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.fusesource.stomp.jms.StompJmsConnectionFactory;
import org.fusesource.stomp.jms.StompJmsDestination;

import com.yammer.dropwizard.jersey.params.LongParam;

import edu.sjsu.cmpe.library.config.LibraryServiceConfiguration;

public class Producer {

    public void sendMessageToQueue(LongParam isbn) throws JMSException {

    	LibraryServiceConfiguration libraryServiceConfiguration = new LibraryServiceConfiguration();
    	String user = libraryServiceConfiguration.getApolloUser();
    	String password = libraryServiceConfiguration.getApolloPassword();
    	String host = libraryServiceConfiguration.getApolloHost();
    	int port = Integer.parseInt(libraryServiceConfiguration.getApolloPort());
    	
    	
	String queue = "/queue/66309.book.orders";
	String destination = "/queue/66309.book.orders";

	StompJmsConnectionFactory factory = new StompJmsConnectionFactory();
	factory.setBrokerURI("tcp://" + host + ":" + port);

	Connection connection = factory.createConnection(user, password);
	connection.start();
	Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	Destination dest = new StompJmsDestination(destination);
	MessageProducer producer = session.createProducer(dest);
	producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

	System.out.println("Sending messages to " + queue + "...");
	
	/*String data = "library b" + ":" + isbn;*/
	String data = libraryServiceConfiguration.getLibraryName() + ":" + isbn;
	
	System.out.println(data);
	
	TextMessage msg = session.createTextMessage(data);
	msg.setLongProperty("id", System.currentTimeMillis());
	producer.send(msg);

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
