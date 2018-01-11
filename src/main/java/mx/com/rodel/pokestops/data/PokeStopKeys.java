package mx.com.rodel.pokestops.data;

import java.util.Map;
import java.util.UUID;

import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.key.KeyFactory;
import org.spongepowered.api.data.value.mutable.MapValue;
import org.spongepowered.api.data.value.mutable.Value;

import com.flowpowered.math.vector.Vector3i;
import com.google.common.reflect.TypeToken;

public class PokeStopKeys {
	public static Key<MapValue<UUID, Long>> PICKUP_DATA = KeyFactory.makeMapKey(new TypeToken<Map<UUID, Long>>() {}, 
			new TypeToken<MapValue<UUID, Long>>() {}, DataQuery.of("PSPickUpData"), "pokestops:pspickupdata", "PS PickUp Data");
	// The world its getted from the current position
	public static Key<Value<Vector3i>> INITIAL_LOCATION = KeyFactory.makeSingleKey(TypeToken.of(Vector3i.class), 
			new TypeToken<Value<Vector3i>>() {}, DataQuery.of("PSPickUpData"), "pokestops:pspickupdata", "PS PickUp Data");
}
