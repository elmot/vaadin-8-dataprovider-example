package brp.data;

import com.vaadin.data.provider.AbstractDataProvider;
import com.vaadin.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.data.provider.Query;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExampleDataProvider extends AbstractDataProvider<SomeStuff, Void>
        implements ConfigurableFilterDataProvider<SomeStuff, Void, StuffFilter> {

    private final List<SomeStuff> srcList;
    private List<SomeStuff> filteredList;

    public ExampleDataProvider(List<SomeStuff> list) {
        this.srcList = list;
        this.filteredList = list;
    }

    @Override
    public void setFilter(StuffFilter filter) {
        if (filter == null) {
            filteredList = srcList;
        } else {
            Predicate<SomeStuff> predicate;
            if (filter.isIncludes()) {
                predicate = stuff -> stuff.getName().contains(filter.getFragment());
            } else {
                predicate = stuff -> stuff.getName().startsWith(filter.getFragment());
            }
            filteredList = srcList.stream().filter(predicate).collect(Collectors.toList());
        }
        refreshAll();
    }

    @Override
    public boolean isInMemory() {
        return false;
    }

    @Override
    public int size(Query<SomeStuff, Void> query) {
        return Math.min(filteredList.size() - query.getOffset(), query.getLimit());
    }

    @Override
    public Stream<SomeStuff> fetch(Query<SomeStuff, Void> query) {
        return filteredList.stream().skip(query.getOffset()).limit(query.getLimit());
    }
}
