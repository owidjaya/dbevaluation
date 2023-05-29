package com.oscar

import com.oscar.data.Person
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PersonRepository: CoroutineCrudRepository<Person, Int> {}

