package ua.kpi.its.lab.rest.config

import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.function.RouterFunction
import org.springframework.web.servlet.function.router
import ua.kpi.its.lab.rest.dto.ExampleDto
import java.text.SimpleDateFormat

@Configuration
@EnableWebMvc
class WebConfig : WebMvcConfigurer {

    override fun configureMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {
        val builder = Jackson2ObjectMapperBuilder()
            .indentOutput(true)
            .dateFormat(SimpleDateFormat("yyyy-MM-dd"))
            .modulesToInstall(KotlinModule.Builder().build())

        converters
            .add(MappingJackson2HttpMessageConverter(builder.build()))
    }

    @Bean
    fun functionalRoutes(): RouterFunction<*> = router {
        "/fn".nest {
            "/example".nest {
                GET("") {
                    ok().body(ExampleDto("example"))
                }
            }

        }
    }
}