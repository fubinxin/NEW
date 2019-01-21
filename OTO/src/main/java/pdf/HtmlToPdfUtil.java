package pdf;


import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.PropertiesUtil;

import java.io.FileOutputStream;

/**
 * @Author: gaofeng_peng
 * @Date: 2018/7/5 13:13
 */
public class HtmlToPdfUtil {
    //wkhtmltopdf在系统中的路径
    // private static final String toPdfTool = "D:\\wkhtmltox\\bin\\wkhtmltopdf.exe";
    private static Logger logger = LoggerFactory.getLogger(HtmlToPdfUtil.class);

    /**
     * html转pdf
     *
     * @param srcPath         html路径，可以是硬盘上的路径，也可以是网络路径
     * @param pdfLocationPath pdf保存路径
     * @return 转换成功返回true
     */
    public static boolean convert(String srcPath, String pdfLocationPath, String systemOs) {
        boolean result = true;

        try {
            String toPdfTool = "";
            if (systemOs.toLowerCase().startsWith("win")) {
                toPdfTool = HtmlToPdfUtil.class.getClassLoader().getResource("wkhtmltox/bin/wkhtmltopdf.exe").getPath();
            } else {
                toPdfTool = PropertiesUtil.getProperty("wkhtmltopdfPath");
            }
            StringBuilder cmd = new StringBuilder();
            cmd.append(toPdfTool);
            cmd.append(" ");
            cmd.append("  --background");
            cmd.append(" --debug-javascript");
            cmd.append("  --header-line");//页眉下面的线
            cmd.append(" --header-spacing 10 ");//    (设置页眉和内容的距离,默认0)

            cmd.append(srcPath);
            cmd.append(" ");
            cmd.append(pdfLocationPath);

            Process proc = Runtime.getRuntime().exec(cmd.toString());
            HtmlToPdfInterceptor error = new HtmlToPdfInterceptor(proc.getErrorStream());
            HtmlToPdfInterceptor output = new HtmlToPdfInterceptor(proc.getInputStream());
            error.start();
            output.start();
            proc.waitFor();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
        return result;
    }

    /**
     * @param sourceFilePath    源文件路径
     * @param fileWaterMarkPath 水印生成文件路径
     * @throws Exception
     */
    public static void setWaterMarkForPDF(String sourceFilePath, String fileWaterMarkPath, String waterMarkName) throws Exception {
        // byte[] ownerPassword="TrchMahindra".getBytes();
        String waterPath = HtmlToPdfUtil.class.getClassLoader().getResource("1.png").getPath();
        PdfReader reader = new PdfReader(sourceFilePath);
        PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(fileWaterMarkPath));
        stamp.setEncryption(null, null,
                PdfWriter.ALLOW_PRINTING, false);
        float pageWidth = reader.getPageSize(1).getWidth() / 2;
        float pageHeight = reader.getPageSize(1).getHeight() / 2;
        int total = reader.getNumberOfPages() + 1;
        PdfContentByte under = null;
        Image img = Image.getInstance(waterPath);
        img.setAbsolutePosition(pageWidth, pageHeight);//坐标
        img.scalePercent(50);//依照比例缩放
        BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA,
                BaseFont.CP1252, BaseFont.NOT_EMBEDDED);

        for (int i = 1; i < total; i++)  // 每一页都加水印
        {
            under = stamp.getUnderContent(i);
            // 添加图片
            under.addImage(img);
            under.beginText();
            under.setColorFill(BaseColor.BLACK);
            under.setFontAndSize(bf, 30);
            PdfGState gs = new PdfGState();
            gs.setFillOpacity(0.3f);// 设置透明度为0.3
            under.setGState(gs);
            under.setTextMatrix(pageWidth, pageHeight);
            under.endText();
        }

        stamp.close();
        reader.close();
    }

}
