package edu.sjsu.cmpe.library;

import javax.jms.JMSException;

import de.spinscale.dropwizard.jobs.Job;
import de.spinscale.dropwizard.jobs.annotations.Every;
import edu.sjsu.cmpe.library.config.LibraryServiceConfiguration;
import edu.sjsu.cmpe.library.pubsub.LibraryListenerA;
import edu.sjsu.cmpe.library.pubsub.LibraryListenerB;




@Every("50s")
public class ListenJobs  extends Job{
	
	@Override
	public void doJob(){
		
		LibraryServiceConfiguration libraryServiceConfiguration = new LibraryServiceConfiguration();
		String libraryName = libraryServiceConfiguration.getLibraryName();
		
		if(libraryName.equalsIgnoreCase("library-a")){
			LibraryListenerA  libraryListenerA = new LibraryListenerA();
			try{
				libraryListenerA.listenToAllTopics(); 
			}catch(JMSException e){
			e.printStackTrace();
			}
		}
		if(libraryName.equalsIgnoreCase("library-b")){
			LibraryListenerB  libraryListenerB = new LibraryListenerB();
			try{
				libraryListenerB.listenToTopicComputer();
			}catch(JMSException e){
				e.printStackTrace();
			}
		}
	}

}
