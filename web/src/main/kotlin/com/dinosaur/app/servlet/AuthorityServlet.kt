package com.dinosaur.app.servlet

import com.google.inject.Singleton
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Singleton
class AuthorityServlet: HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    override fun service(req: ServletRequest?, res: ServletResponse?) {
        if (!(req is HttpServletRequest &&
                        res is HttpServletResponse)) {
            throw ServletException("non-HTTP request or response")
        }
        res.writer.println("AuthorityServlet")
    }
}