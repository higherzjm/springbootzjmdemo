package com.zjm.kindsmodels.Interpreter;

/*
 * 上下文
 */
public  class Context {

	private String input;
	private String output;

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public Context(String input) {
		super();
		this.input = input;
	}

	
}
