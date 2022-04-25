package com.jobberwocky.demo.controllers

import com.jobberwocky.demo.persistance.entities.Opportunity
import com.jobberwocky.demo.service.OpportunityService
import com.jobberwocky.demo.utils.EntityValidator
import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.inject.Inject
import javax.validation.Valid

@RestController
@RequestMapping("/v1/opportunity")
class OpportunityController(private val opportunityService : OpportunityService) {

    @Inject
    var validator : EntityValidator<Opportunity>? = null

    @ApiOperation("Get all opportunities")
    @GetMapping
    fun findAll()= opportunityService.findAll()

    @ApiOperation("Get an opportunity by Id")
    @GetMapping("/{id}")
    fun findById(@PathVariable id:Long): ResponseEntity<Opportunity> {
        val opportunity = opportunityService.getById(id)
        println(HttpStatus.NO_CONTENT)
        return ResponseEntity.status(if(opportunity!=null) HttpStatus.OK else HttpStatus.NO_CONTENT)
                .body(opportunity)
    }

    @ApiOperation("Get opportunities by params")
    @GetMapping("/opp")
    fun getOpportunities(
            @RequestParam(required = false) company: String?,
            @RequestParam(required = false) position: String?,
            @RequestParam(required = false) location: String?,
    ):
            ResponseEntity<List<Opportunity?>> {
        val opps = opportunityService.getOpportunities(company, position, location)
        return ResponseEntity.status(if(opps.isNotEmpty()) HttpStatus.OK else HttpStatus.NO_CONTENT)
                .body(opps)
    }

    @ApiOperation("Save a new opportunity")
    @PostMapping
    fun save(@Valid @RequestBody opportunity: Opportunity)= ResponseEntity.status(HttpStatus.CREATED).body(this.opportunityService.insert(opportunity))

    @ApiOperation("Update an existing opportunity")
    @PutMapping
    fun update(@RequestBody opportunity: Opportunity)= ResponseEntity.status(HttpStatus.OK).body(this.opportunityService.update(opportunity))

    @ApiOperation("Delete opportunity by Id")
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = ResponseEntity.status(HttpStatus.OK).body(this.opportunityService.delete(id))
}