/*
 * Copyright (C) 2014 Francesco Azzola
 *  Surviving with Android (http://www.survivingwithandroid.com)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.davidmlee.kata.moviesearch100.listadapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.davidmlee.kata.moviesearch100.R;
import com.davidmlee.kata.moviesearch100.controller.MainController;
import com.davidmlee.kata.moviesearch100.controller.MovieDetailController;
import com.davidmlee.kata.moviesearch100.core.MyApp;
import com.davidmlee.kata.moviesearch100.models.FilmSummaryEntity;
import com.davidmlee.kata.moviesearch100.util.Util;

import java.util.List;

public class FilmListAdapter extends RecyclerView.Adapter<FilmListAdapter.FilmViewHolder> {

    private static List<FilmSummaryEntity> filmEntityList;

    public FilmListAdapter(List<FilmSummaryEntity> filmEntityList) {
        FilmListAdapter.filmEntityList = filmEntityList;
    }

    @Override
    public int getItemCount() {
        return filmEntityList.size();
    }

    @Override
    public void onBindViewHolder(FilmViewHolder holder, int position) {
        FilmSummaryEntity fe = filmEntityList.get(position);
        holder.id = fe.getId();
        holder.tvTitle.setText(fe.getTitle());
        String releaseDate = fe.getReleaseDate();
        if (Util.isNullOrEmpty(releaseDate)) {
            holder.tvDate.setText(MyApp.getStrRes(R.string.label_not_available));
        } else {
            holder.tvDate.setText(releaseDate);
        }
        holder.tvOverview.setText(fe.getOverview());
        // Should we fetch the next page?
        if (position == filmEntityList.size() - 1) {
            MainController.searchMoviesNextPage();
        }
    }

    @Override
    public FilmViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(R.layout.card_layout, viewGroup, false);
            return new FilmViewHolder(itemView);
    }

    static class FilmViewHolder extends RecyclerView.ViewHolder {
        protected String id;
        TextView tvTitle;
        TextView tvOverview;
        TextView tvDate;

        FilmViewHolder(View itemView) {
            super(itemView);
            this.tvTitle =  (TextView)itemView.findViewById(R.id.tv_title);
            this.tvDate = (TextView)itemView.findViewById(R.id.tv_date);
            this.tvOverview = (TextView)itemView.findViewById(R.id.tv_overview);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //int view_item_osition = getLayoutPosition();
                    int data_list_position = getAdapterPosition();
                    final String filmId = filmEntityList.get(data_list_position).getId();
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            MovieDetailController.queryMovieDetail(filmId);
                        }
                    }.start();
                }
            });
        }
    }
}
