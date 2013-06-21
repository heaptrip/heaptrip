package com.heaptrip.util.tuple;

import java.io.Serializable;

public class TreObject<U, D, T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private final U uno;
	private final D due;
	private final T tre;

	public TreObject(U uno, D due, T tre) {
		this.uno = uno;
		this.due = due;
		this.tre = tre;
	}

	public U getUno() {
		return this.uno;
	}

	public D getDue() {
		return this.due;
	}

	public T getTre() {
		return this.tre;
	}

	public String toString() {
		return "(" + this.uno + ", " + this.due + ", " + this.tre + ")";
	}

}