package openblocks.sync;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;

public class SyncableBoolean implements ISyncableObject {
	private boolean value = false;
	private boolean hasChanged = false;
	private int ticksSinceChange = 0;

	public SyncableBoolean(boolean value) {
		this.value = value;
	}

	public SyncableBoolean() {
		this(false);
	}

	@Override
	public void readFromStream(DataInputStream stream) throws IOException {
		value = stream.readBoolean();
	}

	public int getTicksSinceChange() {
		return ticksSinceChange;
	}

	public void setValue(boolean val) {
		if (val != value) {
			value = val;
			setHasChanged();
		}
	}

	public boolean getValue() {
		return value;
	}
	
	public void negate() {
		setValue(!value);
	}

	@Override
	public void writeToStream(DataOutputStream stream, boolean fullData) throws IOException {
		stream.writeBoolean(value);
	}

	@Override
	public void writeToNBT(NBTTagCompound tag, String name) {
		tag.setBoolean(name, value);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag, String name) {
		if (tag.hasKey(name)) {
			value = tag.getBoolean(name);
		}
	}

	@Override
	public boolean hasChanged() {
		return hasChanged;
	}

	@Override
	public void resetChangeStatus() {
		hasChanged = false;
		ticksSinceChange++;
	}

	@Override
	public void setHasChanged() {
		hasChanged = true;
		ticksSinceChange = 0;
	}
}