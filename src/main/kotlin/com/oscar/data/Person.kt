package com.oscar.data

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("tutorial.person")
data class Person(@Id val id: Int?, val firstname: String, val lastname: String, val processed: Boolean = false)