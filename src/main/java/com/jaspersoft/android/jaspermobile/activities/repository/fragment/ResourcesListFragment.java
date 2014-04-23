/*
 * Copyright (C) 2012-2014 Jaspersoft Corporation. All rights reserved.
 * http://community.jaspersoft.com/project/jaspermobile-android
 *
 * Unless you have purchased a commercial license agreement from Jaspersoft,
 * the following license terms apply:
 *
 * This program is part of Jaspersoft Mobile for Android.
 *
 * Jaspersoft Mobile is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Jaspersoft Mobile is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Jaspersoft Mobile for Android. If not, see
 * <http://www.gnu.org/licenses/lgpl>.
 */

package com.jaspersoft.android.jaspermobile.activities.repository.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.github.rtyley.android.sherlock.roboguice.fragment.RoboSherlockListFragment;
import com.jaspersoft.android.jaspermobile.activities.repository.ResourcesActivity;
import com.jaspersoft.android.sdk.client.oxm.resource.ResourceLookup;
import com.jaspersoft.android.sdk.ui.adapters.ResourceLookupListAdapter;

import java.util.Comparator;
import java.util.List;

/**
 * @author Ivan Gadzhega
 * @since 2.0
 */
public class ResourcesListFragment extends RoboSherlockListFragment implements ResourcesFragment {

    private ArrayAdapter<ResourceLookup> resourcesAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        registerForContextMenu(getListView());
        ((ResourcesActivity) getActivity()).loadResources(false);
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        ResourceLookup resource = (ResourceLookup) getListView().getItemAtPosition(position);
        ((ResourcesActivity) getActivity()).onResourceClick(resource);
    }

    //---------------------------------------------------------------------
    // ResourcesFragment
    //---------------------------------------------------------------------

    @Override
    public void initResourcesAdapter(List<ResourceLookup> resourceLookups) {
        initResourcesAdapter(resourceLookups, null);
    }

    @Override
    public void initResourcesAdapter(List<ResourceLookup> resourceLookups, Comparator<ResourceLookup> comparator) {
        ResourceLookupListAdapter adapter = new ResourceLookupListAdapter(getActivity(), resourceLookups);
        if (comparator != null) adapter.sort(comparator);
        setResourcesAdapter(adapter);
    }

    @Override
    public void setResourcesAdapter(ArrayAdapter<ResourceLookup> adapter) {
        resourcesAdapter = adapter;
        super.setListAdapter(adapter);
    }

    @Override
    public ArrayAdapter<ResourceLookup> getResourcesAdapter() {
        return resourcesAdapter;
    }

    @Override
    public AbsListView getResourcesView() {
        return getListView();
    }

    @Override
    public void setListAdapter(ListAdapter adapter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ViewType getViewType() {
        return ViewType.LIST;
    }
}
