package com.jobberwocky.demo.persistance.entities

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.Min
import javax.validation.constraints.Size

@Entity
@Table(name = "opportunity")
data class Opportunity(

        @Id
        @get:Min(1)
        var id: Long,

        @get:Size(min=3,max=20, message = "Company name incorrect")
        var company: String,

        @get:Size(min=3,max=20, message = "Position incorrect")
        var position: String,

        @get:Size(min=3,max=20, message = "Location incorrect")
        var location: String,
        var description:String
)