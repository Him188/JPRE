package net.mamoe.jpre;

/**
 * @author XianD @ JPRE Project
 * @since JPRE 1.0.0
 */
public enum OnlineStatus {
	STATUS_ONLINE(10),                       //我在线上
	STATUS_Q_ME(60),                            //Q 我吧
	STATUS_LEAVE(30),                            //离开
	STATUS_WORKING(50),                          //忙碌
	STATUS_DO_NOT_DISTURB(70),               //请勿打扰
	STATUS_HIDE(201);                             //隐身

	private int id;

	OnlineStatus(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public static OnlineStatus match(int id) {
		for (OnlineStatus onlineStatus : OnlineStatus.values()) {
			if (onlineStatus.getId() == id) {
				return onlineStatus;
			}
		}

		return STATUS_ONLINE;
	}
}