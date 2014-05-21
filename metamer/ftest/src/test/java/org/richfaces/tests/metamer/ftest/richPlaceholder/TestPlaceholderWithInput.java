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

import org.richfaces.tests.metamer.ftest.annotations.RegressionTest;
import org.richfaces.tests.metamer.ftest.extension.configurator.templates.annotation.Templates;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:jstefek@redhat.com">Jiri Stefek</a>
 */
public class TestPlaceholderWithInput extends AbstractPlaceholderNonJSFTest {

    public TestPlaceholderWithInput() {
        super("input");
    }

    @Test
    public void testClickOnInputWithPlaceholder() {
        super.testClickOnInputWithPlaceholder();
    }

    @Test
    public void testDeleteTextFromInputWithPlaceholder() {
        super.testDeleteTextFromInputWithPlaceholder();
    }

    @Test
    @RegressionTest("https://issues.jboss.org/browse/RF-12650")
    @Templates(value = "plain")
    public void testRendered() {
        super.testRendered();
    }

    @Test
    public void testSelectorSingle() {
        super.testSelectorSingle();
    }

    @Test
    public void testSelectorMultiple() {
        super.testSelectorMultiple();
    }

    @Test
    public void testSelectorEmpty() {
        super.testSelectorEmpty();
    }

    @Test
    public void testStyleClass() {
        super.testStyleClass();
    }

    @Test
    public void testTypeToInputWithPlaceholder() {
        super.testTypeToInputWithPlaceholder();
    }
}
