package com.crm.api_gateway;

import com.crm.api_gateway.config.GatewayRouteConfig;
import com.crm.api_gateway.security.JwtAuthenticationFilter;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

class GatewayRouteConfigTest {

    @Test
    void routeLocatorShouldConfigureRoutes() {
        // Arrange
        RouteLocatorBuilder builder = Mockito.mock(RouteLocatorBuilder.class);
        RouteLocatorBuilder.Builder routesBuilder = Mockito.mock(RouteLocatorBuilder.Builder.class);
        JwtAuthenticationFilter jwtFilter = Mockito.mock(JwtAuthenticationFilter.class);

        Mockito.when(builder.routes()).thenReturn(routesBuilder);
        Mockito.when(routesBuilder.route(Mockito.any())).thenReturn(routesBuilder);
        Mockito.when(routesBuilder.build()).thenReturn(Mockito.mock(RouteLocator.class));

        GatewayRouteConfig config = new GatewayRouteConfig();

        // Act
        RouteLocator routeLocator = config.routeLocator(builder, jwtFilter);

        // Assert
        assertThat(routeLocator).isNotNull();
        Mockito.verify(builder).routes();
        Mockito.verify(routesBuilder).route(Mockito.any());
        Mockito.verify(routesBuilder).build();
    }
}