package brp;

import brp.data.ExampleDataProvider;
import brp.data.SomeStuff;
import brp.data.StuffFilter;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Binder;
import com.vaadin.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

import javax.servlet.annotation.WebServlet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * This UI is the application entry point. A UI may either represent a browser window
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
@Push
public class MyUI1 extends UI {

    private ProgressBar progressBar;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        ConfigurableFilterDataProvider<SomeStuff, Void, StuffFilter> dataProvider = dataProvider();
        Grid<SomeStuff> grid = new Grid<>();
        grid.setSizeFull();
        grid.addColumn(SomeStuff::getId).setCaption("Id");
        grid.addColumn(SomeStuff::getName).setCaption("Name");
        TextField textField = new TextField("Q:");
        CheckBox includesCB = new CheckBox("Includes");
        Binder<StuffFilter> stuffFilterBinder = new Binder<>();
        stuffFilterBinder.bind(textField, StuffFilter::getFragment, StuffFilter::setFragment);
        stuffFilterBinder.bind(includesCB, StuffFilter::isIncludes, StuffFilter::setIncludes);
        stuffFilterBinder.setBean(new StuffFilter());
        stuffFilterBinder.addValueChangeListener(e -> dataProvider.setFilter(stuffFilterBinder.getBean()));

        GridLayout layout = new GridLayout(2, 3);
        layout.setSpacing(true);
        layout.setMargin(true);
        layout.setSizeFull();
        layout.setRowExpandRatio(2, 1);
        layout.addComponent(textField, 0, 0);
        layout.addComponent(includesCB, 1, 0);
        progressBar = new ProgressBar();
        progressBar.setIndeterminate(true);
        layout.addComponent(progressBar,0,1,1,1);
        layout.addComponent(grid, 0, 2, 1, 2);
        setContent(layout);
        new Thread(() -> {
            try {
                Thread.sleep(5000);
                this.access(() ->{
                    grid.setDataProvider(dataProvider);
                    progressBar.setVisible(false);
                });


            } catch (InterruptedException ignored) {
            }

        }).start();
    }

    private ConfigurableFilterDataProvider<SomeStuff, Void, StuffFilter> dataProvider() {
        ArrayList<SomeStuff> someStuffs = loadData();
        return new ExampleDataProvider(someStuffs);
    }

    private ArrayList<SomeStuff> loadData() {
        ArrayList<SomeStuff> someStuffs = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(MyUI.class.getResourceAsStream("/brp/words_alpha.txt")))) {
            String s;
            for (int i = 1; (s = reader.readLine()) != null; i++) {
                someStuffs.add(new SomeStuff(i, s));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return someStuffs;
    }

    @WebServlet(urlPatterns = "2/*", name = "MyUIServlet1", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI1.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
