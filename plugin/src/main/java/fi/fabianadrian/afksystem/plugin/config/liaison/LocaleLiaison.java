package fi.fabianadrian.afksystem.plugin.config.liaison;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import space.arim.dazzleconf.LoadResult;
import space.arim.dazzleconf.engine.DeserializeInput;
import space.arim.dazzleconf.engine.SerializeDeserialize;
import space.arim.dazzleconf.engine.SerializeOutput;
import space.arim.dazzleconf.engine.TypeLiaison;
import space.arim.dazzleconf.reflect.TypeToken;

import java.util.Locale;

public final class LocaleLiaison implements TypeLiaison {
	@Override
	public @Nullable <V> Agent<V> makeAgent(@NonNull TypeToken<V> typeToken, @NonNull Handshake handshake) {
		return Agent.matchOnToken(typeToken, Locale.class, LocaleAgent::new);
	}

	private static class LocaleAgent implements Agent<Locale> {
		@Override
		public @NonNull SerializeDeserialize<Locale> makeSerializer() {
			return new SerializeDeserialize<>() {

				@Override
				public @NonNull LoadResult<Locale> deserialize(@NonNull DeserializeInput deser) {
					LoadResult<String> result = deser.requireString();
					if (result.isFailure()) {
						return LoadResult.failure(result.getErrorContexts());
					}
					String value = result.getOrThrow();
					if (value.isBlank()) {
						return LoadResult.of(Locale.getDefault());
					}
					return LoadResult.of(Locale.forLanguageTag(value));
				}

				@Override
				public void serialize(@NonNull Locale value, @NonNull SerializeOutput ser) {
					ser.outString(value.toLanguageTag());
				}
			};
		}
	}
}
