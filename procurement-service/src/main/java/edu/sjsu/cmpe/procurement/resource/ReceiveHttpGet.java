package edu.sjsu.cmpe.procurement.resource;

import java.util.ArrayList;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import edu.sjsu.cmpe.procurement.domain.Book;
import edu.sjsu.cmpe.procurement.domain.ShippedBooks;

public class ReceiveHttpGet {
	
	@SuppressWarnings("unchecked")
	public ArrayList <Book> JerseyClientGet(){
		
		@SuppressWarnings("rawtypes")
		ArrayList <Book> book = new ArrayList();
		 
		try{	
			Client client = Client.create();
			 
			WebResource webResource = client.resource("http://54.215.210.214:9000/orders/66309");
			ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
			if (response.getStatus() != 200) {
				   throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
				}
		 
				ShippedBooks shippedBooks = response.getEntity(ShippedBooks.class);
				 book = shippedBooks.getShipped_books();
				
				System.out.println("Output from Server .... \n");
				
				for(int i = 0; i< book.size(); i++){
					Book receivedBooks = new Book();
					receivedBooks = book.get(i);
					System.out.println(receivedBooks.getIsbn());
					System.out.println(receivedBooks.getTitle());
					System.out.println(receivedBooks.getCategory());
					System.out.println(receivedBooks.getCoverimage());
				}
			
			
		}catch(Exception e) {
			 
			e.printStackTrace();
		}
		
		return(book);
	}


}
