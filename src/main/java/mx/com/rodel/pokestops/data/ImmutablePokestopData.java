package mx.com.rodel.pokestops.data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;
import org.spongepowered.api.data.value.immutable.ImmutableMapValue;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

import com.flowpowered.math.vector.Vector3i;

public class ImmutablePokestopData extends AbstractImmutableData<ImmutablePokestopData, PokeStopData>{
	private final Map<UUID, Long> data;
	private final Vector3i initialLocation;
	
	public ImmutablePokestopData(Map<UUID, Long> data, Vector3i initialLocation) {
		this.data = data;
		this.initialLocation = initialLocation;
	}
	
	public ImmutablePokestopData() {
		this(new HashMap<>(), new Vector3i());
	}
	
	protected ImmutableMapValue<UUID, Long> data(){
		return Sponge.getRegistry().getValueFactory().createMapValue(PokeStopKeys.PICKUP_DATA, data, new HashMap<>()).asImmutable();
	}
	
	protected ImmutableValue<Vector3i> initialLocation(){
		return Sponge.getRegistry().getValueFactory().createValue(PokeStopKeys.INITIAL_LOCATION, initialLocation, new Vector3i()).asImmutable();
	}

	private Map<UUID, Long> getData(){
		return data;
	}
	
	private Vector3i getInitialLocation(){
		return initialLocation;
	}
	
	@Override
	protected void registerGetters() {
		registerFieldGetter(PokeStopKeys.PICKUP_DATA, this::getData);
		registerKeyValue(PokeStopKeys.PICKUP_DATA, this::data);
		
		registerFieldGetter(PokeStopKeys.INITIAL_LOCATION, this::getInitialLocation);
		registerKeyValue(PokeStopKeys.INITIAL_LOCATION, this::initialLocation);
	}
	
	@Override
	public PokeStopData asMutable() {
		return new PokeStopData(data, initialLocation);
	}
	
	@Override
	public int getContentVersion() {
		return 1;
	}
	
	@Override
	public DataContainer toContainer() {
		return super.toContainer()
				.set(PokeStopKeys.PICKUP_DATA, getData())
				.set(PokeStopKeys.INITIAL_LOCATION, getInitialLocation());
	}
}
