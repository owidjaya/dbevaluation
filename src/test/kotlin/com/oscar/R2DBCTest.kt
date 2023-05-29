package com.oscar

import com.oscar.data.Person
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.relational.core.query.Criteria.where
import org.springframework.data.relational.core.query.Query.query
import org.springframework.data.relational.core.query.Update.update
import org.springframework.data.relational.core.query.isEqual
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.await
import org.springframework.transaction.ReactiveTransactionManager
import org.springframework.transaction.reactive.TransactionalOperator
import org.springframework.transaction.reactive.executeAndAwait

@SpringBootTest
class R2DBCTest {
    @Autowired
    lateinit var db: DatabaseClient

    @Autowired
    lateinit var reactiveTransactionManager: ReactiveTransactionManager


    lateinit var rxtx: TransactionalOperator


    @BeforeEach
    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    fun setup() {
//        rxtx = TransactionalOperator.create(reactiveTransactionManager)
    }

    @Test
    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    fun test1() {
//        repeat(10000) {
//            val aList = mutableListOf<Job>()
//            aList.add(launch(Dispatchers.IO) {
//                db.sql("INSERT INTO tutorial.person(firstname, lastname, processed) VALUES(:firstname, :lastname, :processed)")
//                    .bind("firstname", "oscar$it")
//                    .bind("lastname", "widjaya$it")
//                    .bind("processed", false)
//                    .await()
//            })
////            aList.joinAll()
//        }
        repeat(10000) {
            runTest {
                launch(Dispatchers.IO) {
//            for(i in generateSequence(0) { it }){
                    TransactionalOperator.create(reactiveTransactionManager).executeAndAwait {
                        db.sql("SELECT * FROM tutorial.person WHERE processed = false LIMIT 100 FOR UPDATE SKIP LOCKED")
                        db.sql("SELECT * FROM tutorial.person WHERE processed = false LIMIT 100 FOR UPDATE SKIP LOCKED")
                            .fetch().all().asFlow().collect { row ->
                                return@
                                db.sql("UPDATE tutorial.person SET processed = true WHERE id = :id")
                                    .bind("id", row.get("id")!!).fetch().rowsUpdated().asFlow().collect {
                                        println("from coroutine 1 : $row")
                                    }
                            }
                    }
                }
            }
        }
    }
}