package com.mahindra.po;

import com.mahindra.util.StringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@ApiModel(value = "信号")
public class OriginalLoopNamePo implements Serializable
{

	@ApiModelProperty(value = "编号")
	private String orgNo;
	// 这里以后加控制器.....// 以免导入的时候,会有冲突.
	@ApiModelProperty(value = "识别所使用的序号")
	private Integer discern_order ;
	@ApiModelProperty(value = " 冗余 标志1 1+  2 2+ 3")
	private String rep_tag;
	@ApiModelProperty(value = "冗余编号。")
	private Integer   reg_No;
	@ApiModelProperty(value = "相关性 标志")
	private String ralate_tag;
	@ApiModelProperty(value = "相关性编号")
	private Integer  ralate_No;
	@ApiModelProperty(value = "以前的冗余，与相关放一起，这个标志表示是不是真冗余，现无用。")
	private Boolean repBlock;
	@ApiModelProperty(value = "控制器ID")
	private String controllId;

	// below is the signal data
	@ApiModelProperty(value = "tag name")
	private String loop_name;
	@ApiModelProperty(value = "p&id")
	private String pid_tag;
	@ApiModelProperty(value = "sig type")
	private String sig_type;
	@ApiModelProperty(value = "sensor type")
	private String sensor_type;
	@ApiModelProperty(value = "目前只支持汉字的..因为英文的太不规律了..")
	private String desc_info;
	// 如果要加英文的,可以再加一个字段.把英文的识别的描述做为一列.
	// card info.
	@ApiModelProperty(value = "card no")
	private Integer cardNo;
	@ApiModelProperty(value = "左侧右侧。 0 左 ，1 右")
	private Integer isLeft;
	@ApiModelProperty(value = "一般说来，1-6为左，9-14为右")
	private Integer chNumber;

	////////以下为...识别之用.

	private static String[] preGroup  = {"左","右","A","1","2","3","4","5","1",};
	private static String[] afterGroup  = {"右","左","B","2","3","4","5","6","3",};
	// 客户希望的 一组一组的互斥的冗余信号标识。
	// {上下}
	// {前后}
	// {1，2，3，4}
	// 已开，已关，故障，远控
	private static String[] relatedEnd = { "已开","已关","故障","远控" } ;
//	private static String[] relatedStart = {"启动","停止", "开","关" ,"停"} ;
	private static String[] relatedStart = {"开","关" ,"停"} ;


	/**
	 * 去尾.
	 * @param chDesc
	 * @return
	 */
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
				return chDesc.substring(1);
//				return chDesc.substring(relatedStart[i].length());
			}
		}
		return null;
	}


	/**
	 * 按语义来分析。。这个现在是使用汉字来判断，以后可以修改为用英文判断。
	 * @param chDesc
	 * @return
	 */


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
	/**
	 * 按语义来分析。。这个现在是使用汉字来判断，以后可以修改为用英文判断。
	 * @param chDesc
	 * @return
	 */
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

	/**
	 * 这个indexS 是找到了数组的哪一组。。不是找到的位置。
	 * @param chDesc
	 * @param thisDesc
	 * @param indexStart
	 * @return
	 */
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

	/**
	 * 先汉字,再英文 , 找到最后的汉字.
	 * @return
	 */
	public String getCHDesc ()
	{
		return this.getDesc_info();

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



	//Excel 导出字段顺序
	public List<String> turnToRowList() {
		List<String> rowList = new ArrayList<>();
		rowList.add(StringUtil.convertString(this.getOrgNo()));
		rowList.add(StringUtil.convertString(this.getRep_tag()));
		rowList.add(StringUtil.convertString(this.getRepBlock().toString()));

		rowList.add(StringUtil.convertString(this.getLoop_name()));
		rowList.add(StringUtil.convertString(this.getPid_tag()));
		rowList.add(StringUtil.convertString(this.getDesc_info()));


		rowList.add(StringUtil.convertString(this.getSig_type()));
		rowList.add(StringUtil.convertString(this.getSensor_type()));

		rowList.add(StringUtil.convertString(this.getControllId()));

		rowList.add(StringUtil.convertString(this.getCardNo()));
		if (this.getIsLeft() == null) {
			rowList.add(StringUtil.convertString(this.getIsLeft()));
		} else {
			rowList.add(StringUtil.convertString(this.getIsLeft() == 0 ? "left" : "right"));
		}

		rowList.add(StringUtil.convertString(this.getChNumber()));

		return rowList;

	}

	//-------------------------


	public Integer getCardNo() {
		return cardNo;
	}

	public void setCardNo(Integer cardNo) {
		this.cardNo = cardNo;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getRep_tag() {
		return rep_tag;
	}

	public void setRep_tag(String rep_tag) {
		this.rep_tag = rep_tag;
	}

	public Boolean getRepBlock() {
		return repBlock;
	}

	public void setRepBlock(Boolean repBlock) {
		this.repBlock = repBlock;
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

	public String getSig_type() {
		return sig_type;
	}

	public void setSig_type(String sig_type) {
		this.sig_type = sig_type;
	}

	public String getSensor_type() {
		return sensor_type;
	}

	public void setSensor_type(String sensor_type) {
		this.sensor_type = sensor_type;
	}

	public String getRalate_tag()
	{
		return ralate_tag;
	}

	public void setRalate_tag(String ralate_tag)
	{
		this.ralate_tag = ralate_tag;
	}

	public String getDesc_info()
	{
		return desc_info;
	}

	public void setDesc_info(String desc_info)
	{
		this.desc_info = desc_info;
	}

	public String getControllId() {
		return controllId;
	}

	public void setControllId(String controllId) {
		this.controllId = controllId;
	}

	public Integer getDiscern_order() {
		return discern_order;
	}

	public void setDiscern_order(Integer discern_order) {
		this.discern_order = discern_order;
	}

	public Integer getReg_No() {
		return reg_No;
	}

	public void setReg_No(Integer reg_No) {
		this.reg_No = reg_No;
	}

	public Integer getRalate_No() {
		return ralate_No;
	}

	public void setRalate_No(Integer ralate_No) {
		this.ralate_No = ralate_No;
	}

	public Integer getIsLeft() {
		return isLeft;
	}

	public void setIsLeft(Integer isLeft) {
		this.isLeft = isLeft;
	}

	public Integer getChNumber() {
		return chNumber;
	}

	public void setChNumber(Integer chNumber) {
		this.chNumber = chNumber;
	}

	@Override
	public String toString() {
		return "OriginalLoopNamePo{" +
				"orgNo='" + orgNo + '\'' +
				", discern_order=" + discern_order +
				", rep_tag='" + rep_tag + '\'' +
				", reg_No=" + reg_No +
				", ralate_tag='" + ralate_tag + '\'' +
				", ralate_No=" + ralate_No +
				", repBlock=" + repBlock +
				", controllId='" + controllId + '\'' +
				", loop_name='" + loop_name + '\'' +
				", pid_tag='" + pid_tag + '\'' +
				", sig_type='" + sig_type + '\'' +
				", sensor_type='" + sensor_type + '\'' +
				", desc_info='" + desc_info + '\'' +
				", cardNo=" + cardNo +
				", isLeft=" + isLeft +
				", chNumber=" + chNumber +
				'}';
	}
}
