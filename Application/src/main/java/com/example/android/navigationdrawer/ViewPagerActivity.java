/*
 * Copyright (C) 2013 The Android Open Source Project
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

package com.example.android.navigationdrawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.navigationdrawer.content.ContentItem;

import java.util.ArrayList;

/**
 * This sample shows you how a provide a {@link ShareActionProvider} with ActionBarCompat,
 * backwards compatible to API v7.
 * <p>
 * The sample contains a {@link ViewPager} which displays content of differing types: image and
 * text. When a new item is selected in the ViewPager, the ShareActionProvider is updated with
 * a share intent specific to that content.
 * <p>
 * This Activity extends from {@link ActionBarActivity}, which provides all of the function
 * necessary to display a compatible Action Bar on devices running Android v2.1+.
 */
public class ViewPagerActivity extends ActionBarActivity {

    // The items to be displayed in the ViewPager
    private final ArrayList<ContentItem> mItems = getSampleContent();

    // Keep reference to the ShareActionProvider from the menu
    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set content view (which contains a CheeseListFragment)
        setContentView(R.layout.sample_main);

        // Retrieve the ViewPager from the content view
        ViewPager vp = (ViewPager) findViewById(R.id.viewpager);

        // Set an OnPageChangeListener so we are notified when a new item is selected
        vp.setOnPageChangeListener(mOnPageChangeListener);

        // Finally set the adapter so the ViewPager can display items
        vp.setAdapter(mPagerAdapter);//페이지가 번경되었을떄 핸들러 실행
    }

    // BEGIN_INCLUDE(get_sap)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu resource
        getMenuInflater().inflate(R.menu.main_menu, menu);

        // Retrieve the share menu item
        MenuItem shareItem = menu.findItem(R.id.menu_share);

        // Now get the ShareActionProvider from the item
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);

        // Get the ViewPager's current item position and set its ShareIntent.
        int currentViewPagerItem = ((ViewPager) findViewById(R.id.viewpager)).getCurrentItem();
        setShareIntent(currentViewPagerItem);

        return super.onCreateOptionsMenu(menu);
    }
    // END_INCLUDE(get_sap)

    /**
     * A PagerAdapter which instantiates views based on the ContentItem's content type.
     */
    private final PagerAdapter mPagerAdapter = new PagerAdapter() {
        LayoutInflater mInflater;

        @Override
        public int getCount() {
            return mItems.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // Just remove the view from the ViewPager
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // Ensure that the LayoutInflater is instantiated
            if (mInflater == null) {
                mInflater = LayoutInflater.from(ViewPagerActivity.this);
            }

            // Get the item for the requested position
            final ContentItem item = mItems.get(position);

            // The view we need to inflate changes based on the type of content
            switch (item.contentType) {
                case ContentItem.CONTENT_TYPE_TEXT: {
                    // Inflate item layout for text
                    TextView tv = (TextView) mInflater
                            .inflate(R.layout.item_text, container, false);

                    // Set text content using it's resource id
                    tv.setText(item.contentResourceId);

                    // Add the view to the ViewPager
                    container.addView(tv);
                    return tv;
                }
                case ContentItem.CONTENT_TYPE_IMAGE: {
                    // Inflate item layout for images
                    ImageView iv = (ImageView) mInflater
                            .inflate(R.layout.item_image, container, false);

                    // Load the image from it's content URI
                    iv.setImageURI(item.getContentUri());

                    // Add the view to the ViewPager
                    container.addView(iv);
                    return iv;
                }
            }

            return null;
        }
    }; //익명클래스로 어댑터 객체 생성

    private void setShareIntent(int position) {
        // BEGIN_INCLUDE(update_sap)
        if (mShareActionProvider != null) {
            // Get the currently selected item, and retrieve it's share intent
            ContentItem item = mItems.get(position);
            Intent shareIntent = item.getShareIntent(ViewPagerActivity.this);

            // Now update the ShareActionProvider with the new share intent
            mShareActionProvider.setShareIntent(shareIntent);
        }
        // END_INCLUDE(update_sap)
    }

    /**
     * A OnPageChangeListener used to update the ShareActionProvider's share intent when a new item
     * is selected in the ViewPager.
     */
    private final ViewPager.OnPageChangeListener mOnPageChangeListener
            = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // NO-OP
        }

        @Override
        public void onPageSelected(int position) {
            setShareIntent(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            // NO-OP
        }
    };

    /**
     * @return An ArrayList of ContentItem's to be displayed in this sample
     */
    static ArrayList<ContentItem> getSampleContent() {
        ArrayList<ContentItem> items = new ArrayList<ContentItem>();

        /*이미지 수정*/
        //                                         설명
        items.add(new ContentItem(ContentItem.CONTENT_TYPE_TEXT, R.string.quote_1));//텍스트
        items.add(new ContentItem(ContentItem.CONTENT_TYPE_IMAGE, "friend1_1.jpg"));//이미지
        items.add(new ContentItem(ContentItem.CONTENT_TYPE_IMAGE, "friend1_2.jpg"));//이미지
        items.add(new ContentItem(ContentItem.CONTENT_TYPE_IMAGE, "friend1_3.jpg"));//이미지
        items.add(new ContentItem(ContentItem.CONTENT_TYPE_IMAGE, "friend1_4.jpg"));//이미지

        items.add(new ContentItem(ContentItem.CONTENT_TYPE_TEXT, R.string.quote_2));//텍스트
        items.add(new ContentItem(ContentItem.CONTENT_TYPE_IMAGE, "friend2_1.jpg"));//이미지
        items.add(new ContentItem(ContentItem.CONTENT_TYPE_IMAGE, "friend2_2.jpg"));//이미지
        items.add(new ContentItem(ContentItem.CONTENT_TYPE_IMAGE, "friend2_3.jpg"));//이미지
        items.add(new ContentItem(ContentItem.CONTENT_TYPE_IMAGE, "friend2_4.jpg"));//이미지

        items.add(new ContentItem(ContentItem.CONTENT_TYPE_TEXT, R.string.quote_3));//텍스트
        items.add(new ContentItem(ContentItem.CONTENT_TYPE_IMAGE, "friend3_1.jpg"));//이미지
        items.add(new ContentItem(ContentItem.CONTENT_TYPE_IMAGE, "friend3_2.jpg"));//이미지
        items.add(new ContentItem(ContentItem.CONTENT_TYPE_IMAGE, "friend3_3.jpg"));//이미지
        //items.add(new ContentItem(ContentItem.CONTENT_TYPE_TEXT, "friend3_4.jpg"));//이미지

        items.add(new ContentItem(ContentItem.CONTENT_TYPE_TEXT, R.string.quote_4));//텍스트
        items.add(new ContentItem(ContentItem.CONTENT_TYPE_IMAGE, "friend4_1.jpg"));//이미지
        items.add(new ContentItem(ContentItem.CONTENT_TYPE_IMAGE, "friend4_2.jpg"));//이미지
        items.add(new ContentItem(ContentItem.CONTENT_TYPE_IMAGE, "friend4_3.jpg"));//이미지
        items.add(new ContentItem(ContentItem.CONTENT_TYPE_IMAGE, "friend4_4.jpg"));//이미지


        items.add(new ContentItem(ContentItem.CONTENT_TYPE_TEXT, R.string.quote_5));//텍스트
        items.add(new ContentItem(ContentItem.CONTENT_TYPE_IMAGE, "friend5_1.jpg"));//이미지
        items.add(new ContentItem(ContentItem.CONTENT_TYPE_IMAGE, "friend5_2.jpg"));//이미지
        items.add(new ContentItem(ContentItem.CONTENT_TYPE_IMAGE, "friend5_3.jpg"));//이미지
        //items.add(new ContentItem(ContentItem.CONTENT_TYPE_TEXT, "friend5_4.jpg"));//이미지


        items.add(new ContentItem(ContentItem.CONTENT_TYPE_TEXT, R.string.quote_6));//텍스트
        items.add(new ContentItem(ContentItem.CONTENT_TYPE_IMAGE, "friend6_1.jpg"));//이미지
        items.add(new ContentItem(ContentItem.CONTENT_TYPE_IMAGE, "friend6_2.jpg"));//이미지
        items.add(new ContentItem(ContentItem.CONTENT_TYPE_IMAGE, "friend6_3.jpg"));//이미지
        //items.add(new ContentItem(ContentItem.CONTENT_TYPE_TEXT, "friend6_4.jpg"));//이미지


        return items;
    }

}