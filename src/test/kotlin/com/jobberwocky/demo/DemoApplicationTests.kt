package com.jobberwocky.demo

import com.fasterxml.jackson.databind.ObjectMapper
import com.jobberwocky.demo.extensions.body
import com.jobberwocky.demo.extensions.bodyTo
import com.jobberwocky.demo.persistance.entities.Opportunity
import com.jobberwocky.demo.service.OpportunityService
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import kotlin.random.Random


@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private lateinit var webApplicationContext : WebApplicationContext

	private val mockMvc : MockMvc by lazy{
		MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.alwaysDo<DefaultMockMvcBuilder>(MockMvcResultHandlers.print()).build()
	}

	@Autowired
	private lateinit var mapper: ObjectMapper

	@Autowired
	private lateinit var  opportunityService: OpportunityService

	private val ENDPOINT = "/v1/opportunity"

	@Test
	@Order(3)
	fun findAll() {

		val opportunities: List<Opportunity> =  mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT))
				.andExpect(status().isOk)
				.bodyTo(mapper)

		assert(opportunities.isNotEmpty())
	}

	@Test
	fun findById(){
		val opportunities  = opportunityService.findAll()
		assert(opportunities.isNotEmpty()){"Should not be empty"}

		val opportunityTest = opportunities.first()

		mockMvc.perform(MockMvcRequestBuilders.get("$ENDPOINT/${opportunityTest.id}"))
				.andExpect(status().isOk)
				.andExpect(jsonPath("$.position", Matchers.`is`(opportunityTest.position)))
	}

	@Test
	fun findByIdEmpty(){
		mockMvc.perform(MockMvcRequestBuilders.get("$ENDPOINT/${Random.nextLong()}"))
				.andExpect(status().isNoContent)
				.andExpect(jsonPath("$").doesNotExist())
	}

	@Test
	fun saveSuccessfully(){
		val newOpportunity = Opportunity(3, "Avature", "Analyst QA", "Córdoba", "Description for QA")

		val result: Opportunity = mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT)
				.body(data = newOpportunity, mapper = mapper))
				.andExpect(status().isCreated)
				.bodyTo(mapper)

		assert(result.position == newOpportunity.position)
	}

	@Test
	fun saveFail(){
		val opportunities  = opportunityService.findAll()
		assert(opportunities.isNotEmpty()){"Should not be empty"}
		val opp = opportunities.first()

		 mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT)
				.body(data = opp , mapper = mapper))
				.andExpect(status().isConflict)
	}

	@Test
	fun updateSuccessfully(){
		val opportunities  = opportunityService.findAll()
		assert(opportunities.isNotEmpty()){"Should not be empty"}
		var opportunityTest = opportunities.first()
		opportunityTest.location = "Rosario"

		val result : Opportunity = mockMvc.perform(MockMvcRequestBuilders.put(ENDPOINT)
				.body(data=opportunityTest, mapper =mapper))
				.andExpect(status().isOk)
				.bodyTo(mapper)

		assertThat(opportunityTest, Matchers.`is`(result))
	}

	@Test
	fun updateFail(){
		val opportunityTest = Opportunity( Random.nextLong(), "Avature", "QA", "Rosario", "Description" )

		mockMvc.perform(MockMvcRequestBuilders.put(ENDPOINT)
				.body(data=opportunityTest, mapper =mapper))
				.andExpect(status().isConflict)
	}

	@Test
	fun deleteById(){
		val opportunities  = opportunityService.findAll()
		assert(opportunities.isNotEmpty()){"Should not be empty"}
		val opportunityTest = opportunities.last()


		mockMvc.perform(MockMvcRequestBuilders.delete("$ENDPOINT/${opportunityTest.id}"))
				.andExpect(status().isOk)

		assert(!opportunityService.findAll().contains(opportunityTest))
	}

	@Test
	fun deleteFailed(){
		 mockMvc.perform(MockMvcRequestBuilders.delete("$ENDPOINT/${Random.nextLong()}"))
				.andExpect(status().isConflict)
	}
}
