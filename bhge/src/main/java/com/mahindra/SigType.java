package com.mahindra;

/**
 * Created by Administrator on 2018/6/12.
 */
public enum SigType
{

	AI("传感器","AI"), DI("状态", "DI"), DO("控制", "DO"), TC("温度", "TC");

	// 成员变量
	private String name;
	private String code;

	private SigType(String name, String code) {
		this.name = name;
		this.code = code;
	}


	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}
}
