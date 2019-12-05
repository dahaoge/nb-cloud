package cn.hao.nb.cloud.common.component.config.errorConfig;

import cn.hao.nb.cloud.common.entity.Rv;
import cn.hao.nb.cloud.common.penum.EErrorCode;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: hao
 * @Date: 2019-12-03 10:55
 * @Description:
 */
@Api(description = "错误提示接口")
@RestController
@RequestMapping("/error")
public class ErrorController {

    @ResponseBody
    @RequestMapping(value = "/400")
    public Rv error_400() {
        return Rv.getInstance(EErrorCode.missingArg, null);
    }

    @ResponseBody
    @RequestMapping(value = "/401")
    public Rv error_401() {
        return Rv.getInstance(EErrorCode.authErr, null);
    }

    @ResponseBody
    @RequestMapping(value = "/403")
    public Rv error_403() {
        return Rv.getInstance(EErrorCode.authDenied, null);
    }

    @ResponseBody
    @RequestMapping(value = "/404")
    public Rv error_404() {
        return Rv.getInstance(EErrorCode.c404, null);
    }

    @ResponseBody
    @RequestMapping(value = "/500")
    public Rv error_500() {
        return Rv.getInstance(EErrorCode.c500, null);
    }
}
