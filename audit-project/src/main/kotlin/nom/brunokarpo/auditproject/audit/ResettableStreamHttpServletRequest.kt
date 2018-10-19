package nom.brunokarpo.auditproject.audit

import org.apache.commons.io.IOUtils
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.InputStreamReader
import java.nio.charset.Charset
import javax.servlet.ServletInputStream
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletRequestWrapper

class ResettableStreamHttpServletRequest(private var originalRequest: HttpServletRequest) : HttpServletRequestWrapper(originalRequest) {

    private val defaultCharset: Charset = Charset.forName("UTF-8")
    private val servletStream: ResettableServletInputStream = ResettableServletInputStream()

    private var rawData: ByteArray? = null

    fun resetInputStream() {
        if (rawData != null) {
            servletStream.setStream(ByteArrayInputStream(rawData!!))
        }
    }

    override fun getInputStream(): ServletInputStream {
        if (rawData == null) {
            rawData = IOUtils.toByteArray(this.originalRequest.reader, defaultCharset)
            servletStream.setStream(ByteArrayInputStream(rawData!!))
        }
        return servletStream
    }

    override fun getReader(): BufferedReader {
        if (rawData == null) {
            rawData = safeReadRequestData()
            servletStream.setStream(ByteArrayInputStream(rawData!!))
        }
        return BufferedReader(InputStreamReader(servletStream, defaultCharset))
    }

    private fun safeReadRequestData(): ByteArray {
        return try {
            IOUtils.toByteArray(this.originalRequest.reader, defaultCharset)
        } catch (ies: IllegalStateException) {
            ByteArray(0)
        }
    }


}