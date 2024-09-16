package com.archiving.archivingTool.model;

public class Overview {

    int Active ;
    int Total;
    int Completed;
    int Failed;
    int Terminated;
    int  Did_not_Start;
    int Suspended;

    public Overview(int Active, int Total, int Completed, int Failed, int Terminated, int Did_not_Start, int Suspended) {
        this.Active = Active;
        this.Total = Total;
        this.Completed = Completed;
        this.Failed = Failed;
        this.Terminated = Terminated;
        this.Did_not_Start = Did_not_Start;
        this.Suspended = Suspended;
    }

    public int getActive() {
        return Active;
    }

    public void setActive(int Active) {
        this.Active = Active;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int Total) {
        this.Total = Total;
    }

    public int getCompleted() {
        return Completed;
    }

    public void setCompleted(int Completed) {
        this.Completed = Completed;
    }

    public int getFailed() {
        return Failed;
    }

    public void setFailed(int Failed) {
        this.Failed = Failed;
    }

    public int getTerminated() {
        return Terminated;
    }

    public void setTerminated(int Terminated) {
        this.Terminated = Terminated;
    }

    public int getDid_not_Start() {
        return Did_not_Start;
    }

    public void setDid_not_Start(int Did_not_Start) {
        this.Did_not_Start = Did_not_Start;
    }

    public int getSuspended() {
        return Suspended;
    }

    public void setSuspended(int Suspended) {
        this.Suspended = Suspended;
    }
}
