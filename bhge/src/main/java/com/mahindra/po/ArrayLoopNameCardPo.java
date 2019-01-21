package com.mahindra.po;

/**
 * Created by Administrator on 2018/7/16.
 */
public class ArrayLoopNameCardPo
{

	private int cardNo;    // card no .id .
	private String cardSigType; // 这里 卡是什么类型.
	private String cardSensorType; // 这里 卡是什么类型.

	private int isRedundancy ; //背卡..反面的,一般放冗余的信号 1 为正 0 为负.
	/**
	 * //这里放什么信号,DI的要加倍.
	 */
	private int maxSigNum; // 这里根据 卡类型，以及信号,来确定最大的放入的数量.


	public int getCardNo()
	{
		return cardNo;
	}

	public void setCardNo(int cardNo)
	{
		this.cardNo = cardNo;
	}

	public String getCardSigType()
	{
		return cardSigType;
	}

	public void setCardSigType(String cardSigType)
	{
		this.cardSigType = cardSigType;
	}

	public String getCardSensorType()
	{
		return cardSensorType;
	}

	public void setCardSensorType(String cardSensorType)
	{
		this.cardSensorType = cardSensorType;
	}

	public int getIsRedundancy()
	{
		return isRedundancy;
	}

	public void setIsRedundancy(int isRedundancy)
	{
		this.isRedundancy = isRedundancy;
	}

	public int getMaxSigNum()
	{
		return maxSigNum;
	}

	public void setMaxSigNum(int maxSigNum)
	{
		this.maxSigNum = maxSigNum;
	}

	@Override
	public String toString() {
		return "ArrayLoopNameCardPo{" +
				"cardNo=" + cardNo +
				", cardSigType='" + cardSigType + '\'' +
				", cardSensorType='" + cardSensorType + '\'' +
				", maxSigNum=" + maxSigNum +
				'}';
	}
}
