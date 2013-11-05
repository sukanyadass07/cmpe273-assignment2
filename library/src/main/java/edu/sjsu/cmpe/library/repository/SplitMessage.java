package edu.sjsu.cmpe.library.repository;

import java.net.MalformedURLException;
import java.net.URL;

import edu.sjsu.cmpe.library.domain.Book;


public class SplitMessage {
	
	
	private String[] messageArray;
	private Long isbn;
	private String title;
	private String category;
	private URL coverImage;
	
	

	public void splitMessage(String message) {
		
			messageArray = message.split(":");
		
			System.out.println(messageArray);
			isbn = Long.valueOf(messageArray[0]).longValue();
			title = messageArray[1];
			category = messageArray[2];
			String header = messageArray[3];
			String tail = messageArray[4];
			String fullAddress = header + ":" + tail;
			try{
			coverImage = new URL(fullAddress);
			}catch(MalformedURLException e){
				 e.printStackTrace();
			}
			
			System.out.println(isbn);
			System.out.println(title);
			System.out.println(category);
			System.out.println(coverImage);
			Book book = null;
			BookRepository bookRepository = new BookRepository();
			try{
				if(isbn != null ){
					book = bookRepository.searchBook(isbn);
				}
			}catch(NullPointerException e){
				e.printStackTrace();
			}
			
			System.out.println(book);
			if(book != null){
				
				book.setStatus("available");
				System.out.println("Status Updated successfully");
			
			}else{
				System.out.println("New Book to be added..");
				Book savebook = new Book();
				savebook.setIsbn(isbn);
				savebook.setCategory(category);
				savebook.setTitle(title);
				savebook.setCoverimage(coverImage);
				try{
				bookRepository.saveNewBook(isbn,savebook);
				}catch(NullPointerException e){
					e.printStackTrace();
				}
				System.out.println("New Book added successfully");
			}
			
	
	
		
		
	}
	

}
