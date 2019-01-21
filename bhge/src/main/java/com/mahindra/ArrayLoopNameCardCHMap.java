package com.mahindra;

import com.mahindra.po.OriginalLoopNamePo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/5/27 0027.
 */
public class ArrayLoopNameCardCHMap
{

	private HashMap<String,Integer> cardNetStrMap = new HashMap(); // 这里

	private List<String> keyList = new ArrayList<>();

	private static ArrayLoopNameCardCHMap instance = new ArrayLoopNameCardCHMap();

	private ArrayLoopNameCardCHMap()
	{

	}
	public static  ArrayLoopNameCardCHMap getInstance()
	{
		return  instance;
	}

	public void pubKeyValue(String key , Integer value)
	{
		if (key ==null || key .isEmpty()) return ;
		String truncKey = OriginalLoopNamePo.truncTail(key);
		if (this.cardNetStrMap.containsKey(truncKey)) return;

		this.cardNetStrMap.put(truncKey, value);

	}

	public int getCH (String key)
	{
		System.out.println("find_key : " + key);
		if (key ==null || key .isEmpty()) return 1;
		String truncKey = OriginalLoopNamePo.truncHead(key);
		Integer returnCH = this.cardNetStrMap.get(truncKey);
		if (returnCH == null) return 1;

		return (returnCH);

	}





}
