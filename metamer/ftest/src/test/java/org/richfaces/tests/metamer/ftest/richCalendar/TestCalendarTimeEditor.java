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
package org.richfaces.tests.metamer.ftest.richCalendar;

import static org.jboss.test.selenium.support.url.URLUtils.buildUrl;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.net.URL;

import org.joda.time.DateTime;
import org.richfaces.fragment.calendar.TimeEditor;
import org.richfaces.fragment.calendar.TimeEditor.SetValueBy;
import org.richfaces.tests.metamer.ftest.extension.configurator.templates.annotation.Templates;
import org.richfaces.tests.metamer.ftest.webdriver.MetamerPage;
import org.richfaces.tests.metamer.ftest.webdriver.MetamerPage.WaitRequestType;
import org.testng.annotations.Test;

/**
 * Test case for time editor of a calendar on page faces/components/richCalendar/simple.xhtml.
 *
 * @author <a href="mailto:ppitonak@redhat.com">Pavol Pitonak</a>
 * @version $Revision: 21568 $
 */
public class TestCalendarTimeEditor extends AbstractCalendarTest {

    @Override
    public URL getTestUrl() {
        return buildUrl(contextPath, "faces/components/richCalendar/simple.xhtml");
    }

    @Test
    @Templates("plain")
    public void testCancelButton() {
        int plusMinutes = 5;
        MetamerPage.waitRequest(popupCalendar.openPopup().getFooterControls(), WaitRequestType.XHR).setTodaysDate();
        TimeEditor openedTimeEditor = popupCalendar.openPopup().getFooterControls().openTimeEditor();
        MetamerPage.waitRequest(openedTimeEditor, WaitRequestType.NONE).setTime(todayMidday.plusMinutes(plusMinutes), SetValueBy.BUTTONS);
        DateTime time1 = openedTimeEditor.getTime();
        assertEquals(time1.getMinuteOfHour(), plusMinutes);

        MetamerPage.waitRequest(openedTimeEditor, WaitRequestType.NONE).cancelTime();
        assertFalse(openedTimeEditor.isVisible());
        openedTimeEditor = popupCalendar.openPopup().getFooterControls().openTimeEditor();
        time1 = openedTimeEditor.getTime();
        assertEquals(time1.getHourOfDay(), 12);//default value
        assertEquals(time1.getMinuteOfHour(), 0);//default value
    }

    @Test
    @Templates("plain")
    public void testHoursInputClick() {
        testTimeSet(new int[]{ 2, 15 }, Time.hours, SetValueBy.BUTTONS);
    }

    @Test
    @Templates("plain")
    public void testHoursInputType() {
        testTimeSet(new int[]{ 2, 15 }, Time.hours, SetValueBy.TYPING);
    }

    @Test
    @Templates("plain")
    public void testMinutesInputClick() {
        testTimeSet(new int[]{ 1, 59 }, Time.minutes, SetValueBy.BUTTONS);
    }

    @Test
    @Templates("plain")
    public void testMinutesInputType() {
        testTimeSet(new int[]{ 1, 59 }, Time.minutes, SetValueBy.TYPING);
    }

    @Test
    @Templates("plain")
    public void testSecondsInputClick() {
        calendarAttributes.set(CalendarAttributes.datePattern, "MMM d, yyyy HH:mm:ss");
        testTimeSet(new int[]{ 1, 59 }, Time.seconds, SetValueBy.BUTTONS);
    }

    @Test
    @Templates("plain")
    public void testSecondsInputType() {
        calendarAttributes.set(CalendarAttributes.datePattern, "MMM d, yyyy HH:mm:ss");
        testTimeSet(new int[]{ 1, 59 }, Time.seconds, SetValueBy.TYPING);
    }

    @Test
    @Templates("plain")
    public void testShowTimeEditor() {
        MetamerPage.waitRequest(popupCalendar.openPopup().getFooterControls(), WaitRequestType.XHR).setTodaysDate();
        TimeEditor openedTimeEditor = popupCalendar.openPopup().getFooterControls().openTimeEditor();
        assertTrue(openedTimeEditor.isVisible());
        DateTime time1 = openedTimeEditor.getTime();
        assertEquals(time1.getHourOfDay(), 12);
        assertEquals(time1.getMinuteOfHour(), 0);
    }

    private void testTimeSet(int[] valuesToTest, Time time, SetValueBy interaction) {
        for (int value : valuesToTest) {
            MetamerPage.waitRequest(popupCalendar.openPopup().getFooterControls(), WaitRequestType.XHR).setTodaysDate();
            TimeEditor openedTimeEditor = popupCalendar.openPopup().getFooterControls().openTimeEditor();
            DateTime changedTime = time.change(todayMidday, value);
            openedTimeEditor.setTime(changedTime, interaction).confirmTime();
            openedTimeEditor = popupCalendar.openPopup().getFooterControls().openTimeEditor();
            DateTime time1 = openedTimeEditor.getTime();
            time.checkTimeChanged(changedTime, time1);
        }
    }

    private enum Time {

        hours {
                @Override
                public DateTime change(DateTime time, int value) {
                    return time.plusHours(value);
                }

                @Override
                public void checkTimeChanged(DateTime referenceTime, DateTime changedTime) {
                    assertEquals(changedTime.getHourOfDay(), referenceTime.getHourOfDay());
                }
            },
        minutes {
                @Override
                public DateTime change(DateTime time, int value) {
                    return time.plusMinutes(value);
                }

                @Override
                public void checkTimeChanged(DateTime referenceTime, DateTime changedTime) {
                    assertEquals(changedTime.getMinuteOfHour(), referenceTime.getMinuteOfHour());
                }
            },
        seconds {
                @Override
                public DateTime change(DateTime time, int value) {
                    return time.plusSeconds(value);
                }

                @Override
                public void checkTimeChanged(DateTime referenceTime, DateTime changedTime) {
                    assertEquals(changedTime.getSecondOfMinute(), referenceTime.getSecondOfMinute());
                }
            };

        public abstract DateTime change(DateTime time, int value);

        public abstract void checkTimeChanged(DateTime referenceTime, DateTime changedTime);
    }
}
