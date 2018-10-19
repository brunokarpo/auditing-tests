package nom.brunokarpo.auditproject.audit

interface AuditRecordRepository {

    fun save(auditRecord: AuditRecord)
}