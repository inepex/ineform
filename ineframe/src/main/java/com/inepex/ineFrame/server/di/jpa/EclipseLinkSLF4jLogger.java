package com.inepex.ineFrame.server.di.jpa;

import org.eclipse.persistence.logging.AbstractSessionLog;
import org.eclipse.persistence.logging.SessionLog;
import org.eclipse.persistence.logging.SessionLogEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * add these lines to your persistence.xml to use this class:
 * 
 * 
 * <pre>
 * {@code 
 * <properties>
 * 	<property name="eclipselink.logging.logger"
 * 		value="com.inepex.ineFrame.server.di.jpa.EclipseLinkSLF4jLogger" />
 * </properties>
 * }
 * </pre>
 * 
 * 
 * @author sebi
 */
public class EclipseLinkSLF4jLogger extends AbstractSessionLog implements SessionLog {

    public static final Logger LOG = LoggerFactory.getLogger("EclipseLink");
    public static final Logger SQLLOG = LoggerFactory.getLogger("EclipseLinkSQL");

    @Override
    public void log(SessionLogEntry sessionLogEntry) {
        Logger logger = LOG;
        if (sessionLogEntry.getNameSpace() != null && sessionLogEntry.getNameSpace().equals("sql")) {
            logger = SQLLOG;
        }
        switch (sessionLogEntry.getLevel()) {
            case SEVERE:
                if (logger.isErrorEnabled()) {
                    if (sessionLogEntry.getException() != null)
                        logger.error("exeption", sessionLogEntry.getException());
                    else
                        logger.error(formatMessage(sessionLogEntry));
                }
                break;
            case WARNING:
                if (logger.isWarnEnabled()) {
                    if (sessionLogEntry.getException() != null)
                        logger.warn("exeption", sessionLogEntry.getException());
                    else
                        logger.warn(formatMessage(sessionLogEntry));
                }
                break;
            case INFO:
                if (logger.isInfoEnabled()) {
                    if (sessionLogEntry.getException() != null)
                        logger.info("exeption", sessionLogEntry.getException());
                    else
                        logger.info(formatMessage(sessionLogEntry));
                }
                break;
            default:
                if (logger.isDebugEnabled()) {
                    if (sessionLogEntry.getException() != null)
                        logger.debug("exeption", sessionLogEntry.getException());
                    else
                        logger.debug(formatMessage(sessionLogEntry));
                }
        }
    }

}
