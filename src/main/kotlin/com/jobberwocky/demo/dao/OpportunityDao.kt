package com.jobberwocky.demo.dao

import com.jobberwocky.demo.persistance.entities.Opportunity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OpportunityDao:JpaRepository<Opportunity,Long>