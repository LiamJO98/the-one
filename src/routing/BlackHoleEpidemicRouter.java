/*
 * Copyright 2010 Aalto University, ComNet
 * Released under GPLv3. See LICENSE.txt for details.
 */
package routing;

import java.util.HashMap;
import java.util.List;

import core.Message;
import core.MessageListener;
import core.Settings;

/**
 * Epidemic message router with drop-oldest buffer and only single transferring
 * connections at a time.
 */
public class BlackHoleEpidemicRouter extends ActiveRouter {

	/**
	 * Constructor. Creates a new message router based on the settings in
	 * the given Settings object.
	 * @param s The settings object
	 */
	public BlackHoleEpidemicRouter(Settings s) {
		super(s);
		//TODO: read&use epidemic router specific settings (if any)
	}

	/**
	 * Copy constructor.
	 * @param r The router prototype where setting values are copied from
	 */
	protected BlackHoleEpidemicRouter(BlackHoleEpidemicRouter r) {
		super(r);
		//TODO: copy epidemic settings here (if any)
	}

	@Override
	public void update() {
		super.update();
		if (isTransferring() || !canStartTransfer()) {
			return; // transferring, don't try other connections yet
		}

		// Try first the messages that can be delivered to final recipient
		if (exchangeDeliverableMessages() != null) {
			return; // started a transfer, don't try others (yet)
		}

		// then try any/all message to any/all connection
		this.tryAllMessagesToAllConnections();
	}


	@Override
	public BlackHoleEpidemicRouter replicate() {
		return new BlackHoleEpidemicRouter(this);
	}
	
	
	private List <MessageListener> listeners;
	private HashMap<String,Message> messages;
	
	@Override 
	protected void addToMessages(Message m, boolean newMessage) {
		messages = getMessages();
		listeners = getListeners();
		
		this.messages.put(m.getId(),m);
		
		if(newMessage) {
				for(MessageListener ml2 : this.listeners) {
					
					ml2.newMessage(m);
				}
		}else {
			deleteMessage(m.getId(),true);
		}
		
	
		
	//System.out.println("Messages: \t" + messages + "\n");
		
	//	if(messages.get(m.getId()) != null) {
	//		System.out.println("Current Message \t" + messages.get(m.getId()) + "\n");
	//	}
	//	else {
	//		System.out.println("Successfully deleted message \t" + m.getId() + "\n" + this.getHost().getRoutingInfo().toString() + "\n");
	//	}
		
		
		
	}
	
	
	
	
	
	
	
	
	
	

}