package com.inepex.ineom.shared.validation.basicvalidators;

import junit.framework.Assert;

import org.junit.Test;

public class EmailValidatorTest {

    @Test
    public void testIt() {
        Assert.assertEquals(true, testMatch(EmailValidator.regExpr, "alma@korte.er"));
        Assert.assertEquals(true, testMatch(EmailValidator.regExpr, "alma.alma@korte.er"));
        Assert.assertEquals(true, testMatch(EmailValidator.regExpr, "alma@korte.korte.er"));
        Assert.assertEquals(true, testMatch(EmailValidator.regExpr, "alma@korte.kk.er"));
        Assert.assertEquals(true, testMatch(EmailValidator.regExpr, "alma@ko-kk.er"));
        Assert.assertEquals(
            true,
            testMatch(EmailValidator.regExpr, "firstname.lastname@sg-domain.com"));
        Assert.assertEquals(true, testMatch(EmailValidator.regExpr, "al-ma@korte.er"));
        Assert.assertEquals(true, testMatch(EmailValidator.regExpr, "al.ma@korte.er"));
        Assert.assertEquals(true, testMatch(EmailValidator.regExpr, "istvan.laszlo.gel@korte.er"));
        Assert.assertEquals(
            true,
            testMatch(EmailValidator.regExpr, "istvan.laszlo.gel-kis@korte.er"));
        Assert.assertEquals(
            true,
            testMatch(EmailValidator.regExpr, "istvan.kis-laszlo.gel@korte.er"));
        Assert.assertEquals(true, testMatch(EmailValidator.regExpr, "alma1341@alma.hu"));
        Assert.assertEquals(true, testMatch(EmailValidator.regExpr, "l.ugyfel@g-n.com"));
        Assert.assertEquals(true, testMatch(EmailValidator.regExpr, "l.ugyfel@1-2.com"));
        Assert.assertEquals(
            true,
            testMatch(EmailValidator.regExpr, "l.l.l.l.l.ugyfel@g-n-n-n-n-n.n+e+e.com"));
        Assert.assertEquals(true, testMatch(EmailValidator.regExpr, "alma@korte.k.er"));

        Assert.assertEquals(false, testMatch(EmailValidator.regExpr, "alméa@korte.er")); // specical
                                                                                         // char
                                                                                         // é
        Assert.assertEquals(false, testMatch(EmailValidator.regExpr, "alma@korte..er"));
        Assert.assertEquals(false, testMatch(EmailValidator.regExpr, "alma.@korte.er"));
        Assert.assertEquals(false, testMatch(EmailValidator.regExpr, "al..ma@korte.er"));
        Assert.assertEquals(false, testMatch(EmailValidator.regExpr, "alma-@korte.er"));
        Assert.assertEquals(false, testMatch(EmailValidator.regExpr, "alm@korte.e"));
    }

    private static boolean testMatch(String regex, String string) {
        return string.matches(regex);
    }
}
