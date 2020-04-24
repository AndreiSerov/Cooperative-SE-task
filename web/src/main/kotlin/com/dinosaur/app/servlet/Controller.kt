package com.dinosaur.app.servlet

import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "Echo", urlPatterns = ["echo/*", "echo/get", "echo/post"])
class Controller: HttpServlet() {

    override fun doGet(req: HttpServletRequest,res: HttpServletResponse) =
            // TODO check cases "get123"
            if (!req.requestURL.contains("/echo/get")) {
                res.sendError(404, "Page not found. GET")
            } else {
                val id = req.getParameter("id")
                req.setAttribute("id", id)
                req.getRequestDispatcher("../res.jsp")
                        .forward(req, res)
            }

    override fun doPost(req: HttpServletRequest, res: HttpServletResponse) {
        if (!req.requestURL.contains("/echo/post")) {
            res.sendError(404, "Page not found. POST")
        } else {
            val id = req.getParameter("input")
            res.sendRedirect("get?id=$id")
        }
    }
}
