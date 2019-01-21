package com.mahindra;

import com.mahindra.po.OriginalLoopNamePo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2018/4/20.
 * TODO  这里重排....
 */
public class ArrangeLoopNameArray
{
    private static String[] twoLoopNameType = {""};
    private static String[] forLoopNameType = {"MD-FU301"};

    private int maxModuleN = 14;

    public boolean maxModuleNum = false;
    public boolean module_addressing = false;
    private List<OriginalLoopNamePo> perOriginalLoopNamePoList;
	String preSigSensorType ="";

    private List<List<List<OriginalLoopNamePo>>> OriginalLoopNamePoList = new ArrayList();
    //private static List<OriginalLoopNamePo> totalLoopNameTypeList  = new ArrayList<OriginalLoopNamePo>(); // userless

    public  List<List<List<OriginalLoopNamePo>>> getOriginalLoopNamePoList()
    {
        return OriginalLoopNamePoList;
    }

    public int getTotalSize()
    {
        if (OriginalLoopNamePoList == null || OriginalLoopNamePoList.size() == 0) return 0;
        int total = 0;
        System.out.println("OriginalLoopNamePoList.size(): " + OriginalLoopNamePoList.size());
		for (List<List<OriginalLoopNamePo>> typeList :OriginalLoopNamePoList)
		{
			for (List<OriginalLoopNamePo> loopNameList : typeList)
			{
				System.out.println("arranged List : per: " + loopNameList);
				total += loopNameList.size();
			}
		}
        return total;
    }




	/**
	 *  把冗余信号，或相类的信号来进行重排。一个个的放入list ，最后形成一个LIST
	 * @param OriginalLoopNamePo 每次要放入的信号变量
	 */
	public void addLoopNametoArray4(OriginalLoopNamePo OriginalLoopNamePo)
	{
//		if (module_addressing) addLoopNametoCHArray(OriginalLoopNamePo);
//		else
		{
			boolean haveArrange = false;
			String  sigSensorType =  OriginalLoopNamePo.getSigSensorType();
			if (preSigSensorType.isEmpty() ||  (preSigSensorType != null &&  !preSigSensorType.equals(sigSensorType)) )
			{
				List<OriginalLoopNamePo> newArrageLoopList = new ArrayList<OriginalLoopNamePo>();
				newArrageLoopList.add( OriginalLoopNamePo);
				List<List<OriginalLoopNamePo>> newTypeArraygeLoopList = new ArrayList<List<OriginalLoopNamePo>>();
				newTypeArraygeLoopList.add(newArrageLoopList);
				OriginalLoopNamePoList.add(newTypeArraygeLoopList);
				//每个 新的 array ....
			}else
			{


				for (List<List<OriginalLoopNamePo>> pTypeLoopList : OriginalLoopNamePoList)
				{
					String listType = pTypeLoopList.get(0).get(0).getSigSensorType();
					if (sigSensorType.equals(listType))
					{
						for (int fi =  pTypeLoopList.size() ; fi>0 ; fi--)
						{
							// 先把空的放一起。
							List<OriginalLoopNamePo> pArrangeLoopList = pTypeLoopList.get(fi-1);

							// 加入条件.!OriginalLoopNamePo.getRep_tag().trim().isEmpty()  把这些先放最后一组里..

							if (haveSerial(pArrangeLoopList, OriginalLoopNamePo))
							{
								pArrangeLoopList.add(OriginalLoopNamePo);
								haveArrange = true;
								break;
							}
						}
						if (!haveArrange)
						{
							List<OriginalLoopNamePo> newArrageLoopList = new ArrayList<OriginalLoopNamePo>();
							newArrageLoopList.add(OriginalLoopNamePo);
							pTypeLoopList.add(newArrageLoopList);
						}
					}

				}
			}

			preSigSensorType = sigSensorType;
		}
	}

	private boolean haveSerial(List<OriginalLoopNamePo> perArrangeLoopList , OriginalLoopNamePo haveOriginalLoopNamePo)
	{
		if (perArrangeLoopList == null || perArrangeLoopList.size() == 0 || haveOriginalLoopNamePo == null)
		{
			return false;
		}
		OriginalLoopNamePo lastOriginalLoopNamePo = perArrangeLoopList.get(0);
		if (lastOriginalLoopNamePo.getRepBlock() != haveOriginalLoopNamePo.getRepBlock()) return false;


		for (OriginalLoopNamePo OriginalLoopNamePo : perArrangeLoopList)
		{
			if (OriginalLoopNamePo == null || OriginalLoopNamePo.getSigSensorType() == null) continue;

			if (OriginalLoopNamePo.getSigSensorType().equals(haveOriginalLoopNamePo.getSigSensorType()))
			{
				String reTag = haveOriginalLoopNamePo.getRep_tag();
				String nowRe = OriginalLoopNamePo.getRep_tag();
				String dhDesc = OriginalLoopNamePo.getCHDesc();

				if ((reTag==null || reTag .trim().isEmpty() ) &&( nowRe==null || nowRe .trim().isEmpty()))
					return true;

				if (reTag.equals(nowRe)) return  false;

				if (haveOriginalLoopNamePo.judgeByCh(dhDesc)) return true;

				if (haveOriginalLoopNamePo.judgeBySameCh(dhDesc)) return true;

			}

		}
		return false;
	}

    /**
     * 这里只放 非冗余，非相关性的信号。
     * 重排了type 只把相同的type 放一起。不再放冗余的。
     * @param OriginalLoopNamePo
     */
    public void addLoopNametoArray(OriginalLoopNamePo OriginalLoopNamePo)
    {
//        if (module_addressing) addLoopNametoCHArray(OriginalLoopNamePo);
//        else
        {
			boolean haveArrange = false;
            String  sigSensorType =  OriginalLoopNamePo.getSigSensorType();
			if (preSigSensorType.isEmpty()  )
			{
                System.out.println("这里是第一组，只进一次");
                List<OriginalLoopNamePo> newArrageLoopList = new ArrayList<OriginalLoopNamePo>();
				newArrageLoopList.add( OriginalLoopNamePo);
				List<List<OriginalLoopNamePo>> newTypeArraygeLoopList = new ArrayList<List<OriginalLoopNamePo>>();
				newTypeArraygeLoopList.add(newArrageLoopList);
				OriginalLoopNamePoList.add(newTypeArraygeLoopList);
				//每个 新的 array ....
			}else
			{


				for (List<List<OriginalLoopNamePo>> pTypeLoopList : OriginalLoopNamePoList)
				{
					String listType = pTypeLoopList.get(0).get(0).getSigSensorType();
					if (1==2)
					{
                        List<OriginalLoopNamePo>  newArrageLoopList =  pTypeLoopList.get(0);
                        newArrageLoopList .add(OriginalLoopNamePo);
                        haveArrange = true;
//						for (List<OriginalLoopNamePo> pArrangeLoopList : pTypeLoopList)
//						{
//							// 先把空的放一起。
//
//                            //这里使用了数据库之后，不能再使用maxNum.
//						    // 加入条件.!OriginalLoopNamePo.getRep_tag().trim().isEmpty()  把这些先放最后一组里..
////							if (maxModuleNum )
////                            {
////                                if ( pArrangeLoopList != null && pArrangeLoopList.size() > maxModuleN && (!OriginalLoopNamePo.getRep_tag().trim().isEmpty() ))
////                                continue;
////                            }
//
//
//							if (haveType(pArrangeLoopList, OriginalLoopNamePo))
//							{
//								pArrangeLoopList.add(OriginalLoopNamePo);
//								haveArrange = true;
//								break;
//							}
//						}
//						if (!haveArrange)
//						{
//							List<OriginalLoopNamePo> newArrageLoopList = new ArrayList<OriginalLoopNamePo>();
//							newArrageLoopList.add(OriginalLoopNamePo);
//							pTypeLoopList.add(newArrageLoopList);
//						}
					}

				}
				if(!haveArrange)
                {
                    List<OriginalLoopNamePo> newArrageLoopList = new ArrayList<OriginalLoopNamePo>();
                    newArrageLoopList.add( OriginalLoopNamePo);
                    List<List<OriginalLoopNamePo>> newTypeArraygeLoopList = new ArrayList<List<OriginalLoopNamePo>>();
                    newTypeArraygeLoopList.add(newArrageLoopList);
                    OriginalLoopNamePoList.add(newTypeArraygeLoopList);

                }
			}

			// 不能在这里重排。。

			preSigSensorType = sigSensorType;
        }
    }


//	public void arrangeLastIntotype()
//	{
//		//这里对第三重排.
//		if (maxModuleNum)
//		{
//			for (List<List<OriginalLoopNamePo>> pTypeLoopList: OriginalLoopNamePoList)
//			{
//				// 最后一组..
//				List<OriginalLoopNamePo> nullTypeLoopList = pTypeLoopList.get(pTypeLoopList.size()-1);
//
//				if (nullTypeLoopList.size()==0 || nullTypeLoopList.get(0).getRep_tag() != null && !nullTypeLoopList.get(0).getRep_tag().isEmpty()) return;
//
//				Iterator<OriginalLoopNamePo> it = nullTypeLoopList.iterator();
//				while(it.hasNext()){
//					OriginalLoopNamePo nullTypeloop = it.next();
//
//					for (int i =0 ;i< pTypeLoopList.size()-1;i++)
//					{
//						// TODOp 这里可能 会出现 1个数多于13
//						List<OriginalLoopNamePo> pArrangeLoopList = pTypeLoopList.get(i);
//						if (pArrangeLoopList.size() > maxModuleN ) continue;
//						{
//							pArrangeLoopList.add(nullTypeloop);
//							it.remove();
//							break;
//						}
//					}
//				}
//				if (nullTypeLoopList.size()==0)
//				pTypeLoopList.remove(pTypeLoopList.size()-1);
//				else
//				{
//					pTypeLoopList.remove(pTypeLoopList.size() - 1);
//					List<OriginalLoopNamePo> nullRemainList = null;
//					for (int i = 0; i < nullTypeLoopList.size(); i++)
//					{
//						if (i%maxModuleN ==0)
//						{
//							nullRemainList = new ArrayList<OriginalLoopNamePo>();
//							nullRemainList.add(nullTypeLoopList.get(i));
//							pTypeLoopList.add(nullRemainList);
//						}
//						else
//						{
//							nullRemainList.add(nullTypeLoopList.get(i));
//						}
//					}
//				}
//			}
//		}
//	}
//=======
    public void arrangeLastIntotype()
    {
        //这里对 第三.重排.就是把多余的放到其它的有数字的 module里.
        if(maxModuleNum)
        {
            //重排对.
            for (List<List<OriginalLoopNamePo>> pTypeLoopList : OriginalLoopNamePoList)
            {
                // 最后一组...
                List<OriginalLoopNamePo> nullTypeLoopList = pTypeLoopList.get(pTypeLoopList.size()-1);

                if (nullTypeLoopList.size()==0 || nullTypeLoopList.get(0).getRep_tag()!=null && !nullTypeLoopList.get(0).getRep_tag().isEmpty())
                    return;
                Iterator<OriginalLoopNamePo> it = nullTypeLoopList.iterator();
                while ( it.hasNext()) {
                    OriginalLoopNamePo nullTypeloop = it.next();

                    for (int i = 0; i < pTypeLoopList.size() - 1; i++) {
                        //TODO .. 这里可能会出现,1 个数自己本身多于13个...这样还是一组的..
                        List<OriginalLoopNamePo> pArrangeLoopList = pTypeLoopList.get(i);
                        if (pArrangeLoopList.size() > maxModuleN ) continue;
                        {
                            pArrangeLoopList.add(nullTypeloop);
                            it.remove();
                            break;
                        }

                    }
                }

                if (nullTypeLoopList.size()==0)
                    pTypeLoopList.remove(pTypeLoopList.size()-1);
                else
                {
                    pTypeLoopList.remove(pTypeLoopList.size()-1);
                    List<OriginalLoopNamePo> nullRemainList =null;
                    for (int i =0 ;i< nullTypeLoopList.size(); i++)
                    {

                        if (i%maxModuleN == 0)
                        {
                            nullRemainList = new ArrayList<>();
                            nullRemainList.add(nullTypeLoopList.get(i));
                            pTypeLoopList.add(nullRemainList);
                        }
                        else
                        {
                            nullRemainList.add(nullTypeLoopList.get(i));
                        }

                    }

                }


            }
        }
    }


    /**
     * 带CH的。
     * @param OriginalLoopNamePo
     */
    public void addLoopNametoCHArray(OriginalLoopNamePo OriginalLoopNamePo)
    {
//        boolean haveArrange = false;
//
//
//        for (List<OriginalLoopNamePo> pArrangeLoopList : OriginalLoopNamePoList)
//        {
//            if (maxModuleNum && pArrangeLoopList != null && pArrangeLoopList.size() > 15) continue;
//
//            if (haveNum(pArrangeLoopList, OriginalLoopNamePo) >= 2) continue;
//            if ((haveNum(pArrangeLoopList, OriginalLoopNamePo) < 2))
//            {
//                ChArrangeAddLoopName(pArrangeLoopList, OriginalLoopNamePo, totalLoopNameTypeList);
//                haveArrange = true;
//                break;
//            }
//        }
//        if (!haveArrange)
//        {
//            List<OriginalLoopNamePo> newArrageLoopList = new ArrayList<OriginalLoopNamePo>();
//
//            ChArrangeAddLoopName(newArrageLoopList, OriginalLoopNamePo, totalLoopNameTypeList);
//
//            OriginalLoopNamePoList.add(newArrageLoopList);
//        }
    }

//    public  void CHReArrage(List<List<OriginalLoopNamePo>> OriginalLoopNamePoList)
//    {
//        if(OriginalLoopNamePoList ==null || OriginalLoopNamePoList.size() == 0) return;
//
//        totalLoopNameTypeList.addAll(OriginalLoopNamePoList.get(OriginalLoopNamePoList.size()-1));
//        int ch=0;
//        for (OriginalLoopNamePo fOraLoopName :totalLoopNameTypeList)
//        {
//            fOraLoopName .setChNumber(ch++);
//
//        }
//
//        for (int i = OriginalLoopNamePoList.size() ;i >0 ;i--)
//        {
//            List<OriginalLoopNamePo> fOraLoopNameList = OriginalLoopNamePoList.get(i-1);
//            chAssign(fOraLoopNameList,totalLoopNameTypeList );
//
//        }
//    }

//    private void chAssign(List<OriginalLoopNamePo> pArrangeLoopList, List<OriginalLoopNamePo> fOraLoopNameList)
//    {
//        if (pArrangeLoopList ==null ||  fOraLoopNameList ==null) return;
//        boolean doubleCH = false;
//        for (OriginalLoopNamePo OriginalLoopNamePo:pArrangeLoopList)
//        {
//            String oraLoopNameType = OriginalLoopNamePo.getLoopNameType();
//
//            int totolHaveNum = haveNum(totalLoopNameTypeList, OriginalLoopNamePo) ;
//            int arrangeHaveNum = haveNum(pArrangeLoopList ,OriginalLoopNamePo ) ;
//
//            if (totolHaveNum ==0 && arrangeHaveNum == 1)
//            {
//                OriginalLoopNamePo.setChNumber( fOraLoopNameList.size());
//                fOraLoopNameList.add(OriginalLoopNamePo);
//
//            }
//            if (totolHaveNum >= 1 && arrangeHaveNum == 1)
//            {
//                int getCh = getLoopNamebyType(fOraLoopNameList,oraLoopNameType );
//                OriginalLoopNamePo.setChNumber( getCh );
//            }
//
//            if (totolHaveNum == 0 && arrangeHaveNum == 2)
//            {
//                OriginalLoopNamePo.setChNumber( fOraLoopNameList.size());
//                fOraLoopNameList.add(OriginalLoopNamePo);
//                doubleCH = true;
//            }
//            if (totolHaveNum == 1 && arrangeHaveNum == 2)
//            {
//                if (!doubleCH)
//                {
//                    doubleCH = true;
//                    int getCh = getLoopNamebyType(fOraLoopNameList,oraLoopNameType );
//                    OriginalLoopNamePo.setChNumber( getCh );
//                }else
//                {
//                    OriginalLoopNamePo.setChNumber(fOraLoopNameList.size());
//                    fOraLoopNameList.add(OriginalLoopNamePo);
//                }
//
//            }
//
//            if (totolHaveNum == 2 && arrangeHaveNum == 2)
//            {
//                if (!doubleCH)
//                {
//                    doubleCH = true;
//                    int getCh = getLoopNamebyType(fOraLoopNameList,oraLoopNameType );
//                    OriginalLoopNamePo.setChNumber( getCh );
//                }else
//                {
//                    int getCh = getLastLoopNamebyType(fOraLoopNameList,oraLoopNameType );
//                    OriginalLoopNamePo.setChNumber( getCh );
//                }
//            }
//
//        }
//    }

    private int getLastLoopNamebyType(List<OriginalLoopNamePo> fOraLoopNameList, String oraLoopNameType)
    {
        int result = 0;
        if (fOraLoopNameList==null||oraLoopNameType==null  ) return 0;
        for (int i =fOraLoopNameList.size(); i>0; i-- )
        {
            OriginalLoopNamePo OriginalLoopNamePo = fOraLoopNameList.get(i-1);
            if (oraLoopNameType.equals(OriginalLoopNamePo.getLoopNameType()))
            {
                return OriginalLoopNamePo.getChNumber();
            }
        }
        return result;
    }

    private int getLoopNamebyType(List<OriginalLoopNamePo> fOraLoopNameList, String oraLoopNameType)
    {
        int result = 0;
        if (fOraLoopNameList==null||oraLoopNameType==null  ) return 0;
        for (OriginalLoopNamePo OriginalLoopNamePo:fOraLoopNameList)
        {
            if (oraLoopNameType.equals(OriginalLoopNamePo.getLoopNameType()))
            {
                return OriginalLoopNamePo.getChNumber();
            }
        }
        return result;
    }


    private void ChArrangeAddLoopName(List<OriginalLoopNamePo> pArrangeLoopList, OriginalLoopNamePo OriginalLoopNamePo, List<OriginalLoopNamePo> totalLoopNameTypeList)
    {
        if (pArrangeLoopList==null || OriginalLoopNamePo == null || totalLoopNameTypeList ==null) return;
        String oraLoopNameType = OriginalLoopNamePo.getLoopNameType();
        int totolHaveNum = haveNum(totalLoopNameTypeList, OriginalLoopNamePo) ;
        int arrangeHaveNum = haveNum(pArrangeLoopList ,OriginalLoopNamePo ) ;
        if (totolHaveNum ==0 && arrangeHaveNum ==0)
        {
            for (int i = 0 ;i< totalLoopNameTypeList.size();i++)
            {
                pArrangeLoopList.add( new OriginalLoopNamePo());
            }
            totalLoopNameTypeList.add(OriginalLoopNamePo);
            pArrangeLoopList.add(OriginalLoopNamePo);

        }

        if (totolHaveNum==1 && arrangeHaveNum ==0)
        {
            for (int i = 0 ;i< totalLoopNameTypeList.size();i++)
            {
                OriginalLoopNamePo totalLoopName = totalLoopNameTypeList.get(i);
                if (totalLoopName==null || totalLoopName.getLoopNameType() ==null) continue;
                if (totalLoopName.getLoopNameType().equals(OriginalLoopNamePo.getLoopNameType()))
                    break;

                if (pArrangeLoopList.size()< i+1 || pArrangeLoopList.get(i)==null)
                {
                    pArrangeLoopList.add(new OriginalLoopNamePo());
                }

            }
            pArrangeLoopList.add(OriginalLoopNamePo);

        }
        if (totolHaveNum==1 && arrangeHaveNum ==1)
        {
            for (int i = 0 ;i< totalLoopNameTypeList.size();i++)
            {
                OriginalLoopNamePo totalLoopName = totalLoopNameTypeList.get(i);
                if (totalLoopName==null || totalLoopName.getLoopNameType() ==null) continue;
                if (totalLoopName.getLoopNameType().equals(OriginalLoopNamePo.getLoopNameType()))
                    break;

                if (pArrangeLoopList.get(i)==null)
                {
                    pArrangeLoopList.add(new OriginalLoopNamePo());
                }

            }
            totalLoopNameTypeList.add(OriginalLoopNamePo);
            pArrangeLoopList.add(OriginalLoopNamePo);
        }

        if (totolHaveNum==2 && arrangeHaveNum == 1)
        {
            for (int i = 0 ;i< totalLoopNameTypeList.size();i++)
            {
                OriginalLoopNamePo totalLoopName = totalLoopNameTypeList.get(i);
                if (totalLoopName==null || totalLoopName.getLoopNameType() ==null) continue;
                if (totalLoopName.getLoopNameType().equals(OriginalLoopNamePo.getLoopNameType()))
                    break;

                if (pArrangeLoopList.get(i)==null)
                {
                    pArrangeLoopList.add(new OriginalLoopNamePo());
                }

            }

            pArrangeLoopList.add(OriginalLoopNamePo);
        }

    }

	private boolean haveType(List<OriginalLoopNamePo> perArrangeLoopList, OriginalLoopNamePo haveOriginalLoopNamePo)
	{
		if (perArrangeLoopList == null || perArrangeLoopList.size() == 0 || haveOriginalLoopNamePo == null)
		{
			return false;
		}

		for (OriginalLoopNamePo OriginalLoopNamePo : perArrangeLoopList)
		{
			if (OriginalLoopNamePo == null || OriginalLoopNamePo.getSigSensorType() == null) continue;
			if (OriginalLoopNamePo.getSigSensorType().equals(haveOriginalLoopNamePo.getSigSensorType()))
			{

			    return true;
//				String reTag = haveOriginalLoopNamePo.getRep_tag();
//				String nowRe = OriginalLoopNamePo.getRep_tag();
//				//直接把+ 的这种给排除掉算了..反正排序没有要求.
//				reTag = reTag.replace("+","");
//				nowRe = nowRe.replace("+","");
//
//				if (reTag.equals(nowRe))
//				   return  true;
//				if (reTag.equals("1") &&  nowRe.equals("1+"))
//					return  true;
//				if (reTag.equals("2") &&  nowRe.equals("2+"))
//				return  true;
//				if (reTag.equals("3") &&  nowRe.equals("3+"))
//					return  true;
//				// 这里是新开的。。。另一组。。
//				if (reTag.equals("1+") &&  nowRe.equals("1"))
//					return true;
				// END。
//                如果再加上2的话，就不行。因为1跟空同，空跟2同。。
//                if (reTag.equals("2") &&  nowRe.isEmpty())
//                    return  true;
//                if(reTag.isEmpty() && nowRe.equals("2"))
//                    return true;
			}


		}
		return false;
	}

    private int haveNum(List<OriginalLoopNamePo> perArrangeLoopList, OriginalLoopNamePo haveOriginalLoopNamePo)
    {
        if (perArrangeLoopList == null || perArrangeLoopList.size() == 0 || haveOriginalLoopNamePo == null)
        {
            return 0;
        }
        int haveNum = 0;
        for (OriginalLoopNamePo OriginalLoopNamePo : perArrangeLoopList)
        {
            if (OriginalLoopNamePo == null || OriginalLoopNamePo.getLoopNameType() == null) continue;
            if (OriginalLoopNamePo.getLoopNameType().equals(haveOriginalLoopNamePo.getLoopNameType()))
            {
                haveNum++;
            }


        }
        return haveNum;

    }


	/**
	 * 把每个type 的空闲的排在最后.好容易的插入.
	 */
	public void arrangeLastInType()
	{

		for (List<List<OriginalLoopNamePo>> pTypeLoopList : OriginalLoopNamePoList)
		{
			for (List<OriginalLoopNamePo> nullTypeLoopList:pTypeLoopList )
			{
				// 最后一组...
				OriginalLoopNamePo nullTypeloop  = nullTypeLoopList.get(0);
				if (nullTypeloop.getRep_tag() ==null || nullTypeloop.getRep_tag() .trim().isEmpty())
				{
					pTypeLoopList.remove(nullTypeLoopList);
					pTypeLoopList.add(nullTypeLoopList);
					break;
				}

			}



//			for (List<OriginalLoopNamePo> nullTypeLoopList:pTypeLoopList )
//			{
//				// 最后一组...
//				OriginalLoopNamePo nullTypeloop  = nullTypeLoopList.get(0);
//				if (nullTypeloop.getRep_tag() != null && nullTypeloop.isRepBlock() )
//				{
//					Collections.sort(pTypeLoopList, new RepBlockSorter());
////					pTypeLoopList.remove(nullTypeLoopList);
////					pTypeLoopList.add(nullTypeLoopList);
////					break;
//				}
//
//			}

		}

	}
}
