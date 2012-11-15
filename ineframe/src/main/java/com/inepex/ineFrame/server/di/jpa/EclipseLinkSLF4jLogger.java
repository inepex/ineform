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

	@Override
	public void log(SessionLogEntry sessionLogEntry) {
		switch (sessionLogEntry.getLevel()) {
		case SEVERE:
			if(LOG.isErrorEnabled()) {
				if(sessionLogEntry.getException()!=null)
					LOG.error("exeption"+sessionLogEntry.getException());
				else
					LOG.error(formatMessage(sessionLogEntry));
			}
			break;
		case WARNING:
			if(LOG.isWarnEnabled()) {
				if(sessionLogEntry.getException()!=null)
					LOG.warn("exeption"+sessionLogEntry.getException());
				else
					LOG.warn(formatMessage(sessionLogEntry));
			}
			break;
		case INFO:
			if(LOG.isInfoEnabled()) {
				if(sessionLogEntry.getException()!=null)
					LOG.info("exeption"+sessionLogEntry.getException());
				else
					LOG.info(formatMessage(sessionLogEntry));
			}
			break;
		default:
			if(LOG.isDebugEnabled()) {
				if(sessionLogEntry.getException()!=null)
					LOG.debug("exeption"+sessionLogEntry.getException());
				else
					LOG.debug(formatMessage(sessionLogEntry));
			}
		}
	}

}
