package com.liferay.folder.apio.internal.architect.resource;

import static com.liferay.portal.apio.idempotent.Idempotent.idempotent;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.folder.apio.architect.identifier.FolderIdentifier;
import com.liferay.folder.apio.architect.identifier.RootFolderIdentifier;
import com.liferay.folder.apio.internal.architect.form.FolderForm;
import com.liferay.portal.apio.permission.HasPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(immediate = true)
public class FolderNestedCollectionResource
	implements NestedCollectionResource
		<Folder, Long, FolderIdentifier, Long, RootFolderIdentifier> {
    
    private Folder _addFolder(long groupId, FolderForm folderForm)
		throws PortalException {

		long parentFolderId = 0;

		return _dlAppService.addFolder(
			groupId, parentFolderId, folderForm.getName(),
			folderForm.getDescription(), new ServiceContext());
	}

}
