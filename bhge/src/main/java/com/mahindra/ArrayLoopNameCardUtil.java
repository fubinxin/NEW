package com.mahindra;

import com.mahindra.po.OriginalLoopNamePo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/27 0027.
 */
public class ArrayLoopNameCardUtil
{

	private int cardType; // 这里 卡是什么类型.
	private boolean isRedundancy ; //背卡..反面的,一般放冗余的信号
	/**
	 * //这里放什么信号,DI的要加倍.
	 */
	private int sigType;
	private int maxSigNum; // 这里根据 卡类型，以及信号,来确定最大的放入的数量.

    private   boolean isfull;

    public List<OriginalLoopNamePo> orarialLoopNameList = new ArrayList<>();
	public List<Integer> allotCh ; // 再分配的 CH
	private List<Integer>  lastFetch;  //获得所有的剩余CH
	private boolean fetch = false;   // 是否被



	public void putOrarialLoopName(List<OriginalLoopNamePo> loopNameList)
	{
		if (loopNameList == null ||loopNameList.size()==0 ) return;
		for (int i=0; i< loopNameList.size();i++)
		{
			OriginalLoopNamePo orarialLoopName = loopNameList.get(i);
			orarialLoopName.setChNumber(allotCh.get(i));
			orarialLoopNameList.add(orarialLoopName);

		}
		fetch = false;
	}


	public  List<Integer> getSerialCH(int chNumber)
	{
		return  getSerialCH(chNumber , 1);
	}
	/**
	 * 要保证同侧..相邻. 不能跨8-9.
	 * @param chNumber 连续的几个。
	 * @return
	 */
	public  List<Integer> getSerialCH(int chNumber, int startCH )
	{

		List<Integer>  noUseList = getOtherCH();
		if (noUseList ==null || noUseList.size()==0) return  null;

		for (Integer fNoUsed : noUseList)
		{
			if  (fNoUsed < startCH) continue;
			if (fNoUsed < 6 && fNoUsed + chNumber > 6) continue;
			if (fNoUsed + chNumber > noUseList.size() ) return null;
			if (inChList(noUseList,fNoUsed,chNumber)!= null)
			{
				return inChList(noUseList,fNoUsed,chNumber);
			}
		}

		return null;
	}



	/**
	 *
	 * @param noUseList
	 * @param start
	 * @param chNumber
	 * @return
	 */
	private   List<Integer>   inChList (List<Integer> noUseList, int start, int chNumber)
	{
		if (noUseList==null ||noUseList.size()==0) return null;
		List<Integer> result = new ArrayList<>();
		for (int i = start; i < start + chNumber; i ++ )
		{
			if ( !noUseList.contains(i)) return null;
			result.add(i);
		}
		return result;
	}

	/** 获得所有的剩余CH
	 *
	 * @return
	 */
	public  List<Integer> getOtherCH()
	{
		if ( fetch ) return lastFetch;

		SortCHArray sortCHArray = new SortCHArray();

		for (OriginalLoopNamePo orarialLoopName:orarialLoopNameList)
		{
			sortCHArray.addCh(orarialLoopName.getChNumber());
		}

		lastFetch = sortCHArray.getSortedNoUsedCH();
		fetch = true;
		return  lastFetch;
	}



	public int getMaxSigNum()
	{
//		if (sigType== SigType.DI.getCode())
//		return 28;
//
//		else return 14;
		int totalNum = 12;
		if (orarialLoopNameList ==null || orarialLoopNameList.size()==0) return totalNum;
		OriginalLoopNamePo orarialLoopName = orarialLoopNameList.get(0);
		if (orarialLoopName.getSig_type().equalsIgnoreCase(SigType.DI.getName()))
		{
			return 24;
		}
		return totalNum;

	}


	public int getSigType()
	{
		return sigType;
	}

	public void setSigType(int sigType)
	{
		this.sigType = sigType;
	}

	public boolean isIsfull()
	{
		for (OriginalLoopNamePo orarialLoopName:orarialLoopNameList)
		{
			if(orarialLoopName.getChNumber()== 0 )
				return false;
		}

		return true;
	}

	public int getCardType()
	{
		return cardType;
	}

	public void setCardType(int cardType)
	{
		this.cardType = cardType;
	}


	public static List<ArrayLoopNameCardUtil> getCardformListArray(List<ArrayLoopNameCardUtil> cardList, int lastNum, int perNum, int lastCardNumber  )
	{
		return  getCardformListArray(cardList,  lastNum,  perNum,  lastCardNumber  ,1);
	}
	/**
	 * TODO ..只差这一个了.
	 * @param cardList
	 * @param lastNum // 一共有几张卡..
	 * @param perNum  // 连续几个数.
	 * @return
	 */
	public static List<ArrayLoopNameCardUtil> getCardformListArray(List<ArrayLoopNameCardUtil> cardList, int lastNum, int perNum, int lastCardNumber  , int startCH)
	{
		//  还是这里....这里找连续的卡有问题.
		System.out.println("getCardformListArray...stated .");
		List<ArrayLoopNameCardUtil> resultList = new ArrayList<>();
		int num =0;
//这里也有空的问题.先获得第一个.
		for (int start = startCH; start < 15 ; start++) {
			resultList = new ArrayList<>();
			for (int  i = 0; i< cardList.size() ; i++)
			{
				ArrayLoopNameCardUtil arrayLoopNameCard = getCardformListNoNewCard(cardList, perNum, start, lastCardNumber + i );

				if (arrayLoopNameCard !=null && !resultList.contains(arrayLoopNameCard))
				{
					if (start != arrayLoopNameCard.allotCh.get(0) )
					{
						continue;
					}
					arrayLoopNameCard.allotCh = new ArrayList<>();
					arrayLoopNameCard.allotCh.add(start);

					resultList.add(arrayLoopNameCard); // 第一个卡的起始值需要记住..然后,后面的卡以这个值开始.
				}
				if (resultList!=null && resultList.size()==lastNum)
				{
					return resultList;
				}
			}
//			if (resultList!=null && resultList.size()==lastNum)
//			{
//				return resultList;
//			}else
//			{
//				continue;
//			}

		}

		{
			lastCardNumber = cardList.size();
			resultList = new ArrayList<>();
			for (int i =0;i<lastNum;i++ )
			{
				ArrayLoopNameCardUtil arrayLoopNameCard = new ArrayLoopNameCardUtil();

				arrayLoopNameCard.allotCh = new ArrayList<>();
				arrayLoopNameCard.allotCh.add(0);
				cardList.add(arrayLoopNameCard);
				resultList.add(arrayLoopNameCard);
			}
			return resultList;

		}


	}

	public static ArrayLoopNameCardUtil getCardCHformList(List<ArrayLoopNameCardUtil> cardList	, int startCH)
	{
		if ( cardList.size() ==0)
		{

			ArrayLoopNameCardUtil arrayLoopNameCard = new ArrayLoopNameCardUtil();

			arrayLoopNameCard.allotCh = new ArrayList<>();
			arrayLoopNameCard.allotCh.add(1);
			cardList.add(arrayLoopNameCard);
			return arrayLoopNameCard;

		}
		for (ArrayLoopNameCardUtil arrayLoopNameCard  : cardList)
		{
			if (arrayLoopNameCard.orarialLoopNameList.size()< arrayLoopNameCard.getMaxSigNum())
			{
				arrayLoopNameCard.allotCh = new ArrayList<>();
				List<Integer> getCH = arrayLoopNameCard.getSerialCH(1,startCH);
				if (getCH ==null) continue;

				arrayLoopNameCard.allotCh.addAll(getCH);
				return arrayLoopNameCard;
			}
		}

		ArrayLoopNameCardUtil arrayLoopNameCard = new ArrayLoopNameCardUtil();

		arrayLoopNameCard.allotCh = new ArrayList<>();
		arrayLoopNameCard.allotCh.add(1);
		cardList.add(arrayLoopNameCard);
		return arrayLoopNameCard;
	}

	public static ArrayLoopNameCardUtil getCardCHformList(List<ArrayLoopNameCardUtil> cardList	)
	{
		return getCardCHformList(cardList,1);

	}


	public static ArrayLoopNameCardUtil getCardformList(List<ArrayLoopNameCardUtil> cardList, int arraySize, int startCH)
	{

		if ( cardList.size() ==0)
		{

			ArrayLoopNameCardUtil arrayLoopNameCard = new ArrayLoopNameCardUtil();

			arrayLoopNameCard.allotCh = new ArrayList<>();
			arrayLoopNameCard.allotCh.add(startCH);
			cardList.add(arrayLoopNameCard);
			return arrayLoopNameCard;

		}
		for (ArrayLoopNameCardUtil arrayLoopNameCard  : cardList)
		{
			if (arrayLoopNameCard.orarialLoopNameList.size()< arrayLoopNameCard.getMaxSigNum())
			{
				arrayLoopNameCard.allotCh = new ArrayList<>();
				List<Integer> chList = arrayLoopNameCard.getSerialCH(arraySize  );
				if (chList ==null)continue;
				arrayLoopNameCard.allotCh.addAll(chList);
				return arrayLoopNameCard;
			}
		}

		ArrayLoopNameCardUtil arrayLoopNameCard = new ArrayLoopNameCardUtil();

		arrayLoopNameCard.allotCh = new ArrayList<>();
		arrayLoopNameCard.allotCh.add(startCH);
		cardList.add(arrayLoopNameCard);
		return arrayLoopNameCard;
	}

	public static ArrayLoopNameCardUtil getCardformList(List<ArrayLoopNameCardUtil> cardList, int arraySize)
	{

		if ( cardList.size() ==0)
		{

			ArrayLoopNameCardUtil arrayLoopNameCard = new ArrayLoopNameCardUtil();

			arrayLoopNameCard.allotCh = new ArrayList<>();
			arrayLoopNameCard.allotCh.add(0);
			cardList.add(arrayLoopNameCard);
			return arrayLoopNameCard;

		}
		for (ArrayLoopNameCardUtil arrayLoopNameCard  : cardList)
		{
			if (arrayLoopNameCard.orarialLoopNameList.size()< arrayLoopNameCard.getMaxSigNum())
			{
				arrayLoopNameCard.allotCh = new ArrayList<>();
                List<Integer> chList = arrayLoopNameCard.getSerialCH(arraySize  );
                if (chList ==null)continue;
                arrayLoopNameCard.allotCh.addAll(chList);
				return arrayLoopNameCard;
			}
		}

		ArrayLoopNameCardUtil arrayLoopNameCard = new ArrayLoopNameCardUtil();

		arrayLoopNameCard.allotCh = new ArrayList<>();
		arrayLoopNameCard.allotCh.add(0);
		cardList.add(arrayLoopNameCard);
		return arrayLoopNameCard;
	}

	public static ArrayLoopNameCardUtil getCardformListNoNewCard(List<ArrayLoopNameCardUtil> cardList, int arraySize, int start , int lastCardNumber)
	{

		long startTime =  System.currentTimeMillis();
		System.out.println("getCardformListNoNewCard  started..."+ startTime);

		for ( int i = lastCardNumber;i <  cardList.size();i++)
		{
			ArrayLoopNameCardUtil arrayLoopNameCard = cardList.get(i);
			if (arrayLoopNameCard.orarialLoopNameList.size()< arrayLoopNameCard.getMaxSigNum())
			{
				arrayLoopNameCard.allotCh = new ArrayList<>();
                List<Integer> chList = arrayLoopNameCard.getSerialCH(arraySize ,start );
                if (chList == null) continue;
				arrayLoopNameCard.allotCh.addAll(chList);
				//System.out.println("getCardformListNoNewCard  finded ...."+ System.currentTimeMillis());
				System.out.println("getCardformListNoNewCard  finded  spend time ...."+ (System.currentTimeMillis() -startTime));
				return arrayLoopNameCard;
			}
		}

		//System.out.println("getCardformListNoNewCard  no finded ...."+ System.currentTimeMillis());
		System.out.println("getCardformListNoNewCard  no finded  spend time ...."+ (System.currentTimeMillis() -startTime));
		return null;
	}

	/**
	 * 加入单个的.
	 * @param orarialLoopName
	 */
	public void addOrarialLoopNameList(OriginalLoopNamePo orarialLoopName) {
		fetch = false;
	    int chNumber = -1;
		// 这里 放了一下元素之后，必须CH加1. 以支持连续加。
	    if (this.allotCh !=null && this.allotCh.size()>0) {
			chNumber = this.allotCh.get(0);
			this.allotCh.remove(0);
			this.allotCh.add(chNumber+1);
		}
		orarialLoopName.setChNumber(chNumber);
		this.orarialLoopNameList.add(orarialLoopName);


	}

	/**
	 * 可以用在冗余的互斥的系列里.
	 * 加入一个相关的list
	 * @param forArrageLoopList
	 */
	public void addOrarialLoopNameList(List<OriginalLoopNamePo> forArrageLoopList) {
		fetch = false;

		for (int i = 0;i< forArrageLoopList.size() ;i++)
		{
			OriginalLoopNamePo orarialLoopName = forArrageLoopList.get(i);
            addOrarialLoopNameList(orarialLoopName);

		}
	}
}
