package com.mahindra.mapper;

import com.mahindra.po.OriginalLoopNamePo;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/7/13.
 */
public interface OrarialLoopNameMapper {
    public List<OriginalLoopNamePo> getGoodsByOrgNos(String[] orgNos);
    public List<OriginalLoopNamePo> selectAllGoods();
	public List<OriginalLoopNamePo> selectREGoods() throws Exception;
    public List<OriginalLoopNamePo> getGoodsListByDiscernOrder(OriginalLoopNamePo originalLoopNamePo);
//selectReLatedGoods
	public List<OriginalLoopNamePo> selectReLatedGoods() throws Exception;
	public List<OriginalLoopNamePo> selectOtherGoodsByDiscernOrder()  throws Exception;
	public List<OriginalLoopNamePo> selectGoodsListByReOrder() throws Exception;



    public List<OriginalLoopNamePo> selectRecommendGoods();

    public void insertGood(OriginalLoopNamePo originalLoopNamePo);

    public void batchInsertGood(List<OriginalLoopNamePo> originalLoopNamePos);

    public void delAllGoods();

    public void delGoodsByOrgNoAndControllerId(Map<String, Object> map);

	public void updateGood(OriginalLoopNamePo originalLoopNamePo);

    public void batchUpdateGood(List<OriginalLoopNamePo> originalLoopNamePos);

    void batchUpdateGoodNotRelate(List<OriginalLoopNamePo> originalLoopNamePos);

    void batchUpdateGoodNotRelateForH2(OriginalLoopNamePo originalLoopNamePos);

    /**
     * 根据控制器获取SigType
     * @param controllerId
     * @return 返回所有SigType数据
     */
    public List<String> getSigTypeByControll(String controllerId);

    /**
     * 根据控制器获取SensorType
     * @param map
     * @return 返回所有SigType数据
     */
    public List<String> getSensorTypeByControll(Map<String, Object> map);

    List<OriginalLoopNamePo> getGoodsListByCard(int cardNo);

}
