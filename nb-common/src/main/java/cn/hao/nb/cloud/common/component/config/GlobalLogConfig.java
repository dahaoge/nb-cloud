package cn.hao.nb.cloud.common.component.config;

import cn.hao.nb.cloud.common.entity.NBException;
import cn.hao.nb.cloud.common.entity.Rv;
import cn.hao.nb.cloud.common.penum.EErrorCode;
import cn.hao.nb.cloud.common.util.HttpUtil;
import cn.hao.nb.cloud.common.util.SpringUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.validation.support.BindingAwareModelMap;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: scootXin
 * @Date: 2018/12/5 15:48
 */
@Slf4j
@Aspect
@Component
public class GlobalLogConfig {

    // 非记录日志
    private final String[] IPACKAGE = new String[]{"request", "session", "response", "file", "byte"};

    @Pointcut("execution(public * cn.hao.nb.cloud..controller.*.*(..))")
    public void logPointCut() {
    }

    @Before("logPointCut()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        HttpServletRequest request = SpringUtil.getRequest();

        StringBuilder msg = new StringBuilder();
        msg.append("received request >> ").append(" url:").append(request.getRequestURL().toString()).append(" || httpMethod:").append(request
                .getMethod()).append(" || ip:").append(HttpUtil.getIpAddress(request)).append(" || handler:").append(joinPoint.getSignature()
                .getDeclaringTypeName() + "." + joinPoint.getSignature().getName()).append("  || Params: ");
        appendObjLog(msg, joinPoint.getArgs());

        log.info(msg.toString());
    }

    @AfterReturning(returning = "ret", pointcut = "logPointCut()")// returning的值和doAfterReturning的参数名一致
    public void doAfterReturning(Object ret) throws Throwable {
        StringBuilder msg = new StringBuilder();
        msg.append("send response >> ");

        if (ret instanceof Rv) {
            Rv rv = (Rv) ret;
            msg.append(" || code:" + rv.getCode()).append(" || msg:").append(rv.getMsg()).append(" || data:");
            appendObjLog(msg, rv.getData());
        } else {
            msg.append(ret == null ? null : ret.getClass());
        }

        log.info(msg.toString());
    }

    @AfterThrowing(throwing = "ex", pointcut = "logPointCut()")
    public void doAfterThrowing(Throwable ex) throws Throwable {
        if (ex instanceof NBException) {
            NBException pex = (NBException) ex;

            if (pex.getErrorCode() == EErrorCode.apiErr || pex.getErrorCode() == EErrorCode.apiArgErr || pex.getErrorCode() == EErrorCode.noData ||
                    pex.getErrorCode() == EErrorCode.c500 || pex.getErrorCode() == EErrorCode.dataParseError || pex.getErrorCode() == EErrorCode
                    .unkown || pex.getErrorCode() == EErrorCode.versionError) {
                log.warn(String.format("controller throw exception >> %s", pex.getMeMessage()), pex);
            } else {
                log.warn(String.format("controller throw exception >> %s - %s", pex.getErrorCode().getValue(), pex.getMeMessage()));
            }
        } else {
            log.error(String.format("controller throw exception >> %s", ex.getMessage()), ex);
        }
    }


    @Around("logPointCut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object ob = pjp.proceed();// ob 为方法的返回值
        log.info("userTime: " + (System.currentTimeMillis() - startTime));
        return ob;
    }

    /**
     * 判断and添加对象日志
     *
     * @param sb
     * @param args
     */
    private void appendObjLog(StringBuilder sb, Object... args) {
        if (args == null || args.length == 0) {
            return;
        }
        outer:
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];

            if (arg == null) {
                continue;
            }

            String className = arg.getClass().getCanonicalName();
            sb.append(" ");
            if (className != null) {
                className = className.toLowerCase();
            }

            //package过滤
            for (String pname : IPACKAGE) {
                if (className == null || className.contains(pname)) {
                    sb.append("{}");
                    continue outer;
                }
                //modelMap
                else if (className.startsWith("org.springframework.validation.support.BindingAwareModelMap")) {
                    BindingAwareModelMap target = (BindingAwareModelMap) arg;
                    sb.append(target.toString());
                    continue outer;
                }
            }

            sb.append(JSON.toJSONString(arg));

            if (sb.length() > 500) {
                sb.setLength(500);
                sb.append("......");
                break;
            }
        }
    }

    /**
     * 输出response
     *
     * @param sb
     * @param args
     */
    private void responseObjLog(StringBuilder sb, Object... args) {
        if (args == null || args.length == 0) {
            return;
        }
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];

            if (arg == null) {
                continue;
            }

            String className = arg.getClass().getCanonicalName();
            sb.append(" ");
            if (className != null) {
                className = className.toLowerCase();
            }
            sb.append(className).append(" => ");

            if (Rv.class.isAssignableFrom(arg.getClass())) {
                Rv rvo = (Rv) arg;

                sb.append("code:").append(rvo.getCode());
                sb.append(" msg:").append(rvo.getMsg());

                if (rvo.getData() instanceof Page) {
                    Page pd = (Page) rvo.getData();
                    sb.append(" total:").append(pd.getTotal());
                    sb.append(" rows:").append(pd.getRecords().size());
                } else if (rvo.getData() instanceof List) {
                    List pd = ((List) rvo.getData());
                    sb.append(" total:").append(pd.size());
                } else {
                    sb.append(rvo.getData() == null ? null : rvo.getData().getClass().getCanonicalName());
                }
            } else {
                sb.append(arg == null ? arg : arg.getClass().getCanonicalName());
            }
        }
    }
}
