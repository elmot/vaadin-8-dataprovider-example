package brp;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.vaadin.addon.elmot.fluent.Fluent;

/**
 * This UI is the application entry point. A UI may either represent a browser window
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Grid<String> grid = new Grid<>();
        grid.setSizeFull();
        grid.addColumn(String::toString).setCaption("Text");
        grid.addColumn(String::hashCode).setCaption("Hash");
        grid.addColumn(String::length).setCaption("length");
        grid.setItems("Moses", "said", "let", "my", "people", "go");
        VerticalLayout layout = (VerticalLayout) Fluent.vLayout()
                .sizeFull()
                .add(
                Fluent.panel(
                        Fluent.hLayout()
                                .styles(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING)
                                .addAll(
                                        new Label("Label. Just Label."),
                                        new Label("shrt"),
                                        new Label("Of Moderate Size"),
                                        new Label("Label too"),
                                        new Label("One more Label"),
                                        new Label("Yet Another Label"),
                                        new Label("This is a Label with pretty long Enlish text inside"),
                                        new Label("This is a Label with loooooooooooooooooooooooooooooong text")
                                ).get())
                        .styles(ValoTheme.PANEL_WELL), 0).add(
                grid, 1000
        ).get();
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
