package com.mahindra.service.impl;

import com.mahindra.Excel_writer;
import com.mahindra.MainExcel;
import com.mahindra.OrarialLoopName;
import com.mahindra.common.exception.MyException;
import com.mahindra.mapper.LoopNameCardMapper;
import com.mahindra.mapper.OrarialLoopNameMapper;
import com.mahindra.po.ArrayLoopNameCardPo;
import com.mahindra.po.OriginalLoopNamePo;
import com.mahindra.service.LoopNameService;
import com.mahindra.util.ExcelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by weican on 2018/8/6.
 */
@Service
public class LoopNameServiceImpl implements LoopNameService {
    private Logger log = LoggerFactory.getLogger(LoopNameServiceImpl.class);

    @Autowired
    private LoopNameCardMapper loopNameCardMapper;

    @Autowired
    private OrarialLoopNameMapper orarialLoopNameMapper;

    @Autowired
    private Environment env;


    public List<OriginalLoopNamePo> getGoodsListByDiscernOrder() {
        return orarialLoopNameMapper.selectAllGoods();
    }

    public void clearCard()
    {
        loopNameCardMapper.clearCard();
    }

    public List<ArrayLoopNameCardPo> getAllCard()
    {
         return loopNameCardMapper.selectAllCard();
    }
    @Override
    public List<OriginalLoopNamePo> getGoodsListByDiscernOrder(String controllId, String sigType, String sensorType, Integer cardNo) {
        OriginalLoopNamePo originalLoopNamePo = new OriginalLoopNamePo();
        if (controllId != null) {
            originalLoopNamePo.setControllId(controllId);
        }
        if (!StringUtils.isEmpty(sigType)) {
            originalLoopNamePo.setSig_type(sigType);
        }
        if (!StringUtils.isEmpty(sensorType)) {
            originalLoopNamePo.setSensor_type(sensorType);
        }
        if (cardNo != null) {
            originalLoopNamePo.setCardNo(cardNo);
        }

        return orarialLoopNameMapper.getGoodsListByDiscernOrder(originalLoopNamePo);
    }

    @Override
	 public List<OriginalLoopNamePo> getGoodsREListByDiscernOrder()
	{
		List<OriginalLoopNamePo> resultList = null;
		try {
			resultList = orarialLoopNameMapper.selectREGoods();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}



	@Override
	public List<OriginalLoopNamePo> getRelateListByDiscernOrder()
	{
		List<OriginalLoopNamePo> resultList = null;
		try {
			resultList = orarialLoopNameMapper.selectReLatedGoods();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}

    @Override
    public List<OriginalLoopNamePo> getOtherListByDiscernOrder()
    {
        List<OriginalLoopNamePo> resultList = null;
        try {
            resultList = orarialLoopNameMapper.selectOtherGoodsByDiscernOrder();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

	@Override
	public List<OriginalLoopNamePo> getGoodsListByReOrder()
	{
		List<OriginalLoopNamePo> resultList = null;
		try {
			resultList = orarialLoopNameMapper.selectGoodsListByReOrder();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}

	public void saveGoods(List<OrarialLoopName> orarialLoopNameList, String controllerId) {
        if (orarialLoopNameList == null || orarialLoopNameList.size() == 0) return;

        List<OriginalLoopNamePo> list = new ArrayList<>();
        StringBuffer orgNos = new StringBuffer();
        int discern_order = 1;
        for (OrarialLoopName orarialLoopName : orarialLoopNameList) {

            OriginalLoopNamePo originalLoopNamePo = new OriginalLoopNamePo();
            originalLoopNamePo.setOrgNo(orarialLoopName.getOrgNo());
            originalLoopNamePo.setDiscern_order(discern_order++);
            originalLoopNamePo.setLoop_name(orarialLoopName.getLoop_name());
            originalLoopNamePo.setPid_tag(orarialLoopName.getPid_tag());
            originalLoopNamePo.setSig_type(orarialLoopName.getSig_type());
            originalLoopNamePo.setSensor_type(orarialLoopName.getSensor_type());
            originalLoopNamePo.setDesc_info(orarialLoopName.getCHDesc());
            originalLoopNamePo.setRep_tag(orarialLoopName.getRep_tag());
            originalLoopNamePo.setRalate_tag(orarialLoopName.getRep_tag());
            originalLoopNamePo.setRepBlock(orarialLoopName.isRepBlock());
            originalLoopNamePo.setControllId(controllerId);
//            orgNos.append(orarialLoopName.getOrgNo()).append(",");
            list.add(originalLoopNamePo);
        }
//        Map<String, Object> map = new HashMap<>();
//        map.put("orgNos", orgNos.substring(0, orgNos.length() - 1));
//        map.put("controllerId", controllerId);
//        orarialLoopNameMapper.delGoodsByOrgNoAndControllerId(map);
        orarialLoopNameMapper.batchInsertGood(list);
    }

    public void updateGoods(List<OriginalLoopNamePo> orarialLoopNameList) {
        String databaseType = env.getProperty("spring.datasource.database");
        if (StringUtils.isEmpty(databaseType) || databaseType.toLowerCase().equals("h2")) {
            if (!CollectionUtils.isEmpty(orarialLoopNameList)) {
                for (OriginalLoopNamePo originalLoopNamePo : orarialLoopNameList) {
                    orarialLoopNameMapper.updateGood(originalLoopNamePo);
                }
            }
        } else {
            orarialLoopNameMapper.batchUpdateGood(orarialLoopNameList);
        }

    }

    @Override
    public void downloadExcel(OutputStream os, Integer stepNumber) throws MyException {
        MainExcel mainEx = new MainExcel();
        String fileType = "xlsx";
        List<OriginalLoopNamePo> dataGoodslist;
        if (stepNumber == 2) {
            dataGoodslist = getGoodsListByReOrder();
        } else {
            dataGoodslist = getGoodsListByDiscernOrder();
        }
        String[] array = {"NO", "Redundant", "RepBlock", "TAG NAME", "P&ID", "DESCRIPTION", "SIG TYPE", "SENSOR TYPE", "CONTROLLID", "MODULE","IsLeft", "CH"};

        try {
            mainEx.arrangeReTag(dataGoodslist);
            List<List<String>> oldArrayList = mainEx.sortArragedListsBySimple(dataGoodslist);
            Excel_writer.writer(os, fileType, oldArrayList, array);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new MyException("excel下载失败!");
        }
    }

    @Override
    public List<ArrayLoopNameCardPo> getCardByType(String  sigType,  int needNum)
    {
        List<ArrayLoopNameCardPo> resultList = null;
        try {
            Map<String , Object> paraMap = new HashMap<>();
            paraMap.put("sigType",sigType);
            paraMap.put("needNum",needNum);

            resultList = loopNameCardMapper.getCardByTypeNoLeft(paraMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

	@Override
	public List<ArrayLoopNameCardPo> getCardByType(String  sigType, int isLeft, int needNum)
	{
		List<ArrayLoopNameCardPo> resultList = null;
		try {
		    Map<String , Object> paraMap = new HashMap<>();
		    paraMap.put("sigType",sigType);
            paraMap.put("isLeft",isLeft);  // 0 左，1右。。
            paraMap.put("needNum",needNum); // 这里是卡有这么多余位的都可以。

            resultList = loopNameCardMapper.getCardByType(paraMap);// 左侧可以放得下的卡。
            List<ArrayLoopNameCardPo> resultList2   = loopNameCardMapper.getCardByTypeNewRight(paraMap);     // 右侧新开的卡完全无左侧的信号可以放得下的卡。
            resultList.addAll(resultList2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}

	@Override
	public int getMaxCardNum()
	{
		int resultList =1;
		try {
			int count = loopNameCardMapper.getCardCount();
			if(count==0)
				return 0;
			resultList = loopNameCardMapper.getMaxCardNum();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}

	@Override
	public void saveCard(ArrayLoopNameCardPo arrayLoopNameCard)
	{
		try {
			 loopNameCardMapper.saveCard(arrayLoopNameCard);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

    @Override
    public List<OriginalLoopNamePo> getGoodsListByCard(int cardNo) {
        return orarialLoopNameMapper.getGoodsListByCard(cardNo);
    }

    @Override
    public void batchUpdateGoodNotRelate(List<OriginalLoopNamePo> originalLoopNamePos) {
        String databaseType = env.getProperty("spring.datasource.database");
        if (StringUtils.isEmpty(databaseType) || databaseType.toLowerCase().equals("h2")) {
            if (!CollectionUtils.isEmpty(originalLoopNamePos)) {
                for (OriginalLoopNamePo originalLoopNamePo : originalLoopNamePos) {
                    orarialLoopNameMapper.batchUpdateGoodNotRelateForH2(originalLoopNamePo);
                }
            }
        } else {
            orarialLoopNameMapper.batchUpdateGoodNotRelate(originalLoopNamePos);
        }
    }

    @Override
    public void getUpExcelGoods(MultipartFile upFile, String controllId) throws MyException {
        try {
            loopNameCardMapper.clearCard();
            orarialLoopNameMapper.delAllGoods();
            MainExcel mainEx = new MainExcel();
            int[] selectCols = ExcelUtil.checkExcelTitle(upFile.getInputStream());
            List<OrarialLoopName> oldList = mainEx.getOriginal(upFile.getInputStream(), 1, selectCols);
            saveGoods(oldList, controllId);
            log.info("upload data size = {}", oldList.size());
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            String errorMsg = "excel上传失败!";
            if (e instanceof MyException){
                errorMsg = e.getMessage();
            }
            throw new MyException(errorMsg);
        }
    }

    @Override
    public List<Map<String, Object>> getSigTypeByControll(String controllId) {
        List<Map<String, Object>> sigTypeOrSensorTypes = new ArrayList<>();
        List<String> sigTypes = orarialLoopNameMapper.getSigTypeByControll(controllId);
        for (String sigType : sigTypes) {
            Map<String, Object> map = new HashMap<>();
            map.put("key", sigType);
            List<String> sensorTypes = getSensorTypeByControll(controllId, sigType);
            List<Map<String,String>> sensorTypeList = new ArrayList<>();
            for (String sensorType : sensorTypes) {
                Map<String,String> sensorType1 = new HashMap<>();
                sensorType1.put("key", sensorType);
                sensorTypeList.add(sensorType1);
            }
            map.put("sensorType",sensorTypeList);

            sigTypeOrSensorTypes.add(map);
        }
        return sigTypeOrSensorTypes;
    }


    @Override
    public List<String> getSensorTypeByControll(String controllId, String sigType) {
        Map<String, Object> map = new HashMap<>();
        if (!StringUtils.isEmpty(controllId)) {
            map.put("controllId", controllId);
        }
        if (!StringUtils.isEmpty(sigType)) {
            map.put("sig_type", sigType);
        }
        return orarialLoopNameMapper.getSensorTypeByControll(map);
    }
}
