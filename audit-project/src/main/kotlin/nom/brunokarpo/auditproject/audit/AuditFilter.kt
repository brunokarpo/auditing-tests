package nom.brunokarpo.auditproject.audit

import org.apache.logging.log4j.util.Strings
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.context.support.SpringBeanAutowiringSupport
import java.io.IOException
import javax.servlet.*
import javax.servlet.http.HttpServletRequest


@Component
class AuditFilter (val auditProcessor: AuditRecordService): Filter {

    private var log = LoggerFactory.getLogger(AuditFilter::class.java)

    override fun init(filterConfig: FilterConfig) {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                filterConfig.servletContext)
    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {

        when (request) {
            is HttpServletRequest ->
                when (request.requestURI) {
                    "/access_token" -> chain.doFilter(request, response)
                    else -> audit(request, chain, response)
                }
            else -> chain.doFilter(request, response)
        }
    }

    private fun audit(request: HttpServletRequest, chain: FilterChain, response: ServletResponse) {
        val method = request.method
        val endpoint = request.requestURI
        val parameters = request.queryString
        val wrappedRequest = ResettableStreamHttpServletRequest(request)

        val bodyContent: String? = when (method) {
            "GET" -> null
            else -> {
                val body = wrappedRequest.reader.lineSequence().joinToString(", ")
                wrappedRequest.resetInputStream()
                body.takeIf { Strings.isNotBlank(it) }
            }
        }



        val user: String = resolveUsername()
        val ipAddr: String = request.remoteAddr

        auditProcessor.process(user, ipAddr, method, endpoint, parameters, bodyContent)

        if (log.isInfoEnabled) {
            log.debug("AUDIT {} called endpoint {} {} with {} and body {}",
                    user,
                    method,
                    endpoint,
                    parameters,
                    bodyContent
            )
        }

        chain.doFilter(wrappedRequest, response)
    }

    private fun resolveUsername(): String {

        return "Anonymous User"
    }

    override fun destroy() {
        //We don't need to do anything different when destroying this filter
    }
}
