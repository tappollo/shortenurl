package com.tappollo.urlshortener.shorten.dao

import org.junit.Assert
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import javax.transaction.Transactional

@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest
class UrlConfigEntityDaoTest(
    @Autowired
    private val urlConfigDao: UrlConfigDao
) {

    @Test
    @Transactional
    @Rollback(true)
    fun testInsertUrlConfig() {
        val entity = UrlConfigEntity("http://localhost", 1L)

        val saved = urlConfigDao.save(entity)

        val first = urlConfigDao.findAll().first()

        Assert.assertEquals(first.id, saved.id)
        Assert.assertEquals(first.createdAt, saved.createdAt)
        Assert.assertEquals(first.targetUrl, saved.targetUrl)
    }

    @Test
    @Transactional
    @Rollback(true)
    fun testFindByUrl() {
        val entity = UrlConfigEntity("http://localhost", 1L)

        val saved = urlConfigDao.save(entity)

        val findByUrl = urlConfigDao.findByUrl("http://localhost")

        Assert.assertEquals(findByUrl?.targetUrl, saved.targetUrl)
        Assert.assertEquals(findByUrl?.createdAt, saved.createdAt)
        Assert.assertEquals(findByUrl?.id, saved.id)
    }

    @Test
    @Transactional
    @Rollback(true)
    fun findById() {
        val entity = UrlConfigEntity("http://localhost", 1L)

        val saved = urlConfigDao.save(entity)

        val optional = urlConfigDao.findById(entity.id ?: 0)

        Assert.assertEquals(optional.isPresent, true)

        val findById = optional.get()

        Assert.assertEquals(findById.targetUrl, saved.targetUrl)
        Assert.assertEquals(findById.createdAt, saved.createdAt)
        Assert.assertEquals(findById.id, saved.id)
    }

    @Test
    @Transactional
    @Rollback(true)
    fun delete() {
        val entity = UrlConfigEntity("http://localhost", 1L)

        val saved = urlConfigDao.save(entity)

        Assert.assertEquals(urlConfigDao.findAll().toList().size, 1)

        urlConfigDao.delete(saved)

        Assert.assertEquals(urlConfigDao.findAll().toList().size, 0)
    }
}