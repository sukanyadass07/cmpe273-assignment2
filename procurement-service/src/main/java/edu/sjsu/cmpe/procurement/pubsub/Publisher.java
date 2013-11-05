package edu.sjsu.cmpe.procurement.pubsub;

import java.net.URL;
import java.util.ArrayList;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.fusesource.stomp.jms.StompJmsConnectionFactory;
import org.fusesource.stomp.jms.StompJmsDestination;

import edu.sjsu.cmpe.procurement.domain.Book;

public class Publisher {

	private long isbn;
	private String title;
	private String category;
	private URL coverImage;
		
	public void publishTopics(ArrayList <Book> shippedBooks) throws JMSException {
		
			
		String user = env("APOLLO_USER", "admin");
		String password = env("APOLLO_PASSWORD", "password");
		String host = env("APOLLO_HOST", "54.215.210.214");
		int port = Integer.parseInt(env("APOLLO_PORT", "61613"));
		//String destination = "/topic/66309.books." + Book.getCategory();

		StompJmsConnectionFactory factory = new StompJmsConnectionFactory();
		factory.setBrokerURI("tcp://" + host + ":" + port);

		Connection connection = factory.createConnection(user, password);
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		for(int i = 0; i<shippedBooks.size(); i++){
			
			Book book = shippedBooks.get(i);
			if(book.getCategory().equalsIgnoreCase("computer")){
				
				String destination1 = "/topic/66309.book." + book.getCategory();
					
				Destination dest = new StompJmsDestination(destination1);
				MessageProducer producer = session.createProducer(dest);
				producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
				
				isbn = book.getIsbn();
				title = book.getTitle();
				category = book.getCategory();
				coverImage = book.getCoverimage();
				
				String data = isbn + ":" + title + ":" + category + ":" + coverImage;
				System.out.println(data);
				TextMessage msg = session.createTextMessage(data);
				msg.setLongProperty("id", System.currentTimeMillis());
				producer.send(msg);
				System.out.println("Data published succesfully");
			}
			if(book.getCategory().equalsIgnoreCase("comics")){
				
				String destination2 = "/topic/66309.book." + book.getCategory();
								
				Destination dest = new StompJmsDestination(destination2);
				MessageProducer producer = session.createProducer(dest);
				producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
				
				isbn = book.getIsbn();
				title = book.getTitle();
				category = book.getCategory();
				coverImage = book.getCoverimage();
				
				String data = isbn + ":" + title + ":" + category + ":" + coverImage;
				System.out.println(data);
				TextMessage msg = session.createTextMessage(data);
				msg.setLongProperty("id", System.currentTimeMillis());
				producer.send(msg);
				
				System.out.println("Data published succesfully");

			}
			if(book.getCategory().equalsIgnoreCase("management")){
				String destination3 = "/topic/66309.book." + book.getCategory();
				
				Destination dest = new StompJmsDestination(destination3);
				MessageProducer producer = session.createProducer(dest);
				producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
				
				isbn = book.getIsbn();
				title = book.getTitle();
				category = book.getCategory();
				coverImage = book.getCoverimage();
				
				
				String data = isbn + ":" + title + ":" + category + ":" + coverImage;
				System.out.println(data);
				TextMessage msg = session.createTextMessage(data);
				msg.setLongProperty("id", System.currentTimeMillis());
				producer.send(msg);
				
				System.out.println("Data published succesfully");

			}
			if(book.getCategory().equalsIgnoreCase("selfimprovement")){
				String destination4 = "/topic/66309.book." + book.getCategory();
								
				Destination dest = new StompJmsDestination(destination4);
				MessageProducer producer = session.createProducer(dest);
				producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
				
				isbn = book.getIsbn();
				title = book.getTitle();
				category = book.getCategory();
				coverImage = book.getCoverimage();
				
				String data = isbn + ":" + title + ":" + category + ":" + coverImage;
				TextMessage msg = session.createTextMessage(data);
				msg.setLongProperty("id", System.currentTimeMillis());
				producer.send(msg);
				
				System.out.println("Data published succesfully");
			}
		}
		
		/**
		 * Notify all Listeners to shut down. if you don't signal them, they
		 * will be running forever.
		 */
		//producer.send(session.createTextMessage("SHUTDOWN"));
		connection.close();
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
