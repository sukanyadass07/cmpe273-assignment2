package edu.sjsu.cmpe.procurement;

import java.util.ArrayList;

import javax.jms.JMSException;

import de.spinscale.dropwizard.jobs.Job;
import de.spinscale.dropwizard.jobs.annotations.Every;
import edu.sjsu.cmpe.procurement.domain.Book;
import edu.sjsu.cmpe.procurement.pointtopoint.Consumer;
import edu.sjsu.cmpe.procurement.pubsub.Publisher;
import edu.sjsu.cmpe.procurement.resource.ReceiveHttpGet;
import edu.sjsu.cmpe.procurement.resource.SendHttpPost;


@Every("5mn")
public class JobsToDo extends Job {
	
	@SuppressWarnings("rawtypes")
	private ArrayList arrayOfIsbn = new ArrayList();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ArrayList <Book> shippedBooks = new ArrayList();
	boolean callPost = false;
	boolean receiveGet = false;
	
	@Override
	public void doJob(){
		
		Consumer consumer = new Consumer();
		try{
			arrayOfIsbn = consumer.readMessageFromQueue();
		}catch(JMSException e){
			e.printStackTrace();
		}
		
		if( !arrayOfIsbn.isEmpty()){
			callPost = true;
			SendHttpPost sendMessage = new SendHttpPost();
			sendMessage.JerseyClientPost(arrayOfIsbn);
		}
		
		if(callPost == true){
			ReceiveHttpGet receiveShippedBooks = new ReceiveHttpGet();
			shippedBooks = receiveShippedBooks.JerseyClientGet();
			receiveGet = true;
		}
		
		if(receiveGet == true){
			Publisher publisher = new Publisher();
			try{
				publisher.publishTopics(shippedBooks);
			}catch(JMSException e){
				e.printStackTrace();
			}
			
			
		}
		
	}
	
}
