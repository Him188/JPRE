package com.him188.jpre.event.request;

import com.him188.jpre.event.Event;

abstract public class RequestEvent extends Event {
	abstract public boolean isAccept();

	abstract public void setAccept(boolean accept);

	abstract public long getQQ();

	abstract public int getTime();

	abstract public String getMessage();

	abstract public String getResponseFlag();
}
