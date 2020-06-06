package cn.hao.nb.cloud.common.component.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @Auther: hao
 * @Date: 2019-12-03 11:05
 * @Description:
 */
@Component
public class RestTemplateConfig {
    @Bean("restTemplate")
    @LoadBalanced
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

//        //换上fastjson
//        List<HttpMessageConverter<?>> httpMessageConverterList = restTemplate.getMessageConverters();
//        Iterator<HttpMessageConverter<?>> iterator = httpMessageConverterList.iterator();
//        if (iterator.hasNext()) {
//            HttpMessageConverter<?> converter = iterator.next();
//            //原有的String是ISO-8859-1编码 去掉
//            if (converter instanceof StringHttpMessageConverter) {
//                iterator.remove();
//            }
//        }
//        httpMessageConverterList.add(new StringHttpMessageConverter(Charset.forName("utf-8")));
//        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
//        FastJsonConfig fastJsonConfig = new FastJsonConfig();
//        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature
//                .WriteNullListAsEmpty, SerializerFeature.DisableCircularReferenceDetect);
//
//        List<MediaType> supportedMediaTypes = new ArrayList<>();
//        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
//        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
//        supportedMediaTypes.add(MediaType.APPLICATION_ATOM_XML);
//        supportedMediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
//        supportedMediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
//        supportedMediaTypes.add(MediaType.APPLICATION_PDF);
//        supportedMediaTypes.add(MediaType.APPLICATION_RSS_XML);
//        supportedMediaTypes.add(MediaType.APPLICATION_XHTML_XML);
//        supportedMediaTypes.add(MediaType.APPLICATION_XML);
//        supportedMediaTypes.add(MediaType.IMAGE_GIF);
//        supportedMediaTypes.add(MediaType.IMAGE_JPEG);
//        supportedMediaTypes.add(MediaType.IMAGE_PNG);
//        supportedMediaTypes.add(MediaType.TEXT_EVENT_STREAM);
//        supportedMediaTypes.add(MediaType.TEXT_HTML);
//        supportedMediaTypes.add(MediaType.TEXT_MARKDOWN);
//        supportedMediaTypes.add(MediaType.TEXT_PLAIN);
//        supportedMediaTypes.add(MediaType.TEXT_XML);
//
//        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
//        fastJsonHttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
//        httpMessageConverterList.add(0, fastJsonHttpMessageConverter);
//
//        //设置超时
//        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
//        requestFactory.setConnectTimeout(60 * 1000);
//        requestFactory.setReadTimeout(60 * 1000);
//
//        restTemplate.setRequestFactory(requestFactory);

        return restTemplate;
    }
}
