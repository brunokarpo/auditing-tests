package nom.brunokarpo.auditproject.audit

import java.time.LocalDateTime

data class AuditRecord(

        var id: Long = 0,

        var ipAddr: String,

        var date: LocalDateTime,

        var user: String,

        var endpoint: String,

        var method: String,

        var params: String?,

        var body: String?
)