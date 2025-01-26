package com.community.global.config;

import java.util.Optional;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

import com.community.global.config.security.filter.JwtAuthenticationFilter;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {
	private final ApplicationContext applicationContext;

	private Info apiInfo(String appVersion) {
		return new Info()
			.title("ShareDuck Server Swagger")
			.version(appVersion)
			.description("[USER] : 사용자 권한 User <br> [ALL] 비로그인 사용자");
	}

	@Bean
	public OpenAPI openAPI(@Value("${springdoc.version}") String appVersion) {
		SecurityScheme bearAuth = new SecurityScheme().name("Bearer Authentication")
			.type(SecurityScheme.Type.HTTP)
			.bearerFormat("JWT")
			.scheme("Bearer");
		SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearAuth");
		return new OpenAPI()
			.components(new Components().addSecuritySchemes("bearAuth", bearAuth))
			.addSecurityItem(securityRequirement)
			.info(apiInfo(appVersion));
	}

	@Bean
	public OpenApiCustomizer authLoginOpenApi() {
		FilterChainProxy filterChainProxy = applicationContext.getBean(
			AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME, FilterChainProxy.class);
		return openAPI -> {
			for (SecurityFilterChain filterChain : filterChainProxy.getFilterChains()) {
				Optional<JwtAuthenticationFilter> optionalFilter =
					filterChain.getFilters().stream()
						.filter(JwtAuthenticationFilter.class::isInstance)
						.map(JwtAuthenticationFilter.class::cast)
						.findAny();
				if (optionalFilter.isPresent()) {
					Operation operation = new Operation();
					Schema<?> schema = new ObjectSchema()
						.addProperty("email", new StringSchema())
						.addProperty("password", new StringSchema());
					RequestBody requestBody = new RequestBody().content(
						new Content().addMediaType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE,
							new MediaType().schema(schema)));
					operation.requestBody(requestBody);
					ApiResponses apiResponses = new ApiResponses();
					apiResponses.addApiResponse(String.valueOf(HttpStatus.OK.value()),
						new ApiResponse().description(HttpStatus.OK.getReasonPhrase()));
					apiResponses.addApiResponse(String.valueOf(HttpStatus.BAD_REQUEST.value()),
						new ApiResponse().description(HttpStatus.BAD_REQUEST.getReasonPhrase()));
					operation.responses(apiResponses);
					operation.addTagsItem("AUTH");
					PathItem pathItem = new PathItem().post(operation);
					openAPI.getPaths().addPathItem("/api/login", pathItem);
				}
			}
		};
	}

	@Bean
	public GroupedOpenApi apiGroup() {
		String[] pathToMatch = {
			"/api/v1/**",
			"/api/**"
		};
		String[] pathToExclude = {};
		return commonApi()
			.group("All")
			.pathsToMatch(pathToMatch)
			.pathsToExclude(pathToExclude)
			.build();
	}

	@Bean
	public GroupedOpenApi apiUserGroup() {
		String[] pathToMatch = {
			"/api/v1/users/**"
		};
		String[] pathToExclude = {};
		return commonApi()
			.group("_USER")
			.pathsToMatch(pathToMatch)
			.pathsToExclude(pathToExclude)
			.build();
	}

	@Bean
	public GroupedOpenApi apiCategoryGroup() {
		String[] pathToMatch = {
			"/api/categories/**"
		};
		String[] pathToExclude = {};
		return commonApi()
			.group("_CATEGORY")
			.pathsToMatch(pathToMatch)
			.pathsToExclude(pathToExclude)
			.build();
	}

	@Bean
	public GroupedOpenApi apiMemoGroup() {
		String[] pathToMatch = {
			"/api/memos/**"
		};
		String[] pathToExclude = {};
		return commonApi()
			.group("_MEMO")
			.pathsToMatch(pathToMatch)
			.pathsToExclude(pathToExclude)
			.build();
	}

	@Bean
	public GroupedOpenApi apiPostGroup() {
		String[] pathToMatch = {
			"/api/posts/**"
		};
		String[] pathToExclude = {};
		return commonApi()
			.group("_POST")
			.pathsToMatch(pathToMatch)
			.pathsToExclude(pathToExclude)
			.build();
	}

	@Bean
	public GroupedOpenApi apiUploadGroup() {
		String[] pathToMatch = {
			"/api/upload/**"
		};
		String[] pathToExclude = {};
		return commonApi()
			.group("_UPLOAD")
			.pathsToMatch(pathToMatch)
			.pathsToExclude(pathToExclude)
			.build();
	}

	private GroupedOpenApi.Builder commonApi() {
		return GroupedOpenApi.builder()
			.addOpenApiCustomizer(authLoginOpenApi());
	}
}

