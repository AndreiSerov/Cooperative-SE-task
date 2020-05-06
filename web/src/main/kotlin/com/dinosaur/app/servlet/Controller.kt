package com.dinosaur.app.servlet

import com.google.inject.Singleton
import java.net.URLEncoder.encode
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Singleton
class Controller: HttpServlet() {

    override fun doGet(req: HttpServletRequest,res: HttpServletResponse) {
        // TODO check cases "get123"
        if (!req.requestURL.contains("/echo/get")) {
            res.sendError(404, "Page not found. GET")
        } else {
            val id = req.getParameter("id")
            req.setAttribute("id", id)
            req.getRequestDispatcher("../res.jsp")
                    .forward(req, res)
        }
    }

    override fun doPost(req: HttpServletRequest, res: HttpServletResponse) {
        if (!req.requestURL.contains("/echo/post")) {
            res.sendError(404, "Page not found. POST")
        } else {
            val id = req.getParameter("input")
            res.sendRedirect("get?id=${encode(id, Charsets.UTF_8)}")
        }
    }
}
