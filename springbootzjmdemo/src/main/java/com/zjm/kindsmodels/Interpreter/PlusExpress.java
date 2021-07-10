package com.zjm.kindsmodels.Interpreter;

/*
 * 递增解释器
 */
public class PlusExpress extends Express {

	private Context context;
	public PlusExpress(Context context) {
		super();
		this.context = context;
	}
	@Override
	public void Interpreter() {
		System.out.println("递增");
		String stringinput=this.context.getInput();
		int intinput=Integer.parseInt(stringinput);//字符类型转换为整数类型
		intinput++;
		this.context.setOutput(String.valueOf(intinput));//整数类型转换为字符类型
       
	}

	
	

}
