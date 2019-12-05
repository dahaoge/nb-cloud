package cn.hao.nb.cloud.common.component.config.errorConfig;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * @Auther: hao
 * @Date: 2019-12-03 11:00
 * @Description:
 */
@Component
public class ErrorPageConfig implements ErrorPageRegistrar {

    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        ErrorPage[] errorPages = new ErrorPage[]{new ErrorPage(HttpStatus.BAD_REQUEST, "/error/400"), new ErrorPage(HttpStatus.UNAUTHORIZED,
                "/error/401"), new ErrorPage(HttpStatus.FORBIDDEN, "/error/403"), new ErrorPage(HttpStatus.NOT_FOUND, "/error/404")
                , new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500")
                , new ErrorPage(Throwable.class, "/error/500")
        };
        registry.addErrorPages(errorPages);

    }

}
