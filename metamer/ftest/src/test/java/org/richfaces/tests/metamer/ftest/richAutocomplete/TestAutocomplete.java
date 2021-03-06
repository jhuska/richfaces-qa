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
package org.richfaces.tests.metamer.ftest.richAutocomplete;

import static org.jboss.test.selenium.support.url.URLUtils.buildUrl;
import static org.richfaces.tests.metamer.ftest.extension.configurator.use.annotation.ValuesFrom.FROM_FIELD;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.net.URL;

import org.jboss.arquillian.graphene.Graphene;
import org.richfaces.fragment.autocomplete.SelectOrConfirm;
import org.richfaces.fragment.common.ClearType;
import org.richfaces.fragment.common.ScrollingType;
import org.richfaces.fragment.common.picker.ChoicePickerHelper;
import org.richfaces.tests.metamer.ftest.annotations.RegressionTest;
import org.richfaces.tests.metamer.ftest.extension.configurator.use.annotation.UseForAllTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 */
public class TestAutocomplete extends AbstractAutocompleteTest {

    @UseForAllTests(valuesFrom = FROM_FIELD, value = "booleans")
    private Boolean autofill;
    @UseForAllTests(valuesFrom = FROM_FIELD, value = "booleans")
    private Boolean selectFirst;

    @Override
    public URL getTestUrl() {
        return buildUrl(contextPath, "faces/components/richAutocomplete/autocomplete.xhtml");
    }

    @BeforeMethod(groups = "smoke")
    public void prepareProperties() {
        autocompleteAttributes.set(AutocompleteAttributes.autofill, autofill);
        autocompleteAttributes.set(AutocompleteAttributes.selectFirst, selectFirst);
        autocomplete.clear();
    }

    @Test(groups = "smoke")
    @RegressionTest("https://issues.jboss.org/browse/RF-11323")
    public void testTypePrefixDeleteAll_typePrefixConfirm() {
        Graphene.guardAjax(autocomplete).type("ala");
        autocomplete.advanced().clear(ClearType.BACKSPACE);
        autocomplete.advanced().waitForSuggestionsToBeNotVisible().perform();
        assertTrue(autocomplete.advanced().getSuggestionsElements().isEmpty());

        SelectOrConfirm typed = Graphene.guardAjax(autocomplete).type("ala");
        assertFalse(autocomplete.advanced().getSuggestionsElements().isEmpty());
        Graphene.guardAjax(typed).confirm();
        assertTrue(autocomplete.advanced().getSuggestionsElements().isEmpty());
        String expectedStateForPrefix = getExpectedStateForPrefix("ala", selectFirst);
        assertEquals(autocomplete.advanced().getInput().getStringValue(), expectedStateForPrefix);
        checkOutput(expectedStateForPrefix);
    }

    @Test
    public void testSimpleSelectionWithMouse() {
        autocomplete.type("a").select(ChoicePickerHelper.byVisibleText().endsWith("na"));
        checkOutput("Arizona");
    }

    @Test
    public void testSimpleSelectionWithKeyboard() {
        autocomplete.advanced().setScrollingType(ScrollingType.BY_KEYS);
        autocomplete.type("a").select(ChoicePickerHelper.byVisibleText().endsWith("na"));
        checkOutput("Arizona");
    }
}
