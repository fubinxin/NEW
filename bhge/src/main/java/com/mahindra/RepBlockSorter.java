package com.mahindra;

import java.util.Comparator;
import java.util.List;

/**
 * Created by Administrator on 2018/4/22 0022.
 */
public class RepBlockSorter implements Comparator
{

    @Override
    public int compare(Object o1, Object o2 )
    {
        if (o1 instanceof List && o2 instanceof List )
        {
			int result =0;
//			OrarialLoopName s1 = (OrarialLoopName) o1;
//			OrarialLoopName s2 = (OrarialLoopName) o2;
//			if (s1.getSig_type()==null || s2.getSig_type() ==null ) return 0;
//
//			int result = s1.getSig_type().compareTo(s2.getSig_type());
//			if (result!=0) return result;
//			if (result ==0)
//				result = s1.getSensor_type().compareTo(s2.getSensor_type());
//			if (result!= 0) return result;
//			if (result == 0)
				result = (((List) o1).size()-(((List) o2).size()));


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
