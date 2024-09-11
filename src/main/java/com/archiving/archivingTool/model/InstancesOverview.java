package com.archiving.archivingTool.model;

public class InstancesOverview {

    String active ;
    String total;
    String completed;
    String failed;
    String terminated;

    public InstancesOverview(String active, String total, String completed, String failed, String terminated) {
        this.active = active;
        this.total = total;
        this.completed = completed;
        this.failed = failed;
        this.terminated = terminated;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }

    public String getFailed() {
        return failed;
    }

    public void setFailed(String failed) {
        this.failed = failed;
    }

    public String getTerminated() {
        return terminated;
    }

    public void setTerminated(String terminated) {
        this.terminated = terminated;
    }

    @Override
    public String toString() {
        return "InstancesOverview{" +
                "active='" + active + '\'' +
                ", total='" + total + '\'' +
                ", completed='" + completed + '\'' +
                ", failed='" + failed + '\'' +
                ", terminated='" + terminated + '\'' +
                '}';
    }
}
