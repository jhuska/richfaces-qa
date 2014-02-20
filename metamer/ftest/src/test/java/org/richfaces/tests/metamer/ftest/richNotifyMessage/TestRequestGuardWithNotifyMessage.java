/*******************************************************************************
 * JBoss, Home of Professional Open Source
 * Copyright 2010-2014, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 *******************************************************************************/
package org.richfaces.tests.metamer.ftest.richNotifyMessage;

import static org.jboss.test.selenium.support.url.URLUtils.buildUrl;

import java.net.URL;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.page.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.richfaces.tests.metamer.ftest.AbstractWebDriverTest;
import org.testng.annotations.Test;

/**
 * @author ppitonak
 */
public class TestRequestGuardWithNotifyMessage extends AbstractWebDriverTest {

    @Override
    public URL getTestUrl() {
        return buildUrl(contextPath, "faces/components/richNotifyMessage/guardReproducer.xhtml");
    }

    @FindBy(css = "input[id$=a4jButton]")
    private WebElement submitButton;

    @Page
    private NotifyMessagePage page;

    @Test
    public void testWithPageObject() {
        Graphene.guardAjax(page.getA4jCommandButton()).click();
        waiting(3000);
    }

    @Test
    public void testWithInjectedElement() {
        Graphene.guardAjax(submitButton).click();
        waiting(3000);
    }

    @Test
    public void testSimple() {
        Graphene.guardAjax(driver.findElement(By.cssSelector("input[id$=a4jButton]"))).click();
        waiting(3000);
    }
}
