package com.jobberwocky.demo.config

import com.jobberwocky.demo.persistance.entities.Opportunity
import com.jobberwocky.demo.service.OpportunityService
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class OnBoot(private val opportunityService: OpportunityService) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        opportunityService.insert(Opportunity(1,"Avature","FrontEnd Dev", "Buenos Aires", "Description"))
    }
}