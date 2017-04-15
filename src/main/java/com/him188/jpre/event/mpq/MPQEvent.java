package com.him188.jpre.event.mpq;

import com.him188.jpre.event.Event;
import com.him188.jpre.network.MPQClient;

/**
 * @author Him188
 */
@SuppressWarnings("WeakerAccess")
abstract public class MPQEvent extends Event {
	protected final MPQClient client;

	public MPQEvent(MPQClient client){
		this.client = client;
	}

	public MPQClient getClient(){
		return client;
	}

}
