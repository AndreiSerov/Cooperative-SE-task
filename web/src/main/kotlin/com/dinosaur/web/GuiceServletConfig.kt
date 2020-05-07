package com.dinosaur.web

import com.dinosaur.web.servlet.ActivityServlet
import com.dinosaur.web.servlet.AuthorityServlet
import com.dinosaur.web.servlet.Controller
import com.dinosaur.web.servlet.UserServlet
import com.google.inject.Guice
import com.google.inject.Injector
import com.google.inject.matcher.Matchers
import com.google.inject.servlet.GuiceServletContextListener
import com.google.inject.servlet.ServletModule


class GuiceServletConfig(): GuiceServletContextListener() {
    override fun getInjector(): Injector {
        return Guice.createInjector(object : ServletModule() {
            override fun configureServlets() {
                super.configureServlets()
                bindListener(Matchers.any(), InjectLogger.Log4JTypeListener)

                serve(
                        "/echo/*",
                        "/echo/get",
                        "/echo/post"
                ).with(Controller::class.java)

                serve("/ajax/user").with(UserServlet::class.java)
                serve("/ajax/authority").with(AuthorityServlet::class.java)
                serve("/ajax/activity").with(ActivityServlet::class.java)
            }
        })
    }

}