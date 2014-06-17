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
package org.richfaces.tests.metamer.bean.rich;


import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.richfaces.component.UIRichMessage;
import org.richfaces.tests.metamer.Attributes;
import org.richfaces.tests.metamer.bean.abstractions.MessageTestingBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple bean for rich:message component example.
 *
 * @author <a href="mailto:jjamrich@redhat.com">Jan Jamrich</a>
 * @version $Revision: 23138 $
 */
@ManagedBean(name = "richMessageBean")
@SessionScoped
public class RichMessageBean extends MessageTestingBean implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(RichMessageBean.class);
    private static final long serialVersionUID = 1L;

    @PostConstruct
    public void init() {
        LOGGER.debug("initializing bean " + getClass().getName());
        attributes = Attributes.getComponentAttributesFromFacesConfig(UIRichMessage.class, getClass());

        // setting up incorrect values to fire-up validator
        simpleInput1 = "-5";
        simpleInput2 = "-5";

        // to get working this component example correctly is required that for
        // property has been initialized
        attributes.setAttribute("for", "simpleInput1");
        attributes.setAttribute("rendered", true);
        attributes.setAttribute("escape", true);
        attributes.setAttribute("showSummary", true);
        attributes.setAttribute("ajaxRendered", true); // make sense for a4j:commandButton submit
    }
}
