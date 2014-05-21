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

import static org.richfaces.tests.metamer.ftest.extension.configurator.use.annotation.ValuesFrom.STRINGS;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.List;

import javax.faces.event.PhaseId;

import org.jboss.arquillian.graphene.Graphene;
import org.richfaces.fragment.common.Actions;
import org.richfaces.fragment.common.Event;
import org.richfaces.fragment.switchable.SwitchType;
import org.richfaces.fragment.tree.Tree.TreeNode;
import org.richfaces.tests.metamer.ftest.BasicAttributes;
import org.richfaces.tests.metamer.ftest.annotations.IssueTracking;
import org.richfaces.tests.metamer.ftest.extension.configurator.templates.annotation.Templates;
import org.richfaces.tests.metamer.ftest.extension.configurator.use.annotation.UseWithField;
import org.testng.annotations.Test;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;

/**
 *
 * @author <a href="mailto:jstefek@redhat.com">Jiri Stefek</a>
 */
public class TestTreeAttributes extends AbstractTreeTest {

    @Test
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = { "simpleSwingTreeNode", "simpleRichFacesTreeDataModel" })
    public void testData() {
        testData(selectFirstNodeAjaxAction);
    }

    @Test(groups = { "Future" })
    @IssueTracking("https://issues.jboss.org/browse/RF-11766")
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = "simpleRichFacesTreeNode")
    public void testDataRFTreeNode() {
        testData();
    }

    @Test
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = { "simpleSwingTreeNode", "simpleRichFacesTreeDataModel" })
    @Templates("plain")
    public void testDir() {
        testDir(tree.advanced().getRootElement());
    }

    @Test
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = { "simpleSwingTreeNode", "simpleRichFacesTreeDataModel" })
    public void testExecute() {
        treeAttributes.set(TreeAttributes.execute, "executeChecker @this");
        selectFirstNodeAjaxAction.perform();
        page.assertListener(PhaseId.UPDATE_MODEL_VALUES, "executeChecker");
    }

    @Test(groups = { "Future" })
    @IssueTracking("https://issues.jboss.org/browse/RF-11766")
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = "simpleRichFacesTreeNode")
    public void testExecuteRFTreeNode() {
        testExecute();
    }

    @Test
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = { "simpleSwingTreeNode", "simpleRichFacesTreeDataModel" })
    @Templates("plain")
    public void testHandleClass() {
        TreeNode node = getGuarded(tree, SwitchType.AJAX).expandNode(0);
        testStyleClass(node.advanced().getHandleElement(), BasicAttributes.handleClass);
        testStyleClass(getGuarded(node, SwitchType.AJAX).expandNode(0).advanced().getHandleElement(),
            BasicAttributes.handleClass);
    }

    @Test
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = { "simpleSwingTreeNode", "simpleRichFacesTreeDataModel" })
    @Templates("plain")
    public void testIconClass() {
        TreeNode node = getGuarded(tree, SwitchType.AJAX).expandNode(0);
        testStyleClass(node.advanced().getIconElement(), BasicAttributes.iconClass);
        testStyleClass(getGuarded(node, SwitchType.AJAX).expandNode(0).advanced().getIconElement(), BasicAttributes.iconClass);
    }

    @Test
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = { "simpleSwingTreeNode", "simpleRichFacesTreeDataModel" })
    @Templates("plain")
    public void testIconCollapsed() {
        treeAttributes.set(TreeAttributes.iconCollapsed, IMAGE_URL);
        TreeNode node = tree.advanced().getFirstNode();
        assertTrue(node.advanced().getIconElement().getAttribute("src").endsWith(IMAGE_URL));
        String attribute = Optional.fromNullable(
            getGuarded(node.advanced(), SwitchType.AJAX).expand().advanced().getIconElement().getAttribute("src")).or("");
        assertFalse(attribute.endsWith(IMAGE_URL));
        assertTrue(getGuarded(node.advanced(), SwitchType.AJAX).collapse().advanced().getIconElement().getAttribute("src")
            .endsWith(IMAGE_URL));
    }

    @Test
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = { "simpleSwingTreeNode", "simpleRichFacesTreeDataModel" })
    @Templates("plain")
    public void testIconExpanded() {
        treeAttributes.set(TreeAttributes.iconExpanded, IMAGE_URL);
        TreeNode node = getGuarded(tree, SwitchType.AJAX).expandNode(0);
        assertTrue(node.advanced().getIconElement().getAttribute("src").endsWith(IMAGE_URL));
        String attribute = Optional.fromNullable(
            getGuarded(node.advanced(), SwitchType.AJAX).collapse().advanced().getIconElement().getAttribute("src")).or("");
        assertFalse(attribute.endsWith(IMAGE_URL));
        assertTrue(getGuarded(node.advanced(), SwitchType.AJAX).expand().expandNode(0).advanced().getIconElement()
            .getAttribute("src").endsWith(IMAGE_URL));
    }

    @Test
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = { "simpleSwingTreeNode", "simpleRichFacesTreeDataModel" })
    @Templates("plain")
    public void testIconLeaf() {
        treeAttributes.set(TreeAttributes.toggleType, SwitchType.CLIENT.toString().toLowerCase());
        treeAttributes.set(TreeAttributes.iconLeaf, IMAGE_URL);
        TreeNode leaf = tree.expandNode(0).expandNode(0).advanced().getFirstNode();
        assertTrue(leaf.advanced().isLeaf());
        assertTrue(leaf.advanced().getIconElement().getAttribute("src").endsWith(IMAGE_URL));
    }

    @Test
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = { "simpleSwingTreeNode", "simpleRichFacesTreeDataModel" })
    public void testImmediate() {
        treeAttributes.set(TreeAttributes.immediate, Boolean.FALSE);
        expandFirstNodeAjaxAction.perform();
        page.assertListener(PhaseId.PROCESS_VALIDATIONS, "tree toggle listener invoked");

        treeAttributes.set(TreeAttributes.immediate, Boolean.TRUE);
        collapseFirstNodeAjaxAction.perform();
        page.assertListener(PhaseId.APPLY_REQUEST_VALUES, "tree toggle listener invoked");
    }

    @Test
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = { "simpleSwingTreeNode", "simpleRichFacesTreeDataModel" })
    @Templates("plain")
    public void testLabelClass() {
        testStyleClass(tree.advanced().getFirstNode().advanced().getLabelElement(), BasicAttributes.labelClass);
    }

    @Test
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = { "simpleSwingTreeNode", "simpleRichFacesTreeDataModel" })
    @Templates("plain")
    public void testLang() {
        testLang(tree.advanced().getRootElement());
    }

    @Test
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = { "simpleSwingTreeNode", "simpleRichFacesTreeDataModel" })
    public void testLimitRender() {
        treeAttributes.set(TreeAttributes.limitRender, true);
        treeAttributes.set(TreeAttributes.render, "@this renderChecker");
        String renderCheckerText = page.getRenderCheckerOutputElement().getText();
        selectFirstNodeAjaxAction.perform();
        Graphene.waitAjax().until().element(page.getRenderCheckerOutputElement()).text().not().equalTo(renderCheckerText);
    }

    @Test(groups = { "Future" })
    @IssueTracking("https://issues.jboss.org/browse/RF-11766")
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = "simpleRichFacesTreeNode")
    public void testLimitRenderRFTreeNode() {
        testLimitRender();
    }

    @Test
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = { "simpleSwingTreeNode", "simpleRichFacesTreeDataModel" })
    @Templates("plain")
    public void testNodeClass() {
        testStyleClass(tree.advanced().getFirstNode().advanced().getNodeInfoElement(), BasicAttributes.nodeClass);
    }

    @Test
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = { "simpleSwingTreeNode", "simpleRichFacesTreeDataModel" })
    public void testOnbeforedomupdate() {
        testFireEvent(treeAttributes, TreeAttributes.onbeforedomupdate, selectFirstNodeAjaxAction);
    }

    @Test(groups = { "Future" })
    @IssueTracking("https://issues.jboss.org/browse/RF-11766")
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = "simpleRichFacesTreeNode")
    public void testOnbeforedomupdateRFTreeNode() {
        testOnbeforedomupdate();
    }

    @Test
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = { "simpleSwingTreeNode", "simpleRichFacesTreeDataModel" })
    public void testOnbeforenodetoggle() {
        testFireEvent(treeAttributes, TreeAttributes.onbeforenodetoggle, expandFirstNodeAjaxAction);
    }

    @Test(groups = { "Future" })
    @IssueTracking("https://issues.jboss.org/browse/RF-11766")
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = "simpleRichFacesTreeNode")
    public void testOnbeforenodetoggleRFTreeNode() {
        testOnbeforenodetoggle();
    }

    @Test
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = { "simpleSwingTreeNode", "simpleRichFacesTreeDataModel" })
    public void testOnbeforeselectionchange() {
        testFireEvent(treeAttributes, TreeAttributes.onbeforeselectionchange, selectFirstNodeAjaxAction);
    }

    @Test(groups = { "Future" })
    @IssueTracking("https://issues.jboss.org/browse/RF-11766")
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = "simpleRichFacesTreeNode")
    public void testOnbeforeselectionchangeRFTreeNode() {
        testOnbeforeselectionchange();
    }

    @Test
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = { "simpleSwingTreeNode", "simpleRichFacesTreeDataModel" })
    public void testOnbegin() {
        testFireEvent(treeAttributes, TreeAttributes.onbegin, selectFirstNodeAjaxAction);
    }

    @Test(groups = { "Future" })
    @IssueTracking("https://issues.jboss.org/browse/RF-11766")
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = "simpleRichFacesTreeNode")
    public void testOnbeginRFTreeNode() {
        testOnbegin();
    }

    @Test
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = { "simpleSwingTreeNode", "simpleRichFacesTreeDataModel" })
    @Templates("plain")
    public void testOnclick() {
        testFireEvent(treeAttributes, TreeAttributes.onclick, expandFirstNodeAjaxAction);
    }

    @Test
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = { "simpleSwingTreeNode", "simpleRichFacesTreeDataModel" })
    public void testOncomplete() {
        testFireEvent(treeAttributes, TreeAttributes.oncomplete, selectFirstNodeAjaxAction);
    }

    @Test(groups = { "Future" })
    @IssueTracking("https://issues.jboss.org/browse/RF-11766")
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = "simpleRichFacesTreeNode")
    public void testOncompleteRFTreeNode() {
        testOncomplete();
    }

    @Test
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = { "simpleSwingTreeNode", "simpleRichFacesTreeDataModel" })
    @Templates("plain")
    public void testOndblclick() {
        testFireEvent(treeAttributes, TreeAttributes.ondblclick,
            new Actions(driver).doubleClick(tree.advanced().getRootElement()).build());
    }

    @Test
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = { "simpleSwingTreeNode", "simpleRichFacesTreeDataModel" })
    @Templates("plain")
    public void testOnkeydown() {
        testFireEventWithJS(tree.advanced().getRootElement(), treeAttributes, TreeAttributes.onkeydown);
    }

    @Test
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = { "simpleSwingTreeNode", "simpleRichFacesTreeDataModel" })
    @Templates("plain")
    public void testOnkeypress() {
        testFireEventWithJS(tree.advanced().getRootElement(), treeAttributes, TreeAttributes.onkeypress);
    }

    @Test
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = { "simpleSwingTreeNode", "simpleRichFacesTreeDataModel" })
    @Templates("plain")
    public void testOnkeyup() {
        testFireEventWithJS(tree.advanced().getRootElement(), treeAttributes, TreeAttributes.onkeyup);
    }

    @Test
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = { "simpleSwingTreeNode", "simpleRichFacesTreeDataModel" })
    @Templates("plain")
    public void testOnmousedown() {
        testFireEvent(treeAttributes, TreeAttributes.onmousedown,
            new Actions(driver).triggerEventByWD(Event.CLICK, tree.advanced().getRootElement()).build());
    }

    @Test
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = { "simpleSwingTreeNode", "simpleRichFacesTreeDataModel" })
    @Templates("plain")
    public void testOnmousemove() {
        testFireEvent(treeAttributes, TreeAttributes.onmousemove,
            new Actions(driver).triggerEventByWD(Event.MOUSEMOVE, tree.advanced().getRootElement()).build());
    }

    @Test
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = { "simpleSwingTreeNode", "simpleRichFacesTreeDataModel" })
    @Templates("plain")
    public void testOnmouseout() {
        testFireEvent(treeAttributes, TreeAttributes.onmouseout,
            new Actions(driver).triggerEventByWD(Event.MOUSEOUT, tree.advanced().getRootElement()).build());
    }

    @Test
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = { "simpleSwingTreeNode", "simpleRichFacesTreeDataModel" })
    @Templates("plain")
    public void testOnmouseover() {
        new Actions(driver).moveToElement(page.getRequestTimeElement()).perform();
        testFireEvent(treeAttributes, TreeAttributes.onmouseover,
            new Actions(driver).triggerEventByWD(Event.MOUSEOVER, tree.advanced().getRootElement()).build());
    }

    @Test
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = { "simpleSwingTreeNode", "simpleRichFacesTreeDataModel" })
    @Templates("plain")
    public void testOnmouseup() {
        testFireEvent(treeAttributes, TreeAttributes.onmouseup,
            new Actions(driver).triggerEventByWD(Event.CLICK, tree.advanced().getRootElement()).build());
    }

    @Test
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = { "simpleSwingTreeNode", "simpleRichFacesTreeDataModel" })
    public void testOnnodetoggle() {
        testFireEvent(treeAttributes, TreeAttributes.onnodetoggle, expandFirstNodeAjaxAction);
    }

    @Test
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = { "simpleSwingTreeNode", "simpleRichFacesTreeDataModel" })
    @Templates(exclude = "a4jRegion")
    public void testOnselectionchange() {
        testFireEvent(treeAttributes, TreeAttributes.onselectionchange, selectFirstNodeAjaxAction);
    }

    @Test(groups = "Future")
    @Templates(value = "a4jRegion")
    @IssueTracking("https://issues.jboss.org/browse/RF-13322")
    public void testOnselectionchangeInRegion() {
        testFireEvent(treeAttributes, TreeAttributes.onselectionchange, selectFirstNodeAjaxAction);
    }

    @Test(groups = { "Future" })
    @IssueTracking("https://issues.jboss.org/browse/RF-11766")
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = "simpleRichFacesTreeNode")
    public void testOnselectionChangeRFTreeNode() {
        testOnselectionchange();
    }

    @Test
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = { "simpleSwingTreeNode", "simpleRichFacesTreeDataModel" })
    public void testRender() {
        treeAttributes.set(TreeAttributes.render, "renderChecker");
        String renderCheckerText = page.getRenderCheckerOutputElement().getText();
        selectFirstNodeAjaxAction.perform();
        Graphene.waitAjax().until().element(page.getRenderCheckerOutputElement()).text().not().equalTo(renderCheckerText);
    }

    @Test(groups = { "Future" })
    @IssueTracking("https://issues.jboss.org/browse/RF-11766")
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = "simpleRichFacesTreeNode")
    public void testRenderRFTreeNode() {
        testRender();
    }

    @Test
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = { "simpleSwingTreeNode", "simpleRichFacesTreeDataModel" })
    @Templates("plain")
    public void testRendered() {
        treeAttributes.set(TreeAttributes.rendered, Boolean.TRUE);
        assertVisible(tree.advanced().getRootElement(), "Tree should be visible");
        treeAttributes.set(TreeAttributes.rendered, Boolean.FALSE);
        assertNotVisible(tree.advanced().getRootElement(), "Tree should not be visible");
    }

    @Test
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = { "simpleSwingTreeNode", "simpleRichFacesTreeDataModel" })
    @Templates(exclude = "a4jRegion")
    public void testSelectionClientSideEventsOrder() {
        String[] events = new String[]{ "beforeselectionchange", "begin", "beforedomupdate", "complete", "selectionchange" };
        testRequestEventsBefore(events);
        selectFirstNodeAjaxAction.perform();
        testRequestEventsAfter(events);
    }

    @Test(groups = { "Future" })
    @IssueTracking("https://issues.jboss.org/browse/RF-11766")
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = "simpleRichFacesTreeNode")
    @Templates(exclude = "a4jRegion")
    public void testSelectionClientSideEventsOrderWithSimpleTreeNode() {
        testSelectionClientSideEventsOrder();
    }

    @Test(groups = { "Future" })
    @IssueTracking("https://issues.jboss.org/browse/RF-11319")
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = { "simpleSwingTreeNode", "simpleRichFacesTreeDataModel" })
    @Templates(value = "a4jRegion")
    public void testSelectionClientSideEventsOrderInRegion() {
        testSelectionClientSideEventsOrder();
    }

    @Test
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = { "simpleSwingTreeNode", "simpleRichFacesTreeDataModel" })
    public void testSelectionType() {
        treeAttributes.set(TreeAttributes.selectionType, SwitchType.AJAX.toString().toLowerCase());
        selectFirstNodeAjaxAction.perform();

        treeAttributes.set(TreeAttributes.selectionType, SwitchType.CLIENT.toString().toLowerCase());
        Graphene.guardNoRequest(tree).selectNode(1);
    }

    @Test(groups = { "Future" })
    @IssueTracking("https://issues.jboss.org/browse/RF-11766")
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = "simpleRichFacesTreeNode")
    public void testSelectionTypeRFTreeNode() {
        testSelectionType();
    }

    @Test
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = { "simpleSwingTreeNode", "simpleRichFacesTreeDataModel" })
    public void testStatus() {
        testStatus(selectFirstNodeAjaxAction);
    }

    @Test(groups = { "Future" })
    @IssueTracking("https://issues.jboss.org/browse/RF-11766")
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = "simpleRichFacesTreeNode")
    public void testStatusRFTreeNode() {
        testStatus();
    }

    @Test
    @Templates("plain")
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = { "simpleSwingTreeNode", "simpleRichFacesTreeDataModel" })
    public void testStyle() {
        testStyle(tree.advanced().getRootElement());
    }

    @Test
    @Templates("plain")
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = { "simpleSwingTreeNode", "simpleRichFacesTreeDataModel" })
    public void testStyleClass() {
        testStyleClass(tree.advanced().getRootElement());
    }

    @Test
    @Templates("plain")
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = { "simpleSwingTreeNode", "simpleRichFacesTreeDataModel" })
    public void testTitle() {
        testStyleClass(tree.advanced().getRootElement());
    }

    @Test(groups = "Future")
    @IssueTracking("https://issues.jboss.org/browse/RF-10265")
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = { "simpleSwingTreeNode", "simpleRichFacesTreeDataModel" })
    public void testToggleClientSideEventsOrder() {
        treeAttributes.set(TreeAttributes.toggleType, SwitchType.AJAX.toString().toLowerCase());
        String[] events = new String[]{ "beforenodetoggle", "begin", "beforedomupdate", "complete", "nodetoggle" };
        testRequestEventsBefore(events);
        expandFirstNodeAjaxAction.perform();
        testRequestEventsAfter(events);
    }

    @Test
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = { "simpleSwingTreeNode", "simpleRichFacesTreeDataModel" })
    public void testToggleNodeEvent() {
        treeAttributes.set(TreeAttributes.toggleType, SwitchType.AJAX.toString().toLowerCase());
        List<Event> testedEvents = Lists.newArrayList(Event.CLICK, Event.CONTEXTCLICK, Event.DBLCLICK);
        for (boolean toggleByHandle : new boolean[]{ true, false }) {
            tree.advanced().setupToggleByHandle(toggleByHandle);
            for (Event event : testedEvents) {
                treeAttributes.set(TreeAttributes.toggleNodeEvent, event.toString());
                if (!toggleByHandle) {
                    tree.advanced().setupToggleNodeEvent(event);
                }
                (tree.advanced().getFirstNode().advanced().isExpanded() ? collapseFirstNodeAjaxAction
                    : expandFirstNodeAjaxAction).perform();
            }
        }
    }

    @Test
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = { "simpleSwingTreeNode", "simpleRichFacesTreeDataModel" })
    public void testToggleType() {
        treeAttributes.set(TreeAttributes.toggleType, SwitchType.AJAX.toString().toLowerCase());
        getGuarded(tree, SwitchType.AJAX).expandNode(0);

        treeAttributes.set(TreeAttributes.toggleType, SwitchType.CLIENT.toString().toLowerCase());
        getGuarded(tree, SwitchType.CLIENT).expandNode(1);

        treeAttributes.set(TreeAttributes.toggleType, SwitchType.SERVER.toString().toLowerCase());
        getGuarded(tree, SwitchType.SERVER).expandNode(2);
    }

    @Test
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = { "simpleSwingTreeNode", "simpleRichFacesTreeDataModel" })
    public void testTreeSelectionChangeListener() {
        selectFirstNodeAjaxAction.perform();
        page.assertListener(PhaseId.APPLY_REQUEST_VALUES, "selection change listener invoked");
    }

    @Test(groups = { "Future" })
    @IssueTracking("https://issues.jboss.org/browse/RF-11766")
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = "simpleRichFacesTreeNode")
    public void testTreeSelectionChangeListenerWithSimpleTreeNode() {
        testTreeSelectionChangeListener();
    }

    @Test
    @UseWithField(field = "sample", valuesFrom = STRINGS, value = { "simpleSwingTreeNode", "simpleRichFacesTreeDataModel" })
    public void testTreeToggleListener() {
        expandFirstNodeAjaxAction.perform();
        page.assertListener(PhaseId.PROCESS_VALIDATIONS, "tree toggle listener invoked");
    }
}
