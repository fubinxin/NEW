package com.mahindra.mapper;

import com.mahindra.ArrayLoopNameCardUtil;
import com.mahindra.po.ArrayLoopNameCardPo;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/7/13.
 */
public interface LoopNameCardMapper
{
	public List<ArrayLoopNameCardPo> selectAllCard() ;


	List<ArrayLoopNameCardPo> getCardByType(Map paraMap);
	List<ArrayLoopNameCardPo> getCardByTypeNoLeft(Map paraMap);

	int getMaxCardNum();
	int getCardCount();

	void clearCard();

	void saveCard(ArrayLoopNameCardPo arrayLoopNameCard);

	List<ArrayLoopNameCardPo> getCardByTypeNewRight(Map<String, Object> paraMap);

}

