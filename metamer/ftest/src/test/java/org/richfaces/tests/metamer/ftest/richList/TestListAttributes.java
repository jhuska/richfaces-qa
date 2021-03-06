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
package org.richfaces.tests.metamer.ftest.richList;

import static org.jboss.test.selenium.support.url.URLUtils.buildUrl;
import static org.richfaces.tests.metamer.ftest.extension.configurator.use.annotation.ValuesFrom.FROM_ENUM;
import static org.richfaces.tests.metamer.ftest.extension.configurator.use.annotation.ValuesFrom.FROM_FIELD;

import java.net.URL;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.richfaces.fragment.common.Event;
import org.richfaces.fragment.list.RichFacesListItem;
import org.richfaces.tests.metamer.ftest.BasicAttributes;
import org.richfaces.tests.metamer.ftest.extension.configurator.templates.annotation.Templates;
import org.richfaces.tests.metamer.ftest.extension.configurator.use.annotation.UseWithField;
import org.richfaces.tests.metamer.ftest.webdriver.Attributes;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @author <a href="mailto:jstefek@redhat.com">Jiri Stefek</a>
 */
public class TestListAttributes extends AbstractListTest {

    private final Attributes<ListAttributes> listAttributes = getAttributes();

    enum ListType {

        ORDERED("ordered"), UNORDERED("unordered"), DEFINITIONS("definitions");
        private final String value;

        private ListType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    private ListType type;

    @Override
    public URL getTestUrl() {
        return buildUrl(contextPath, "faces/components/richList/simple.xhtml");
    }

    private WebElement getTestedItem() {
        return list.getItems().get(0).getRootElement();
    }

    @Test
    @Templates(value = "plain")
    public void testRendered() {
        listAttributes.set(ListAttributes.rendered, Boolean.FALSE);
        assertNotVisible(list.getRootElement(), "List should not be visible");
    }

    @Test
    @Templates("plain")
    public void testDir() {
        testDir(list.getRootElement());
    }

    @Test(groups = "smoke")
    @UseWithField(field = "first", valuesFrom = FROM_FIELD, value = "INTS")
    public void testFirst() {
        listAttributes.set(ListAttributes.first, first);
        rows = 20;
        listAttributes.set(ListAttributes.rows, rows);
        verifyList();
    }

    @Test
    @Templates("plain")
    public void testLang() {
        testLang(list.getRootElement());
    }

    @Test
    @Templates(value = "plain")
    public void testOnclick() {
        testFireEvent(listAttributes, ListAttributes.onclick,
            new Actions(driver).click(list.getRootElement()).build());
    }

    @Test
    @Templates(value = "plain")
    public void testOndblclick() {
        testFireEvent(listAttributes, ListAttributes.ondblclick,
            new Actions(driver).doubleClick(list.getRootElement()).build());
    }

    @Test
    @Templates(value = "plain")
    public void testOnkeydown() {
        testFireEventWithJS(list.getRootElement(), listAttributes,
            ListAttributes.onkeydown);
    }

    @Test(groups = "smoke")
    @Templates(value = "plain")
    public void testOnkeypress() {
        testFireEventWithJS(list.getRootElement(), listAttributes,
            ListAttributes.onkeypress);
    }

    @Test
    @Templates(value = "plain")
    public void testOnkeyup() {
        testFireEventWithJS(list.getRootElement(), listAttributes,
            ListAttributes.onkeyup);

    }

    @Test
    @Templates(value = "plain")
    public void testOnmousedown() {
        testFireEventWithJS(list.getRootElement(), listAttributes,
            ListAttributes.onmousedown);
    }

    @Test
    @Templates(value = "plain")
    public void testOnmousemove() {
        testFireEventWithJS(list.getRootElement(), listAttributes,
            ListAttributes.onmousemove);

    }

    @Test
    @Templates(value = "plain")
    public void testOnmouseout() {
        testFireEventWithJS(list.getRootElement(), listAttributes,
            ListAttributes.onmouseout);
    }

    @Test
    @Templates(value = "plain")
    public void testOnmouseover() {
        testFireEventWithJS(list.getRootElement(), listAttributes,
            ListAttributes.onmouseover);
    }

    @Test
    @Templates(value = "plain")
    public void testOnmouseup() {
        testFireEventWithJS(list.getRootElement(), listAttributes,
            ListAttributes.onmouseup);
    }

    @Test
    @Templates(value = "plain")
    public void testOnrowclick() {
        testFireEvent(listAttributes, ListAttributes.onrowclick,
            new Actions(driver).click(getTestedItem()).build());
    }

    @Test
    @Templates(value = "plain")
    public void testOnrowdblclick() {
        testFireEvent(listAttributes, ListAttributes.onrowdblclick,
            new Actions(driver).doubleClick(getTestedItem()).build());
    }

    @Test
    @Templates(value = "plain")
    public void testOnrowkeydown() {
        testFireEventWithJS(getTestedItem(), Event.KEYDOWN, listAttributes,
            ListAttributes.onrowkeydown);
    }

    @Test
    @Templates(value = "plain")
    public void testOnrowkeypress() {
        testFireEventWithJS(getTestedItem(), Event.KEYPRESS, listAttributes,
            ListAttributes.onrowkeypress);
    }

    @Test
    @Templates(value = "plain")
    public void testOnrowkeyup() {
        testFireEventWithJS(getTestedItem(), Event.KEYUP, listAttributes,
            ListAttributes.onrowkeyup);
    }

    @Test
    @Templates(value = "plain")
    public void testOnrowmousedown() {
        testFireEventWithJS(getTestedItem(), Event.MOUSEDOWN, listAttributes,
            ListAttributes.onrowmousedown);
    }

    @Test
    @Templates(value = "plain")
    public void testOnrowmousemove() {
        testFireEventWithJS(getTestedItem(), Event.MOUSEMOVE, listAttributes,
            ListAttributes.onrowmousemove);

    }

    @Test
    @Templates(value = "plain")
    public void testOnrowmouseout() {
        testFireEventWithJS(getTestedItem(), Event.MOUSEOUT, listAttributes,
            ListAttributes.onrowmouseout);
    }

    @Test
    @Templates(value = "plain")
    public void testOnrowmouseover() {
        testFireEventWithJS(getTestedItem(), Event.MOUSEOVER, listAttributes,
            ListAttributes.onrowmouseover);
    }

    @Test
    @Templates(value = "plain")
    public void testOnrowmouseup() {
        testFireEventWithJS(getTestedItem(), Event.MOUSEUP, listAttributes,
            ListAttributes.onrowmouseup);
    }

    @Test
    @Templates(value = "plain")
    public void testRowClass() {
        List<RichFacesListItem> items = list.getItems();
        testStyleClass(items.get(0).getRootElement(), BasicAttributes.rowClass);
        testStyleClass(items.get(items.size() - 1).getRootElement(), BasicAttributes.rowClass);
    }

    @Test
    @Templates(value = "plain")
    public void testRowClasses() {
        List<RichFacesListItem> items = list.getItems();
        testStyleClass(items.get(0).getRootElement(), BasicAttributes.rowClasses);
        testStyleClass(items.get(items.size() - 1).getRootElement(), BasicAttributes.rowClasses);
    }

    @Test(groups = "smoke")
    @UseWithField(field = "rows", valuesFrom = FROM_FIELD, value = "INTS")
    public void testRows() {
        listAttributes.set(ListAttributes.rows, rows);
        verifyList();
    }

    @Test
    @Templates(value = "plain")
    public void testStyle() {
        testStyle(list.getRootElement());
    }

    @Test
    @Templates(value = "plain")
    public void testStyleClass() {
        testStyleClass(list.getRootElement());
    }

    @Test
    @Templates("plain")
    public void testTitle() {
        testTitle(list.getRootElement());
    }

    @Test(groups = "smoke")
    @UseWithField(field = "type", valuesFrom = FROM_ENUM, value = "")
    public void testType() {
        listAttributes.set(ListAttributes.type, type.getValue());
    }
}
