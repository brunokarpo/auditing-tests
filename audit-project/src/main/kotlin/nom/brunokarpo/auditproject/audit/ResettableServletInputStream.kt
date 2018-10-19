package nom.brunokarpo.auditproject.audit

import java.io.IOException
import java.io.InputStream
import javax.servlet.ReadListener
import javax.servlet.ServletInputStream

class ResettableServletInputStream : ServletInputStream() {

    private lateinit var stream: InputStream

    @Throws(IOException::class)
    override fun read(): Int {
        return stream.read()
    }

    override fun isFinished(): Boolean {
        return true
    }

    override fun isReady(): Boolean {
        return true
    }

    override fun setReadListener(readListener: ReadListener) {
        //We don't use this
    }

    fun setStream(stream: InputStream) {
        this.stream = stream
    }
}