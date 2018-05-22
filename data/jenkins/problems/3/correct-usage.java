package hudson.model;
import hudson.security.Permission;
import hudson.security.PermissionGroup;

public <T extends Item> T getItemByFullName(String fullName, Class<T> type) {
        StringTokenizer tokens = new StringTokenizer(fullName,"/");
        ItemGroup parent = this;

        if(!tokens.hasMoreTokens()) return null;    // for example, empty full name.

        while(true) {
            Item item = parent.getItem(tokens.nextToken());
            if(!tokens.hasMoreTokens()) {
                if(type.isInstance(item))
                    return type.cast(item);
                else
                    return null;
            }

            if(!(item instanceof ItemGroup))
                return null;    // this item can't have any children

            if (!item.hasPermission(Item.READ))
                return null;

            parent = (ItemGroup) item;
        }
    }
