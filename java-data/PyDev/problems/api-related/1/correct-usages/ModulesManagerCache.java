package org.python.pydev.editor.codecompletion.revisited;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.IDocument;
import org.python.pydev.core.IModule;
import org.python.pydev.core.IModulesManager;
import org.python.pydev.core.IPythonNature;
import org.python.pydev.core.ModulesKey;
import org.python.pydev.core.REF;
import org.python.pydev.core.cache.Cache;
import org.python.pydev.core.cache.LRUCache;
import org.python.pydev.editor.codecompletion.PyCodeCompletion;
import org.python.pydev.editor.codecompletion.revisited.modules.AbstractModule;
import org.python.pydev.editor.codecompletion.revisited.modules.CompiledModule;
import org.python.pydev.editor.codecompletion.revisited.modules.EmptyModule;
import org.python.pydev.editor.codecompletion.revisited.modules.SourceModule;
import org.python.pydev.plugin.PydevPlugin;

public abstract class ModulesManager implements IModulesManager, Serializable {

	private final class ModulesManagerCache extends LRUCache<ModulesKey, AbstractModule> {
		private ModulesManagerCache(int size) {
			super(size);
		}

		/**
		 * Overriden so that if we do not find the key, we have the chance to create it.
		 */
		public AbstractModule getObj(ModulesKey key) {
			AbstractModule obj = super.getObj(key);
			if(obj == null && modulesKeys.containsKey(key)){
				key = modulesKeys.get(key); //get the 'real' key
				obj = AbstractModule.createEmptyModule(key.name, key.file);
				this.add(key, obj);
			}
			return obj;
		}
	}
}
