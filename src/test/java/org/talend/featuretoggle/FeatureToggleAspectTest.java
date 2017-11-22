// ============================================================================
// Copyright (C) 2006-2017 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// https://github.com/Talend/data-prep/blob/master/LICENSE
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.featuretoggle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class FeatureToggleAspectTest {
    @Autowired
    ToBeTested toBeTested;

    @Test
    public void testFeatureToggleAspectNullReturn(){
        Object result = toBeTested.shouldAlwaysReturnNull();
        assertNull(result);
    }
    @Test
    public void testFeatureToggleAspectObjectReturn(){
        Object result = toBeTested.shouldReturnAnObject();
        assertNotNull(result);
    }

    @Test
    public void testFeatureToggleAspectEmptyListReturn(){
        Object result = toBeTested.shouldReturnAnEmptyList();
        assertNotNull(result);
        Assert.isInstanceOf(List.class, result);
    }
}
