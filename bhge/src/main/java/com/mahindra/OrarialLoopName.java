package com.mahindra;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 导入EXCEL使用..
 */
public class OrarialLoopName {

  //  public static String[] loop_name_dict ={"Y-E301","A-A301",}
    private String orgNo;

	private String rep_tag; // 1 1+  2 2+ 3
	private boolean repBlock;

    private String loop_name;
    private String pid_tag;
	private String sig_type;
	private String sensor_type;
	private String desc;
    private int chNumber;
    private boolean used;

    private static String[] preGroup  = {"左","右","A","1","2","3","4","1",};
    private static String[] afterGroup  = {"右","左","B","2","3","4","5","3",};
	// 客户希望的 一组一组的互斥的冗余信号标识。
	// {上下}
	// {前后}
	// {1，2，3，4}
	// 已开，已关，故障，远控
	private static String[] relatedEnd = { "已开","已关","故障","远控" };
	private static String[] relatedStart = {"开","关" ,"停"};



	public static void main(String[] args)
	{
		OrarialLoopName orarialLoopName = new OrarialLoopName();
		System.out.println(orarialLoopName.getCHDesc());
		System.out.println(orarialLoopName.getENDesc());

	}

	/**
	 * 去尾.
	 * @param chDesc
	 * @return

	public static String truncTail(String chDesc)
	{

		if (chDesc==null) return null;
		for (int i =0; i< relatedEnd.length;i++)
		{
			// 上下组。
			if (chDesc.indexOf(relatedEnd[i]) > -1)
			{

				return chDesc.substring(0,chDesc.indexOf(relatedEnd[i]));

			}

		}
		return null;
	}
	 */

	/**
	 * 去头..
	 * @param chDesc
	 * @return
	 */
	public static String truncHead(String chDesc)
	{
		if (chDesc==null) return null;
		for (int i =0; i< relatedStart.length;i++)
		{
			// 上下组。开关停
			if (chDesc.indexOf(relatedStart[i]) == 0)
			{

				return chDesc.substring(relatedStart[i].length());
			}
		}
		return null;
	}


	/**
	 * 按语义来分析。。这个现在是使用汉字来判断，以后可以修改为用英文判断。
	 * @param chDesc
	 * @return



	public boolean judgeBySameCh(String chDesc)
	{

		if (chDesc==null || chDesc.trim().isEmpty())
			return false;
		if (chDesc.equals(getCHDesc())) return false;

		boolean chHave = false;
		boolean thisHave = false;
		String thisDesc = getCHDesc();
		int prei =0;

		String netString =  truncTail  (thisDesc);
		if (netString==null)
		{
			netString = truncHead(thisDesc);
		}
		if (netString==null ) return false;

		String netchDesc = truncTail(chDesc);
		if (netchDesc==null)
		{
			netchDesc = truncHead(chDesc);
		}
		if (netchDesc==null ) return false;

		if (netString.equals(netchDesc)) return true;

		return false;

	}
	 */



	/**
	 * 按语义来分析。。这个现在是使用汉字来判断，以后可以修改为用英文判断。
	 * @param chDesc
	 * @return

    public boolean judgeByCh(String chDesc)
    {
		if (chDesc==null || chDesc.trim().isEmpty())
        return false;
		if (chDesc.equals(getCHDesc())) return true;

		for (int i =0; i< preGroup.length;i++)
		{
			// 上下组。
			if (chDesc.lastIndexOf(preGroup[i]) > -1)
			{
				String thisDesc = getCHDesc();
				if (thisDesc.lastIndexOf(afterGroup[i]) > -1)
				{
					if (judgeDelKeywords(chDesc, thisDesc, i )) return true;

				}
				//return false;

			}
		}

		return false;

    }


	private boolean judgeRelateKeywords(String chDesc, String thisDesc,int indexStart,int indexEnd)
	{
		String fPreDesc = "";
		String ePreDesc = "";

		if (chDesc.length()< indexEnd || thisDesc.length()< indexEnd) return false;

		fPreDesc = chDesc.substring(indexStart, indexEnd );
		String fNowDesc = thisDesc.substring(indexStart ,indexEnd);

		if (fPreDesc.equals(fNowDesc) ) return true;

		return false;
	}
	 */

	/**
	 * 这个indexS 是找到了数组的哪一组。。不是找到的位置。
	 * @param chDesc
	 * @param thisDesc
	 * @param indexStart
	 * @return

	private boolean judgeDelKeywords(String chDesc, String thisDesc,int indexStart )
	{
		// 这里要对， related 进行一下 replace .TODO
		for (String reWord:relatedEnd)
		{
			chDesc = chDesc.replace(reWord,"");
			thisDesc = thisDesc.replace(reWord,"");
		}
		for (String reWord:relatedStart)
		{
			if (chDesc.indexOf(reWord)==0)
			chDesc = chDesc.substring(1);
			if (thisDesc.indexOf(reWord)==0)
			thisDesc = thisDesc.substring(1);
		}
		String fPreDesc = "";
		String fNowDesc = "" ;
		String ePreDesc = "";

		int arrayI = indexStart;
		String preS = preGroup[arrayI];
		String afterS = afterGroup[arrayI];

		if (chDesc.lastIndexOf(preS) > 0)
		fPreDesc = chDesc.substring(0, chDesc.lastIndexOf(preS));

		if (thisDesc.lastIndexOf(afterS) > 0)
		   fNowDesc = thisDesc.substring(0 ,thisDesc.lastIndexOf(afterS));

		   ePreDesc = chDesc.substring(chDesc.lastIndexOf(preS)+ 1);

		 String eNowDesc = thisDesc.substring(thisDesc.lastIndexOf(afterS)+ 1);

		if (fPreDesc==null ||fNowDesc ==null || ePreDesc==null ||eNowDesc==null ) return false;

		if (fPreDesc.equals(fNowDesc) && ePreDesc.equals(eNowDesc) ) return true;

		return false;
	}
	 */

	/**
	 * 先汉字,再英文 , 找到最后的汉字.
	 * @return
	 */
	public String getCHDesc ()
	{
		String wholeDesc = getDesc();
		if (wholeDesc ==null || wholeDesc.trim().isEmpty())
		{
			wholeDesc = "Internet 网络 is 真好 very  good ^_^!Pattern.compil";
		}
		String showName = "";
		String re = "[\\u4e00-\\u9fa5]";
		String str = wholeDesc ;
		if (str==null || str.isEmpty() ) return null;

		Pattern p = Pattern.compile(re);
		Matcher m = p.matcher(str);
		int last=0;
		while (m.find())
		{
			showName = str.substring(0,m.end());
			last = m.end();
		}
	    //  TODO 这里判断一下，下一个是不是数字。如果是数字，再加上这个字符
		// 改之前的代码存在的bug，如果全部都是中文，则会出现indexof 这里去掉了） 小的右括号是不可以的。
		char perhapsNum = str.length() <= last ? str.charAt(last -1 ) : str.charAt(last);
		if (perhapsNum>'0' && perhapsNum <'9' || perhapsNum == ')')
		{
			last = last +1;
		}
		{
			showName = str.substring(0,last);;
		}
		return  showName;
		
	}

	public String getENDesc ()
	{
		String wholeDesc = getDesc();
		if (wholeDesc ==null || wholeDesc.trim().isEmpty())
		{
			wholeDesc = "Internet 网络 is 真好 very  good ^_^!Pattern.compil";
		}
		String showName = "";
		String re = "[\\u4e00-\\u9fa5]";
		String str = wholeDesc ;
		if (str==null || str.isEmpty() ) return null;

		Pattern p = Pattern.compile(re);
		Matcher m = p.matcher(str);
		int last=0;
		while (m.find())
		{
			showName = str.substring(0,m.end());
			last = m.end();
		}

		{
			showName = str.substring(last);;
		}
		return  showName;

	}

	public String getSigSensorType()
	{
		return this.sig_type +"_"+ this.sensor_type;
	}
    /**
     *  MD-FU30102B 中的，MD-FU301
     * @return
     */
    public String getLoopNameType()
    {
        String showName = "";
        String re = "[0-9]{3}";
        String str = getLoop_name() ;
        if (str==null || str.isEmpty() ) return null;

        Pattern p = Pattern.compile(re);
        Matcher m = p.matcher(str);
        if (m.find())
        {
            showName = str.substring(0,m.end());
        }else
        {
            showName = str;
        }
        return  showName;
    }
    /**
     * 去掉最后一位字母的，无字母则不去。
     * @return
     */
    public String getShortLoop_name()
    {
        if (this.loop_name ==null || this.loop_name.isEmpty())
            return null;

        char lastCh = this.loop_name.charAt( this.loop_name.length()-1);
        if (lastCh <= 'z' && lastCh >= 'A')
            return this.loop_name.substring(0, this.loop_name.length()-1);
        else
            return this.loop_name;
    }

    @Override
    public String toString() {
		String repS = repBlock?"\", repBlock ='true\'":"";

        return "OrarialLoopName{" +
                "orgNo='" + orgNo + '\'' +
                ", loop_name='" + loop_name + '\'' +
                ", sig_type='" + sig_type + '\'' +
				", sensor_type='" + sensor_type + '\'' +
				", rep_tag='" + rep_tag + '\'' +
				  repS  +
                '}';
    }

    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }

    public String getLoop_name() {
        return loop_name;
    }

    public void setLoop_name(String loop_name) {
        this.loop_name = loop_name;
    }

    public String getPid_tag() {
        return pid_tag;
    }

    public void setPid_tag(String pid_tag) {
        this.pid_tag = pid_tag;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public int getChNumber()
    {
        return chNumber;
    }

    public void setChNumber(int chNumber)
    {
        this.chNumber = chNumber;
    }

	public String getSig_type()
	{
		return sig_type;
	}

	public void setSig_type(String sig_type)
	{
		this.sig_type = sig_type;
	}

	public String getSensor_type()
	{
		return sensor_type;
	}

	public void setSensor_type(String sensor_type)
	{
		this.sensor_type = sensor_type;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	public String getRep_tag()
	{
		return rep_tag;
	}

	public void setRep_tag(String rep_tag)
	{
		this.rep_tag = rep_tag;
	}

	public boolean isRepBlock()
	{
		return repBlock;
	}

	public void setRepBlock(boolean repBlock)
	{
		this.repBlock = repBlock;
	}
}
