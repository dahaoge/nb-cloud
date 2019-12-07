package cn.hao.nb.cloud.gateway.Util;

import cn.hao.nb.cloud.common.entity.Rv;
import com.alibaba.fastjson.JSONObject;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @Auther: hao
 * @Date: 2019-12-07 10:49
 * @Description:
 */
public class FilterUtil {

    /**
     * 封装返回值
     *
     * @param response
     * @param result
     * @return
     */
    public static DataBuffer getBodyBuffer(ServerHttpResponse response, Rv rv) {
        return response.bufferFactory().wrap(JSONObject.toJSONBytes(rv));
    }

    /**
     * filter返回
     *
     * @param exchange
     * @param rv
     * @return
     */
    public static Mono<Void> resp(ServerWebExchange exchange, Rv rv) {
        return exchange.getResponse().writeWith(Flux.just(FilterUtil.getBodyBuffer(exchange.getResponse(), rv)));
    }
}
