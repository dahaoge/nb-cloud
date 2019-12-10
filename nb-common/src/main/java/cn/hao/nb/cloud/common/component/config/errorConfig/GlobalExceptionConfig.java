package cn.hao.nb.cloud.common.component.config.errorConfig;

import cn.hao.nb.cloud.common.entity.NBException;
import cn.hao.nb.cloud.common.entity.Rv;
import cn.hao.nb.cloud.common.penum.EErrorCode;
import cn.hao.nb.cloud.common.util.CheckUtil;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;


/**
 * @Auther: hao
 * @Date: 2019-12-03 11:01
 * @Description:
 */
@RestControllerAdvice
public class GlobalExceptionConfig {

    /**
     * 默认错误
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Rv handlerException(Exception e) {
        if (e instanceof NoHandlerFoundException) {
            return Rv.getInstance(EErrorCode.noHandlerError, "服务器忙,请稍后再试", ((NoHandlerFoundException)
                    e).getRequestURL());
        } else if (e instanceof NBException) {
            NBException e1 = (NBException) e;

            return Rv.getInstance(e1.getErrorCode(), CheckUtil.strIsEmpty(e1.getMessage()) ? "操作失败" : e1.getMeMessage(), null);
        } else {
            if (e instanceof DuplicateKeyException) {
                return Rv.getInstance(EErrorCode.repeat, "重复的数据", e.getMessage());
            }
            if (e instanceof AccessDeniedException) {
                return Rv.getInstance(EErrorCode.authErr, "权限不足", e.getMessage());
            }
            return Rv.getInstance(EErrorCode.unkown, "服务器忙,请稍后再试", e.getClass().getName());
        }
    }

}
