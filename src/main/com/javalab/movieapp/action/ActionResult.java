package com.javalab.movieapp.action;

public class ActionResult {
    String path;
    boolean isRedirect;

    public ActionResult(String path, boolean isRedirect) {
        this.path = path;
        this.isRedirect = isRedirect;
    }

    public ActionResult() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isRedirect() {
        return isRedirect;
    }

    public void setRedirect(boolean redirect) {
        isRedirect = redirect;
    }
}
