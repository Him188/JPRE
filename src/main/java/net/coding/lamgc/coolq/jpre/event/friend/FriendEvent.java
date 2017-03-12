package net.coding.lamgc.coolq.jpre.event.friend;

import net.coding.lamgc.coolq.jpre.event.Event;

abstract public class FriendEvent extends Event {
	abstract public long getQQ();

	abstract public int getTime();
}