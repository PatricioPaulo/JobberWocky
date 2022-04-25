package com.jobberwocky.demo.persistance.repository

interface EntityManagerRepository<T,I> {
    fun insert(t: T): T
    fun update(t: T): T
    fun findAll(): List<T>?
    fun getById(id: I) : T?
    fun delete(id: I): T
}