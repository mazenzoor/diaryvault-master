/*
 * Copyright (C) 2015 The Android Open Source Project
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

package cloud.thecode.android.diaryvault;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.support.android.designlibdemo.R;

import java.io.File;

public class PostDetailActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "post_name";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        final String postTitle = intent.getStringExtra(EXTRA_NAME);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(postTitle);

        loadBackdrop();
    }

    private void loadBackdrop() {
         ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        imageView.setImageURI(Uri.parse(getIntent().getStringExtra("Image")));
        imageView.invalidate();
        //Glide.with(this).loadFromMediaStore(Uri.parse(getIntent().getStringExtra("Image"))).centerCrop().into(imageView);
        //imageView.setImageURI(getIntent().getStringExtra("Image"));
        Toast.makeText(this, Uri.parse(getIntent().getStringExtra("Image")).toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }
}
