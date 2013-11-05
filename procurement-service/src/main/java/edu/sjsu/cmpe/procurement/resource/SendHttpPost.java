package edu.sjsu.cmpe.procurement.resource;

import java.util.ArrayList;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class SendHttpPost {

	@SuppressWarnings("rawtypes")
	public void JerseyClientPost(ArrayList arrayOfIsbn){
		 
		try{
			Client client = Client.create();
			WebResource webResource = client.resource("http://54.215.210.214:9000/orders");
			
			
			String input = "{\"id\":\"66309\",\"order_book_isbns\":"+arrayOfIsbn+"}";
			//String input = "{\"id\""05322\",\"order_book_isbns\":"+book.getOrderIsbnList()+"}";
			
			ClientResponse response = webResource.type("application/json").post(ClientResponse.class, input);
			
			if (response.getStatus() != 200){
				
				throw new RuntimeException("Failed to place order: HTTP error code:" + response.getStatus());
			}			
			else{
				
				System.out.println("Your order has been submitted successfully");
			}
			
			System.out.println("Output from Server .... \n");
			String output = response.getEntity(String.class);
			System.out.println(output);
			
						
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
