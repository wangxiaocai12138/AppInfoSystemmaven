package cn.appsys.tools;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@EnableWebMvc
@EnableSwagger2/*启用swagger2*/
@ComponentScan(basePackages = {"cn.appsys.controller"})/*扫描controller包*/
@Configuration/*自动本类上下加载环境信息*/
public class SwaggerConfig extends WebMvcConfigurationSupport {

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()/*控制那些接口不被暴露出来，可以暴露出测试通过的接口给前端调用*/
				.apis(RequestHandlerSelectors.any())/*会扫描相应包下的swagger注解的api*/
				.paths(PathSelectors.any())
				.build();
	}
	/*api文档的基本信息*/
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("App信息管理平台")
				.termsOfServiceUrl("http://localhost:8888/AppInfoSystem-maven")
				.contact("App信息")
				.version("1.0")
				.build();
	}
}