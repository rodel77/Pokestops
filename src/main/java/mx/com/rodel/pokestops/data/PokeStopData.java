package mx.com.rodel.pokestops.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.MapValue;
import org.spongepowered.api.data.value.mutable.Value;

import com.flowpowered.math.vector.Vector3i;

public class PokeStopData extends AbstractData<PokeStopData, ImmutablePokestopData>{
	private Map<UUID, Long> data;
	private Vector3i initialLocation;
	
	public PokeStopData() {
		this(new HashMap<>(), new Vector3i());
	}
	
	public PokeStopData(Map<UUID, Long> data, Vector3i initalLocation) {
		this.data = data;
		this.initialLocation = initalLocation;
		registerGettersAndSetters();
	}

	protected MapValue<UUID, Long> data(){
		return Sponge.getRegistry().getValueFactory().createMapValue(PokeStopKeys.PICKUP_DATA, this.data, new HashMap<>());
	}
	
	protected Value<Vector3i> initialLocation(){
		return Sponge.getRegistry().getValueFactory().createValue(PokeStopKeys.INITIAL_LOCATION, new Vector3i());
	}

	private Map<UUID, Long> getData(){
		return data;
	}

	private void setData(Map<UUID, Long> data){
		this.data = data;
	}
	
	private Vector3i getInitialLocation(){
		return initialLocation;
	}
	
	private void setInitialLocation(Vector3i initialLocation){
		this.initialLocation = initialLocation;
	}
	
	@Override
	protected void registerGettersAndSetters() {
		registerFieldGetter(PokeStopKeys.PICKUP_DATA, this::getData);
		registerFieldSetter(PokeStopKeys.PICKUP_DATA, this::setData);
		registerKeyValue(PokeStopKeys.PICKUP_DATA, this::data);
		
		registerFieldGetter(PokeStopKeys.INITIAL_LOCATION, this::getInitialLocation);
		registerFieldSetter(PokeStopKeys.INITIAL_LOCATION, this::setInitialLocation);
		registerKeyValue(PokeStopKeys.INITIAL_LOCATION, this::initialLocation);
	}
	
	@Override
	public Optional<PokeStopData> fill(DataHolder dataHolder) {
		PokeStopData pokeStopData = dataHolder.get(PokeStopData.class).orElse(null);
		return Optional.ofNullable(pokeStopData);
	}
	
	@Override
	public Optional<PokeStopData> fill(DataHolder dataHolder, MergeFunction overlap) {
		PokeStopData pokeStopData = overlap.merge(this, dataHolder.get(PokeStopData.class).orElse(null));
		return Optional.ofNullable(pokeStopData);
	}

	@Override
	public Optional<PokeStopData> from(DataContainer container) {
		
		if(container.contains(PokeStopKeys.PICKUP_DATA.getQuery(), PokeStopKeys.INITIAL_LOCATION.getQuery())){
			final Map<UUID, Long> data = (Map<UUID, Long>) container.getMap(PokeStopKeys.PICKUP_DATA.getQuery()).get();
			final Vector3i uses = (Vector3i) container.get(PokeStopKeys.INITIAL_LOCATION.getQuery()).get();

			setData(data);
			setInitialLocation(uses);
			
			return Optional.of(this);
		}
		
		return Optional.empty();
	}

	@Override
	public PokeStopData copy() {
		return new PokeStopData(getData(), getInitialLocation());
	}

	@Override
	public ImmutablePokestopData asImmutable() {
		return new ImmutablePokestopData(data, initialLocation);
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
