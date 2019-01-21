//package com.ge.controller;
//
//
//import com.itextpdf.text.*;
//import com.itextpdf.text.pdf.BaseFont;
//import com.itextpdf.text.pdf.PdfWriter;
//import com.itextpdf.text.pdf.draw.LineSeparator;
//import com.itextpdf.tool.xml.XMLWorkerHelper;
//import org.joda.time.DateTime;
//import org.jsoup.Jsoup;
//
//import java.io.*;
//import java.nio.charset.Charset;
//import java.util.Date;
//
///**
// * HTML文件转换为PDF
// *
// * @author &lt;a href="http://www.micmiu.com"&gt;Michael Sun&lt;/a&gt;
// */
//public class Demo4URL2PDF {
//
//    /**
//     * @param args
//     */
//    public static void main(String[] args) throws Exception {
//        String blogURL = "http://news.baidu.com/";
//
//        // 直接把网页内容转为PDF文件
//        String pdfFile = "d:/demo-URL.pdf";
//        Demo4URL2PDF.parseURL2PDFFile(pdfFile, blogURL);
//
//        // 把网页内容转为PDF中的Elements
//
//    }
//
//    /**
//     * 根据URL提前blog的基本信息，返回结果&gt;&gt;:[主题 ,分类,日期,内容]等.
//     *
//     * @param blogURL
//     * @return
//     * @throws Exception
//     */
//    public static String[] extractBlogInfo(String blogURL) throws Exception {
//        String[] info = new String[4];
//        org.jsoup.nodes.Document doc = Jsoup.connect(blogURL).get();
//        //   org.jsoup.nodes.Element e_title = doc.select("h2.title").first();
//        info[0] = "测试";
//
////        org.jsoup.nodes.Element e_category = doc.select("a[rel=category tag]")
////                .first();
//        info[1] = "test";
//
////        org.jsoup.nodes.Element e_date = doc.select("span.post-info-date")
////                .first();
//
//        // String dateStr = e_date.text().split("日期")[1].trim();
//        info[2] = DateTime.now().toString();
//        org.jsoup.nodes.Element entry = doc.select("div.l-left-col").first();
//        info[3] = formatContentTag(entry);
//        info[3] = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
//                + "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"
//                + "<html xmlns=\"http://www.w3.org/1999/xhtml\">  "
//                + "<head>  "
//                + "<style>  "
//                + "body{  "
//                + "font-family:SimSun;  "
//                + "font-size:14px;  "
//                + "}  "
//                + "</style>  "
//                + "<meta http-equiv=\"Content-Type\" content=\"text/html;charset=UTF-8\"></meta></head><body>" + info[3] + "</body></html>";
//        return info;
//    }
//
//    /**
//     * 格式化 img标签
//     *
//     * @param entry
//     * @return
//     */
//    private static String formatContentTag(org.jsoup.nodes.Element entry) {
//        try {
//            entry.select("div").remove();
//            // 把 &lt;a href="*.jpg" &gt;&lt;img src="*.jpg"/&gt;&lt;/a&gt; 替换为 &lt;img
//            // src="*.jpg"/&gt;
//            for (org.jsoup.nodes.Element imgEle : entry
//                    .select("a[href~=(?i)\\.(png|jpe?g)]")) {
//                imgEle.replaceWith(imgEle.select("img").first());
//            }
//            return entry.html();
//        } catch (Exception e) {
//            return "";
//        }
//    }
//
//    /**
//     * 把String 转为 InputStream
//     *
//     * @param content
//     * @return
//     */
//    public static InputStream parse2Stream(String content) {
//        try {
//            ByteArrayInputStream stream = new ByteArrayInputStream(
//                    content.getBytes("utf-8"));
//            return stream;
//        } catch (Exception e) {
//
//            return null;
//        }
//    }
//
//    /**
//     * 直接把网页内容转为PDF文件
//     *
//     * @param pdfFile
//     * @throws Exception
//     */
//    public static void parseURL2PDFFile(String pdfFile, String blogURL)
//            throws Exception {
//
//        BaseFont bfCN = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H",
//                false);
//        // 中文字体定义
//        Font chFont = new Font(bfCN, 14, Font.NORMAL, BaseColor.BLUE);
//        Font secFont = new Font(bfCN, 12, Font.NORMAL, new BaseColor(0, 204,
//                255));
//        Font textFont = new Font(bfCN, 12, Font.NORMAL, BaseColor.BLACK);
//
//        Document document = new Document();
//        PdfWriter pdfwriter = PdfWriter.getInstance(document,
//                new FileOutputStream(pdfFile));
//        pdfwriter.setViewerPreferences(PdfWriter.HideToolbar);
//        document.open();
//
//        String[] blogInfo = extractBlogInfo(blogURL);
//
//        int chNum = 1;
//        Chapter chapter = new Chapter(new Paragraph("URL转PDF测试", chFont),
//                chNum++);
//
//        Section section = chapter
//                .addSection(new Paragraph(blogInfo[0], secFont));
//        section.setIndentation(10);
//        section.setIndentationLeft(10);
//        section.setBookmarkOpen(false);
//        section.setNumberStyle(Section.NUMBERSTYLE_DOTTED_WITHOUT_FINAL_DOT);
//        section.add(new Chunk("分类：" + blogInfo[1] + " 日期：" + blogInfo[2],
//                textFont));
//
//        LineSeparator line = new LineSeparator(1, 100, new BaseColor(204, 204,
//                204), Element.ALIGN_CENTER, -2);
//        Paragraph p_line = new Paragraph(" ");
//        p_line.add(line);
//        section.add(p_line);
//        section.add(Chunk.NEWLINE);
//
//        document.add(chapter);
//
//        // html文件
//        XMLWorkerHelper.getInstance().parseXHtml(pdfwriter, document,
//                parse2Stream(blogInfo[3]), Charset.forName("UTF-8"));
//
//        document.close();
//    }
//
//    /**
//     * 把网页内容转为PDF中的Elements
//     *
//     * @param pdfFile
//     * @param
//     */
//    public static void parseURL2PDFElement(String pdfFile, String blogURL) {
//        try {
//            Document document = new Document(PageSize.A4);
//            InputStream htmlFileStream = new FileInputStream(blogURL);
//            FileOutputStream outputStream = new FileOutputStream(pdfFile);
//            PdfWriter pdfwriter = PdfWriter.getInstance(document, outputStream);
//            pdfwriter.setViewerPreferences(PdfWriter.HideToolbar);
//            document.open();
//
//            InputStreamReader isr = new InputStreamReader(htmlFileStream, "UTF-8");
//            XMLWorkerHelper.getInstance().parseXHtml(pdfwriter, document, isr);
//            document.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//}