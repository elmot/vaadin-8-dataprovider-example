package brp.data;

public class StuffFilter {
    private boolean includes;
    private String fragment;

    public StuffFilter() {
        this.includes = false;
        this.fragment = "";
    }

    public boolean isIncludes() {
        return includes;
    }

    public void setIncludes(boolean includes) {
        this.includes = includes;
    }

    public String getFragment() {
        return fragment;
    }

    public void setFragment(String fragment) {
        this.fragment = fragment;
    }
}
