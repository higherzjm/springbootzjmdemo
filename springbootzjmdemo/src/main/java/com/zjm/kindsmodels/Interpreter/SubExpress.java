package com.zjm.kindsmodels.Interpreter;


/*
 *减法解释器
 */
public class SubExpress extends Express {

	private Context context;

	public SubExpress(Context context) {
		super();
		this.context = context;
	}

	@Override
	public void Interpreter() {
		System.out.println("递减");
		String stringinput = context.getInput();
		int intinput = Integer.parseInt(stringinput);// 字符类型转换为整数类型
		int sub = 3;
		int num = intinput - sub;
		this.context.setOutput(String.valueOf(num));// 整数类型转换为字符类型

	}

}
