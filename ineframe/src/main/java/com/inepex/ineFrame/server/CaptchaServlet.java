package com.inepex.ineFrame.server;

import static nl.captcha.Captcha.NAME;

import java.awt.Color;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nl.captcha.Captcha;
import nl.captcha.backgrounds.FlatColorBackgroundProducer;
import nl.captcha.backgrounds.GradiatedBackgroundProducer;
import nl.captcha.servlet.CaptchaServletUtil;
import nl.captcha.servlet.SimpleCaptchaServlet;

import com.google.inject.Singleton;

@Singleton
public class CaptchaServlet extends SimpleCaptchaServlet {

    private static final long serialVersionUID = 5903017002857824756L;

    @Override
    public void doGet(final HttpServletRequest req, final HttpServletResponse resp)
        throws ServletException,
        IOException {
        final HttpSession session = req.getSession();

        final Captcha captcha =
            new Captcha.Builder(_width, _height)
                .addText()
                .addBackground(new GradiatedBackgroundProducer())
                .gimp()
                .addBackground(new FlatColorBackgroundProducer(new Color(238, 238, 238)))
                .addBorder()
                .addNoise()
                .build();

        session.setAttribute(NAME, captcha);

        CaptchaServletUtil.writeImage(resp, captcha.getImage());
    }

}
