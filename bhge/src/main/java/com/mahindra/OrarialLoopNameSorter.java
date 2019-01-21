package com.mahindra;

import com.mahindra.po.OriginalLoopNamePo;

import java.util.Comparator;

/**
 * Created by Administrator on 2018/4/22 0022.
 */
public class OrarialLoopNameSorter implements Comparator
{

    @Override
    public int compare(Object o1, Object o2 )
    {
        if (o1 instanceof OrarialLoopName && o2 instanceof OriginalLoopNamePo)
        {
			OrarialLoopName s1 = (OrarialLoopName) o1;
			OrarialLoopName s2 = (OrarialLoopName) o2;
			if (s1.getSig_type()==null || s2.getSig_type() ==null ) return 0;

			int result = s1.getSig_type().compareTo(s2.getSig_type());
			if (result!=0) return result;
			if (result ==0)
				result = s1.getSensor_type().compareTo(s2.getSensor_type());
			if (result!= 0) return result;
			if (result == 0)
				result = s1.getPid_tag().compareTo(s2.getPid_tag());
			if (result!= 0) return result;
			if (result == 0)
				result = s1.getLoop_name().compareTo(s2.getLoop_name());
			if (result!= 0) return result;
			if (result == 0)
			{
				if (s1.getRep_tag()== null || s2.getRep_tag()== null) return 0;

				result = s1.getRep_tag().compareTo(s2.getRep_tag());
			}

			return result;
//            if (s1.getSig_type() < s2.num)
//            {
//                return 1;
//            } else
//            {
//                return -1;
//            }
        }
        return 0;
    }
}
