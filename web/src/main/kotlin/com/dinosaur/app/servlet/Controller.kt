package com.dinosaur.app.servlet

import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@WebServlet(name = "Echo", value = ["/echo/*"])
class Controller: HttpServlet() {

    override fun doGet(req: HttpServletRequest, res: HttpServletResponse) {
        // TODO check cases "get123"
        if (!req.requestURL.contains("/echo/get")) {
            res.sendError(404, "Page not found")
        }
    }

    override fun doPost(req: HttpServletRequest, res: HttpServletResponse) {
        if (!req.requestURL.contains("/echo/post")) {
            res.sendError(404, "Page not found")
        }
    }
}
