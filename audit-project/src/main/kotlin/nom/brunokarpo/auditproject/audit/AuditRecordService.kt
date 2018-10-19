package nom.brunokarpo.auditproject.audit

import kotlinx.coroutines.experimental.launch
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class AuditRecordService (private val auditRepo: AuditRecordRepository) {

    private fun saveAuditRecord(auditRecord: AuditRecord) {
        auditRepo.save(auditRecord)
    }

    fun process(user: String, ipAddr: String, method: String, endpoint: String, parameters: String?, bodyContent: String?) {
        val newAuditRecord = AuditRecord(
                ipAddr = ipAddr,
                date = LocalDateTime.now(),
                user = user,
                endpoint = endpoint,
                method =  method,
                params = parameters,
                body = bodyContent
        )

        launch {
            saveAuditRecord(newAuditRecord)
        }
    }
}