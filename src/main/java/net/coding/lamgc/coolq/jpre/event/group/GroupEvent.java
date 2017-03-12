package net.coding.lamgc.coolq.jpre.event.group;

import net.coding.lamgc.coolq.jpre.event.Event;

abstract public class GroupEvent extends Event {
	abstract public long getGroup();

	abstract public int getTime();
}
