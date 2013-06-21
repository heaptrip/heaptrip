package com.heaptrip.util.tuple;

import java.io.Serializable;

public class DueObject<U, D> implements Serializable {

	private static final long serialVersionUID = 1L;

	private final U uno;
	private final D due;

	public DueObject(U uno, D due) {
		this.uno = uno;
		this.due = due;
	}

	public U getUno() {
		return this.uno;
	}

	public D getDue() {
		return this.due;
	}

	public String toString() {
		return "(" + this.uno + ", " + this.due + ")";
	}
}
