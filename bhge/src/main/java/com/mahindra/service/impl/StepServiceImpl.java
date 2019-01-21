package com.mahindra.service.impl;

import com.mahindra.ArrangeLoopNameArray;
import com.mahindra.MainExcel;
import com.mahindra.common.exception.MyException;
import com.mahindra.mapper.LoopNameCardMapper;
import com.mahindra.mapper.OrarialLoopNameMapper;
import com.mahindra.po.ArrayLoopNameCardPo;
import com.mahindra.po.OriginalLoopNamePo;
import com.mahindra.service.LoopNameService;
import com.mahindra.service.StepService;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by weican on 2018/8/6.
 */
@Service
public class StepServiceImpl implements StepService
{
	private Logger log = LoggerFactory.getLogger(StepServiceImpl.class);

	@Autowired
	private LoopNameCardMapper loopNameCardMapper;

	@Autowired
	private OrarialLoopNameMapper orarialLoopNameMapper;

	@Autowired
	private LoopNameService loopNameService;


	@Override
	public void step(Integer stepNumber) throws MyException {
		if (stepNumber == null) {
			throw new MyException("请填写正确的步骤数");
		}

		log.info("step " + stepNumber + " begin execute");

		if (stepNumber == 1) {
			firstStep();
		} else if (stepNumber == 2) {
//			secondStep();
			thirdStep();
			fourthStep();
			fourthStep2();
			fifthStep();
		} else {
			throw new MyException("请填写正确的步骤数");
		}
		log.info("step " + stepNumber + " end !!!");
	}

	@Override
	public void chooseMark(int type, String[] orgNos) throws MyException {
		if (ArrayUtils.isEmpty(orgNos)) {
			throw new MyException("请选择数据");
		}
		List<OriginalLoopNamePo> chooseLoops = orarialLoopNameMapper.getGoodsByOrgNos(orgNos);
		if (type == 1) {
			// 冗余
			forceMarkRelateNo(chooseLoops);
			loopNameService.updateGoods(chooseLoops);
		} else if (type == 2) {
			// 相关
			markRelateNo(chooseLoops);
			loopNameService.updateGoods(chooseLoops);
		} else {
			// 不相关
			markNotRelate(chooseLoops);
			loopNameService.batchUpdateGoodNotRelate(chooseLoops);
		}
	}

	/**
	 * 标记不相关
	 * @param chooseLoops
	 */
	private void markNotRelate(List<OriginalLoopNamePo> chooseLoops) {
		for (OriginalLoopNamePo loopNamePo : chooseLoops) {
            loopNamePo.setRep_tag(null);
			loopNamePo.setRalate_tag(null);
            loopNamePo.setReg_No(999999999);
            loopNamePo.setRalate_No(999999999);
        }
	}

	/**
	 * 处理冗余
	 * @param chooseLoops
	 */
	private void markRegTag(List<OriginalLoopNamePo> chooseLoops) {
		MainExcel mainEx = new MainExcel();
		//识别。。。1 1+ 2 2+。
		mainEx.arrangeReTag(chooseLoops);
		// 冗余
		for (OriginalLoopNamePo originalLoopNamePo:chooseLoops)
		{
			originalLoopNamePo.setReg_No(999999999);
		}
		mainEx.arrangeReg_No(chooseLoops);
	}


	/**
	 * 处理相关
	 * @param dataGoodslist
	 */
	private void markRelateNo(List<OriginalLoopNamePo> dataGoodslist) {
		MainExcel mainEx = new MainExcel();
		// 相关
		for (OriginalLoopNamePo originalLoopNamePo : dataGoodslist)
		{
			originalLoopNamePo.setRalate_No(999999999);
		}

		mainEx.arrangeRelate_No(dataGoodslist);
	}


	/**
	 * 强行标记为相关
	 * @param dataGoodslist
	 */
	private void forceMarkRelateNo(List<OriginalLoopNamePo> dataGoodslist) {
		for (int i = 0; i < dataGoodslist.size(); i++) {
			OriginalLoopNamePo loopNamePo = dataGoodslist.get(i);
			if (i == 0){
				loopNamePo.setRep_tag("1");
			} else {
				loopNamePo.setRep_tag("1+");
			}
			loopNamePo.setRalate_tag(null);
			loopNamePo.setReg_No(999999999);
			loopNamePo.setRalate_No(999999999);
			loopNamePo.setRepBlock(false);
		}

//		mainEx.arrangeRelate_No(dataGoodslist);
	}



	/**
	 * 第一步。识别信号里的冗余，与相关程度。
	 * @throws MyException
	 */
	@Override
	public void firstStep() throws MyException
	{
		List<OriginalLoopNamePo> dataGoodslist = loopNameService.getGoodsListByDiscernOrder();

		//冗余
		markRegTag(dataGoodslist);
		//相关
		markRelateNo(dataGoodslist);

		loopNameService.updateGoods(dataGoodslist);
		log.info("dataGoodslist size === " + dataGoodslist.size());
	}


	/**
	 * 第二步只是一个导出EXCEL。
	 * @return
	 * @throws MyException
	 */
	@Override
	public List<OriginalLoopNamePo> secondStep() throws MyException
	{

		List<OriginalLoopNamePo> dataGoodslist = loopNameService.getGoodsListByReOrder();
		return dataGoodslist;

	}

	@Override
	public void thirdStep() throws MyException
	{

		List<OriginalLoopNamePo> dataGoodslist = loopNameService.getGoodsREListByDiscernOrder();
		for (OriginalLoopNamePo originalLoopNamePo:dataGoodslist)
		{
			originalLoopNamePo.setCardNo(0);
			originalLoopNamePo.setIsLeft(0);
			originalLoopNamePo.setChNumber(0);
		}
		// loopNameService.updateGoods(dataGoodslist);

		//System.out.println(dataGoodslist);
		//dataGoodslist = loopNameService.getGoodsREListByDiscernOrder();
		// 不需要重新获取。
		List<OriginalLoopNamePo> oldList2 = dataGoodslist;

		ArrangeLoopNameArray arrangeLoopNameArray = new ArrangeLoopNameArray();
		for (OriginalLoopNamePo loName :oldList2 )
		{
			arrangeLoopNameArray.addLoopNametoArray4(loName);
		}
		System.out.println("-------------------");
		System.out.println("oldList2。size."+ oldList2.size());
		System.out.println("arrangeLoopNameArray:getTotalSize:"+arrangeLoopNameArray.getTotalSize());
		// 这里是原来的list表。
		System.out.println("oldList2.★★★★."+ oldList2);
		// 经过后排之后的list表。 可以使用对比工具来对比。
		System.out.println("list.★★★★."+ arrangeLoopNameArray.getOriginalLoopNamePoList());
		MainExcel mainEx = new MainExcel();
		// arrangeLoopNameArray. CHReArrage(arrangeLoopNameArray.getOrarialLoopNameList());
		for (List<List<OriginalLoopNamePo>> sameTypeList :arrangeLoopNameArray.getOriginalLoopNamePoList() )
		{
			mainEx.arrayCardDBForSameTypeGoodList(sameTypeList);
		}
//
//		newArrayList = mainEx.reArraygeListCh(arrangeLoopNameArray, newList);


		//System.out.println(dataGoodslist);
		loopNameService.updateGoods(dataGoodslist); ///编号。冗余。。

	}

	@Override
	public void fourthStep() throws MyException
	{

		List<OriginalLoopNamePo> dataGoodslist = loopNameService.getRelateListByDiscernOrder();
		for (OriginalLoopNamePo originalLoopNamePo:dataGoodslist)
		{
			originalLoopNamePo.setCardNo(0);
			originalLoopNamePo.setIsLeft(0);
			originalLoopNamePo.setChNumber(0);
		}
		// loopNameService.updateGoods(dataGoodslist);

		//System.out.println(dataGoodslist);
		//dataGoodslist = loopNameService.getGoodsREListByDiscernOrder();
		// 不需要重新获取。
		List<OriginalLoopNamePo> oldList2 = dataGoodslist;

		ArrangeLoopNameArray arrangeLoopNameArray = new ArrangeLoopNameArray();
		for (OriginalLoopNamePo loName :oldList2 )
		{
			arrangeLoopNameArray.addLoopNametoArray4(loName);
		}
		System.out.println("-------------------");
		System.out.println("oldList2。size."+ oldList2.size());
		System.out.println("arrangeLoopNameArray:getTotalSize:"+arrangeLoopNameArray.getTotalSize());
		// 这里是原来的list表。
		System.out.println("oldListRA.★★★★."+ oldList2);
		// 经过后排之后的list表。 可以使用对比工具来对比。
		System.out.println("listRA.★★★★."+ arrangeLoopNameArray.getOriginalLoopNamePoList());
		MainExcel mainEx = new MainExcel();
		// arrangeLoopNameArray. CHReArrage(arrangeLoopNameArray.getOrarialLoopNameList());
		// 相同的类型的一组信号。
		for (List<List<OriginalLoopNamePo>> sameTypeList :arrangeLoopNameArray.getOriginalLoopNamePoList() )
		{
			mainEx.arrayCardDBForSameTypeGoodList(sameTypeList);
		}
//
//		newArrayList = mainEx.reArraygeListCh(arrangeLoopNameArray, newList);


		//System.out.println(dataGoodslist);
		loopNameService.updateGoods(dataGoodslist); ///编号。相关。。

	}

	@Override
	/**
	 * 非相关的其它信号
	 */
	public void fourthStep2() throws MyException
	{

		List<OriginalLoopNamePo> dataGoodslist = loopNameService.getOtherListByDiscernOrder();
		for (OriginalLoopNamePo originalLoopNamePo:dataGoodslist)
		{
			originalLoopNamePo.setCardNo(99);
			originalLoopNamePo.setIsLeft(99);
			originalLoopNamePo.setChNumber(0);
		}

		List<OriginalLoopNamePo> oldList2 = dataGoodslist;

		ArrangeLoopNameArray arrangeLoopNameArray = new ArrangeLoopNameArray();
		for (OriginalLoopNamePo loName :oldList2 )
		{
			arrangeLoopNameArray.addLoopNametoArray(loName);
		}
		System.out.println("-------------------");
		System.out.println("oldList3。size."+ oldList2.size());
		System.out.println("arrangeLoopNameArray:getTotalSize:"+arrangeLoopNameArray.getTotalSize());
		// 这里是原来的list表。
		System.out.println("oldListOTHER3.★★★★."+ oldList2);
		// 经过后排之后的list表。
		System.out.println("listOTHER3.★★★★."+ arrangeLoopNameArray.getOriginalLoopNamePoList());
		MainExcel mainEx = new MainExcel();
		// arrangeLoopNameArray. CHReArrage(arrangeLoopNameArray.getOrarialLoopNameList());
		// 相同的类型的一组信号。
		for (List<List<OriginalLoopNamePo>> sameTypeList :arrangeLoopNameArray.getOriginalLoopNamePoList() )
		{

//			mainEx.arrayCardDBForSameTypeGoodList(sameTypeList);
			mainEx.arrayCardDBForSameTypeGoodListNoLeft(sameTypeList);
		}

		loopNameService.updateGoods(dataGoodslist); ///编号。不相关

	}

	@Override
	// 单独的编CH。 针对白总的最后一步要单独排CH
	public void fifthStep() throws MyException
	{

		// 洗清掉以前的CH。
		List<OriginalLoopNamePo> dataGoodslist = loopNameService .getRelateListByDiscernOrder();
		for (OriginalLoopNamePo originalLoopNamePo:dataGoodslist)
		{
			originalLoopNamePo.setChNumber(0);
		}
		// loopNameService.updateGoods(dataGoodslist);
		// 把一张卡里的所有的信号拿出来。
		List<ArrayLoopNameCardPo> dataCardlist = loopNameService.getAllCard();
		for (ArrayLoopNameCardPo arrayLoopNameCardPo:dataCardlist)
		{

			int cardNo = arrayLoopNameCardPo.getCardNo();
			List<OriginalLoopNamePo> cardGoodslist = loopNameService .getGoodsListByCard(cardNo);
			if (cardGoodslist==null || cardGoodslist.size()==0) continue;
			for (int i = 0 ; i <cardGoodslist.size();i++)
			{
				OriginalLoopNamePo originalLoopNamePo =cardGoodslist.get(i);
				originalLoopNamePo.setChNumber(i+1);

			}
			loopNameService.updateGoods(cardGoodslist);
		}

	}


}
