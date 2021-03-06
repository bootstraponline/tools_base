/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.build.gradle.internal.dsl;

import static com.android.build.OutputFile.NO_FILTER;

import com.android.annotations.NonNull;
import com.google.common.collect.Sets;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Data for per-density splits.
 */
public class DensitySplitOptions extends SplitOptions {

    private boolean strict = true;
    private Set<String> compatibleScreens;

    /**
     * TODO: Document.
     */
    public boolean isStrict() {
        return strict;
    }

    public void setStrict(boolean strict) {
        this.strict = strict;
    }

    public void setCompatibleScreens(@NonNull List<String> sizes) {
        compatibleScreens = Sets.newHashSet(sizes);
    }

    public void compatibleScreens(@NonNull String... sizes) {
        if (compatibleScreens == null) {
            compatibleScreens = Sets.newHashSet(sizes);
            return;
        }

        compatibleScreens.addAll(Arrays.asList(sizes));
    }

    /**
     * TODO: Document.
     */
    @NonNull
    public Set<String> getCompatibleScreens() {
        if (compatibleScreens == null) {
            return Collections.emptySet();
        }
        return compatibleScreens;
    }

    @NonNull
    @Override
    public Set<String> getApplicableFilters(@NonNull Set<String> allFilters) {
        // use a LinkedHashSet so iteration order follows insertion order.
        LinkedHashSet<String> filters = new LinkedHashSet<String>();

        // if splitting enabled, then add an entry with no filter for universal
        // add it FIRST, since code assume that the first variant output will be the universal one.
        if (isEnable()) {
            filters.add(NO_FILTER);
        }

        filters.addAll(super.getApplicableFilters(allFilters));
        return filters;
    }
}
