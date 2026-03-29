package com.inepex.ineFrame.server.di.jpa;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import com.google.inject.persist.UnitOfWork;

/**
 * edited copy from PersistFilter
 *
 */
@Singleton
public final class InePersistFilter implements Filter {

    private final UnitOfWork unitOfWork;

    @Inject
    public InePersistFilter(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public void doFilter(
        final ServletRequest servletRequest,
        final ServletResponse servletResponse,
        final FilterChain filterChain) throws IOException, ServletException {

        unitOfWork.begin();
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            unitOfWork.end();
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}
