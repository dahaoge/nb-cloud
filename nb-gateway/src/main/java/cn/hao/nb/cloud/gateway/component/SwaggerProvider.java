package cn.hao.nb.cloud.gateway.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: hao
 * @Date: 2019-12-09 16:41
 * @Description:
 */
@Component
@Primary
public class SwaggerProvider implements SwaggerResourcesProvider {
    public static final String API_URI = "/v2/api-docs";

    public static final String ROUTE_DEFINITION_ID_PREFIX = "CompositeDiscoveryClient_";

    @Value("${spring.application.name}")
    public String applicationName;

    private final DiscoveryClientRouteDefinitionLocator routeLocator;

    public SwaggerProvider(DiscoveryClientRouteDefinitionLocator routeLocator) {

        this.routeLocator = routeLocator;

    }

    @Override
    public List<SwaggerResource> get() {

        List<SwaggerResource> resources = new ArrayList<>();

        List<String> routes = new ArrayList<>();

        //从DiscoveryClientRouteDefinitionLocator 中取出routes，构造成swaggerResource

        routeLocator.getRouteDefinitions().subscribe(routeDefinition -> {

            if (routeDefinition.getId().indexOf(this.applicationName) < 0)
                resources.add(swaggerResource(routeDefinition.getId().substring(ROUTE_DEFINITION_ID_PREFIX.length()), routeDefinition.getPredicates().get(0).getArgs().get("pattern").replace("/**", API_URI)));

        });

        return resources;

    }

    private SwaggerResource swaggerResource(String name, String location) {

        SwaggerResource swaggerResource = new SwaggerResource();

        swaggerResource.setName(name);

        swaggerResource.setLocation(location);

        swaggerResource.setSwaggerVersion("2.0");

        return swaggerResource;

    }

}
