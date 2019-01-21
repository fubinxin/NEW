package com.mahindra;

import java.util.Comparator;

/**
 * Created by Administrator on 2018/4/22 0022.
 */
public class OrarialLoopNameSorterRe implements Comparator
{

    @Override
    public int compare(Object o1, Object o2 )
    {
        if (o1 instanceof OrarialLoopName && o2 instanceof OrarialLoopName )
        {
			OrarialLoopName s1 = (OrarialLoopName) o1;
			OrarialLoopName s2 = (OrarialLoopName) o2;
			if (s1.getSig_type()==null || s2.getSig_type() ==null ) return 0;

			int result = s1.getSig_type().compareTo(s2.getSig_type());
			if (result!=0) return result;
			if (result ==0)
				result = s1.getSensor_type().compareTo(s2.getSensor_type());
			if (result!= 0) return result;
//			if (result == 0)
//				result = s1.getPid_tag().compareTo(s2.getPid_tag());
//			if (result!= 0) return result;
//			if (result == 0)
//				result = s1.getLoop_name().compareTo(s2.getLoop_name());
//			if (result!= 0) return result;
			if (result ==0)
			{
				if (! s2.isRepBlock() &&   s1.isRepBlock() ) return -1;
				if (! s1.isRepBlock() && s2.isRepBlock() ) return 1;

				return 0;
				//if ( s1.isRepBlock() && s2.isRepBlock() ) return 1;

			}
//			if (result == 0)
//			{
//				if (s2.getRep_tag()== null || s2.getRep_tag().isEmpty()) return -1;
//				if (s1.getRep_tag()== null || s1.getRep_tag().isEmpty() ) return 1;
//
//				String s1re = s1.getRep_tag().replace("+","");
//				String s2re = s2.getRep_tag().replace("+","");
//
////
////				if (s1.getRep_tag().equals("1") &&  s2.getRep_tag().equals("1+") ) return 0;
////				if (s1.getRep_tag().equals("1+") &&  s2.getRep_tag().equals("1") ) return 0;
//
//				result = s1re.trim().compareTo(s2re.trim());
//			}

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
