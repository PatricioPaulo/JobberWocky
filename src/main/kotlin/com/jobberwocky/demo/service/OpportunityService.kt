package com.jobberwocky.demo.service

import com.jobberwocky.demo.dao.OpportunityDao
import com.jobberwocky.demo.persistance.entities.Opportunity
import com.jobberwocky.demo.persistance.repository.EntityManagerRepository
import com.jobberwocky.demo.utils.update
import org.springframework.dao.DuplicateKeyException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class OpportunityService(private val opportunityDao: OpportunityDao) : EntityManagerRepository<Opportunity, Long> {

   /* private val opportunities : MutableSet<Opportunity> = mutableSetOf(
            Opportunity(1,"Avature","FrontEnd Dev", "Mendoza", "Description"),
            Opportunity(2,"Avature","BackEnd Dev", "Buenos Aires", "Description")
    )*/


    override fun findAll() : List<Opportunity> = this.opportunityDao.findAll()

    override fun insert(entity: Opportunity): Opportunity {
        return if(!this.opportunityDao.existsById(entity.id)) this.opportunityDao.save(entity)
        else throw DuplicateKeyException("${entity.position}, this opportunity already exist.")

    }

    override fun update(entity: Opportunity): Opportunity {
        return if(this.opportunityDao.existsById(entity.id)) this.opportunityDao.save(entity)
        else throw EntityNotFoundException("${entity.position} does not exist.")
    }

    override fun getById(id: Long): Opportunity? = this.opportunityDao.findByIdOrNull(id)

    override fun delete(id: Long): Opportunity {
        val opp = this.getById(id)
        return if(opp!=null){
            opportunityDao.delete(opp)
            opp
        }
        else throw EntityNotFoundException("$id does not exist.")
    }

    fun getOpportunities(company: String?, position: String?, location: String?): List<Opportunity?> {
        val opps = this.findAll()
        return opps.filter { o -> (company.isNullOrEmpty() || o.company.contains(company))
                        && (position.isNullOrEmpty() || o.position.contains(position))
                        && (location.isNullOrEmpty() || o.location.contains(location))}

    }
}