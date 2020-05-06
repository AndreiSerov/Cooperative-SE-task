package com.dinosaur.app.servlet

import com.dinosaur.app.servlet.logger.InjectLogger
import com.google.inject.Singleton
import org.apache.logging.log4j.kotlin.KotlinLogger
import java.net.URLEncoder.encode
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Singleton
class Controller: HttpServlet() {
    @InjectLogger
    lateinit var logger: KotlinLogger

    override fun doGet(req: HttpServletRequest,res: HttpServletResponse) {
        // TODO check cases "get123"
        if (!req.requestURL.contains("/echo/get")) {
            logger.error("Wrong Url")
            res.sendError(404, "Page not found. GET")
        } else {
            logger.info("/echo/get")
            val id = req.getParameter("id")
            req.setAttribute("id", id)
            req.getRequestDispatcher("../res.jsp")
                    .forward(req, res)
        }
    }

    override fun doPost(req: HttpServletRequest, res: HttpServletResponse) {
        if (!req.requestURL.contains("/echo/post")) {
            logger.error("Wrong Url")
            res.sendError(404, "Page not found. POST")
        } else {
            logger.info("/echo/post")
            val id = req.getParameter("input")
            res.sendRedirect("get?id=${encode(id, Charsets.UTF_8)}")
        }
    }
}
