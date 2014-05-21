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
package org.richfaces.tests.metamer.ftest.richTree;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.richfaces.fragment.switchable.SwitchType;
import org.richfaces.fragment.tree.Tree.TreeNode;
import org.testng.annotations.BeforeMethod;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 * @author <a href="mailto:ppitonak@redhat.com">Pavol Pitonak</a>
 */
public abstract class AbstractTreeSelectionTest extends AbstractTreeTest {

    @FindByJQuery("input:submit[id$=expandAll]")
    private WebElement expandAll;
    @FindBy(css = "span[id$=selection]")
    private WebElement selection;
    @FindBy(css = "span[id$=selectionEventNewSelection]")
    private WebElement newSelection;
    @FindBy(css = "span[id$=selectionEventOldSelection]")
    private WebElement oldSelection;
    @FindBy(css = "span[id$=selectionEventClientId]")
    private WebElement clientId;
    @FindBy(className = "rf-trn-sel")
    private List<WebElement> allSelectedItems;

    private SwitchType selectionType;

    private static final SwitchType[] selectionTypes = new SwitchType[]{ SwitchType.AJAX, SwitchType.CLIENT };
    private static final SwitchType[] selectionTypeAjax = new SwitchType[]{ SwitchType.AJAX };
    private static final SwitchType[] selectionTypeClient = new SwitchType[]{ SwitchType.CLIENT };
    private static final SwitchType[] eventEnabledSelectionTypes = new SwitchType[]{ SwitchType.AJAX };

    protected Integer[][] selectionPaths = new Integer[][]{ { 3, 2 }, { 1, 0, 0 }, { 1 }, { 3, 9, 2 } };

    protected TreeNode treeNode;

    protected void expandAll() {
        for (Integer[] path : selectionPaths) {
            treeNode = null;
            for (int i = 0; i < path.length; i++) {
                int index = path[i];
                treeNode = (treeNode == null) ? tree.advanced().getNodes().get(index) : treeNode.advanced().getNodes()
                    .get(index);
                if (i < path.length - 1) {
                    treeNode.advanced().expand();
                }
            }
        }
    }

    protected String getClientId() {
        return clientId.getText();
    }

    protected Integer[] getIntsFromString(String string) {
        Pattern pattern = Pattern.compile(".*\\[((?:(?:\\d+)(?:, )?)+)\\].*");
        Matcher matcher = pattern.matcher(string);
        if (matcher.find()) {
            String[] strings = StringUtils.split(matcher.group(1), ", ");
            Integer[] numbers = new Integer[strings.length];
            for (int i = 0; i < strings.length; i++) {
                numbers[i] = Integer.valueOf(strings[i]);
            }
            return numbers;
        }
        throw new IllegalStateException("pattern does not match");
    }

    protected Integer[] getNewSelection() {
        return getIntsFromString(newSelection.getText());
    }

    protected Integer[] getOldSelection() {
        return getIntsFromString(oldSelection.getText());
    }

    protected Integer[] getSelection() {
        return getIntsFromString(selection.getText());
    }

    @BeforeMethod
    public void prepareTest() {
        treeAttributes.set(TreeAttributes.selectionType, selectionType.toString().toLowerCase());
        treeAttributes.set(TreeAttributes.toggleType, selectionType.toString().toLowerCase());
    }

    protected void testSubNodesSelectionWithEvents() {
        expandAll();
        assertEquals(allSelectedItems.size(), 0);
        boolean checkEvents = selectionType.equals(eventEnabledSelectionTypes[0]);
        Integer[] old = null;
        for (Integer[] path : selectionPaths) {
            treeNode = null;
            for (int index : path) {
                treeNode = (treeNode == null) ? tree.advanced().getNodes().get(index) : treeNode.advanced().getNodes()
                    .get(index);
            }
            String previousSelectionValue = selection.getText();
            assertFalse(treeNode.advanced().isSelected());
            getGuarded(treeNode.advanced(), selectionType).select();

            assertTrue(treeNode.advanced().isSelected());
            assertEquals(allSelectedItems.size(), 1);

            if (checkEvents) {
                // there is delay before select triggers output update
                Graphene.waitAjax().until().element(selection).text().not().equalTo(previousSelectionValue);

                assertEquals(
                    getSelection(),
                    path,
                    String.format("Actual Selection (%s) doesn't correspond to expected (%s)",
                        Arrays.deepToString(getSelection()), Arrays.deepToString(path)));

                assertEquals(
                    getNewSelection(),
                    path,
                    String.format("Actual New selection (%s) doesn't correspond to expected (%s)",
                        Arrays.deepToString(getNewSelection()), Arrays.deepToString(path)));
                if (old != null) {
                    assertEquals(
                        getOldSelection(),
                        old,
                        String.format("Actual Old selection (%s) doesn't correspond to expected (%s)",
                            Arrays.deepToString(getOldSelection()), Arrays.deepToString(old)));
                } else {
                    assertEquals(oldSelection.getText(), "[]");
                }
                old = getNewSelection();
            }
        }
    }
}
