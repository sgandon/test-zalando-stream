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

import org.springframework.stereotype.Component;
import org.talend.featuretoggle.annotation.FeatureToggle;

import java.util.ArrayList;
import java.util.List;

@Component
class ToBeTested {

    @FeatureToggle("not-authorized-feature")
    public Object shouldAlwaysReturnNull() {
        return new Object();
    }

    @FeatureToggle("authorized-feature")
    public Object shouldReturnAnObject() {
        return new Object();
    }

    @FeatureToggle(value = "not-authorized-feature", defaultReturns = FeatureToggle.RETURNS.EMPTY_LIST)
    public List shouldReturnAnEmptyList() {
        return null;
    }
}
