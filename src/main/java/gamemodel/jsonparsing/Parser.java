package gamemodel.jsonparsing;

import java.util.List;

@FunctionalInterface
public interface Parser<T> {
	public List<T>pars(String json);
}
