package com.inepex.ineFrame.server;

import static nl.captcha.Captcha.NAME;

import java.awt.Color;
import java.io.IOException;

import javax.imageio.ImageIO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import nl.captcha.Captcha;
import nl.captcha.backgrounds.FlatColorBackgroundProducer;
import nl.captcha.backgrounds.GradiatedBackgroundProducer;

import jakarta.inject.Singleton;

@Singleton
public class CaptchaServlet extends HttpServlet {

    private static final long serialVersionUID = 5903017002857824756L;

    private int _width = 200;
    private int _height = 50;

    @Override
    public void doGet(final HttpServletRequest req, final HttpServletResponse resp)
        throws ServletException,
        IOException {
        final HttpSession session = req.getSession();

        final Captcha captcha = new Captcha.Builder(_width, _height)
            .addText()
            .addBackground(new GradiatedBackgroundProducer())
            .gimp()
            .addBackground(new FlatColorBackgroundProducer(new Color(238, 238, 238)))
            .addBorder()
            .addNoise()
            .build();

        session.setAttribute(NAME, captcha);

        resp.setContentType("image/png");
        ImageIO.write(captcha.getImage(), "png", resp.getOutputStream());
    }

}
