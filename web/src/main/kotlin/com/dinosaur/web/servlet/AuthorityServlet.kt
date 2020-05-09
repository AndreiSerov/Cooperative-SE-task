package com.dinosaur.web.servlet

import com.dinosaur.web.InjectLogger
import com.google.inject.Singleton
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.apache.logging.log4j.kotlin.KotlinLogger

@Singleton
class AuthorityServlet : HttpServlet() {
    @InjectLogger
    lateinit var logger: KotlinLogger

    @Throws(ServletException::class, IOException::class)
    override fun service(req: ServletRequest?, res: ServletResponse?) {
        if (!(req is HttpServletRequest &&
                        res is HttpServletResponse)) {
            logger.error("Wrong address")
            throw ServletException("non-HTTP request or response")
        }
        logger.info("/ajax/authority")
        val stackTrace = Thread.currentThread().stackTrace

        res.writer.println("Method called from: ${stackTrace[1].className}\n" +
                "\nFull stacktrace:")
        stackTrace.map { res.writer.println(it.className) }
    }
}
