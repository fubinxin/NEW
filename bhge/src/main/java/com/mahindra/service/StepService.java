package com.mahindra.service;

import com.mahindra.common.exception.MyException;
import com.mahindra.po.OriginalLoopNamePo;

import java.util.List;

/**
 * Created by Administrator on 2018/8/3.
 */
public interface StepService
{


	/**
	 * 操作步骤
	 * @param stepNumber 步骤数(1,2,3,4)
	 */
	public void step(Integer stepNumber) throws MyException;

	/**
	 *
	 * @param type
	 * @param orgNos
	 * @throws MyException
	 */
	public void chooseMark(int type, String[] orgNos) throws MyException;

	/**
	 * 第一步。

	 * @return
	 * @throws com.mahindra.common.exception.MyException
	 */
	public void firstStep() throws MyException;
	public  List<OriginalLoopNamePo> secondStep() throws MyException;
	public void thirdStep() throws MyException;
	public void fourthStep() throws MyException;
	public void fourthStep2() throws MyException;

	public void fifthStep() throws MyException;


}
