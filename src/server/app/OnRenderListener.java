package server.app;

public interface OnRenderListener {
    void beforeRender(String template);
    void onRender(String template);
    void afterRender(String template,String rendered);
}
