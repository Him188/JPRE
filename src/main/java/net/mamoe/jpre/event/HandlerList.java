package net.mamoe.jpre.event;

import java.util.ArrayList;

/**
 * 不知道当初写这个是干什么的
 *
 * @author Him188 @ JPRE Project
 * @since JPRE 1.0.0
 */
public class HandlerList extends ArrayList<Handler> {
	public ArrayList<Handler> getAll() {
		return this;
	}
}
