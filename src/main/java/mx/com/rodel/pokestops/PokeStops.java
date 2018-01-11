package mx.com.rodel.pokestops;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.data.DataRegistration;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.entity.spawn.SpawnCause;
import org.spongepowered.api.event.cause.entity.spawn.SpawnTypes;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.google.common.collect.Maps;
import com.google.inject.Inject;

import mx.com.rodel.pokestops.data.ImmutablePokestopData;
import mx.com.rodel.pokestops.data.PokeStopData;
import mx.com.rodel.pokestops.data.PokeStopManipulatorBuilder;

@Plugin(id = "pokestops", authors = "rodel77", description = "PokeStops in your sponge server!", version = PluginInfo.VERSION, name="PokeStops")
public class PokeStops {
	
	@Inject
	private PluginContainer container;
	
	@Listener
	public void onPreInitialization(GamePreInitializationEvent e){
		DataRegistration.<PokeStopData, ImmutablePokestopData>builder()
		.dataClass(PokeStopData.class)
		.immutableClass(ImmutablePokestopData.class)
		.manipulatorId("pokestops")
		.dataName("PokeStops Data")
		.builder(new PokeStopManipulatorBuilder(PokeStopData.class, 1))
		.buildAndRegister(container);
		
		Sponge.getCommandManager().register(this, new CommandCallable() {
			
			@Override
			public boolean testPermission(CommandSource source) {
				return false;
			}
			
			@Override
			public CommandResult process(CommandSource source, String arguments) throws CommandException {
				Player player = (Player) source;
				Map<UUID, Long> data = Maps.newHashMap();
				
				data.put(player.getUniqueId(), System.currentTimeMillis());
				
				Entity armor = player.getWorld().createEntity(EntityTypes.CREEPER, player.getLocation().getPosition());
				armor.offer(new PokeStopData(data, player.getLocation().getBlockPosition()));
				SpawnCause cause = SpawnCause.builder().type(SpawnTypes.PLUGIN).build();
				player.getWorld().spawnEntity(armor, Cause.source(cause).build());
				return CommandResult.success();
			}
			
			@Override
			public Text getUsage(CommandSource source) {
				return null;
			}
			
			@Override
			public List<String> getSuggestions(CommandSource source, String arguments, Location<World> targetPosition)
					throws CommandException {
				return null;
			}
			
			@Override
			public Optional<Text> getShortDescription(CommandSource source) {
				return null;
			}
			
			@Override
			public Optional<Text> getHelp(CommandSource source) {
				return null;
			}
		}, "foobar");
	}
}
