package com.oscar

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.RedisTemplate
import kotlin.system.measureTimeMillis


@SpringBootTest
class PersonRepositoryTest(){
    @Test
    fun test() {
        assertEquals(true, true)
    }
//    @Autowired
//    lateinit var personRepository: PersonRepository<Person, Int>
//
    @Autowired
    lateinit var redisTemplate: RedisTemplate<String, String>


    @BeforeEach
    fun setup() {
        (1..1000).forEach{
            redisTemplate.opsForList().leftPush("TESTLIST", "VALUE $it")
        }
    }

    @Test
    fun testRepository() = runTest {

        val jobs = mutableListOf<Job>()
        repeat(10) {
            jobs.add(launch(Dispatchers.IO) {
                do {
                    val aThing = redisTemplate.opsForList().rightPop("TESTLIST")
//                    println("$aThing ")
                } while (aThing != null)
            })
        }

        val elapsedTime = measureTimeMillis {
            jobs.joinAll()
        }
        println("It took $elapsedTime ms")
    }
}