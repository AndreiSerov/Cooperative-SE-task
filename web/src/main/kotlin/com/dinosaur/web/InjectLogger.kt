package com.dinosaur.web

import com.google.inject.MembersInjector
import com.google.inject.TypeLiteral
import com.google.inject.spi.TypeEncounter
import com.google.inject.spi.TypeListener
import java.lang.reflect.Field
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.FIELD
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.kotlin.KotlinLogger
import org.apache.logging.log4j.spi.ExtendedLogger

@Target(FIELD)
@Retention(RUNTIME)
annotation class InjectLogger {
    /*
    *Custom annotation for bind logger injection in servlets
    * */
    class Log4JMembersInjector<T>(private val field: Field) : MembersInjector<T> {
        private val logger: KotlinLogger = KotlinLogger(
                LogManager.getLogger(field.declaringClass) as ExtendedLogger
        )

        override fun injectMembers(t: T) {
            try {
                field.set(t, logger)
            } catch (e: IllegalAccessException) {
                throw RuntimeException(e)
            }
        }

        init {
            field.isAccessible = true
        }
    }

    object Log4JTypeListener : TypeListener {
        override fun <T> hear(typeLiteral: TypeLiteral<T>, typeEncounter: TypeEncounter<T>) {
            var clazz: Class<*>? = typeLiteral.rawType
            while (clazz != null) {
                for (field in clazz.declaredFields) {
                    if (field.type == KotlinLogger::class.java &&
                            field.isAnnotationPresent(InjectLogger::class.java)) {
                        typeEncounter.register(Log4JMembersInjector(field))
                    }
                }
                clazz = clazz.superclass
            }
        }
    }
}
