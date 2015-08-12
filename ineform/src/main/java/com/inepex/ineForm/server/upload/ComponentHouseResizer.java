package com.inepex.ineForm.server.upload;

/*
 * Copyright (c) 2007 Component House (Hugo Vidal Teixeira). All Rights Reserved.
 * Visit: http://www.componenthouse.com
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  o Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  o Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  o Neither the name of Component House (Hugo Vidal Teixeira) nor the names of
 *    its contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.HashMap;
import java.util.Map;

import sun.awt.image.BufferedImageGraphicsConfig;

/**
 * High-Quality Image Resize with Java http://www.componenthouse.com/article-20
 * 
 * @author Hugo Teixeira - new functionality (cut and resize) added by Istvan
 *         Szoboszlai
 */
public class ComponentHouseResizer {

    /*
     * public static void main(String[] args) { try { BufferedImage image =
     * ImageIO.read(new File("c:\\picture.jpg"));
     * ImageIO.write(resizeTrick(image, 24, 24), "png", new
     * File("c:\\picture3.png")); } catch (IOException e) { e.printStackTrace();
     * } }
     */

    private static BufferedImage resize(BufferedImage image, int width, int height) {
        int type = image.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : image.getType();
        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.setRenderingHint(
            RenderingHints.KEY_INTERPOLATION,
            RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }

    public static
        BufferedImage
        cutAndResize(BufferedImage image, Rectangle cutRect, int targetWidth) {
        BufferedImage subImage =
            image.getSubimage(cutRect.x, cutRect.y, cutRect.width, cutRect.height);
        return resize(subImage, targetWidth);
    }

    public static BufferedImage resize(BufferedImage image, int width) {
        return resize(image, width, Math.round(image.getHeight() * width / image.getWidth()));
    }

    public static BufferedImage resizeTrick(BufferedImage image, int width, int height) {
        image = createCompatibleImage(image);
        image = resize(image, 100, 100);
        image = blurImage(image);
        return resize(image, width, height);
    }

    private static BufferedImage blurImage(BufferedImage image) {
        float ninth = 1.0f / 9.0f;
        float[] blurKernel = { ninth, ninth, ninth, ninth, ninth, ninth, ninth, ninth, ninth };

        Map<RenderingHints.Key, Object> map = new HashMap<RenderingHints.Key, Object>();
        map.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        map.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        RenderingHints hints = new RenderingHints(map);
        BufferedImageOp op =
            new ConvolveOp(new Kernel(3, 3, blurKernel), ConvolveOp.EDGE_NO_OP, hints);
        return op.filter(image, null);
    }

    private static BufferedImage createCompatibleImage(BufferedImage image) {
        GraphicsConfiguration gc = BufferedImageGraphicsConfig.getConfig(image);
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage result = gc.createCompatibleImage(w, h, Transparency.TRANSLUCENT);
        Graphics2D g2 = result.createGraphics();
        g2.drawRenderedImage(image, null);
        g2.dispose();
        return result;
    }

}
