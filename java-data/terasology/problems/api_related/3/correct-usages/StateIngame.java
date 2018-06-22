 package org.terasology.engine.modes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.TeraOVR;
import org.terasology.asset.AssetManager;
import org.terasology.config.Config;
import org.terasology.engine.ComponentSystemManager;
import org.terasology.engine.GameEngine;
import org.terasology.engine.GameThread;
import org.terasology.engine.module.ModuleManager;
import org.terasology.engine.subsystem.DisplayDevice;
import org.terasology.entitySystem.entity.EntityManager;
import org.terasology.entitySystem.entity.EntityRef;
import org.terasology.entitySystem.entity.internal.EngineEntityManager;
import org.terasology.entitySystem.event.internal.EventSystem;
import org.terasology.entitySystem.systems.UpdateSubscriberSystem;
import org.terasology.game.Game;
import org.terasology.input.InputSystem;
import org.terasology.input.cameraTarget.CameraTargetSystem;
import org.terasology.logic.console.Console;
import org.terasology.module.Module;
import org.terasology.module.ModuleEnvironment;
import org.terasology.monitoring.PerformanceMonitor;
import org.terasology.network.NetworkMode;
import org.terasology.network.NetworkSystem;
import org.terasology.physics.engine.PhysicsEngine;
import org.terasology.registry.CoreRegistry;
import org.terasology.rendering.nui.NUIManager;
import org.terasology.rendering.nui.databinding.ReadOnlyBinding;
import org.terasology.rendering.oculusVr.OculusVrHelper;
import org.terasology.rendering.opengl.DefaultRenderingProcess;
import org.terasology.rendering.world.WorldRenderer;
import org.terasology.world.block.BlockManager;

import java.util.Collections;

public class StateIngame implements GameState {
	private NUIManager nuiManager;

	public void pattern() {

		nuiManager.getHUD().bindVisible(new ReadOnlyBinding<Boolean>() {
		    @Override
		    public Boolean get() {
			return !CoreRegistry.get(Config.class).getRendering().getDebug().isHudHidden();
		    }
		});

		// ...

		nuiManager.clear();
		nuiManager.getHUD().clearVisibleBinding();
	    }
}
