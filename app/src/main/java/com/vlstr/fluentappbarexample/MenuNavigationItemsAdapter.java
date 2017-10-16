package com.vlstr.fluentappbarexample;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.MenuRes;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.vlstr.fluentappbarexample.FluentAppBar.MORE_ICON_TAG;

/**
 * Created by Valentin on 14/06/2017.
 */

public class MenuNavigationItemsAdapter extends RecyclerView.Adapter<MenuNavigationItemsAdapter.MenuNavItem> {

    private Context context;
    private View.OnClickListener onClickListener;
    private int foregroundColour;
    private boolean keepFluentRipple = true;

    private List<MenuEntry> navItems;

    public MenuNavigationItemsAdapter(Context context, @MenuRes int menuRes, View.OnClickListener onClickListener,
                                      int foregroundColour) {
        this.context = context;
        this.onClickListener = onClickListener;
        this.foregroundColour = foregroundColour;
        this.navItems = new ArrayList<>();

        populateNavigationItems(menuRes);
    }

    @Override
    public MenuNavItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_nav_item, parent, false);
        v.getLayoutParams().width = parent.getMeasuredWidth() / navItems.size();
        return new MenuNavItem(v);
    }

    @Override
    public void onBindViewHolder(MenuNavItem holder, int position) {
        MenuEntry item = navItems.get(position);
        holder.label.setText(item.getTitle());
        holder.label.setTextColor(foregroundColour);
        holder.icon.setImageDrawable(item.getIcon());
        holder.icon.setColorFilter(foregroundColour);
        holder.itemView.setTag(item.getResId());

        if (item.getTitle().isEmpty()) setupMoreIcon(holder);

        handleRipple(holder);

        holder.itemView.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return navItems.size();
    }

    public void setForegroundColour(int foregroundColour) {
        this.foregroundColour = foregroundColour;
    }

    public void setKeepFluentRipple(boolean keepFluentRipple) {
        this.keepFluentRipple = keepFluentRipple;
        notifyDataSetChanged();
    }

    private void handleRipple(MenuNavigationItemsAdapter.MenuNavItem holder) {
        if (!keepFluentRipple) {
            TypedValue outValue = new TypedValue();
            context.getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
            holder.itemView.setBackgroundResource(outValue.resourceId);
        }
    }

    private void populateNavigationItems(int menuRes){
        MenuParserHelper.parseMenu(context, menuRes, navItems);
        Drawable moreIcon = context.getResources().getDrawable(R.drawable.more);
        navItems.add(new MenuEntry("", moreIcon, 0));
    }

    private void setupMoreIcon(MenuNavItem menuNavItem){
        menuNavItem.itemView.setFocusable(false);
        menuNavItem.itemView.setFocusableInTouchMode(false);
        menuNavItem.itemView.setBackground(null);
        menuNavItem.icon.setColorFilter(foregroundColour);

        menuNavItem.itemView.setTag(MORE_ICON_TAG);
    }

    class MenuNavItem extends RecyclerView.ViewHolder{

        ImageView icon;
        TextView label;

        MenuNavItem(View itemView){
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.nav_item_icon);
            label = (TextView) itemView.findViewById(R.id.nav_item_label);
        }
    }
}
