package venus.oa.checkcode.action;

import com.octo.captcha.service.CaptchaServiceException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import venus.oa.checkcode.CaptchaServiceSingleton;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * <p> check code controller </p>
 *
 * @author changming.Y <changming.yang.ah@gmail.com>
 * @since 2018-04-13 23:29
 */
@Controller
@RequestMapping("/checkcode")
public class CheckcodeAction {

    /**
     * fresh building image
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/freshImage")
    public void freshImage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        byte[] captchaChallengeAsJpeg = null;
        // the output stream to render the captcha image as jpeg into
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            // get the session id that will identify the generated captcha.
            //the same id must be used to validate the response, the session id is a good candidate!
            String captchaId = request.getSession().getId();

            // call the ImageCaptchaService getChallenge method
            BufferedImage challenge = CaptchaServiceSingleton.getInstance().getImageChallengeForID(captchaId, request.getLocale());

            // a jpeg encoder
            JPEGImageEncoder jpegEncoder = JPEGCodec.createJPEGEncoder(jpegOutputStream);
            jpegEncoder.encode(challenge);
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        } catch (CaptchaServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        // flush it in the response
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = response.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
    }

}
