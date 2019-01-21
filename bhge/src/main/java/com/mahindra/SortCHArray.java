package com.mahindra;

import java.util.*;

/**
 * Created by Administrator on 2018/5/9.
 */
public class SortCHArray
{

	private static List<SortCH> sortArray = new ArrayList();

//	public  SortCHArray(int total)
//	{
//		initArray(total);
//	}

	public  SortCHArray()
	{
		initArray();
	}

	private void initArray()
	{
		for (int i= 0 ;i < 6 ; i++)
		{
			SortCH sortCH = new SortCH();
			sortCH.setChNo(i);
			sortCH.setUsed(false);
			sortArray.add(sortCH);
		}
		for (int i= 8 ;i < 14 ; i++)
		{
			SortCH sortCH = new SortCH();
			sortCH.setChNo(i);
			sortCH.setUsed(false);
			sortArray.add(sortCH);
		}
	}

	private void initArray(int total)
	{
		for (int i=0;i < total ; i++)
		{
			SortCH sortCH = new SortCH();
			sortCH.setChNo(i);
			sortCH.setUsed(false);
            sortArray.add(sortCH);
		}
	}

	public void addCh(int ch)
	{
		for (SortCH sortCH:sortArray)
		{
			if (ch== sortCH.getChNo())
			{
				sortCH.setUsed(true);
			}
		}
	}
	public  List<Integer> getSortedNoUsedCH()
	{
		List<Integer> resultArray = getNoUsedCH();
		Collections.sort(resultArray);
		return  resultArray;

	}

	public List<Integer> getNoUsedCH()
	{
        List<Integer> resultArray = new ArrayList<Integer>();
		for (SortCH sortCH:sortArray)
		{
			if ( !sortCH.isUsed())
			{
                resultArray.add( sortCH.getChNo());
			}
		}
		return resultArray;
	}

	class SortCH
	{

		int chNo;
		boolean used;

		public int getChNo()
		{
			return chNo;
		}

		public void setChNo(int chNo)
		{
			this.chNo = chNo;
		}

		public boolean isUsed()
		{
			return used;
		}

		public void setUsed(boolean used)
		{
			this.used = used;
		}

	}
}
