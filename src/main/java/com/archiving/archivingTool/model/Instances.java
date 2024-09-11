package com.archiving.archivingTool.model;

import java.util.ArrayList;

public class Instances {
    InstancesOverview instancesOverview ;

    ArrayList<InstanceInformation> instanceInformation;

    public Instances(InstancesOverview instancesOverview, ArrayList<InstanceInformation> instanceInformation) {
        this.instancesOverview = instancesOverview;
        this.instanceInformation = instanceInformation;
    }

    public InstancesOverview getInstancesOverview() {
        return instancesOverview;
    }

    public void setInstancesOverview(InstancesOverview instancesOverview) {
        this.instancesOverview = instancesOverview;
    }

    public ArrayList<InstanceInformation> getInstanceInformation() {
        return instanceInformation;
    }

    public void setInstanceInformation(ArrayList<InstanceInformation> instanceInformation) {
        this.instanceInformation = instanceInformation;
    }

    @Override
    public String toString() {
        return "Instances{" +
                "instancesOverview=" + instancesOverview +
                ", instanceInformation=" + instanceInformation +
                '}';
    }
}
