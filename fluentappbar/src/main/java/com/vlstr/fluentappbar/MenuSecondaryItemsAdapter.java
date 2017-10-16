package com.vlstr.fluentappbar;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.MenuRes;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Valentin on 16/05/2017.
 */

class MenuSecondaryItemsAdapter extends RecyclerView.Adapter<MenuSecondaryItemsAdapter.MenuItem> {

    private Context context;
    private View.OnClickListener onClickListener;
    private int foregroundColour;
    private boolean keepFluentRipple = true;

    private ArrayList<MenuEntry> itemss;

    MenuSecondaryItemsAdapter(Context context, @MenuRes int secondaryMenuId, View.OnClickListener onClickListener,
                                     @ColorInt int foregroundColour) {
        this.context = context;
        this.onClickListener = onClickListener;
        this.foregroundColour = foregroundColour;
        this.itemss = new ArrayList<>();

        MenuParserHelper.parseMenu(context, secondaryMenuId, itemss);
    }

    @Override
    public MenuItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
        return new MenuItem(v);

    }

    @Override
    public void onBindViewHolder(MenuItem holder, int position) {
        holder.label.setText(itemss.get(position).getTitle());
        holder.label.setTextColor(foregroundColour);
        holder.icon.setImageDrawable(itemss.get(position).getIcon());
        holder.icon.setColorFilter(foregroundColour);
        holder.itemView.setTag(itemss.get(position).getResId());

        handleRipple(holder);

        holder.itemView.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return itemss.size();
    }


    public void setForegroundColour(@ColorInt int foregroundColour) {
        this.foregroundColour = foregroundColour;
    }

    public void setKeepFluentRipple(boolean keepFluentRipple) {
        this.keepFluentRipple = keepFluentRipple;
        notifyDataSetChanged();
    }

    private void handleRipple(MenuItem holder) {
        if (!keepFluentRipple) {
            TypedValue outValue = new TypedValue();
            context.getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
            holder.itemView.setBackgroundResource(outValue.resourceId);
        }
    }

    class MenuItem extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView label;

        MenuItem(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.menu_item_icon);
            label = (TextView) itemView.findViewById(R.id.menu_item_label);
        }
    }
}
