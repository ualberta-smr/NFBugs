
package com.mojang.mojam;

import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;

import com.mojang.mojam.Keys.Key;
import com.mojang.mojam.gui.AJoyBindingsMenu;
import com.mojang.mojam.gui.JoyBindingsMenu;

public class JoypadHandler {
    public Controller controller;
    public Button[] buttons;
    public Axis[] axes;
    
    public int buttonCount;
    public int axisCount;
    
    public JoypadHandler(int index) {
      controller = Controllers.getController(index);

      buttonCount = controller.getButtonCount();
      buttons = new Button[buttonCount];

      axisCount = controller.getAxisCount();
      axes = new Axis[axisCount + 2]; 

      // ...

      }

}
