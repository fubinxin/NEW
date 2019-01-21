package pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.output.ByteArrayOutputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * @Author: gaofeng_peng
 * @Date: 2018/6/8 15:10
 */
public class PDFHelper {


    public static void main(String[] args) throws IOException, DocumentException {
        String outPath = System.getProperty("user.dir") + "/PDFStore/file";
        String outWaterPath = System.getProperty("user.dir") + "/PDFStore/waterFile";
        //模板路径
        String templatePath = System.getProperty("user.dir") + "/PDFStore/demo.pdf";
        //生成的新文件路径
        String pdfPage = System.getProperty("user.dir") + "/PDFStore/file/newPdf.pdf";
        String waterPDFPath = outWaterPath+"/newPdf.pdf";
        PdfReader reader;
        FileOutputStream out;
        ByteArrayOutputStream bos;
        PdfStamper stamper;
        try {

            out = new FileOutputStream(pdfPage);//输出流
            reader = new PdfReader(templatePath);//读取pdf模板
            bos = new ByteArrayOutputStream();
            stamper = new PdfStamper(reader, bos);
            AcroFields form = stamper.getAcroFields();

            //处理中文问题
            BaseFont font = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            form.addSubstitutionFont(font);
            Map<String, String> map = new HashedMap();
            map.put("xh", "123456789");
            map.put("xm", "小鲁");
            map.put("sfz", "130222111133338888");
            map.put("zz", "河北省唐山市sssssssssssssssssssas水水水水水水水水水水水水水水水水水水水ssssssssssssssssssssssssssss");
            map.put("sr", "1994-00-00");
            map.put("xb", "男");
            java.util.Iterator<String> it = form.getFields().keySet().iterator();
            while (it.hasNext()) {
                String name = it.next().toString();
                System.out.println(name);
                System.out.println(map.get(name));
                form.setField(name, map.get(name));
            }
            stamper.setFormFlattening(true);//如果为false那么生成的PDF文件还能编辑，一定要设为true
            stamper.close();

            Document doc = new Document();
            PdfCopy copy = new PdfCopy(doc, out);
            doc.open();
            PdfImportedPage importPage = copy.getImportedPage(
                    new PdfReader(bos.toByteArray()), 1);
            copy.addPage(importPage);
            doc.close();
            addWaterMark(pdfPage,waterPDFPath,"www.1234xswefv.com",30,500);

        } catch (IOException e) {
            System.out.println(1);
        } catch (DocumentException e) {
            System.out.println(2);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @param pdfPage      待加水印文件
     * @param waterPDFPath 加水印后存放地址
     * @param text         加水印的文本内容
     * @param textWidth    文字横坐标
     * @param textHeight   文字纵坐标
     * @throws Exception
     */
    public  static void addWaterMark(String pdfPage, String waterPDFPath, String text, int textWidth, int textHeight) throws Exception {
        // 待加水印的文件
        PdfReader reader = new PdfReader(pdfPage);
        // 加完水印的文件
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(waterPDFPath));
        int total = reader.getNumberOfPages() + 1;
        PdfContentByte content;
        // 设置字体
        BaseFont font = BaseFont.createFont();
        String waterImgPath = Class.class.getClass().getResource("/1.png").getPath();
        float scalePercentage = (72 / 300f) * 100.0f;
        Image img = Image.getInstance(waterImgPath);
        img.setAbsolutePosition(40, 700);//坐标
        // 循环对每页插入水印
        for (int i = 1; i < total; i++) {
            // 水印的起始
            content = stamper.getUnderContent(i);
            //插入图片
            img.scalePercent(scalePercentage, scalePercentage);
           content.addImage(img);

            // 开始
            content.beginText();
            // 设置颜色 默认为蓝色
            content.setColorFill(BaseColor.BLUE);
            // 设置字体及字号
            content.setFontAndSize(font, 38);
            // 设置起始位置
            // content.setTextMatrix(400, 880);
            content.setTextMatrix(textWidth, textHeight);
            PdfGState gs = new PdfGState();
            gs.setFillOpacity(0.3f);// 设置透明度为0.8
            content.setGState(gs);
            // 开始写入水印
            content.showTextAligned(Element.ALIGN_LEFT, text, textWidth,
                    textHeight, 45);
            content.endText();
        }
        stamper.close();
    }
}
