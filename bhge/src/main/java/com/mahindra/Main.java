package com.mahindra;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by Administrator on 2018/4/16.
 */
public class Main {
    /*
    15 16  排 无 loopname  无 tag
    前面排8个. MD开头的可以排 6个,其它的 loopnametype 可以排2个.

     */

    List<OrarialLoopName> getOriginal (InputStream excelInput) throws Exception
    {

        List<OrarialLoopName> resultList = new ArrayList();
        Excel_reader test= new Excel_reader();
        ArrayList<ArrayList<String>> arr= test.xlsx_reader(excelInput ,3,4);  //后面的参数代表需要输出哪些列，参数个数可以任意
        for(int i=0;i<arr.size();i++){
            ArrayList<String> row = arr.get(i);
            if (row==null || row.get(0)==null || row.get(0).equals( Excel_reader.Empty_str)) continue;

            OrarialLoopName orarialLoopName = new OrarialLoopName();
            orarialLoopName.setLoop_name(row.get(0));
            orarialLoopName.setPid_tag(row.get(1));
            orarialLoopName.setUsed(false);
            resultList.add(orarialLoopName);

        }
        return  resultList;
    }


    List<List<String>> getArrangedIO (InputStream excelInput) throws Exception
    {

        List<List<String>> resultList = new ArrayList();
        Excel_reader test= new Excel_reader();
        ArrayList<ArrayList<String>> arr = test.xlsx_reader(excelInput );  //后面的参数代表需要输出哪些列，参数个数可以任意
        for(int i=0;i<arr.size();i++){
            ArrayList<String> row = arr.get(i);
            if (row==null || row.get(5)==null || row.get(5).equals( Excel_reader.Empty_str)) continue;

            resultList .add(row);

        }
        return  resultList;
    }

    List<List<String>> getArrangedIOinOriginal ( List<List<String>> ArrangedList,  List<OrarialLoopName> originalList ) throws IOException
    {

        List<List<String>> resultList = new ArrayList();

        List<List<String>> arr = ArrangedList ;
        for(int i=0;i<arr.size();i++){
            List<String> row = arr.get(i);

            if (row==null || row.get(5)==null || row.get(6)==null ) continue;

            if (isExitLoopName_Tag(originalList,row.get(5),row.get(6)))
            {
                resultList .add(row);
            }

        }
        return  resultList;
    }

    boolean isExitLoopName_Tag(List<OrarialLoopName> orarialList ,String loopName,String tag)
    {
        if  (orarialList==null || orarialList.size()==0) return false;
        if (loopName ==null || tag ==null) return  false;

        for (OrarialLoopName orarialLoopName : orarialList)
        {
            if(loopName.equals(orarialLoopName.getLoop_name()) && tag.equals(orarialLoopName.getPid_tag()) )
                return true;
        }
        return false;
    }






    public static void main(String[] args) throws IOException  {
//        Excel_reader test= new Excel_reader();
//        ArrayList<ArrayList<String>> arr=test.xlsx_reader("E:\\!ge\\BHGE\\Original_IO_list.xls" );  //后面的参数代表需要输出哪些列，参数个数可以任意
//        for(int i=0;i<arr.size();i++){
//            ArrayList<String> row = arr.get(i);
//            for(int j=0;j<row.size();j++){
//                System.out.print(row.get(j)+" ");
//            }
//            System.out.println("");
//        }



			ArrayList list = new ArrayList();
			list.add(0);//插入第一个元素
			list.add(1);
			list.add(2);
			list.add(3);
			list.add(4);
			list.add(5);
			System.out.println(list);//打印list数组
			list.add(2, 7);
			System.out.println(list);
		}

}