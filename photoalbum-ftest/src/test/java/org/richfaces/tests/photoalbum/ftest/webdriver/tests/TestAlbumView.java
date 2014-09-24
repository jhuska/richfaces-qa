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
package org.richfaces.tests.photoalbum.ftest.webdriver.tests;

import java.util.List;

import org.jboss.arquillian.graphene.Graphene;
import org.richfaces.tests.photoalbum.ftest.webdriver.fragments.SlideShowPanel;
import org.richfaces.tests.photoalbum.ftest.webdriver.fragments.view.AlbumView;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;

import static org.jboss.arquillian.graphene.Graphene.guardAjax;
import static org.jboss.arquillian.graphene.Graphene.waitAjax;

/**
 *
 * @author <a href="mailto:jstefek@redhat.com">Jiri Stefek</a>
 */
public class TestAlbumView extends AbstractPhotoalbumTest {

    @Test
    public void testImageSizeSlider() {
        page.getLeftPanel().openAlbumInPredefinedGroup("Animals", "Nature");
        AlbumView albumView = page.getContentPanel().albumView();
        waitAjax().until().element(albumView.getSlider()
                .advanced().getRootElement()).is().visible();
        Graphene.guardAjax(albumView.getSlider()).slideToValue(1);
        List<AlbumView.PhotoInfo> photos = albumView.getPhotos();
        photos.get(0).checkAll(200, "1750979205_6e51b47ce9_o.jpg", "Dec 17, 2009");
        photos.get(2).checkAll(200, "4845901485_62db3c5d62_o.jpg", "Dec 17, 2009");
        photos.get(5).checkAll(200, "5395800621_c0bc80ca53_o.jpg", "Dec 17, 2009");
        Graphene.guardAjax(albumView.getSlider()).slideToValue(0);
        photos = albumView.getPhotos();
        photos.get(0).checkAll(80, "1750979205_6e51b47ce9_o.jpg", "Dec 17, 2009");
        photos.get(2).checkAll(80, "4845901485_62db3c5d62_o.jpg", "Dec 17, 2009");
        photos.get(5).checkAll(80, "5395800621_c0bc80ca53_o.jpg", "Dec 17, 2009");
    }

    @Test
    public void testSlideShow() {
        page.getLeftPanel().openAlbumInPredefinedGroup("Monuments and just buildings", "Monuments");
        AlbumView albumView = page.getContentPanel().albumView();
        waitAjax().until().element(albumView.getSlideShowLink()).is().visible();
        albumView.openSlideShow();
        SlideShowPanel slideShowPanel = page.getSlideShowPanel();
        slideShowPanel.advanced().waitUntilPopupIsVisible().perform();
        slideShowPanel.checkImagesInfoFromTooltip("Monuments and just buildings", Lists.newArrayList("8561041065_43449e8697_o.jpg", "2523480499_e988ddf4c1_o.jpg", "4065627169_2e3cea3acf_o.jpg"));
    }
}
