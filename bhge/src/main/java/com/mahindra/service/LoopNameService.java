package com.mahindra.service;

import com.mahindra.OrarialLoopName;
import com.mahindra.common.exception.MyException;
import com.mahindra.po.ArrayLoopNameCardPo;
import com.mahindra.po.OriginalLoopNamePo;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/8/3.
 */
public interface LoopNameService {
    public List<OriginalLoopNamePo> getGoodsListByDiscernOrder();
	public List<OriginalLoopNamePo> getGoodsListByDiscernOrder(String controllId, String sigType, String sensorType, Integer cardNo);
	public List<OriginalLoopNamePo> getGoodsREListByDiscernOrder();

	public List<OriginalLoopNamePo> getRelateListByDiscernOrder();

	public List<OriginalLoopNamePo> getOtherListByDiscernOrder() ;

	public List<OriginalLoopNamePo>  getGoodsListByReOrder();

	public void clearCard ();

	public List<ArrayLoopNameCardPo> getAllCard();

	public void saveGoods(List<OrarialLoopName> orarialLoopNameList, String controllerId);


    /**
     * 导入信号信息，并返回所有excel的数据
     * @param upFile
     * @return
     * @throws MyException
     */
    public void getUpExcelGoods(MultipartFile upFile, String controllId) throws MyException;
	public void updateGoods(List<OriginalLoopNamePo> orarialLoopNameList);

    /**
     * 下载excel
     * @param outputStream 输出流
     */
	public void downloadExcel(OutputStream outputStream, Integer stepNumber) throws MyException;

	/**
	 * 根据控制器获取SigType
	 * @param controllId
	 * @return 返回所有SigType数据
	 */
	public List<Map<String, Object>> getSigTypeByControll(String controllId);

	/**
	 * 根据控制器获取SigType
	 * @param controllId
	 * @return 返回所有SigType数据
	 */
	public List<String> getSensorTypeByControll(String controllId, String sigType);
	/**
	 *
	 * @param sigType  卡类型 最多多少Ch
	 * @param isLeft   左还是右
	 * @param needNum  要放几个信号(空 = 卡NUM- 现在count)
	 * @return
	 */
	List<ArrayLoopNameCardPo> getCardByType(String  sigType, int isLeft, int needNum);

	/**
	 * 这里放非冗余非相关的信号。
	 * @param sigType
	 * @param needNum
	 * @return
	 */
	List<ArrayLoopNameCardPo> getCardByType(String  sigType, int needNum);

	int getMaxCardNum();

	void saveCard(ArrayLoopNameCardPo arrayLoopNameCard);

	List<OriginalLoopNamePo> getGoodsListByCard(int cardNo);

	void batchUpdateGoodNotRelate(List<OriginalLoopNamePo> originalLoopNamePos);
}
