package com.mahindra;

/**
 * Created by Administrator on 2018/6/12.
 */
public enum CardLeftEnum
{

	Left("左",0), Right("右", 1);

	// 成员变量
	private String name;
	private Integer code;

	private CardLeftEnum(String name, Integer code) {
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

	public Integer getCode()
	{
		return code;
	}

	public void setCode(Integer code)
	{
		this.code = code;
	}
}
