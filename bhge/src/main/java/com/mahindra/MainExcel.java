package com.mahindra;

import com.mahindra.po.ArrayLoopNameCardPo;
import com.mahindra.po.OriginalLoopNamePo;
import com.mahindra.service.LoopNameService;
import com.mahindra.util.SpringUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/4/16.
 */
public class MainExcel {
	public static int maxOrderNumber = 999999999;

	int loopNameCol = 2;
	int tabNameCol = 3;

    public List<OrarialLoopName> getOriginal(InputStream excelInput, int startrow, int[] selectCols) throws Exception
	{
		//NO TAG_NAME PID SIG_TYPE SENSOR_TYPE DESCRIPTION TAG_NAME Redundant
//		int [] selectCols ={0,1,2,6,7,3,1};

		return getOrarialLoopNames(excelInput, startrow, selectCols);
	}

	List<OrarialLoopName> getOriginalWithRE (InputStream excelInput) throws Exception
	{
		int [] selectCols ={0,2,3,7,8,4,1};

		return getOrarialLoopNames(excelInput,1, selectCols);
	}

	private List<OrarialLoopName> getOrarialLoopNames(InputStream excelInput, int startrow, int[] selectCols) throws Exception
	{
		List<OrarialLoopName> resultList = new ArrayList();
		Excel_reader test= new Excel_reader();
		ArrayList<ArrayList<String>> arr= test.xlsx_reader(excelInput ,selectCols);  //后面的参数代表需要输出哪些列，参数个数可以任意
		for(int i= startrow;i<arr.size();i++){
			ArrayList<String> row = arr.get(i);
			if (row==null || row.get(0)==null || row.get(0).equals( Excel_reader.Empty_str)) continue;

			OrarialLoopName orarialLoopName = new OrarialLoopName();
			orarialLoopName.setOrgNo(row.get(0));
			orarialLoopName.setLoop_name(row.get(1));
			orarialLoopName.setPid_tag(row.get(2));
			orarialLoopName.setSig_type(row.get(3));
			orarialLoopName.setSensor_type(row.get(4));
			if (row.size() >= 6 ) {
				orarialLoopName.setDesc(row.get(5) == null ? "" : row.get(5));
			}

			if (row.size() >= 7 ) {
				if (row.get(6) != null){
					if ("true".equals(row.get(6)) || "false".equals(row.get(6))) {
						orarialLoopName.setRepBlock("true".equals(row.get(6)) ? true : false);
					} else {
						orarialLoopName.setRep_tag(row.get(6) == null ? "" : row.get(6));
					}
				}
			}
			if (row.size() >= 8 ) {
				if (row.get(7) != null) {
					orarialLoopName.setRepBlock("true".equals(row.get(7)) ? true : false);
				}
			}


			orarialLoopName.setUsed(false);
			resultList.add(orarialLoopName);

		}
		return  resultList;
	}


	List<List<String>> getArrangedIO (InputStream excelInput) throws Exception
    {

        List<List<String>> resultList = new ArrayList();
        Excel_reader test= new Excel_reader();
        ArrayList<ArrayList<String>> arr = test.xlsx_reader(excelInput );  //后面的参数代表需要输出哪些列，参数个数可以任意
        for(int i=0;i<arr.size();i++){
            ArrayList<String> row = arr.get(i);
            if (row==null || row.size()<6 || row.get(2)==null || row.get(2).equals( Excel_reader.Empty_str)) continue;

            resultList .add(row);

        }
        return  resultList;
    }

    List<List<String>> getArrangedIOinOriginal ( List<List<String>> ArrangedList,  List<OrarialLoopName> originalList ) throws IOException
    {

        List<List<String>> resultList = new ArrayList();

        List<List<String>> arr = ArrangedList ;
        for(int i=0;i<arr.size();i++){
            List<String> row = arr.get(i);

			if (row==null || row.size()<6 || row.get(loopNameCol)==null || row.get(tabNameCol)==null ) continue;

			if (isExitLoopName_Tag(originalList,row.get(loopNameCol),row.get(tabNameCol)))
			{
				resultList .add(row);
			}

		}
		return  resultList;
	}

	List<List<String>> getArrangedIOinOriginalNoRepeat ( List<List<String>> ArrangedList,  List<OrarialLoopName> originalList ) throws IOException
	{

		List<List<String>> resultList = new ArrayList();

		List<List<String>> arr = ArrangedList ;
		for(int i=0;i<arr.size();i++){
			List<String> row = arr.get(i);

			if (row==null || row.size()<6 || row.get(loopNameCol)==null || row.get(tabNameCol)==null ) continue;

			if (isExitLoopName_Tag_NoRepeat(originalList,row.get(loopNameCol),row.get(tabNameCol)))
			{
				resultList .add(row);
			}

		}
		return  resultList;
	}


	boolean isExitLoopName_Tag(List<OrarialLoopName> orarialList ,String loopName,String tag)
	{
		if  (orarialList==null || orarialList.size()==0) return false;
		if (loopName ==null || tag ==null) return  false;

		for (OrarialLoopName orarialLoopName : orarialList)
		{
			if(loopName.equals(orarialLoopName.getLoop_name()) && tag.equals(orarialLoopName.getPid_tag()) )
				return true;
		}
		return false;
	}

	boolean isExitLoopName_Tag_NoRepeat(List<OrarialLoopName> orarialList ,String loopName,String tag)
	{
		if  (orarialList==null || orarialList.size()==0) return false;
		if (loopName ==null || tag ==null) return  false;

		for (OrarialLoopName orarialLoopName : orarialList)
		{
			if(loopName.equals(orarialLoopName.getLoop_name()) && tag.equals(orarialLoopName.getPid_tag()) && orarialLoopName.isUsed()==false) {
				orarialLoopName.setUsed(true);
				return true;

			}
		}
		return false;
	}

	public List<String> getLoopNameType(List<OriginalLoopNamePo> oldList)
	{

		List<String> noRepeatLoopname = new ArrayList<String>();
		for (OriginalLoopNamePo orarialLoopName : oldList)
		{
			String showName = "";
			String re = "[0-9]{3}";
			String str = orarialLoopName.getLoop_name() ;
			if (str==null || str.isEmpty() ) continue;

			Pattern p = Pattern.compile(re);
			Matcher m = p.matcher(str);
			if (m.find())
			{
				showName = str.substring(0,m.end());
			}else
			{
				showName = str;
			}

			if( noRepeatLoopname.contains(showName)) continue;
			noRepeatLoopname.add( showName );

		}
		return noRepeatLoopname;

	}

	public List<String> getNorepeatLoopName(List<OriginalLoopNamePo> oldList)
	{

		List<String> noRepeatLoopname = new ArrayList<String>();
		for (OriginalLoopNamePo orarialLoopName : oldList)
		{

			String str = orarialLoopName.getLoop_name() +":"+ orarialLoopName.getPid_tag() +":"+ orarialLoopName.getOrgNo();

			String   showName = str;


			if( noRepeatLoopname.contains(showName))
			{
				System.out.println("repeat!! "+showName);
			};
			noRepeatLoopname.add( showName );

		}
		return noRepeatLoopname;

	}

	public List<ArrangeLoopName> accountLoopName(List<OrarialLoopName> oldList)
	{
		if (oldList ==null || oldList.size()==0) return null;

		List<ArrangeLoopName> arrangeLoopNameList = new ArrayList();
		for (OrarialLoopName orarialLoopName : oldList)
		{

			String strLoopName = orarialLoopName.getLoop_name() ; // 这里可以扩展 到TAG 以处理空的loop name

			String   showName = orarialLoopName.getLoopNameType();
			if (showName ==null) continue;
			boolean already = false;

			for (ArrangeLoopName arrangeLoopName:arrangeLoopNameList) {
				if (showName.equals( arrangeLoopName.getLoopNameType() )) {
					arrangeLoopName.setNum( arrangeLoopName.getNum()+1);
					already =true;
					break;
				}  ;

			}
			if (!already) {
				ArrangeLoopName arrangeLoopName = new ArrangeLoopName();
				arrangeLoopName.setNum(1);
				arrangeLoopName.setLoopName(strLoopName);
				arrangeLoopName.setLoopNameType(showName);
				arrangeLoopNameList.add(arrangeLoopName);
			}

		}
		return arrangeLoopNameList;

	}



	private static void setModCH(List<String> findList, int module, int ch , String rep_tag)
	{
		if ( findList==null ) return;
		while ( findList.size() < 15)
		{
			findList.add("");
		}
		findList.set(12, "" + module);
		findList.set(13 ,""+ ( ch +1) );
		findList.add(1,rep_tag );

	}

	/**
	 * 因为norNo 是唯一 标识
	 * @param newList
	 * @param ffArrangeLoopName
	 * @return
	 */
	private static List<String> findRowList(List<List<String>> newList, OriginalLoopNamePo ffArrangeLoopName)
	{
		if (newList ==null || ffArrangeLoopName == null ) return  null;
		if (ffArrangeLoopName.getOrgNo()==null)
		{
			return new ArrayList<String>();
		}

		for(List<String> sList : newList)
		{
			if (sList == null || sList.size()< 2 ) return null;
			//if (sList.get(2).equals(ffArrangeLoopName.getOrgNo()))

			String orgNo = sList.get(0);
			if (orgNo==null || orgNo.isEmpty()) return null;
			//System.out.println("orgNo *"+orgNo);
			//String tag  = sList .get(5);
			if (orgNo.trim().equalsIgnoreCase(ffArrangeLoopName.getOrgNo().trim()))
			{
				return sList;
			}

		}
		return null;

	}

	private  List<List<String>> sortArragedLists(ArrangeLoopNameArray arrangeLoopNameArray, List<List<String>> newList ,boolean ch)
	{
		List<List<String>> newArrayList = new ArrayList<List<String>>();

		List< List <List<OriginalLoopNamePo>>> typeArrageLoopList =  arrangeLoopNameArray.getOriginalLoopNamePoList();

		for(List <List<OriginalLoopNamePo>> forArrageLoopList: typeArrageLoopList )
		{
			int testff =0;
			for (int fi = 0;fi<  forArrageLoopList.size();fi++)
			{
				List<OriginalLoopNamePo> forArrgeLoopName = forArrageLoopList.get(fi);
				if (forArrgeLoopName == null) continue;
				if (newList == null || newList.size() == 0) break;

				if (ch)
				{
					// 重排 CH...
					int oldCH = 0;
					//			Iterator<OrarialLoopName> iter = forArrgeLoopName.iterator();
					//			while (iter.hasNext()) {
					//				OrarialLoopName item = iter.next();
					//				oldCH = item.getChNumber();
					//
					//				int newCH = (reArrangeCh(item.getChNumber(),oldCH, forArrgeLoopName ));
					//				if (oldCH != newCH)
					//				{
					//					//forArrgeLoopName.remove(item);
					//					item.setChNumber(newCH);
					//					//forArrgeLoopName.add(item);
					//
					//				}
					//
					//			}
					//		reArraygeListCh(forArrgeLoopName);

					//                for (int fj = 0; fj < forArrgeLoopName.size(); fj++)
					//                {
					//                    OrarialLoopName item = forArrgeLoopName.get(fj);
					//
					//                    oldCH = item.getChNumber();
					//
					//                    int newCH = (reArrangeCh(item.getChNumber(), fj, forArrgeLoopName));
					//                    if (oldCH != newCH)
					//                    {
					//                        //forArrgeLoopName.remove(item);
					//                        item.setChNumber(newCH);
					//                        //forArrgeLoopName.add(item);
					//
					//                    }
					//                }
				}
				for (int fj = 0; fj < forArrgeLoopName.size(); fj++)
				{

					OriginalLoopNamePo ffArrangeLoopName = forArrgeLoopName.get(fj);
					if (newList == null || newList.size() == 0) break;
					testff++;
					System.out.println(testff + ":**********************");
					System.out.println("newList.size():" + newList.size());
					List<String> findList = findRowList(newList, ffArrangeLoopName);
					newList.remove(findList);
					if (findList != null)
					{
						System.out.println("find it !!" + findList);
						if (ch)
						{
							int newCHNum = ffArrangeLoopName.getChNumber() % 16;
							ffArrangeLoopName.setChNumber(newCHNum);
							setModCH(findList, fi, newCHNum, ffArrangeLoopName.getRep_tag());
						} else
						{
							setModCH(findList, fi, fj, ffArrangeLoopName.getRep_tag());
						}
						newArrayList.add(findList);

					}
				}
			}
			newArrayList.add(new ArrayList<String>());
		}

		System.out.println("newArrayList***"+newArrayList.size());
		return newArrayList;

	}

	/**
	 * TODO ,这里要改一下..现在先手工的排一下. 这是考虑 4 左右侧,前后面的.
	 * @param arrangeLoopNameArray 这里按冗余标,相关标,然后,再排无冗余的..
	 * newList 这里是取的EXCEL里的.
	 * @see ArrayLoopNameCardUtil
	 * @return
	 */
	public List<List<String>> reArraygeListCh(ArrangeLoopNameArray arrangeLoopNameArray, List<List<String>> newList)
	{

		List<List<String>> newArrayList = new ArrayList<List<String>>();

		List<ArrayLoopNameCardUtil> AllCardList = arrangeLoopNameCards(arrangeLoopNameArray);
		// 对这个卡重排..
		for (int i =0; i< AllCardList.size() ;i++)
		{
			ArrayLoopNameCardUtil arrayLoopNameCard = AllCardList.get(i);
			Collections.sort(arrayLoopNameCard.orarialLoopNameList,new ArrayLooopNameCardSorter());
			List<List<String>> fList = 	sortArragedListsWithCard( arrayLoopNameCard.orarialLoopNameList , newList , i);
			newArrayList.addAll(fList);

		}
		return newArrayList;

	}

	public List<ArrayLoopNameCardUtil> arrangeLoopNameCards(ArrangeLoopNameArray arrangeLoopNameArray)
	{
		List<ArrayLoopNameCardUtil> AllCardList = new ArrayList<>();

		int lastFindCard = 0;

		List< List <List<OriginalLoopNamePo>>> arrageLoopList =  arrangeLoopNameArray.getOriginalLoopNamePoList();

		//第一步，读取这一组。从1 并且有重复标的。一直读。。。读到，1 有重复标，或无相关标。
		for(List <List<OriginalLoopNamePo>> typeArrageLoopList: arrageLoopList )
		{
			List<ArrayLoopNameCardUtil> cardList = new ArrayList<>();
			lastFindCard =0;

			if (typeArrageLoopList ==null || typeArrageLoopList.size()== 0 )continue;

			for ( List<OriginalLoopNamePo>  forArrageLoopList :typeArrageLoopList)
			{
				if (forArrageLoopList ==null || forArrageLoopList.size()== 0 ) continue;

				int arraySize = forArrageLoopList.size();
				OriginalLoopNamePo lastOrialLoopname = forArrageLoopList.get(forArrageLoopList.size()-1);
				//TODO ..
				String lastNumS = lastOrialLoopname.getRep_tag();
				boolean repBlock = lastOrialLoopname.getRepBlock();
				String sigType = lastOrialLoopname.getSig_type();
				if (repBlock)   // 有冗余 的.
				{

					lastNumS = lastNumS.replace("+", "");


					int lastNum = Integer.parseInt(lastNumS); // 几组..

					int perNum = arraySize / lastNum;

					List<ArrayLoopNameCardUtil>  repCardList =null;
					if (sigType.equals(SigType.DO.getCode()))
					{
						int startCH = ArrayLoopNameCardCHMap.getInstance().getCH(lastOrialLoopname.getCHDesc());
						startCH = (startCH/8)*8 + 1 ;
						repCardList= ArrayLoopNameCardUtil.getCardformListArray(cardList, lastNum, perNum, lastFindCard, startCH);

					}else
					{
						repCardList= ArrayLoopNameCardUtil.getCardformListArray(cardList, lastNum, perNum, lastFindCard); // 这里换成取ARRAY卡.

					}


					System.out.println( "getCardformListArray finished...!!");
					List<List<OriginalLoopNamePo>> splitArray = splitArrageLoopList(forArrageLoopList, perNum);
					for (int i=0;i< repCardList.size(); i++)
					{
						ArrayLoopNameCardUtil arrayLoopNameCard = repCardList.get(i); //这里还需要调..
						arrayLoopNameCard.addOrarialLoopNameList(splitArray.get(i));
						// 再把每一组的第一个,的CH,存起来..
						OriginalLoopNamePo orarialLoopName = splitArray.get(i).get(0);
						if (orarialLoopName!=null && orarialLoopName.getSig_type()!=null && orarialLoopName.getSig_type().equals(SigType.DI.getCode()))
						{
							ArrayLoopNameCardCHMap.getInstance().pubKeyValue(orarialLoopName.getCHDesc(),orarialLoopName.getChNumber());
						}
					}
					System.out.println("repBlock"+ forArrageLoopList.get(0).getSig_type());
				}else
				{
					if (lastNumS!=null && !lastNumS.isEmpty()) //相关的.
					{
						ArrayLoopNameCardUtil relateCard = null;
						if (sigType.equals(SigType.DO.getCode()))
						{
							int startCH = ArrayLoopNameCardCHMap.getInstance().getCH(lastOrialLoopname.getCHDesc());
							startCH = (startCH/8)*8 + 1 ;
							//这里是下面的一对.
							relateCard= ArrayLoopNameCardUtil.getCardformList(cardList, arraySize, startCH);

						}else
						{
							relateCard = ArrayLoopNameCardUtil.getCardformList(cardList, arraySize);
						}
						relateCard.addOrarialLoopNameList(forArrageLoopList);
						// 再把每一组的第一个,的CH,存起来..
						//OrarialLoopName orarialLoopName = forArrageLoopList.get(0).get(0);
						if (lastOrialLoopname !=null && lastOrialLoopname .getSig_type()!=null && lastOrialLoopname.getSig_type().equals(SigType.DI.getCode()))
						{
							ArrayLoopNameCardCHMap.getInstance().pubKeyValue(lastOrialLoopname.getCHDesc(),lastOrialLoopname.getChNumber());
						}
					}
					else // 单个的。。
					{
						for (OriginalLoopNamePo orarialLoopName:forArrageLoopList)
						{
							ArrayLoopNameCardUtil relateCard = null;
							if (sigType.equals(SigType.DO.getCode()))
							{
								int startCH = ArrayLoopNameCardCHMap.getInstance().getCH(lastOrialLoopname.getCHDesc());
								startCH = (startCH/4)*4 + 1 ;
								// TODO 这里调的另外的方法了..
								relateCard= ArrayLoopNameCardUtil.getCardCHformList(cardList, startCH);

							}else
							{
								relateCard = ArrayLoopNameCardUtil.getCardCHformList(cardList);
							}
							relateCard.addOrarialLoopNameList(orarialLoopName);
						}
					}

				}

			}
			AllCardList.addAll(cardList);
		}

		System.out.println("finish array card.!");
		return AllCardList;
	}


	private List<List<OriginalLoopNamePo>> splitArrageLoopList(List<OriginalLoopNamePo> forArrageLoopList, int perNum)
	{
		if (forArrageLoopList==null ) return null;
		System.out.println("forArrageLoopList.size>>:"+forArrageLoopList.size());
//
//		System.out.println("perNum>>:"+ perNum);

		List<List<OriginalLoopNamePo>> result = new ArrayList<>();
		List<OriginalLoopNamePo> fList = null;
		for (int i =0 ;i< forArrageLoopList.size();i++)
		{

			if (i% perNum ==0)
			{
				fList = new ArrayList<>();
				result.add(fList);

			}
			OriginalLoopNamePo orarialLoopName = forArrageLoopList.get(i);
			fList.add(orarialLoopName);

		}
		return result;
	}


	private List<Integer> getRepeatCH(List<OrarialLoopName> loopNameList)
	{
		boolean isRepeat = false;
		List<Integer> result = new ArrayList<Integer>();
		for (int i =0; i< loopNameList.size() ;i++)
		{
			OrarialLoopName inLoopName = loopNameList.get(i);
			for (int j =0;j<i; j++)
			{

				if (loopNameList.get(j).getChNumber() == inLoopName.getChNumber() )
				{
					if (!result.contains(i))
					{
						result.add(i);
					}
				}
			}

		}
		return result;
	}

	private List<Integer> getIdleCH(List<OrarialLoopName> loopNameList)
	{
		SortCHArray sortCHArray =new SortCHArray();

		for(OrarialLoopName orarialLoopName:loopNameList)
		{
			sortCHArray.addCh(orarialLoopName.getChNumber());
		}
		List<Integer> idleCh = sortCHArray.getNoUsedCH();
		return idleCh;
	}


	private int reArrangeCh (int forArrgeLoopNameCH,int oldIndex,  List<OrarialLoopName>  loopNameList)
	{
		boolean isRepeat = false;
		for (int i =0; i< loopNameList.size() ;i++)
		{
			if (i== oldIndex ) continue;
			OrarialLoopName inLoopName = loopNameList.get(i);
			if (inLoopName.getChNumber() == forArrgeLoopNameCH )
			{
				isRepeat = true;
			}
		}
		if (isRepeat)
		{
//			SortCHArray sortCHArray =new SortCHArray();
//			for(OrarialLoopName orarialLoopName:loopNameList)
//			{
//				sortCHArray.addCh(orarialLoopName.getChNumber());
//			}
//			int idleCh = sortCHArray.getNoUsedCH();
//			return idleCh;

		}else
		{
			return forArrgeLoopNameCH;
		}
		return 0;

	}

	/**
	 *
	 * @param arrangeLoopNameArray
	 * @param newList
	 * @return
	 */
	private  List<List<String>> sortArragedLists(ArrangeLoopNameArray arrangeLoopNameArray, List<List<String>> newList) {
		return sortArragedLists(arrangeLoopNameArray, newList,false);
	}

	/**
	 * 按简单排序来重排 有EXCEL
	 * @param oldList
	 * @param newList
	 * @return
	 */
	private static List<List<String>> sortArragedListsWithCard(List<OriginalLoopNamePo> oldList, List<List<String>> newList ,int cardNo )
	{
		List<List<String>> newArrayList = new ArrayList<List<String>>();
//		arrangeReTag(oldList );

		for (int fj = 0; fj < oldList.size() ; fj ++)
		{

			OriginalLoopNamePo ffArrangeLoopName = oldList.get(fj);

			System.out.println(":**********************");

			List<String> findList = findRowList(newList ,ffArrangeLoopName );
			if (findList !=null)
			{
				newList.remove(findList);
				System.out.println("find it !!"+findList);

				{
					setModCH(findList, cardNo, ffArrangeLoopName.getChNumber(),ffArrangeLoopName.getRep_tag());

				}
				newArrayList.add(findList);

			}
		}

		return newArrayList;
	}

	/**
	 * 按简单排序来重排 有EXCEL\\TODO
	 * 这里以后改写,直接把数据库里的东西,输出到EXCEL.表..
	 * @param oldList

	 * @return
	 */
	public static List<List<String>> sortArragedListsBySimple(List<OriginalLoopNamePo> oldList )
	{
		List<List<String>> newArrayList = new ArrayList<List<String>>();
//		arrangeReTag(oldList );

		for (int fj = 0; fj < oldList.size() ; fj ++)
		{

			OriginalLoopNamePo ffArrangeLoopName = oldList.get(fj);

//			System.out.println(":**********************");

			List<String> findList = ffArrangeLoopName.turnToRowList( );


			newArrayList.add(findList);


		}

		return newArrayList;
	}

	public static void arrangeReg_No(List<OriginalLoopNamePo> oldList)
	{
		int testRe = 1;
		int testSame = 0;
		int regGenerateNumber = 0;
		OriginalLoopNamePo ffArrangeLoopName = null;
		for (int fj = 0; fj < oldList.size() ; fj ++)
		{
			ffArrangeLoopName = oldList.get(fj);
			int regTagInt = 0;
			String regTag = ffArrangeLoopName.getRep_tag();
			try{
				regTagInt = Integer.parseInt(regTag);
			}catch (Exception e)
			{

			}

			if (regTagInt == 1)
			{

				Math.random();
				regGenerateNumber = (int) System.currentTimeMillis() + (int) Math.random() * 1000;
				regGenerateNumber = dealGenerateNumber(regGenerateNumber);
				ffArrangeLoopName.setReg_No(regGenerateNumber);

			}
			else
			{
				ffArrangeLoopName.setReg_No(regGenerateNumber);
			}
			try
			{
				Thread.sleep(10);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}

		}


	}

	public static void arrangeRelate_No(List<OriginalLoopNamePo> oldList)
	{
		int regGenerateNumber = 0;
		OriginalLoopNamePo ffArrangeLoopName = null;
		for (int fj = 0; fj < oldList.size() ; fj ++)
		{
			ffArrangeLoopName = oldList.get(fj);
			int regTagInt = 0;
			String regTag = ffArrangeLoopName.getRep_tag();
			ffArrangeLoopName.setRalate_tag(regTag);
			try{
				regTagInt = Integer.parseInt(regTag);
			}catch (Exception e)
			{

			}

			if (regTagInt == 1)
			{

				Math.random();
				regGenerateNumber = (int) System.currentTimeMillis() + (int) Math.random() * 1000;
				regGenerateNumber = dealGenerateNumber(regGenerateNumber);

				ffArrangeLoopName.setRalate_No(regGenerateNumber);

			}
			else
			{
				ffArrangeLoopName.setRalate_No(regGenerateNumber);
			}
			try
			{
				Thread.sleep(10);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}

		}


	}

	/**
	 * 处理序列号
	 * @param regGenerateNumber
	 * @return
	 */
	private static int dealGenerateNumber(int regGenerateNumber) {
		while (regGenerateNumber > maxOrderNumber )
        {
            regGenerateNumber -= maxOrderNumber;
        }
		if (regGenerateNumber <= 0 )
        {
            regGenerateNumber += maxOrderNumber;
        }
		return regGenerateNumber;
	}

	public  static void arrangeReTag(List<OriginalLoopNamePo> oldList )
	{
		int testRe = 1;
		int testSame = 0;
		OriginalLoopNamePo preLoopName = null;
		for (int fj = 0; fj < oldList.size() ; fj ++)
		{

			OriginalLoopNamePo ffArrangeLoopName = oldList.get(fj);

			if (preLoopName == null)
			{

			} else
			{
				if (ffArrangeLoopName.judgeBySameCh(preLoopName.getCHDesc()))
				{
					testSame = 1;
				} else
				{
					testSame = 0;
				}
				if (ffArrangeLoopName.judgeByCh(preLoopName.getCHDesc()))
				{
					testRe++;
				} else
				{
					if (testSame == 1)
					{
						int addIndex = preLoopName.getRep_tag().indexOf("+");
						if (addIndex == -1)
							testRe = Integer.parseInt(preLoopName.getRep_tag());
						else
						{
							testRe = Integer.parseInt(preLoopName.getRep_tag().substring(0, addIndex));
						}

					} else
					{
						testRe = 1;
					}
				}

				if (testRe == 1)
				{
// 将前一个EXCEL行的清了。。。
					//List<String> findList = findRowList(newArrayList ,preLoopName );

					//if (findList != null)
					{
						if (testSame == 0 && preLoopName.getRep_tag().equals("1"))
						{
							preLoopName.setRep_tag("");
//							findList.remove(1);
//							findList.add(1, "");
						}


					}
				}
			}
			// 是234的时候，现在也加上的1有同相关。
			//TODO 这里 识别。。rongyu。。。
			if (testSame == 1 && testRe != 0)
			{
				ffArrangeLoopName.setRep_tag("" + testRe + "+");
			}
			else
			{
				ffArrangeLoopName.setRep_tag("" + testRe );
			}



			preLoopName = ffArrangeLoopName;
		}

		//暂时解决最后一行数据rep_tag=1的bug
		if ("1".equals(preLoopName.getRep_tag())) {
			preLoopName.setRep_tag("");
		}


		int regGenerateNumber = 0;
		boolean isInRE = false;
		for (int i = oldList.size() ;i >0 ;i--)
		{
			OriginalLoopNamePo ffArrangeLoopName = oldList.get(i-1);
			if (ffArrangeLoopName.getRep_tag()==null || ffArrangeLoopName.getRep_tag().isEmpty())
			{
				ffArrangeLoopName.setRepBlock(false);
				isInRE = false;
				regGenerateNumber = 0;
			}else
			{
				String regTag = ffArrangeLoopName.getRep_tag();
				regTag= regTag.replaceAll("\\+","");
				int regTagInt = 0;
				try{
					regTagInt = Integer.parseInt(regTag);
				}catch (Exception e)
				{

				}
				if (regTagInt > 1)
				{
					isInRE = true;
					ffArrangeLoopName.setRepBlock(true);
					if (regGenerateNumber!=0)
					{
						ffArrangeLoopName.setReg_No(regGenerateNumber);
					}

					ffArrangeLoopName.setRalate_tag(""+regTagInt);
				}
				if (regTagInt == 1)
				{
					if (isInRE)
					{
						String regTagAll = ffArrangeLoopName.getRep_tag();
						ffArrangeLoopName.setRepBlock(true);
						Math.random();
						regGenerateNumber = (int)System.currentTimeMillis()+ (int)Math.random()*1000;
						regGenerateNumber = dealGenerateNumber(regGenerateNumber);
						ffArrangeLoopName.setReg_No(regGenerateNumber);
						ffArrangeLoopName.setRalate_tag(""+regTagInt);
						if (!regTagAll.contains("+"))
						{
							isInRE = false;
						}

					}
					else
					{
						ffArrangeLoopName.setRepBlock(false);
						regGenerateNumber = 0;
					}
				}


			}
		}
	}

	public static void begin(InputStream excelInput) {
		try {
//			String originalFile = "F:\\softCode\\ge-code\\bhge\\main\\java\\src\\IN_PROCESS_0.xls";
//
//			String arrangeFile = "Arranged_PROCESS_";
//
//			Excel_reader test= new Excel_reader();


			int [] selectCols ={0,1,2,6,7,3,1};
			Excel_reader excel_reader = new Excel_reader();
			MainExcel mainEx = new MainExcel();
			List<OrarialLoopName> oldList = mainEx.getOriginal(excelInput, 1, selectCols);
			System.out.println(oldList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *  相同的类型的一组信号。来进行编排
	 * @param sameTypeList  相同的类型的一组信号
	 */
	public void arrayCardDBForSameTypeGoodList(List<List<OriginalLoopNamePo>> sameTypeList)
	{
		if (sameTypeList ==null || sameTypeList.size()==0) return;
		for (List <OriginalLoopNamePo> reList : sameTypeList )
		{
			int cardMaxNum = 7 ; // 16


			if (reList==null || reList.size()==0) continue;
			String cardType = reList.get(0).getSig_type()  ;
			String sensorType = reList.get(0).getSigSensorType()  ;
			if( cardType ==null ||cardType.isEmpty() ) continue;
			if (cardType.equals(SigType.DI.getCode()))
				cardMaxNum = 13 ; // 32

			int needCardNum = reList.size();
			int needCHNum = 0;
			try
			{
				needCardNum = Integer.parseInt(reList.get(reList.size() - 1).getRep_tag().replaceAll("\\+", ""));
				needCHNum = reList.size() / needCardNum ;
			}catch (Exception e)
			{
				e.printStackTrace();
			}
			LoopNameService loopNameService =  SpringUtil.getBean(LoopNameService.class);

			List<ArrayLoopNameCardPo> alreadyCardNumL =  loopNameService.getCardByType(cardType ,CardLeftEnum.Left.getCode() ,  cardMaxNum - needCHNum  ); // TODO 这里需要UTIL调用service .数据库里查一下，左侧有空的卡。
			List<ArrayLoopNameCardPo> alreadyCardNumR =  loopNameService.getCardByType(cardType, CardLeftEnum.Right.getCode(), cardMaxNum - needCHNum);  //
			int maxCardNum = loopNameService.getMaxCardNum();
			System.out.println("alreadyCardNumL: " + alreadyCardNumL + "alreadyCardNumR: " + alreadyCardNumR);
			if ( alreadyCardNumR==null ||  alreadyCardNumL.size() >= alreadyCardNumR.size())
			{
				arrangeCard(reList, cardMaxNum, cardType, sensorType, needCardNum, needCHNum, loopNameService, alreadyCardNumL, maxCardNum,CardLeftEnum.Left.getCode());

			}else
			{
				arrangeCard(reList, cardMaxNum, cardType, sensorType, needCardNum, needCHNum, loopNameService, alreadyCardNumR, maxCardNum,CardLeftEnum.Right.getCode());

			}


		}


	}

	/**
	 * 零散的。 TODO
	 * @param sameTypeList
	 */
	public void arrayCardDBForSameTypeGoodListNoLeft(List<List<OriginalLoopNamePo>> sameTypeList)
	{
		if (sameTypeList ==null || sameTypeList.size()==0) return;
		for (List <OriginalLoopNamePo> reList : sameTypeList )
		{
			int cardMaxNum = 7 ; // 16


			if (reList==null || reList.size()==0) continue;
			String cardType = reList.get(0).getSig_type()  ;
			String sensorType = reList.get(0).getSigSensorType()  ;
			if( cardType ==null ||cardType.isEmpty() ) continue;
			if (cardType.equals(SigType.DI.getCode()))
				cardMaxNum = 13 ; // 32

			int needCardNum = reList.size();
			int needCHNum = 1;
//			System.out.println("needCardNum: " + needCardNum );
//			try
//			{
//				needCardNum = Integer.parseInt(reList.get(reList.size() - 1).getRep_tag().replaceAll("\\+", ""));
//				needCHNum = reList.size() / needCardNum ;
//			}catch (Exception e)
//			{
//				e.printStackTrace();
//			}
			LoopNameService loopNameService =  SpringUtil.getBean(LoopNameService.class);

			List<ArrayLoopNameCardPo> alreadyCardNumL =  loopNameService.getCardByType(cardType ,CardLeftEnum.Left.getCode() ,  cardMaxNum - needCHNum  ); // TODO 这里需要UTIL调用service .数据库里查一下，左侧有空的卡。
			List<ArrayLoopNameCardPo> alreadyCardNumR =  loopNameService.getCardByType(cardType, CardLeftEnum.Right.getCode(), cardMaxNum - needCHNum);  //
			int maxCardNum = loopNameService.getMaxCardNum();
			System.out.println("alreadyCardNumL: " + alreadyCardNumL + "alreadyCardNumR: " + alreadyCardNumR);
			if ( alreadyCardNumR==null ||  alreadyCardNumL.size() >= alreadyCardNumR.size())
			{
				arrangeCard(reList, cardMaxNum, cardType, sensorType, needCardNum, needCHNum, loopNameService, alreadyCardNumL, maxCardNum,CardLeftEnum.Left.getCode());

			}else
			{
				arrangeCard(reList, cardMaxNum, cardType, sensorType, needCardNum, needCHNum, loopNameService, alreadyCardNumR, maxCardNum,CardLeftEnum.Right.getCode());

			}


		}

	}
	private void arrangeCard(List<OriginalLoopNamePo> reList, int cardMaxNum, String cardType, String sensorType,
							 int needCardNum, int needCHNum, LoopNameService loopNameService, List<ArrayLoopNameCardPo> alreadyCardNumL, int maxCardNum,int isLeft)
	{

		if (alreadyCardNumL == null )
		{
			alreadyCardNumL = new ArrayList<>();
		}

		int alSize = alreadyCardNumL.size();
		if (alSize < needCardNum)
		{
			for(int i =0 ; i< needCardNum - alSize ; i++)
			{
				ArrayLoopNameCardPo arrayLoopNameCard = new ArrayLoopNameCardPo();
				arrayLoopNameCard.setCardNo(++maxCardNum);
				arrayLoopNameCard.setCardSigType(cardType);
				arrayLoopNameCard.setCardSensorType(sensorType);
				arrayLoopNameCard.setMaxSigNum(cardMaxNum);
				alreadyCardNumL.add(arrayLoopNameCard);
				loopNameService.saveCard(arrayLoopNameCard);
			}
		}
		// 这里已经得到了所有的左边有空的卡。

		for (int i =0;i < needCardNum;i++ )
		{
			System.out.println("needCardNum:  alreadyCardNumL.size: isLeft: " + needCardNum +" : "+ alreadyCardNumL.size() +  " : " + isLeft ) ;
			ArrayLoopNameCardPo loopNameCardPo = alreadyCardNumL.get(i);
			int cardNo = loopNameCardPo.getCardNo();
			for (int j =0; j< needCHNum; j++ )
			{
				System.out.println("i:"+ i + "   j:" +j + "   needCHNum:  "+ needCHNum);
				OriginalLoopNamePo originalLoopNamePo  = reList.get(i * needCHNum + j);
				originalLoopNamePo.setCardNo(cardNo);

				originalLoopNamePo.setIsLeft(isLeft);
			}
		}

		loopNameService.updateGoods(reList);
	}


/**
 public static void main(String[] args) throws IOException  {
 //// 预算处理...

 //		MainExcel premainEx = new MainExcel();
 //		List<OrarialLoopName> preoldList = premainEx.getOriginal("E:\\!ge\\BHGE\\control12\\control12.xls");
 //
 //		List<List<String>> prenewList = premainEx.getArrangedIO("E:\\!ge\\BHGE\\control12\\ORIGINAL_IO_LIST.xls");
 ////        List<String> newTitle =Excel_reader.xlsx_reade_head(originalFile);
 //
 //		String[] prearray = {"NO.","TAG NAME"	,"P&ID","DESCRIPTION","FUN SYS","POWER SUPPLY","SIG TYPE","SENSOR TYPE","UNIT",
 //				"LOW LIMIT","HI LIMIT",	"REMARK","MODULE ","	CH "};
 //
 //		List<List<String>> preoldArrayList = sortArragedLists(preoldList , prenewList);
 //
 //		Excel_writer.writer("E:\\!ge\\BHGE\\control12\\" ,"IN_PROCESS_0" ,"xls",preoldArrayList  , prearray);
 //
 //		String originalFile = "E:\\!ge\\BHGE\\IN_PROCESS_0.xls";
 //
 //
 //		if (1==1) return ;

 InputStream excelInput = new FileInputStream("F:\\softCode\\ge-code\\bhge\\IN_PROCESS_0.xls");

 String arrangeFile = "Arranged_PROCESS_";


 //        String re = "[0-9]{3}";
 //        String str = "A-A30102 ";
 //        Pattern p = Pattern.compile(re);
 //        Matcher m = p.matcher(str);
 //        while(m.find()) {
 //            //System.out.println(m.group(1));
 //        System.out.println(m.start() +":"+ m.end());
 //        }



 //        if (1==1 ) return;

 Excel_reader test= new Excel_reader();
 //        ArrayList<ArrayList<String>> arr= test.xlsx_reader("E:\\!ge\\BHGE\\Original_IO_list.xls" );  //后面的参数代表需要输出哪些列，参数个数可以任意
 //        for(int i=0;i<arr.size();i++){
 //            ArrayList<String> row = arr.get(i);
 //            for(int j=0;j<row.size();j++){
 //                System.out.print(row.get(j)+" ");
 //            }
 //            System.out.println("");
 //        }

 Excel_reader excel_reader = new Excel_reader();
 MainExcel mainEx = new MainExcel();
 //TODO 如果数据库里没有数据，可以打下以下四行。
 //        List<OrarialLoopName> oldList = mainEx.getOriginal(originalFile);
 //        System.out.println(oldList.size());
 //
 //		LoopNameService.saveGoods(oldList);    ////TODO 这里有一个存盘的接口...
 //		if (1==1) return ;

 List<OriginalLoopNamePo> dataGoodslist = LoopNameService.getGoodsListByDiscernOrder();


 List<String> noRepeatLoopname = mainEx.getNorepeatLoopName(dataGoodslist);

 List<String> loopNameType = mainEx.getLoopNameType(dataGoodslist);

 //        List<ArrangeLoopName> arrangeLoopNameList = mainEx.accountLoopName(oldList);


 //        int i=0;
 //        for (ArrangeLoopName loName :arrangeLoopNameList )
 //        {
 //            System.out.println(i++ + "    " +loName.getLoopNameType() +":" + loName.getLoopName() + ":"+loName.getNum() );
 //        }
 //
 System.out.println("oldList  :"+ dataGoodslist);
 Collections.sort(dataGoodslist, new OrarialLoopNameSorter());

 System.out.println("sorted oldList  :"+ dataGoodslist);

 //
 //        i=0;
 //        for (ArrangeLoopName loName :arrangeLoopNameList )
 //        {
 //            System.out.println(i++ + "    " +loName.getLoopNameType() +":" + loName.getLoopName() + ":"+loName.getNum() );
 //        }

 ArrangeLoopNameArray arrangeLoopNameArray = new ArrangeLoopNameArray();

 System.out.println("oldList size :"+ dataGoodslist.size());
 System.out.println("oldList  :"+ dataGoodslist);

 List<List<String>> newList = mainEx.getArrangedIO(excelInput);
 //        List<String> newTitle =Excel_reader.xlsx_reade_head(originalFile);

 String[] array = {"NO.","Redundant","TAG NAME"	,"P&ID","DESCRIPTION","FUN SYS","POWER SUPPLY","SIG TYPE","SENSOR TYPE","UNIT",
 "LOW LIMIT","HI LIMIT",	"REMARK","MODULE ","	CH "};
 //识别。。。1 1+ 2 2+。
 mainEx.arrangeReTag(dataGoodslist);
 List<List<String>> oldArrayList = mainEx.sortArragedListsBySimple(dataGoodslist , newList);

 Excel_writer.writer("E:\\!ge\\BHGE" ,arrangeFile+"1","xlsx",oldArrayList  , array);
 System.out.println("===========================");
 System.out.println(dataGoodslist);
 //		if(1==1) return;

 newList = mainEx.getArrangedIO(excelInput);
 List<OriginalLoopNamePo> oldList2 = dataGoodslist;
 //mainEx.arrangeReTag(oldList2);
 System.out.println("======re22222=====================");
 System.out.println(oldList2);
 //oldList2 = oldList;
 List<List<String>> oldArrayList2 = mainEx.sortArragedListsBySimple(dataGoodslist , newList);
 Excel_writer.writer("E:\\!ge\\BHGE" ,arrangeFile+"1_re","xlsx",oldArrayList2  , array);


 arrangeLoopNameArray = new ArrangeLoopNameArray();
 for (OriginalLoopNamePo loName :oldList2 )
 {
 arrangeLoopNameArray.addLoopNametoArray4(loName);  // 这里是原来的排冗余,相关的.
 }
 arrangeLoopNameArray.arrangeLastInType(); // 重排最后的无冗余.无相关的.

 System.out.println("arrangeLoopNameArray:getTotalSize:"+arrangeLoopNameArray.getTotalSize());
 System.out.println("oldList2.★★★★."+ oldList2);
 System.out.println("list.★★★★."+ arrangeLoopNameArray.getOriginalLoopNamePoList());
 List<ArrayLoopNameCardUtil> AllCardList = mainEx.arrangeLoopNameCards(arrangeLoopNameArray);


 if (1==1) return ;
 Collections.sort(oldList2, new OrarialLoopNameSorterRe());
 System.out.println("======22222=====================");
 System.out.println(oldList2);
 newList = mainEx.getArrangedIO(excelInput);
 List<List<String>> oldArrayList3 = mainEx.sortArragedListsBySimple(dataGoodslist , newList);
 Excel_writer.writer("E:\\!ge\\BHGE" ,arrangeFile+"2_re","xlsx",oldArrayList3  , array);
 //====================
 arrangeLoopNameArray.maxModuleNum = false;
 for (OriginalLoopNamePo loName :oldList2 )
 {
 arrangeLoopNameArray.addLoopNametoArray(loName);
 }
 System.out.println("arrangeLoopNameArray:getTotalSize:"+arrangeLoopNameArray.getTotalSize());
 System.out.println("list.★★★."+ arrangeLoopNameArray.getOriginalLoopNamePoList());

 newList = mainEx.getArrangedIO(excelInput);
 List<List<String>> newArrayList = mainEx.sortArragedLists(arrangeLoopNameArray, newList);
 System.out.println("------------------");
 System.out.println("newList**"+newList.size());


 Excel_writer.writer("E:\\!ge\\BHGE" ,arrangeFile+"2","xlsx",newArrayList  , array);

 //		if (2==2) return ;

 arrangeLoopNameArray = new ArrangeLoopNameArray();
 arrangeLoopNameArray.maxModuleNum = true;
 for (OriginalLoopNamePo loName :oldList2 )
 {
 arrangeLoopNameArray.addLoopNametoArray(loName);
 }

 arrangeLoopNameArray.arrangeLastIntotype();

 System.out.println("arrangeLoopNameArray:getTotalSize:"+arrangeLoopNameArray.getTotalSize());

 newList = mainEx.getArrangedIO(excelInput);

 newArrayList = mainEx.sortArragedLists(arrangeLoopNameArray, newList,false);

 Excel_writer.writer("E:\\!ge\\BHGE" ,arrangeFile+"3","xlsx",newArrayList  , array);

 //		if (3 == 3) return ;
 arrangeLoopNameArray = new ArrangeLoopNameArray();
 for (OriginalLoopNamePo loName :oldList2 )
 {
 arrangeLoopNameArray.addLoopNametoArray4(loName);
 }
 arrangeLoopNameArray.arrangeLastInType();

 System.out.println("arrangeLoopNameArray:getTotalSize:"+arrangeLoopNameArray.getTotalSize());
 System.out.println("oldList2.★★★★."+ oldList2);
 System.out.println("list.★★★★."+ arrangeLoopNameArray.getOriginalLoopNamePoList());

 // arrangeLoopNameArray. CHReArrage(arrangeLoopNameArray.getOrarialLoopNameList());
 newList = mainEx.getArrangedIO(excelInput);

 newArrayList = mainEx.reArraygeListCh(arrangeLoopNameArray, newList);

 Excel_writer.writer("E:\\!ge\\BHGE" ,arrangeFile+"4","xlsx",newArrayList  , array , true);

 //        arrangeLoopNameArray = new ArrangeLoopNameArray();
 //        arrangeLoopNameArray.maxModuleNum = false;
 //        arrangeLoopNameArray.module_addressing = true;
 //
 //        for (OrarialLoopName loName :oldList )
 //        {
 //            arrangeLoopNameArray.addLoopNametoArray(loName);
 //        }
 //        System.out.println("arrangeLoopNameArray:getTotalSize:"+arrangeLoopNameArray.getTotalSize());
 //
 //        newList = mainEx.getArrangedIO(originalFile);
 //
 //        newArrayList = sortArragedLists(arrangeLoopNameArray, newList);
 //
 //        Excel_writer.writer("E:\\!ge\\BHGE" ,arrangeFile+"4","xls",newArrayList  , array);



 //        List<List<String>> new2List = mainEx.getArrangedIOinOriginal(newList,oldList )  ;
 //
 //        Excel_writer.writer("E:\\!ge\\BHGE" ,arrangeFile+"2","xls",new2List ,  array);
 //
 //        List<List<String>> new3List = mainEx.getArrangedIOinOriginalNoRepeat(newList,oldList )  ;
 //
 //        Excel_writer.writer("E:\\!ge\\BHGE" ,arrangeFile+"3","xls",new3List ,  array);

 }

 */


}