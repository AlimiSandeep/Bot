package com.pramati.bot.bean;

public class Slots {
private int slot_id;
	private String slot_time;

	public int getSlot_id() {
		return slot_id;
	}

	public void setSlot_id(int slot_id) {
		this.slot_id = slot_id;
	}

	public String getSlot_time() {
		return slot_time;
	}

	public void setSlot_time(String slot_time) {
		this.slot_time = slot_time;
	}

	@Override
	public String toString() {
		return  slot_id + " " + slot_time;
	}

}
