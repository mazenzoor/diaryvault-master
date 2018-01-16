package cloud.thecode.android.diaryvault;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.support.android.designlibdemo.R;

import java.util.List;

/**
 * Created by Mazen on 1/16/2018.
 */

public class PostListFavoritesFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView rv = (RecyclerView) inflater.inflate(
                R.layout.fragment_post_list, container, false);
        setupRecyclerView(rv);
        return rv;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        DatabaseHandler dbh = new DatabaseHandler(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(new PostListFragment.SimpleStringRecyclerViewAdapter(getActivity(),
                dbh.getAllPostsFavorites()));
    }

    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<PostListFragment.SimpleStringRecyclerViewAdapter.ViewHolder> {

        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
        private List<Post> mValues;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public String mTitle;
            public String mDescription;
            public String mRating;
            public Uri mImage;
            public String mDate;
            public String mId;

            public final View mView;
            public final ImageView mImageView;
            public final TextView mTextView;
            public final TextView mDateView;
            public final TextView mRatingView;
            public final TextView mIdView;
            public final LinearLayout mEdit;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = (ImageView) view.findViewById(R.id.avatar);
                mTextView = (TextView) view.findViewById(android.R.id.text1);
                mDateView = (TextView) view.findViewById(R.id.post_date);
                mRatingView = (TextView) view.findViewById(R.id.rating);
                mIdView = (TextView) view.findViewById(R.id.post_id);
                mEdit = (LinearLayout) view.findViewById(R.id.edit_button);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mTextView.getText();
            }
        }

        public SimpleStringRecyclerViewAdapter(Context context, List<Post> items) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
            mValues = items;
        }

        @Override
        public PostListFragment.SimpleStringRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
            view.setBackgroundResource(mBackground);
            return new PostListFragment.SimpleStringRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final PostListFragment.SimpleStringRecyclerViewAdapter.ViewHolder holder, int position) {
            holder.mTitle = mValues.get(position).getTitle();
            holder.mId = mValues.get(position).getId() + "";
            holder.mTextView.setText(mValues.get(position).getTitle());
            holder.mImage = mValues.get(position).getImage();
            holder.mDateView.setText(mValues.get(position).getDate());
            holder.mRatingView.setText(mValues.get(position).getRating() + " Stars");
            holder.mIdView.setText(mValues.get(position).getId() + "");

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, PostDetailActivity.class);
                    intent.putExtra(PostDetailActivity.EXTRA_NAME, holder.mTitle);
                    intent.putExtra("Description", holder.mDescription);
                    intent.putExtra("Image", holder.mImage.getPath());
                    intent.putExtra("Rating", holder.mRating);
                    intent.putExtra("Date", holder.mDate);
                    context.startActivity(intent);
                }
            });

            holder.mEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent i = new Intent(context, EditPost.class);
                    i.putExtra("Id", holder.mId);
                    context.startActivity(i);
                }
            });


            //File f = new File(PostListFragment.getRealPathFromUri(, holder.mImage.toString()));
            /*Glide.with(holder.mImageView.getContext())
                    .load(f.getPath())
                    .fitCenter()
                    .into(holder.mImageView);

            //holder.mImageView.setImageURI(holder.mImage);

            */

        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }
    }

    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
