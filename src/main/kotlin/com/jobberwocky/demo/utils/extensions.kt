package com.jobberwocky.demo.utils

import com.jobberwocky.demo.persistance.entities.Opportunity

fun <E> MutableSet<E>.update(element: E, oldElement : E):Boolean{
    return this.remove( oldElement) && this.add(element)
}