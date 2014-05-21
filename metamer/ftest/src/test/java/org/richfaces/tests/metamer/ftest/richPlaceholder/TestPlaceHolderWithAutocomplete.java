/**
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
 */
package org.richfaces.tests.metamer.ftest.richPlaceholder;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.richfaces.fragment.autocomplete.RichFacesAutocomplete;
import org.richfaces.tests.metamer.ftest.annotations.RegressionTest;
import org.richfaces.tests.metamer.ftest.extension.configurator.templates.annotation.Templates;
import org.testng.annotations.Test;

/**
 *
 * @author <a href="mailto:jstefek@redhat.com">Jiri Stefek</a>
 */
public class TestPlaceHolderWithAutocomplete extends AbstractPlaceholderJSFTest {

    @FindBy(css = INPUT1_ID)
    private RichFacesAutocomplete input1;
    @FindBy(css = INPUT2_ID)
    private RichFacesAutocomplete input2;

    public TestPlaceHolderWithAutocomplete() {
        super("autocomplete");
    }

    @Override
    WebElement getInput1() {
        return input1.advanced().getInput().advanced().getInputElement();
    }

    @Override
    WebElement getInput2() {
        return input2.advanced().getInput().advanced().getInputElement();
    }

    @Test
    @RegressionTest({ "https://issues.jboss.org/browse/RF-12623", "https://issues.jboss.org/browse/RF-12625" })
    @Override
    public void testAjaxSubmit() {
        super.testAjaxSubmit();
    }

    @Test
    @RegressionTest("https://issues.jboss.org/browse/RF-12623")
    @Override
    public void testClickOnInputWithPlaceholder() {
        super.testClickOnInputWithPlaceholder();
    }

    @Test
    @RegressionTest("https://issues.jboss.org/browse/RF-12623")
    @Override
    public void testDeleteTextFromInputWithPlaceholder() {
        super.testDeleteTextFromInputWithPlaceholder();
    }

    @Test
    @RegressionTest("https://issues.jboss.org/browse/RF-12623")
    @Override
    public void testHTTPSubmit() {
        super.testHTTPSubmit();
    }

    @Test
    @RegressionTest({ "https://issues.jboss.org/browse/RF-12623", "https://issues.jboss.org/browse/RF-12650" })
    @Override
    @Templates(value = "plain")
    public void testRendered() {
        super.testRendered();
    }

    @Test
    @Override
    public void testSelector() {
        super.testSelector();
    }

    @Test
    @RegressionTest("https://issues.jboss.org/browse/RF-12621")
    @Override
    public void testSelectorEmpty() {
        super.testSelectorEmpty();
    }

    @Test
    @RegressionTest("https://issues.jboss.org/browse/RF-12623")
    @Override
    @Templates("plain")
    public void testStyleClass() {
        super.testStyleClass();
    }

    @Test
    @RegressionTest("https://issues.jboss.org/browse/RF-12623")
    @Override
    public void testTypeToInputWithPlaceholder() {
        super.testTypeToInputWithPlaceholder();
    }
}
