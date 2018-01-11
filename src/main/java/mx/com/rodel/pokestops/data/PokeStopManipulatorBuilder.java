package mx.com.rodel.pokestops.data;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;

import com.flowpowered.math.vector.Vector3i;

public class PokeStopManipulatorBuilder extends AbstractDataBuilder<PokeStopData> implements DataManipulatorBuilder<PokeStopData, ImmutablePokestopData>{
	public PokeStopManipulatorBuilder(Class<PokeStopData> requiredClass, int supportedVersion) {
		super(requiredClass, supportedVersion);
	}
	
	@Override
	public PokeStopData create() {
		return new PokeStopData();
	}
	
	@Override
	public Optional<PokeStopData> createFrom(DataHolder dataHolder) {
		return Optional.of(dataHolder.get(PokeStopData.class).orElse(new PokeStopData()));
	}
	
	@Override
	protected Optional<PokeStopData> buildContent(DataView container) throws InvalidDataException {
		if(container.contains(PokeStopKeys.PICKUP_DATA.getQuery(), PokeStopKeys.INITIAL_LOCATION.getQuery())){
			final Map<UUID, Long> data = (Map<UUID, Long>) container.getMap(PokeStopKeys.PICKUP_DATA.getQuery()).get();
			final Vector3i initialLocation = (Vector3i) container.get(PokeStopKeys.INITIAL_LOCATION.getQuery()).get();
			
			return Optional.of(new PokeStopData(data, initialLocation));
		}
		return Optional.empty();
	}
}
