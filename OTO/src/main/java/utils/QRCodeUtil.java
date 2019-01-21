package utils;

import com.common.exception.MyException;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @Author: gaofeng_peng
 * @Date: 2018/6/26 11:02
 */
public class QRCodeUtil{

    public  static void qrCode(HttpServletResponse response,String httpUrl) throws ServletException, IOException {

        if (StringUtils.isBlank(httpUrl)){
            throw new MyException("链接无效");
        }
        ByteArrayOutputStream out = QRCode.from(httpUrl).to(ImageType.PNG).stream();

        response.setContentType("image/png");
        response.setContentLength(out.size());

        OutputStream outStream = response.getOutputStream();

        outStream.write(out.toByteArray());

        outStream.flush();
        outStream.close();
    }
}
