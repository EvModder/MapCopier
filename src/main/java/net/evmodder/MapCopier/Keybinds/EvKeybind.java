package net.evmodder.MapCopier.Keybinds;

import java.util.HashSet;
import java.util.function.Function;
import net.evmodder.MapCopier.Main;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil.Type;

public final class EvKeybind extends KeyBinding{
	public static HashSet<EvKeybind> allowedInInventory = new HashSet<>();
	public final Function<Screen, Boolean> allowInScreen;

	public final Runnable onPressedSupplier, onReleasedSupplier;
	public EvKeybind(String translationKey, Runnable onPressed, Runnable onReleased, Function<Screen, Boolean> allowInScreen){
		super("key."+Main.MOD_ID+"."+translationKey, Type.KEYSYM, -1, Main.KEYBIND_CATEGORY);
		onPressedSupplier = onPressed;
		onReleasedSupplier = onReleased;
		this.allowInScreen = allowInScreen;
		if(allowInScreen != null) allowedInInventory.add(this);
		Main.LOGGER.info("Registered keybind: "+translationKey);
	}

	public EvKeybind(String translationKey, Runnable onPressed){this(translationKey, onPressed, ()->{}, a->false);}
	public EvKeybind(String translationKey){this(translationKey, ()->{}, ()->{}, a->false);}

	public EvKeybind(String translationKey, Runnable onPressed, Function<Screen, Boolean> allowInScreen){this(translationKey, onPressed, ()->{}, allowInScreen);}
	public EvKeybind(String translationKey, Function<Screen, Boolean> allowInScreen){this(translationKey, ()->{}, ()->{}, allowInScreen);}

	@Override public final void setPressed(boolean pressed){
		if(pressed != isPressed()){
			if(pressed){
				Main.LOGGER.info("Keybind pressed: "+getTranslationKey());
				onPressedSupplier.run();
			}
			else{
				Main.LOGGER.info("Keybind released: "+getTranslationKey());
				onReleasedSupplier.run();
			}
		}
		super.setPressed(pressed);
	}
}