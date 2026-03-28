package com.inepex.ineFrame.server.di.jpa;

/**
 * This class was previously an EclipseLink SessionLog bridge to SLF4J.
 * With the migration to Hibernate, this is no longer needed.
 * Hibernate uses JBoss Logging which automatically bridges to SLF4J.
 *
 * @deprecated No longer needed with Hibernate. Will be removed in a future version.
 */
@Deprecated
public class EclipseLinkSLF4jLogger {
    // Intentionally empty - kept for backward compatibility during migration
}
