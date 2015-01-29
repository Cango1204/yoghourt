package com.example.hbjia.level2.actionbar;

import android.content.Context;
import android.view.ActionProvider;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Toast;

/**
 * Created by hbjia on 2015/1/29.
 */
public class CustomActionProvider extends ActionProvider {
    private Context mContext;

    public CustomActionProvider(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public View onCreateActionView() {
        return null;
    }

    @Override
    public void onPrepareSubMenu(SubMenu subMenu) {
        subMenu.clear();
        subMenu.add("Compass").setIcon(android.R.drawable.ic_menu_compass)
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Toast.makeText(mContext, "Compass pressed", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
        subMenu.add("Edit").setIcon(android.R.drawable.ic_menu_edit)
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Toast.makeText(mContext, "Edit pressed", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
    }

    @Override
    public boolean hasSubMenu() {
        return true;
    }
}
