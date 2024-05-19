package ua.kpi.its.lab.rest.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import ua.kpi.its.lab.rest.dto.ExampleDto

@RestController
class ExampleController {
    @GetMapping("/example")
    fun example(): ExampleDto = ExampleDto("example")
}
// Your code here