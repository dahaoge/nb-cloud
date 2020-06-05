package cn.hao.nb.cloud.common.component.config.errorConfig;

import cn.hao.nb.cloud.common.entity.Rv;
import cn.hao.nb.cloud.common.penum.EErrorCode;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: hao
 * @Date: 2019-12-03 10:55
 * @Description:
 */
@Slf4j
@Api(description = "错误提示接口")
@RestController
@RequestMapping("/error")
public class ErrorController {

    @Value("${spring.application.name}")
    private String appName;

    @ResponseBody
    @RequestMapping(value = "/400")
    public Rv error_400() {
        log.info(appName.concat(":400"));
        return Rv.getInstance(EErrorCode.missingArg, null);
    }

    @ResponseBody
    @RequestMapping(value = "/401")
    public Rv error_401() {
        log.info(appName.concat(":401"));
        return Rv.getInstance(EErrorCode.authErr, null);
    }

    @ResponseBody
    @RequestMapping(value = "/403")
    public Rv error_403() {
        log.info(appName.concat(":403"));
        return Rv.getInstance(EErrorCode.authDenied, null);
    }

    @ResponseBody
    @RequestMapping(value = "/404")
    public Rv error_404() {
        log.info(appName.concat(":404"));
        return Rv.getInstance(EErrorCode.c404, null);
    }

    @ResponseBody
    @RequestMapping(value = "/500")
    public Rv error_500() {
        log.info(appName.concat(":500"));
        return Rv.getInstance(EErrorCode.c500, null);
    }

    @ResponseBody
    @RequestMapping("nb")
    public Rv pang(HttpServletRequest request, HttpServletResponse response) {
        String errorCode = request.getParameter("errorCode");
        String msg = request.getParameter("msg");
        return Rv.getInstance(EErrorCode.valueOf(errorCode), msg, null);
    }

    @ResponseBody
    @RequestMapping(value = "/basePathDefaultResponse")
    public Rv error_basePathDefaultResponse(HttpServletRequest request, HttpServletResponse response) {
        return Rv.getInstance("hellow world!");
    }
}
