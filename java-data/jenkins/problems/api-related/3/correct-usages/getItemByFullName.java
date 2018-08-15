package hudson.model;
import hudson.security.Permission;
import hudson.security.PermissionGroup;

@ExportedBean
public final class Hudson extends Node implements ItemGroup<TopLevelItem>, StaplerProxy, StaplerFallback, ViewGroup, AccessControlled, DescriptorByNameOwner {
        public <T extends Item> T getItemByFullName(String fullName, Class<T> type) {
                
                StringTokenizer tokens = new StringTokenizer(fullName,"/");
                ItemGroup parent = this;

                while(true) {
                    Item item = parent.getItem(tokens.nextToken());

                    if (!item.hasPermission(Item.READ))
                        return null;
                }
            }
}
